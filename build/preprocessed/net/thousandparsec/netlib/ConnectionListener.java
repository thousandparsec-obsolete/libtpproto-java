package net.thousandparsec.netlib;

/**
 * This is an interface for parties interested in connection events, like frame
 * traffic and connection errors. Warning! this interface is intended for
 * logging purposes only; the frame traffic listener methods receive an entire
 * {@link Frame} and are not supposed to change its contents! (would you like to
 * have a {@code const} keyword in Java? :))
 * <p>
 * TODO: implementation for common logging facilities?
 * 
 * @author ksobolewski
 */
public interface ConnectionListener
{
	/**
	 * Called when the conection being observed had just sent a frame.
	 * 
	 * @param ev
	 *            the event containing the {@link Frame} sent
	 */
	//void frameSent(ConnectionEvent<V> ev);
        void frameSent(ConnectionEvent ev);

	/**
	 * Called when the conection being observed had just received a frame.
	 * 
	 * @param ev
	 *            the event containing the {@link Frame} received and a flag
	 *            indicating if the frame was an asynchronous frame
	 */
	//void frameReceived(ConnectionEvent<V> ev);
        void frameReceived(ConnectionEvent ev);

	/**
	 * Called when a connection error occurs; the exception passed can be of any
	 * type. Depending on type of error and the implementation, the connection
	 * might become unusable or not.
	 * 
	 * @param ev
	 *            the event containing the {@link Exception} that caused the
	 *            connection error and (if possible) the errored {@link Frame}
	 */
	//void connectionError(ConnectionEvent<V> ev);
        void connectionError(ConnectionEvent ev);
}
