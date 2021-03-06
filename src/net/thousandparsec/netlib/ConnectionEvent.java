package net.thousandparsec.netlib;

/**
 * An aggregate of values for {@link Connection} events, in the spirit of
 * Swing's event model.
 * 
 * @author ksobolewski
 */
public class ConnectionEvent<V extends Visitor>
{
	public static enum Type
	{
		FRAME_SENT,
		FRAME_RECEIVED,
		CONNECTION_ERROR;
	}

	private final Type type;
	private final Frame<V> frame;
	private final boolean isAsync;
	private final Exception exception;

	ConnectionEvent(Type type, Frame<V> frame, boolean isAsync, Exception exception)
	{
		this.type=type;
		this.frame=frame;
		this.isAsync=isAsync;
		this.exception=exception;
	}

	public Type getType()
	{
		return type;
	}

	public Frame<V> getFrame()
	{
		return frame;
	}

	public boolean isAsync()
	{
		return isAsync;
	}

	public Exception getException()
	{
		return exception;
	}
}
