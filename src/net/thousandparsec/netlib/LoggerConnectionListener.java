package net.thousandparsec.netlib;

import java.util.*;
import java.util.concurrent.*;
import java.io.*;


/**
 * A {@link ConnectionEvent} logger, that utilizes a list structure.
 * Logs the connection events in memory, and periodically dumps them either to standard-out, or to a file.
 * 
 * This class is thread safe.
 * 
 * @author Victor Ivri
 *
 */
public class LoggerConnectionListener<V extends Visitor> implements ConnectionListener
{
	private final ConcurrentLinkedQueue<ConnectionEventTuple> connectionEventLog;
	private final Calendar sessionStart;
	private final PrintStream printStream;
	private final int dumpWhenReachSize;
	
	/**
	 * Default constructor. Starts session upon creation.
	 */
	public LoggerConnectionListener()
	{
		connectionEventLog = new ConcurrentLinkedQueue<ConnectionEventTuple>(); // a thread-safe class
		sessionStart = Calendar.getInstance();
		printStream = null;
		dumpWhenReachSize = 0;
	}
	
	/**
	 * Constructor that specifies the maximum size of the log. To avoid memory clutter, 
	 * the log will be dumped to a {@link PrintStream} every time it reaches a certain size. 
	 * It means that the data will be offloaded, and then removed from the log.
	 * 
	 * The recommended constructor for use in extensive logging sessions.
	 * 
	 * @param sizeLimit When log reaches this size, dump its contents to the specified {@link PrintStream}. Cannot be 0.
	 * @param ps The {@link PrintStream} where the log will be periodically dumped. Cannot be null.
	 */
	public LoggerConnectionListener(int sizeLimit, PrintStream ps)
	{
		connectionEventLog = new ConcurrentLinkedQueue<ConnectionEventTuple>(); // a thread-safe class
		printStream = ps;
		dumpWhenReachSize = sizeLimit;
		sessionStart = Calendar.getInstance();
	}
	
	/**
	 * Adds a {@link ConnectionEvent} to the log of events.
	 */
	public synchronized void connectionError(ConnectionEvent ev) 
	{
		connectionEventLog.add(new ConnectionEventTuple(ev));
		dumpIfReachedLimit();
	}

	/**
	 * Adds a {@link ConnectionEvent} to the log of events.
	 */
	public synchronized void frameReceived(ConnectionEvent ev) 
	{
		connectionEventLog.add(new ConnectionEventTuple(ev));
		dumpIfReachedLimit();
	}

	/**
	 * Adds a {@link ConnectionEvent} to the log of events.
	 */
	public synchronized void frameSent(ConnectionEvent ev) 
	{
		connectionEventLog.add(new ConnectionEventTuple(ev));
		dumpIfReachedLimit();
	}
	
	
	private synchronized void dumpIfReachedLimit()
	{
		if (dumpWhenReachSize != 0 && printStream != null)
			if (dumpWhenReachSize <= connectionEventLog.size())
				dumpLog(printStream);
	}
	
	/**
	 * 
	 * @return 
	 * an immutable, synchronized <ConnectionEventTuple>{@link Collection}<ConnectionEventTuple>, 
	 * with the full log of {@link ConnectionEvent} properties, including the time of occurence.
	 * The collection stores {@link ConnectionEventTuple} in FIFO manner.
	 * 
	 * NOTE: This Collection is an instance of {@link ConcurrentLinkedQueue}<ConnectionEventTuple>.
	 * 
	 * Warning: 
	 * The user must manually synchronize on the returned list when iterating over it.
	 * Failure to do so might result in nondeterministic behavior.
	 * 
	 * For example:
	 *  
  	 *	synchronized(list) {
     * 	Iterator i = list.iterator(); // Must be in synchronized block
     * 	while (i.hasNext())
     *     operation(i.next());
  	 *	}
  	 *
	 */
	public synchronized Collection<ConnectionEventTuple> getLog()
	{
		return Collections.unmodifiableCollection(connectionEventLog); 
	}
	
	/**
	 * A convenience method.
	 * Dumps the log to standard out, in convenient and informative formatting.
	 * 
	 */
	public synchronized void dumpLogStd()
	{
		dumpLog(System.out);
	}
	
	/*  ----- UNTESTED ----- 
	/**
	 * A convenience method.
	 * 
	 * Dumps the log to a file specified in the filePath, in convenient and informative formatting.
	 * See dumpLog(PrintStream ps) for more information.
	 * @param filePath the path to an existing log file.
	 * 
	 * @throws {@link FileNotFoundException} if the path did not lead to an existing file.
	 *
	public synchronized void dumpLogToFile(String filePath) throws FileNotFoundException
	{
		if (filePath == null)
		{
			pl(System.out, "Enter path of an existing log file: ");
			filePath = new Scanner(System.in).next();
		}
		
		PrintStream fileOut = new PrintStream(new File(filePath));
		dumpLog(fileOut);
		
	}
	*/
	
	/**
	 * Dumps the connection event log to any {@link PrintStream} in convenient formatting, 
	 * and destroys the thus-far accumulated {@link ConnectionEventTuple}s, to avoid memory clutter.
	 * A good idea is to perform this operation periodically.
	 * 
	 * This method is thread-safe.
	 * 
	 * @param ps is the {@link PrintStream} where the log will end up.
	 */
	public synchronized void dumpLog(PrintStream ps) 
	{
		synchronized (connectionEventLog) 
		{	//ensuring that another thread cannot manipulate the list during this operation.
			
			int logSize = connectionEventLog.size();
			
			pl(ps, ">");
			pl(ps, "Printing Connection Event Log:");
			pl(ps, "Session started (DAY/MONTH/YEAR | HOUR:MINUTE:SECOND ):  " + formatTime(sessionStart));
	
			pl(ps, "Current time (DAY/MONTH/YEAR | HOUR:MINUTE:SECOND ):  " + formatTime(getCurrentTime()));
		
			pl(ps, "The log: (first to last)\n");
			
			while (connectionEventLog.peek() != null)
			{
				ConnectionEventTuple cet = connectionEventLog.poll(); //get and remove event
				Exception ex = cet.getException();
				String exception;
				if (ex == null)
					exception = "none";
				else
					exception = ex.getMessage();
			
				pl(ps, formatTimeShort(cet.getTime()) + " Event type: " + cet.getType() + " | Is asynchronous: " + cet.getIsAsynch() +
						"\nFrame type: " + cet.getFrame().getFrameType() + " - " + cet.getFrame().getClass().getSimpleName() + 
						" | Frame sequence number: " + cet.getFrame().getSequenceNumber() + " | Exception: " + exception + 
						"\nFrame toString: " + cet.getFrame().toString() + "\n");
			}
				
				pl(ps, "List of connection events so far. Log size : " + logSize);
				pl(ps, ">");
		}		
	}

	private void pl(PrintStream out, String s)
	{
		out.println(s);
	}
	
	private String formatTime(Calendar cal)
	{
		return "" + cal.get(Calendar.DATE) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.YEAR) + " | " + 
			cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
	}
	
	private String formatTimeShort(long time)
	{
		Date date = new Date(time);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		return "[" + cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE) + ":" +cal.get(Calendar.SECOND) + "]";
	}
	

	
	private Calendar getCurrentTime()
	{
		return Calendar.getInstance();
	}
}
