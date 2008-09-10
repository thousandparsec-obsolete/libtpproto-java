package net.thousandparsec.netlib;

import java.io.EOFException;
import java.io.IOException;

/**
 * This is an interface for {@link Connection} wrappers that expose a sequential
 * mode of communication with the server: the client sends a request and
 * synchronously waits for a response, which is later returned to the client or
 * sent to a {@link Visitor}. The client can also specify the response frame's
 * expected type; the expected sequence number can be (and is required to be)
 * checked automatically.
 * <p>
 * The {@link Connection} class doesn't implement this interface, because
 * sequential methods are mostly incompatible with other modes of communication
 * and so the interface is separate.
 * <p>
 * You can get the underlying {@link Connection} if required via
 * {@link #getConnection()} method.
 * 
 * @author ksobolewski
 */
public interface SequentialConnection<V extends Visitor>
{
	/**
	 * Returns the underlying {@link Connection}.
	 * 
	 * @return the underlying {@link Connection}
	 */
	Connection<V> getConnection();

	/**
	 * Synchronously reads (and returns) next {@link Frame} from this
	 * connection, but only of one, expected type. It will
	 * block if the frame is not immediately available. Will throw
	 * {@link EOFException} if there are no more frames to read. Will throw
	 * {@link TPException} if the frame received is not of the expected type.
	 * 
	 * @return next {@link Frame}
	 * @param expectedClass
	 *            the {@link Class} of the expected response frame
	 * @throws EOFException
	 *             if the connection is closed in the middle of frame or
	 *             there are no more frames to read
	 * @throws IOException
	 *             on any other I/O error
	 * @throws TPException
	 *             if an unexpected frame type is received or on other TP
	 *             protocol error
	 */
	<F extends Frame<V>> F receiveFrame(Class<F> expectedClass) throws EOFException, IOException, TPException;

	/**
	 * Synchronously sends a {@link Frame} to the server via this connection and
	 * returns a response if it is of a specified, expected type. This is a
	 * simple composition of {@link Connection#sendFrame(Frame)} and
	 * {@link #receiveFrame(Class)}, inheriting exceptional conditions and
	 * return values of the latter. Also, see comments in
	 * {@link #sendFrame(Frame, Visitor)}.
	 * 
	 * @see #receiveFrame(Class)
	 * @param frame
	 *            the {@link Frame} to send
	 * @param responseClass
	 *            the {@link Class} of the expected response frame
	 * @throws IOException
	 *             on any I/O error
	 * @throws TPException
	 *             thrown by the {@link Visitor}'s handler methods
	 */
	<F extends Frame<V>> F sendFrame(Frame<V> frame, Class<F> responseClass) throws IOException, TPException;

	/**
	 * Synchronously sends a {@link Frame} to the server via this connection and
	 * sends a response to the specified {@link Visitor}. Note that what this
	 * method does is very dumb: it simply waits for next frame from the server
	 * (in this sequence), so if the frame sent does not expect a response, you
	 * get stuck, and if the response consists of more than one frame, this will
	 * only handle the first one.
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
	void sendFrame(Frame<V> frame, V responseVisitor) throws IOException, TPException;

	/**
	 * Closes this connection.
	 * 
	 * @throws IOException
	 *             on any I/O error
	 */
	void close() throws IOException;
}
