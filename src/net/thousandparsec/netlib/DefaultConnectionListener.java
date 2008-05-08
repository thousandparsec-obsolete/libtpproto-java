package net.thousandparsec.netlib;

/**
 * A {@link ConnectionListener} that prints messages to standard error output.
 * 
 * @author ksobolewski
 */
public class DefaultConnectionListener implements ConnectionListener
{
	private final boolean printSent;
	private final boolean printReceived;
	private final boolean printReceivedAsync;
	private final boolean printError;

	public DefaultConnectionListener(boolean printSent, boolean printReceived, boolean printReceivedAsync, boolean printError)
	{
		this.printSent=printSent;
		this.printReceived=printReceived;
		this.printReceivedAsync=printReceivedAsync;
		this.printError=printError;
	}

	public DefaultConnectionListener()
	{
		this(true, true, true, true);
	}

        public void frameSent(ConnectionEvent ev)
	{
		if (printSent)
		{
			Frame frame=ev.getFrame();
			System.err.printf("%s: Sent frame seq %d, type %d (%s)%n", getClass().getName(), frame.getSequenceNumber(), frame.getFrameType(), frame.toString());
		}
	}

        public void frameReceived(ConnectionEvent ev)
	{
                Frame frame=ev.getFrame();
		if (ev.isAsync())
		{
			if (printReceivedAsync)
				System.err.printf("%s: Received asynchronous frame seq %d, type %d (%s)%n", getClass().getName(), frame.getSequenceNumber(), frame.getFrameType(), frame.toString());
		}
		else
		{
			if (printReceived)
				System.err.printf("%s: Received frame seq %d, type %d (%s)%n", getClass().getName(), frame.getSequenceNumber(), frame.getFrameType(), frame.toString());
		}
	}

	public void connectionError(ConnectionEvent ev)
	{
		if (printError)
		{
			System.err.println("Connection error:");
			ev.getException().printStackTrace(System.err);
		}
	}
}
