package net.thousandparsec.netlib;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
//import java.net.Socket;
//import net.thousandparsec.util.URI;
//import java.net.URISyntaxException;
//import java.net.UnknownHostException;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
import java.util.Vector;
import net.thousandparsec.util.URI;
import net.thousandparsec.util.URISyntaxException;

//import java.util.concurrent.Callable;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;

//import javax.net.ssl.SSLSocketFactory;

/**
 * This class implements the basic client connection for the Thousand Parsec
 * protocol. The connection offers low-level services: it can asynchronously
 * send {@link Frame}s to the server and receive incoming frames, both
 * synchronously and asynchronously. This class is thread safe in that the
 * low-level operations are synchronised and can be mixed with each other; but
 * note that higher-level wrappers might not be thread safe in this manner.
 * <p>
 * This class is parameterised by the concrete {@link Visitor} that matches the
 * protocol version and forces a matching implementation of {@link FrameDecoder}.
 * Unfortunately this is very rigid and to support another protocol version the
 * code has to be duplicated (reimplemented) with frame objects generated for
 * that protocol version.
 * <p>
 * The {@link Connection} object and all of the protocol version implementation
 * will guarantee that any lists and object returned by {@link Frame} objects
 * will not be modified by the library and are safe to use without copying.
 * <p>
 * The Thousand Parsec protocol is mostly request-response based, but there
 * exist some frames which can be sent asynchronously by the server. Those
 * frames are detected by the library and sent to a special asynchronous
 * {@link Visitor}, which has to be provided upon connection creation so that
 * those frames will not interfere with normal (supposedly sequential)
 * processing and will not get dropped. The asynchronous frames are only
 * received if any of the {@code receive*} methods are called - the frame is
 * sent to the asynchronous visitor and the methods wait for another frame
 * without returning.
 * <p>
 * The asynchronous frames are a subset of pipelined communication, which
 * Thousand Parsec protocol supports, but done in a non-pipelined and very
 * low-level way. The asynchronous frames are recognised and sent to a special
 * {@link Visitor}, but only if any other interaction is done on the
 * connection. To achieve a really asynchronous support for asynchronous frames,
 * you should use a higher-level wrapper or read frames asynchronously (see
 * {@link #receiveAllFramesAsync(Visitor)}).
 * 
 * @author ksobolewski
 */
//public class Connection<V extends Visitor>
public class Connection
{
	/**
	 * An enumeration of available TP protocol connection methods. Each enum
	 * value publishes the default prot number for the method in the
	 * {@link #defaultPort} field.
	 * 
	 * @author ksobolewski
	 */
        public static class MethodCode{
            /**
             * The default port used by this connection method
             */
            public final int defaultPort;
            
            private MethodCode(int defaultPort){
                this.defaultPort=defaultPort;
            }
        }
        public static class Method{
            static MethodCode[] codes = new MethodCode[4];
            /**
             * Plain Thousand Parsec connection.
             */
            static final MethodCode tp = new MethodCode(6923);

            /**
             * Secure (by SSL/TLS) Thousand Parsec connection.
             */
            static final MethodCode tps = new MethodCode(6924);

            /**
             * Plain Thousand Parsec connection via HTTP tunnel.
             */
            static final MethodCode http = new MethodCode(80);

            /**
             * Secure (by SSL/TLS) Thousand Parsec connection via HTTPS tunnel.
             * Shouldn't need this one.
             */
            //static final MethodCode https = new MethodCode(443);
            
            public MethodCode[] values(){
                codes[0] = tp;
                codes[1] = tps;
                codes[2] = http;
                //codes[3] = https;
            }
        }

	/**
	 * A convenience method that creates a {@link Connection} connected to a
	 * server specified in a URI as a {@link String}. See
	 * {@link #makeConnection(FrameDecoder, URI, Visitor)} for
	 * details.
	 * 
	 * @see #makeConnection(FrameDecoder, URI, Visitor)
	 * @return the {@link Connection} instance conected to a server described by
	 *         the connection URI
	 * @throws URISyntaxException
	 *             if the URI string is not a valid URI
	 * @throws UnknownHostException
	 *             if the server cannot be found
	 * @throws IOException
	 *             on a I/O error
	 */
	public static  Connection
		makeConnection(FrameDecoder frameDecoder, String serverUri, Visitor asyncVisitor)
		//throws URISyntaxException, UnknownHostException, IOException
                throws IOException
	{
		return makeConnection(
			frameDecoder,
			new URI(serverUri),
			asyncVisitor);
	}

