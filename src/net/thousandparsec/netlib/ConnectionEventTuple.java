package net.thousandparsec.netlib;

/**
 * A container for {@link ConnectionEvent} and the time when it occurred, in
 * standard 'long' form. Class originally designed for
 * {@link LoggerConnectionListener}.
 * 
 * @author Victor Ivri
 */
public class ConnectionEventTuple<V extends Visitor>
{
	private final ConnectionEvent<V> conEvent;
	private final long time; //in milliseconds from Epoch
	
	/**
	 * Constructor for ConnectionEventTuple.
	 * 
	 * @param event {@link ConnectionEvent}
	 */
	public ConnectionEventTuple(ConnectionEvent<V> event) 
	{
		time = System.currentTimeMillis();
		conEvent = event;
	}
	
	/**
	 * @return time in milliseconds from Epoch. Same as in {@link System}.getCurrentTimeMillis()
	 */
	public long getTime()
	{
		return time;
	}
	
	/**
	 * @return the reference object {@link Frame}<Visitor> of the {@link ConnectionEvent}.
	 * Note that the reference is of the object directly, and not of a copy of it,
	 * thus there may be situations where it is not safe.
	 */
	public Frame<V> getFrame()
	{
		return this.conEvent.getFrame();
	}
	
	/**
	 * @return the {@link ConnectionEvent.Type} of the {@link ConnectionEvent}.
	 */
	public ConnectionEvent.Type getType()
	{
		return conEvent.getType();
	}
	
	/**
	 * @return true if the {@link ConnectionEvent} is asynchronous; false otherwise.
	 */
	public boolean getIsAsynch()
	{
		return conEvent.isAsync();
	}
	
	/**
	 * @return a reference to the {@link Exception} object of the {@link ConnectionEvent}, or null if none exists.
	 */
	public Exception getException()
	{
		return conEvent.getException();
	}
	
}