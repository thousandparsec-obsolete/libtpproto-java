package net.thousandparsec.netlib;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This wrapper around {@link Connection} implements a functionality exposing
 * the pipelined nature of Thousand Parsec protocol. This class "slices" the
 * underlying connection into sequential "views" (pipelines) that are logically
 * sequential, but can coexist on a common "wire" (a low-level
 * {@link Connection}). In other words, this class makes
 * {@link SequentialConnection} thread safe.
 * <p>
 * The typical use case is that several threads use this class to sequentially
 * send and receive frames on a common connection. This class spawns a "receiver
 * task", which reads incoming frames in inother thread and distributes them
 * between pipelines. Those pipelines have incoming queues of their own; these
 * queues are blockin queues and have a limited capacity and if any of the
 * threads stops processing its frames, the entire connection blocks after this
 * thread's queue fills up.
 * <p>
 * The asynchronous frames sent by the server are still handled by the
 * underlying {@link Connection}, but this class makes them really asynchronous -
 * while with the low-level connection there needs to be an interaction to read
 * any pending asynchronous frames, with this class the asynchronous frames are
 * processed as soon as they appear in the incoming queue.
 * <p>
 * The errors, like unexpected frame or a response frame for unregistered
 * sequence number, are received by the underlying {@link Connection}'s
 * listeners.
 * 
 * @author ksobolewski
 */
public class PipelinedConnection<V extends Visitor>
{
	public static final int DEFAULT_QUEUE_DEPTH=32;

	private final Connection<V> conn;
	private final Map<Integer, BlockingQueue<Frame<V>>> pipelines;
	private final ExecutorService exec;
	private final Future<Void> receiverFuture;

	/**
	 * Creates a new {@link PipelinedConnection} that wraps the given
	 * {@link Connection}.
	 * 
	 * @param conn
	 *            the underlying {@link Connection}
	 */
	public PipelinedConnection(Connection<V> conn)
	{
		this.conn=conn;
		//we need something stronger that ConcurrentMap
		this.pipelines=Collections.synchronizedMap(new HashMap<Integer, BlockingQueue<Frame<V>>>());
		this.exec=Executors.newSingleThreadExecutor();
		this.receiverFuture=exec.submit(new ReceiverTask());
		//it is safe to do here, per shutdown() contract: it waits for tasks to finish, and then shuts down
		exec.shutdown();
	}

	/**
	 * Returns a synchronized map, as in
	 * {@link Collections#synchronizedMap(Map)}, that maps from a pipeline's
	 * sequence number to this pipeline's {@link BlockingQueue} of {@link Frame}s.
	 * All methods of this map will be synchronized using the map itself as a
	 * monitor; a larger critical section can use it too as a monitor.
	 * 
	 * @return a map from sequence number to a pipeline's {@link BlockingQueue}
	 */
	Map<Integer, BlockingQueue<Frame<V>>> getPipelineQueues()
	{
		return pipelines;
	}

	/**
	 * Returns the underlying {@link Connection}.
	 * 
	 * @return the underlying {@link Connection}
	 */
	public Connection<V> getConnection()
	{
		return conn;
	}

	/**
	 * This is a sequential view of this {@link PipelinedConnection},
	 * implementing the {@link SequentialConnection} interface. The pipeline
	 * starts without a squential number assigned; each frame sent causes this
	 * {@link PipelinedConnection} to register this pipeline with a new
	 * sequential number and all incoming frames with this number are routed to
	 * this pipeline. Subsequent outgoing frames cause this (old) assignment to
	 * be removed.
	 * 
	 * @return a new sequential view of this {@link PipelinedConnection}
	 */
	public SequentialConnection<V> createPipeline()
	{
		return new Pipeline();
	}