	/**
	 * A convenience method that creates a {@link Connection} connected to a
	 * server specified in a {@link URI}. The server part of the URI names the
	 * server to connect to; the scheme can be "tp" for plain conection, "tps"
	 * for secure (by SSL/TLS) connection, "http" for a plain connection via
	 * HTTP tunnel, or "https" for a secure connection via HTTPS tunnel; the
	 * port part overrides default port for the connection method.
	 * 
	 * @return the {@link Connection} instance conected to a server described by
	 *         the connection URI
	 * @throws UnknownHostException
	 *             if the server cannot be found
	 * @throws IOException
	 *             on a I/O error
	 */
	public static  Connection
		makeConnection(FrameDecoder frameDecoder, URI serverUri, Visitor asyncVisitor)
		//throws UnknownHostException, IOException
                throws IOException
	{
		int port=serverUri.getPort();
		return port == -1 
			? makeConnection(
				frameDecoder,
				serverUri.getHost(),
				Method.valueOf(serverUri.getScheme()),
				asyncVisitor)
			: makeConnection(
				frameDecoder,
				serverUri.getHost(),
				Method.valueOf(serverUri.getScheme()),
				port,
				asyncVisitor);
	}

	/**
	 * A convenience method that creates a {@link Connection} connected to a
	 * server specified by a host name and connection {@link Method}.
	 * 
	 * @see #makeConnection(FrameDecoder, URI, Visitor)
	 * @return the {@link Connection} instance conected to a server described by
	 *         the connection URI
	 * @throws UnknownHostException
	 *             if the server cannot be found
	 * @throws IOException
	 *             on a I/O error
	 */
	public static  Connection
		makeConnection(FrameDecoder frameDecoder, String host, Method method, Visitor asyncVisitor)
		//throws UnknownHostException, IOException
                throws IOException
	{
		return makeConnection(
			frameDecoder,
			host,
			method,
			method.defaultPort,
			asyncVisitor);
	}

	/**
	 * A convenience method that creates a {@link Connection} connected to a
	 * server specified by a host name and connection {@link Method} and a
	 * non-default port.
	 * 
	 * @see #makeConnection(FrameDecoder, URI, Visitor)
	 * @return the {@link Connection} instance conected to a server described by
	 *         the connection URI
	 * @throws UnknownHostException
	 *             if the server cannot be found
	 * @throws IOException
	 *             on a I/O error
	 */
	public static Connection
		makeConnection(FrameDecoder frameDecoder, String host, Method method, int port, Visitor asyncVisitor)
		//throws UnknownHostException, IOException
                throws IOException
	{
		if(method.equals(Method.tp)){
                    return makeTPConnection(frameDecoder, host, asyncVisitor);
                }
                /*else if(method.equals(Method.tps)){
                    return makeTPSConnection(frameDecoder, host, asyncVisitor);
                }*/
                
                else if(method.equals(Method.http)){
                    return makeHTTPConnection(frameDecoder, host, asyncVisitor);
                }
                /*else if(method.equals(Method.https)){
                    return makeHTTPSConnection(frameDecoder, host, asyncVisitor);
                }*/
                //default
                else{
                    throw new IllegalArgumentException("Unknown connection method");
                }
               
		
	}

	/**
	 * A convenience method that creates a plain {@link Connection} connected to
	 * a server specified by a host name.
	 * 
	 * @see #makeConnection(FrameDecoder, URI, Visitor)
	 * @return the {@link Connection} instance conected to a server described by
	 *         the connection URI
	 * @throws UnknownHostException
	 *             if the server cannot be found
	 * @throws IOException
	 *             on a I/O error
	 */
	public static Connection
		makeTPConnection(FrameDecoder frameDecoder, String host, Visitor asyncVisitor)
		//throws UnknownHostException, IOException
                throws IOException
	{
		return makeTPConnection(
			frameDecoder,
			host,
			Method.tp.defaultPort,
			asyncVisitor);
	}

