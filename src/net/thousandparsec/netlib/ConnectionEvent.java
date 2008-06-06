package net.thousandparsec.netlib;

/**
 * An aggregate of values for {@link Connection} events, in the spirit of
 * Swing's event model.
 * 
 * @author ksobolewski
 */
public class ConnectionEvent
{
        
	static class Type
	{
		public static final int FRAME_SENT = 1;
		public static final int FRAME_RECEIVED = 2;
		public static final int CONNECTION_ERROR = 3;
	}
        /*static class Type{
            
        }*/

	//private final Type type;
        private final int type;
	private final Frame frame;
	private final boolean isAsync;
	private final Exception exception;

        //ConnectionEvent(Type type, Frame frame, boolean isAsync, Exception exception)
        ConnectionEvent(int type, Frame frame, boolean isAsync, Exception exception)
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
	public Frame getFrame()
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
