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
public class SimpleSequentialConnection<V extends Visitor> implements SequentialConnection<V>
{
	private final Connection<V> conn;
	private int lastSequence;

	/**
	 * Creates a new {@link SimpleSequentialConnection} that wraps the given
	 * {@link Connection}.
	 * 
	 * @param conn
	 *            the underlying {@link Connection}
	 */
	public SimpleSequentialConnection(Connection<V> conn)
	{
		this.conn=conn;
	}

	public Connection<V> unwrap()
	{
		return conn;
	}

	public <F extends Frame<V>> F receiveFrame(Class<F> expectedClass) throws EOFException, IOException, TPException
	{
		Frame<V> frame=conn.receiveFrame();
		if (frame == null)
			throw new EOFException();
		else if (lastSequence != 0 && frame.getSequenceNumber() != lastSequence)
			throw new TPException(String.format("Response frame sequence %s does not match expected frame sequence %s", frame.getSequenceNumber(), lastSequence));
		else if (!expectedClass.isInstance(frame))
			throw new TPException(String.format("Unexpected frame: type %d (%s) while expecting %s", frame.getFrameType(), frame.toString(), expectedClass.getSimpleName()));
		else
			return expectedClass.cast(frame);
	}

	public <F extends Frame<V>> F sendFrame(Frame<V> frame, Class<F> responseClass) throws IOException, TPException
	{
		conn.sendFrame(frame);
		lastSequence=frame.getSequenceNumber();
		return receiveFrame(responseClass);
	}

	public void sendFrame(Frame<V> frame, V responseVisitor) throws IOException, TPException
	{
		conn.sendFrame(frame);
		lastSequence=frame.getSequenceNumber();
		Frame<V> response=conn.receiveFrame();
		if (response.getSequenceNumber() != frame.getSequenceNumber())
			throw new TPException(String.format("Response frame sequence %s does not match request frame sequence %s", response.getSequenceNumber(), frame.getSequenceNumber()));
		response.visit(responseVisitor);
	}

	public void close() throws IOException
	{
		conn.close();
	}
}