	/**
	 * A convenience method that creates a plain {@link Connection} connected to
	 * a server specified by a host name and non-default port.
	 * 
	 * @see #makeConnection(FrameDecoder, URI, Visitor)
	 * @return the {@link Connection} instance conected to a server described by
	 *         the connection URI
	 * @throws UnknownHostException
	 *             if the server cannot be found
	 * @throws IOException
	 *             on a I/O error
	 */
	public static  Connection
		makeTPConnection(FrameDecoder frameDecoder, String host, int port, Visitor asyncVisitor)
		//throws UnknownHostException, IOException
                throws IOException
	{
		//return new Connection<V>(
                return new Connection(
			frameDecoder,
			new Socket(host, port),
			asyncVisitor);
	}

	/**
	 * A convenience method that creates a secure (by SSL/TLS)
	 * {@link Connection} connected to a server specified by a host name.
	 * 
	 * @see #makeConnection(FrameDecoder, URI, Visitor)
	 * @return the {@link Connection} instance conected to a server described by
	 *         the connection URI
	 * @throws UnknownHostException
	 *             if the server cannot be found
	 * @throws IOException
	 *             on a I/O error
	 */
	/*public static Connection
		//makeTPSConnection(FrameDecoder<V> frameDecoder, String host, V asyncVisitor)
                makeTPSConnection(FrameDecoder frameDecoder, String host, Visitor asyncVisitor)
		throws UnknownHostException, IOException
	{
		return makeTPSConnection(
			frameDecoder,
			host,
			Method.tps.defaultPort,
			asyncVisitor);
	}*/

	/**
	 * A convenience method that creates a secure (by SSL/TLS)
	 * {@link Connection} connected to a server specified by a host name and
	 * non-default port.
	 * 
	 * @see #makeConnection(FrameDecoder, URI, Visitor)
	 * @return the {@link Connection} instance conected to a server described by
	 *         the connection URI
	 * @throws UnknownHostException
	 *             if the server cannot be found
	 * @throws IOException
	 *             on a I/O error
	 */
	/*public static Connection
		makeTPSConnection(FrameDecoder frameDecoder, String host, int port, Visitor asyncVisitor)
		throws UnknownHostException, IOException
	{
		//return new Connection<V>(
                return new Connection(
			frameDecoder,
			SSLSocketFactory.getDefault().createSocket(host, port),
			asyncVisitor);
	}*/

	/**
	 * A convenience method that creates a plain {@link Connection} via HTTP
	 * tunnel connected to a server specified by a host name.
	 * 
	 * @see #makeConnection(FrameDecoder, URI, Visitor)
	 * @return the {@link Connection} instance conected to a server described by
	 *         the connection URI
	 * @throws UnknownHostException
	 *             if the server cannot be found
	 * @throws IOException
	 *             on a I/O error
	 */
	public static Connection
		//makeHTTPConnection(FrameDecoder<V> frameDecoder, String host, V asyncVisitor)
                makeHTTPConnection(FrameDecoder frameDecoder, String host, Visitor asyncVisitor)
		//throws UnknownHostException, IOException
                throws IOException
	{
		return makeHTTPConnection(
			frameDecoder,
			host,
			Method.http.defaultPort,
			asyncVisitor);
	}

	/**
	 * A convenience method that creates a plain {@link Connection} via HTTP
	 * tunnel connected to a server specified by a host name and non-default
	 * port.
	 * 
	 * @see #makeConnection(FrameDecoder, URI, Visitor)
	 * @return the {@link Connection} instance conected to a server described by
	 *         the connection URI
	 * @throws UnknownHostException
	 *             if the server cannot be found
	 * @throws IOException
	 *             on a I/O error
	 */
	
	public static Connection
		makeHTTPConnection(FrameDecoder frameDecoder, String host, int port, Visitor asyncVisitor)
		//throws UnknownHostException, IOException
                throws IOException
	{
		throw new UnsupportedOperationException("Not yet implemented");
	}