	/**
	 * Closes the underlying {@link Connection} and shuts down asynchronous
	 * receiver.
	 * 
	 * @throws IOException
	 *             on any I/O error
	 * @throws ExecutionException
	 *             if the receiver task fails
	 * @throws InterruptedException
	 *             if the current thread was interrupted while waiting for
	 *             receiver task
	 */
	public void close() throws IOException, InterruptedException, ExecutionException
	{
		try
		{
			//this should tell receiver task to stop
			conn.close();
		}
		finally
		{
			//check for errors in the receiver task
			receiverFuture.get();
			//check if the pipelines were closed properly
			if (!pipelines.keySet().isEmpty())
				throw new IOException("Not all pipelines were closed properly");
		}
	}

	private class Pipeline implements SequentialConnection<V>
	{
		private BlockingQueue<Frame<V>> incoming=new LinkedBlockingQueue<Frame<V>>();
		private int lastSeq=0;

		Pipeline()
		{
		}

		private void sendFrame(Frame<V> frame) throws IOException
		{
			/*
			 * Strong synchronisation is needed as there is a race condition here.
			 * The procedure:
			 * - remove this pipeline's queue using old sequence number
			 * - send the frame
			 * - update current sequence number with frame's sequence number
			 * - register the queue using the new sequence number
			 * The race exists because we don't know the new sequence number
			 * in advance and we need to get it after the frame is sent;
			 * after we do this, there is a small window before we register
			 * the new sequence number into which a response might slip
			 * and generate an "unexpected frame" error.
			 */
			Map<Integer, BlockingQueue<Frame<V>>> queues=getPipelineQueues();
			synchronized (queues)
			{
				queues.remove(lastSeq);
				getConnection().sendFrame(frame);
				lastSeq=frame.getSequenceNumber();
				queues.put(lastSeq, incoming);
			}
		}

		private Frame<V> receiveFrame() throws InterruptedException
		{
			return incoming.take();
		}

		public <F extends Frame<V>> F receiveFrame(Class<F> expectedClass) throws TPException
		{
			try
			{
				Frame<V> frame=receiveFrame();
				if (!expectedClass.isInstance(frame))
					throw new TPException(String.format("Unexpected frame: type %d (%s) while expecting %s", frame.getFrameType(), frame.toString(), expectedClass.getSimpleName()));
				else
					return expectedClass.cast(frame);
			}
			catch (InterruptedException ex)
			{
				throw new TPException(ex);
			}
		}

		public <F extends Frame<V>> F sendFrame(Frame<V> frame, Class<F> responseClass) throws IOException, TPException
		{
			sendFrame(frame);
			return receiveFrame(responseClass);
		}

		public void sendFrame(Frame<V> frame, V responseVisitor) throws IOException, TPException
		{
			try
			{
				sendFrame(frame);
				receiveFrame().visit(responseVisitor);
			}
			catch (InterruptedException ex)
			{
				throw new TPException(ex);
			}
		}

		/**
		 * Unregisters this pipeline from frame distribution in this
		 * {@link PipelinedConnection}.
		 */
		public void close()
		{
			getPipelineQueues().remove(lastSeq);
		}

		public Connection<V> getConnection()
		{
			return PipelinedConnection.this.getConnection();
		}
	}

	private class ReceiverTask implements Callable<Void>	
	{
		ReceiverTask()
		{
		}

		public Void call() throws Exception
		{
			try
			{
				Connection<V> conn=getConnection();
				Frame<V> frame;
				while (true)
					try
					{
						if ((frame=conn.receiveFrame()) == null)
							break;
						else
						{
							BlockingQueue<Frame<V>> queue=getPipelineQueues().get(frame.getSequenceNumber());
							if (queue == null)
								getConnection().fireErrorEvent(frame, new TPException(String.format("Unexpected frame: seq %d, type %d (%s)", frame.getSequenceNumber(), frame.getFrameType(), frame.toString())));
							else
								queue.put(frame);
						}
					}
					//TPException is a protocol error - try to continue
					catch (TPException ex)
					{
						getConnection().fireErrorEvent(null, ex);
					}
					//IOException is fatal - quit
				return null;
			}
			catch (Exception ex)
			{
				getConnection().fireErrorEvent(null, ex);
				throw ex;
			}
			finally
			{
				try {getConnection().close();} catch (IOException ignore) {}
			}
		}
	}
}
