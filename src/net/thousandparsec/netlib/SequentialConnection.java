package net.thousandparsec.netlib;

import java.io.EOFException;
import java.io.IOException;

/**
 * This class is a wrapper for {@link Connection} that exposes a sequential mode
 * of communication with the server: the client sends a request and
 * synchronously waits for a response, which is later returned to the client or
 * sent to a {@link Visitor}. The client can also specify the response frame's
 * expected type and sequence number, which are both checked by this class.
 * <p>
 * This class is in a distinct class hierarchy than the {@link Connection},
 * because sequential methods are mostly incompatible with other modes on
 * communication and so the interface is separate. Because other modes can
 * interfere with this connection's behaviour, you should avoid parallel use of
 * the same underlying connection when this class is in use; this also implies
 * that the sequential connection is not thread-safe.
 * <p>
 * You can get the underlying {@link Connection} if required via
 * {@link #unwrap()} method.
 * 
 * @author ksobolewski
 */
public class SequentialConnection<V extends Visitor>
{
	private final Connection<V> conn;

	/**
	 * Creates a new {@link SequentialConnection} that wraps the given
	 * {@link Connection}.
	 * 
	 * @param conn
	 *            the underlying {@link Connection}
	 */
	public SequentialConnection(Connection<V> conn)
	{
		this.conn=conn;
	}

	/**
	 * Returns the underlying {@link Connection}.
	 * 
	 * @return the underlying {@link Connection}
	 */
	public Connection<V> unwrap()
	{
		return conn;
	}

	/**
	 * Synchronously reads (and returns) next {@link Frame} from this
	 * connection, but only of one, expected type and sequence number. It will
	 * block if the frame is not immediately available. Will throw
	 * {@link EOFException} if there are no more frames to read. Will throw
	 * {@link TPException} if the frame received is not of the expected type.
	 * 
	 * @return next {@link Frame} of {@literal null} on end of stream
	 * @param expectedClass
	 *            the {@link Class} of the expected response frame
	 * @param expectedSequence
	 *            the received frame's expected sequence number; may be 0 to
	 *            ignore sequence number
	 * @throws EOFException
	 *             if the connection is closed in the middle of frame
	 * @throws IOException
	 *             on any other I/O error
	 * @throws TPException
	 *             if an unexpected frame type is received or on other TP
	 *             protocol error
	 */
	public <F extends Frame<V>> F receiveFrame(Class<F> expectedClass, int expectedSequence) throws EOFException, IOException, TPException
	{
		Frame<V> frame=conn.receiveFrame();
		if (frame == null)
			throw new EOFException();
		else if (expectedSequence != 0 && frame.getSequenceNumber() != expectedSequence)
			throw new TPException(String.format("Response frame sequence %s does not match expected frame sequence %s", frame.getSequenceNumber(), expectedSequence));
		else if (!expectedClass.isInstance(frame))
			throw new TPException(String.format("Unexpected frame: type %d (%s)", frame.getFrameType(), frame.toString()));
		else
			return expectedClass.cast(frame);
	}

	/**
	 * Synchronously sends a {@link Frame} to the server via this connection and
	 * returns a response if it is of a specified, expected type. This is a
	 * simple composition of {@link Connection#sendFrame(Frame)} and
	 * {@link #receiveFrame(Class, int)}, inheriting exceptional conditions and
	 * return values of the latter.
	 * 
	 * @see #receiveFrame(Class, int)
	 * @param frame
	 *            the {@link Frame} to send
	 * @param responseClass
	 *            the {@link Class} of the expected response frame
	 * @throws IOException
	 *             on any I/O error
	 * @throws TPException
	 *             thrown by the {@link Visitor}'s handler methods
	 */
	public <F extends Frame<V>> F sendFrame(Frame<V> frame, Class<F> responseClass) throws IOException, TPException
	{
		conn.sendFrame(frame);
		return receiveFrame(responseClass, frame.getSequenceNumber());
	}

	/**
	 * Synchronously sends a {@link Frame} to the server via this connection and
	 * sends a response to the specified {@link Visitor}. Note that what this
	 * does is very dumb: it simply waits for next frame from the server, so if
	 * the frame sent does not expect a response, you get stuck (because the
	 * operation is synchronised and you won't be able to send anything else),
	 * and if the response consists of more than one frame (like the "Sequence"
	 * response), this will only handle the first one.
	 * 
	 * @param frame
	 *            the {@link Frame} to send
	 * @param responseVisitor
	 *            the {@link Visitor} which will be used to accept the response
	 * @throws IOException
	 *             on any I/O error
	 * @throws TPException
	 *             thrown by the {@link Visitor}'s handler methods
	 */
	public void sendFrame(Frame<V> frame, V responseVisitor) throws IOException, TPException
	{
		conn.sendFrame(frame);
		Frame<V> response=conn.receiveFrame();
		if (response.getSequenceNumber() != frame.getSequenceNumber())
			throw new TPException(String.format("Response frame sequence %s does not match request frame sequence %s", response.getSequenceNumber(), frame.getSequenceNumber()));
		response.visit(responseVisitor);
	}

	/**
	 * Closes this connection; delegates to {@link Connection#close()}.
	 * 
	 * @throws IOException
	 *             on any I/O error
	 */
	public void close() throws IOException
	{
		conn.close();
	}
}