	/**
	 * A convenience method that creates a secure (by SSL/TLS)
	 * {@link Connection} via HTTPS tunnel connected to a server specified by a
	 * host name.
	 * 
	 * @see #makeConnection(FrameDecoder, URI, Visitor)
	 * @return the {@link Connection} instance conected to a server described by
	 *         the connection URI
	 * @throws UnknownHostException
	 *             if the server cannot be found
	 * @throws IOException
	 *             on a I/O error
	 */
	/*public static Connection
		makeHTTPSConnection(FrameDecoder frameDecoder, String host, Visitor asyncVisitor)
		//throws UnknownHostException, IOException
                throws IOException
	{
		return makeHTTPSConnection(
			frameDecoder,
			host,
			Method.https.defaultPort,
			asyncVisitor);
	}*/

	/**
	 * A convenience method that creates a secure (by SSL/TLS)
	 * {@link Connection} via HTTPS tunnel connected to a server specified by a
	 * host name and non-default port.
	 * 
	 * @see #makeConnection(FrameDecoder, URI, Visitor)
	 * @return the {@link Connection} instance conected to a server described by
	 *         the connection URI
	 * @throws UnknownHostException
	 *             if the server cannot be found
	 * @throws IOException
	 *             on a I/O error
	 */
	/*public static Connection
                makeHTTPSConnection(FrameDecoder frameDecoder, String host, int port, Visitor asyncVisitor)
		//throws UnknownHostException, IOException
                throws IOException
	{
		throw new UnsupportedOperationException("Not yet implemented");
	}*/

	private final Object lockSend=new Object();
	private final Object lockRecv=new Object();
	//private final FrameDecoder<V> frameDecoder;
        private final FrameDecoder frameDecoder;
	private final Socket socket;
	//private final V asyncVisitor;
        private final Visitor asyncVisitor;
	private final InputStream in;
	private final TPInputStream tpin;
	private final OutputStream out;
	private final TPOutputStream tpout;
	//private final List<ConnectionListener<V>> listeners;
        //private final List<ConnectionListener<V>> listeners;
        private final Vector listeners;
	//start with sequence 1 not to mistake response for the first frame for asynchronous frame
	private int seq=1;

	/**
	 * Creates a new Thousand Parsec Protocol <em>client</em> connection which
	 * sends to and receives frames from the given {@link Socket}. The socket
	 * should be already connected and properly set up. The frames sent
	 * asynchronously by the server will be sent to the {@link Visitor} passed
	 * as {@code asyncVisitor}.
	 * 
	 * @param frameDecoder
	 *            the {@link FrameDecoder} used to create {@link Frame}s
	 *            encountered in the incoming stream
	 * @param socket
	 *            the {@link Socket} to use to send outgoing {@link Frame}s
	 * @param asyncVisitor
	 *            a {@link Visitor} that will accept asynchronous frames sent by
	 *            the server
	 * @throws IOException
	 *             on any I/O error during connection setup
	 */
	public Connection(FrameDecoder frameDecoder, Socket socket, Visitor asyncVisitor) throws IOException
	{
		if (asyncVisitor == null)
			throw new IllegalArgumentException("asyncVisitor");

		this.frameDecoder=frameDecoder;
		this.socket=socket;
		this.asyncVisitor=asyncVisitor;

		this.in=socket.getInputStream();
		this.tpin=new TPInputStream(this.in);
		this.out=socket.getOutputStream();
		this.tpout=new TPOutputStream(this.out);
		//this.listeners=new ArrayList<ConnectionListener<V>>();
                this.listeners = new Vector();
	}

	private TPInputStream getInputStream()
	{
		return tpin;
	}

	private TPInputStream getInputStream(int limit)
	{
		return new TPInputStream(new LimitInputStream(in, limit));
	}

	private TPOutputStream getOutputStream()
	{
		return tpout;
	}

	/**
	 * Returns next <em>outgoing</em> sequence number.
	 * @return next <em>outgoing</em> sequence number
	 */
	int getNextFrameSequence()
	{
		return seq++;
	}

	/**
	 * Returns protocol version compatible with this connection; exactly the
	 * same as
	 * {@link FrameDecoder#getCompatibility() FrameDecoder.getCompatibility()}
	 * 
	 * @return protocol version compatible with this connection
	 */
	public int getCompatibility()
	{
		return frameDecoder.getCompatibility();
	}

