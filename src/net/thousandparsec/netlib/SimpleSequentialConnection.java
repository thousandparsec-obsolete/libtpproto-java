package net.thousandparsec.netlib;

import java.io.EOFException;
import java.io.IOException;

/**
 * This class is a simple wrapper for {@link Connection} that exposes a
 * sequential mode of communication with the server (see
 * {@link SequentialConnection}). This class additionally checks the responses
 * for expected sequence number, which is equal to last sent frame's sequence
 * number.
 * <p>
 * Because this is a very simple wrapper, other modes can interfere with this
 * connection's behaviour and you should avoid parallel use of the same
 * underlying connection when this class is in use; this also implies that this
 * class is not thread-safe.
 * 
 * @author ksobolewski
 */
public class SimpleSequentialConnection implements SequentialConnection
{
	private final Connection conn;
	private int lastSequence;

	/**
	 * Creates a new {@link SimpleSequentialConnection} that wraps the given
	 * {@link Connection}.
	 * 
	 * @param conn
	 *            the underlying {@link Connection}
	 */
	public SimpleSequentialConnection(Connection conn)
	{
		this.conn=conn;
	}

	public Connection getConnection()
	{
		return conn;
	}

	public Frame receiveFrame(Class expectedClass) throws EOFException, IOException, TPException
	{
		
                Frame frame=conn.receiveFrame();
                System.out.println("DEBUG - EXPECTED FRAME TYPE: " + expectedClass.getName());
                System.out.println("DEBUG - FRAME TYPE RECEIVED: " + frame.getFrameType());
		if (frame == null)
			throw new EOFException();
		else if (lastSequence != 0 && frame.getSequenceNumber() != lastSequence)
			throw new TPException("Response frame sequence " + frame.getSequenceNumber() + " does not match expected frame sequence " + lastSequence);
		else if (!expectedClass.isInstance(frame))
			throw new TPException("Unexpected frame: type " + frame.getFrameType() +" (" + frame.toString() + ") while expecting" + expectedClass.getName());
		else

                    //return null;
                        return frame;
                        //return expectedClass.cast(frame);
                        //return (expectedClass.getName())frame;
	}

	public Frame sendFrame(Frame frame, Class responseClass) throws IOException, TPException
	{
		conn.sendFrame(frame);
		lastSequence=frame.getSequenceNumber();
		return receiveFrame(responseClass);
	}

	public void sendFrame(Frame frame, Visitor responseVisitor) throws IOException, TPException
	{
		conn.sendFrame(frame);
                lastSequence=frame.getSequenceNumber();
		Frame response=conn.receiveFrame();
		if (response.getSequenceNumber() != frame.getSequenceNumber())
			throw new TPException("Response frame sequence" + response.getSequenceNumber() + " does not match request frame sequence" + frame.getSequenceNumber());
		response.visit(responseVisitor);
	}

	public void close() throws IOException
	{
		conn.close();
	}
}
