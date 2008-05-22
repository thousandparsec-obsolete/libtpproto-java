package net.thousandparsec.netlib;

import java.util.*;

/**
 * A basic ConnectionEvent logger, that utilizes a stack structure.
 * This class is not thread safe, so either use it when connection is closed,
 * or else expand it to be thread safe.
 * 
 * @author Victor Ivri
 *
 */
public class LoggerConnectionListener implements ConnectionListener 
{

	private LinkedList<ConnectionEventTuple> connectionEventLog;
	private Iterator<ConnectionEventTuple> eventIterator;
	private Calendar sessionStart;
	private Calendar currentTime;
	
	/**
	 * Default constructor.
	 */
	public LoggerConnectionListener()
	{
		connectionEventLog = new LinkedList<ConnectionEventTuple>();
		eventIterator = connectionEventLog.iterator();
		sessionStart = Calendar.getInstance();
	}
	
	public void connectionError(ConnectionEvent ev) 
	{
		connectionEventLog.add(new ConnectionEventTuple(ev, "CONNECTION_ERROR"));
	}

	public void frameReceived(ConnectionEvent ev) 
	{
		connectionEventLog.add(new ConnectionEventTuple(ev, "FRAME_RECEIVED"));
	}

	public void frameSent(ConnectionEvent ev) 
	{
		connectionEventLog.add(new ConnectionEventTuple(ev, "FRAME_SENT"));
	}
	
	public ConnectionEvent<Visitor> getLastEvent()
	{
		return connectionEventLog.getLast().conEvent;
	}
	
	public ConnectionEvent<Visitor> getFirstEvent()
	{
		return connectionEventLog.getFirst().conEvent;
	}
	
	/**
	 * 
	 * @return {@link Iterator} for the log of events. Returns {@link ConnectionEventTuple} in FIFO manner.
	 */
	public Iterator<ConnectionEventTuple> getLogIterator()
	{
		return eventIterator;
	}
	
	/**
	 * Prints the connection event log to standard-out.
	 *
	 */
	public void printLog()
	{
		pl("Printing Connection Event Log:");
		pl("Session started (DAY/MONTH/YEAR | HOUR:MINUTE:SECOND ):  " + formatTime(sessionStart));

		setCurrentTime();
		pl("Current time (DAY/MONTH/YEAR | HOUR:MINUTE:SECOND ):  " + formatTime(currentTime));
		
		pl("The log: (first to last)");

		for (ConnectionEventTuple cet : connectionEventLog)
		{
			ConnectionEvent<Visitor> ce = cet.conEvent;
			pl(cet.time + " Event type: " + cet.evType + " Frame id: " + ce.getFrame().getFrameType() + 
					" Frame sequence number: " + ce.getFrame().getSequenceNumber());
		}
		
		pl("Finished printing events logged so far.");
	}
	
	private void pl(String s)
	{
		System.out.println(s);
	}
	
	private String formatTime(Calendar cal)
	{
		return cal.get(Calendar.DATE) + "/" + cal.get(Calendar.MONTH) + cal.get(Calendar.YEAR) + " | " + 
			cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE) + cal.get(Calendar.SECOND);
	}
	
	
	private void setCurrentTime()
	{
		currentTime = Calendar.getInstance();
	}
	
	

}