	/**
	 * Adds a {@link ConnectionListener} to this connection.
	 * 
	 * @param l
	 *            the listener to add
	 */
	public void addConnectionListener(ConnectionListener l)
	{
		//listeners.add(l);
                listeners.addElement(l);
	}

	/**
	 * Removes a {@link ConnectionListener} from this connection.
	 * 
	 * @param l
	 *            the listener to remove
	 */
	public void removeConnectionListener(ConnectionListener l)
	{
		//listeners.removeAll(Collections.singleton(l));
                listeners.removeAllElements();//****???
	}

	/**
	 * @param frame
	 *            the {@link Frame} sent
	 */
	void fireFrameSentEvent(Frame frame)
	{
		ConnectionEvent ev=new ConnectionEvent(ConnectionEvent.Type.FRAME_SENT, frame, false, null);
		ConnectionListener l;
                for(int i = 0; i < listeners.size(); i++){
                    l = (ConnectionListener)listeners.elementAt(i);
                    l.frameSent(ev);
                }
                
                //for (ConnectionListener<V> l : listeners)
		//	l.frameSent(ev);
	}

	/**
	 * @param frame
	 *            the {@link Frame} received
	 * @param isAsync
	 *            {@literal true} if this frame was an asynchronous frame
	 */
	void fireFrameReceivedEvent(Frame frame, boolean isAsync)
	{
            ConnectionEvent ev=new ConnectionEvent(ConnectionEvent.Type.FRAME_RECEIVED, frame, isAsync, null);
            ConnectionListener l;
            for ( int i = 0; i < listeners.size(); i++){
                l = (ConnectionListener)listeners.elementAt(i);
                l.frameReceived(ev);
            }
                /*for (ConnectionListener<V> l : listeners)
			l.frameReceived(ev);*/
	}

	/**
	 * @param frame
	 *            the {@link Frame} errored, if any (can be {@literal null})
	 * @param ex
	 *            the exception that caused the error
	 */
	void fireErrorEvent(Frame frame, Exception ex)
	{
		//ConnectionEvent<V> ev=new ConnectionEvent<V>(ConnectionEvent.Type.CONNECTION_ERROR, frame, false, ex);
                ConnectionEvent ev=new ConnectionEvent(ConnectionEvent.Type.CONNECTION_ERROR, frame, false, ex);
		ConnectionListener l;
                for(int i = 0; i < listeners.size(); i++){
                    l = (ConnectionListener)listeners.elementAt(i);
                    l.connectionError(ev);
                }
                
                /*for (ConnectionListener<V> l : listeners)
			l.connectionError(ev);*/
	}

	/**
	 * Asynchronously sends a {@link Frame} to the server via this connection.
	 * <p>
	 * This method, in addition to throwing an exception, notifies registered
	 * {@link ConnectionListener}s of a connection error for exceptions
	 * declared as thrown by this method.
	 * 
	 * @param frame
	 *            the {@link Frame} to send
	 * @throws IOException
	 *             on any I/O error
	 */
	public void sendFrame(Frame frame) throws IOException
	{
		synchronized (lockSend)
		{
			try
			{
				TPOutputStream tpout=getOutputStream();
				frame.write(tpout, this);
				tpout.flush();

				fireFrameSentEvent(frame);
			}
			catch (IOException ex)
			{
				fireErrorEvent(frame, ex);
				throw ex;
			}
		}
	}

	/**
	 * Synchronously reads (and returns) next {@link Frame} from this
	 * connection; it will block if the frame is not immediately available. Will
	 * return {@literal null} if there are no more frames to read (the
	 * connection was gracefully closed).
	 * <p>
	 * This method, in addition to throwing an exception, notifies registered
	 * {@link ConnectionListener}s of a connection error for exceptions
	 * declared as thrown by this method.
	 * 
	 * @return next {@link Frame} of {@literal null} on end of stream
	 * @throws EOFException
	 *             if the connection is closed in the middle of frame
	 * @throws IOException
	 *             on any other I/O error
	 * @throws TPException
	 *             on a Thousand Parsec protocol error
	 */
	public Frame receiveFrame() throws EOFException, IOException, TPException
	{
		synchronized (lockRecv)
		{
			try
			{
				while (true)
				{
					Frame.Header h=Frame.Header.readHeader(getInputStream(), getCompatibility());
					if (h == null)
						return null;
					else
					{
						//Frame<V> frame=frameDecoder.decodeFrame(h.id, getInputStream(h.length));
                                                Frame frame=frameDecoder.decodeFrame(h.id, getInputStream(h.length));
						frame.setSequenceNumber(h.seq);

						boolean async=frame.getSequenceNumber() == 0;

						fireFrameReceivedEvent(frame, async);

						if (async)
							frame.visit(asyncVisitor);
						else
							return frame;
					}
				}
			}
			catch (IOException ex)
			{
				fireErrorEvent(null, ex);
				throw ex;
			}
			catch (TPException ex)
			{
				fireErrorEvent(null, ex);
				throw ex;
			}
		}
	}

