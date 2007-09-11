package net.thousandparsec.netlib;

/**
 * A {@link ConnectionListener} that prints messages to standard error output.
 * 
 * @author ksobolewski
 */
public class DefaultConnectionListener<V extends Visitor> implements ConnectionListener<V>
{
	public void frameSent(ConnectionEvent<V> ev)
	{
		Frame<V> frame=ev.getFrame();
		System.err.printf("%s: Sent frame seq %d, type %d (%s)%n", getClass().getName(), frame.getSequenceNumber(), frame.getFrameType(), frame.toString());
	}

	public void frameReceived(ConnectionEvent<V> ev)
	{
		Frame<V> frame=ev.getFrame();
		if (ev.isAsync())
			System.err.printf("%s: Received asynchronous frame seq %d, type %d (%s)%n", getClass().getName(), frame.getSequenceNumber(), frame.getFrameType(), frame.toString());
		else
			System.err.printf("%s: Received frame seq %d, type %d (%s)%n", getClass().getName(), frame.getSequenceNumber(), frame.getFrameType(), frame.toString());
	}

	public void connectionError(ConnectionEvent<V> ev)
	{
		System.err.println("Connection error:");
		ev.getException().printStackTrace(System.err);
	}
}
