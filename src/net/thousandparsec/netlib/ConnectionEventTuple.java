package net.thousandparsec.netlib;

import java.util.Calendar;

/**
 * A tuple of CennectionEvent, the type of the connection event (FRAME_SENT, FRAME_RECEIVED, CONNECTION_ERROR), and the time when it occurred,
 * in a formatted {@link String} of the form: [hh:mm:ss].
 * Class originally designed for LoggerConnectionListener.
 * 
 * @author Victor Ivri
 */
public class ConnectionEventTuple 
{
	ConnectionEvent<Visitor> conEvent;
	String time;
	String evType;
	
	private ConnectionEventTuple(){
		//dummy constructor
	}
	
	/**
	 * Constructor for ConnectionEventTuple.
	 * 
	 * @param event {@link ConnectionEvent<Visitor>}
	 * @param type String : FRAME_SENT, FRAME_RECEIVED, CONNECTION_ERROR.
	 */
	protected ConnectionEventTuple(ConnectionEvent<Visitor> event, String type) 
	{
		time = setTime();
		conEvent = event;
		evType = type;
	}
	
	private String setTime()
	{
		String formattedTime = "[" + Calendar.HOUR + ":" + Calendar.MINUTE + ":" + Calendar.SECOND + "]";
		return formattedTime;
	}
}