	/**
	 * Synchronously reads next {@link Frame} from this connection and sends it
	 * to the given {@link Visitor}; it will block if the frame is not
	 * immediately available. Will throw {@link EOFException} if there are no
	 * more frames to read (it is assumed that the caller expects the frame to
	 * be there, so it's abnormal to reach end of stream here).
	 * <p>
	 * This method, in addition to throwing an exception, notifies registered
	 * {@link ConnectionListener}s of a connection error for exceptions
	 * declared as thrown by this method, except the exception thrown by the
	 * visitor.
	 * 
	 * @param visitor
	 *            the {@link Visitor} which will receive the incoming frame
	 * @throws EOFException
	 *             if the connection is closed in the middle of frame or there
	 *             are no more frames to read
	 * @throws IOException
	 *             on any other I/O error
	 * @throws TPException
	 *             thrown by the {@link Visitor}'s handler methods
	 */
	public void receiveFrame(Visitor visitor) throws EOFException, IOException, TPException
	{
		Frame frame=receiveFrame();
		if (frame == null)
			throw new EOFException();
		frame.visit(visitor);
	}

	/**
	 * Synchronously reads {@link Frame}s from this connection until end of
	 * stream and sends them to the given {@link Visitor}.
	 * <p>
	 * This method, in addition to throwing an exception, notifies registered
	 * {@link ConnectionListener}s of a connection error for exceptions
	 * declared as thrown by this method.
	 * 
	 * @param visitor
	 *            the {@link Visitor} which will receive incoming frames
	 * @throws EOFException
	 *             if the connection is closed in the middle of frame
	 * @throws IOException
	 *             on any other I/O error
	 * @throws TPException
	 *             thrown by the {@link Visitor}'s handler methods
	 */
	public void receiveAllFrames(Visitor visitor) throws EOFException, IOException, TPException
	{
		Frame frame;
		while ((frame=receiveFrame()) != null)
			frame.visit(visitor);
	}

	/**
	 * Asynchronously reads {@link Frame}s from this connection until end of
	 * stream and sends them to the given {@link Visitor} in another thread. The
	 * returned {@link Future} can be used to {@link Future#get() wait} for the
	 * task to finish, to {@link Future#cancel(boolean) cancel} it, and to
	 * inspect if there were errors during the asnychronous processing when the
	 * task quits (by catching {@link java.util.concurrent.ExecutionException}
	 * thrown by {@link Future#get()}). This task does not close the connection
	 * when it quits. The errors encountered during asynchronous processing are
	 * sent to the registered {@link ConnectionListener}s.
	 * 
	 * @param visitor
	 *            the {@link Visitor} which will receive incoming frames
	 * @return a {@link Future} that represents the asynchronous task
	 */
	public Future<Void> receiveAllFramesAsync(final Visitor visitor)
	{
		final ExecutorService exec=Executors.newSingleThreadExecutor();
		return exec.submit(new Callable<Void>()
			{
				public Void call() throws Exception
				{
					try
					{
						receiveAllFrames(visitor);
						return null;
					}
					finally
					{
						exec.shutdown();
					}
				}
			});
	}

	/**
	 * Shuts down this connection: the underlying socket's output is gracefully
	 * {@link Socket#shutdownOutput() shut down}.
	 * 
	 * @throws IOException
	 *             on any I/O error
	 */
	public void close() throws IOException
	{
		synchronized (lockSend)
		{
			socket.shutdownOutput();
		}
	}
}
