package net.thousandparsec.netlib;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.net.ssl.SSLSocketFactory;

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
public class Connection<V extends Visitor>
{
	/**
	 * An enumeration of available TP protocol connection methods. Each enum
	 * value publishes the default prot number for the method in the
	 * {@link #defaultPort} field.
	 * 
	 * @author ksobolewski
	 */
	public static enum Method
	{
		/**
		 * Plain Thousand Parsec connection.
		 */
		tp(6923),

		/**
		 * Secure (by SSL/TLS) Thousand Parsec connection.
		 */
		tps(6924),

		/**
		 * Plain Thousand Parsec connection via HTTP tunnel.
		 */
		http(80),

		/**
		 * Secure (by SSL/TLS) Thousand Parsec connection via HTTPS tunnel.
		 */
		https(443);

		/**
		 * The default port used by this connection method
		 */
		public final int defaultPort;

		private Method(int defaultPort)
		{
			this.defaultPort=defaultPort;
		}
	}

	/**
	 * A convenience method that creates a {@link Connection} connected to a
	 * server specified in a URI as a {@link String}. See
	 * {@link #makeConnection(FrameDecoder, URI, Visitor, boolean)} for
	 * details.
	 * 
	 * @see #makeConnection(FrameDecoder, URI, Visitor, boolean)
	 * @return the {@link Connection} instance conected to a server described by
	 *         the connection URI
	 * @throws URISyntaxException
	 *             if the URI string is not a valid URI
	 * @throws UnknownHostException
	 *             if the server cannot be found
	 * @throws IOException
	 *             on a I/O error
	 */
	public static <V extends Visitor> Connection<V>
		makeConnection(FrameDecoder<V> frameDecoder, String serverUri, V asyncVisitor, boolean log)
		throws URISyntaxException, UnknownHostException, IOException
	{
		return makeConnection(
			frameDecoder,
			new URI(serverUri),
			asyncVisitor,
			log);
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
	public static <V extends Visitor> Connection<V>
		makeConnection(FrameDecoder<V> frameDecoder, URI serverUri, V asyncVisitor, boolean log)
		throws UnknownHostException, IOException
	{
		int port=serverUri.getPort();
		return port == -1
			? makeConnection(
				frameDecoder,
				serverUri.getHost(),
				Method.valueOf(serverUri.getScheme()),
				asyncVisitor,
				log)
			: makeConnection(
				frameDecoder,
				serverUri.getHost(),
				Method.valueOf(serverUri.getScheme()),
				port,
				asyncVisitor,
				log);
	}

	/**
	 * A convenience method that creates a {@link Connection} connected to a
	 * server specified by a host name and connection {@link Method}.
	 * 
	 * @see #makeConnection(FrameDecoder, URI, Visitor, boolean)
	 * @return the {@link Connection} instance conected to a server described by
	 *         the connection URI
	 * @throws UnknownHostException
	 *             if the server cannot be found
	 * @throws IOException
	 *             on a I/O error
	 */
	public static <V extends Visitor> Connection<V>
		makeConnection(FrameDecoder<V> frameDecoder, String host, Method method, V asyncVisitor, boolean log)
		throws UnknownHostException, IOException
	{
		return makeConnection(
			frameDecoder,
			host,
			method,
			method.defaultPort,
			asyncVisitor,
			log);
	}

	/**
	 * A convenience method that creates a {@link Connection} connected to a
	 * server specified by a host name and connection {@link Method} and a
	 * non-default port.
	 * 
	 * @see #makeConnection(FrameDecoder, URI, Visitor, boolean)
	 * @return the {@link Connection} instance conected to a server described by
	 *         the connection URI
	 * @throws UnknownHostException
	 *             if the server cannot be found
	 * @throws IOException
	 *             on a I/O error
	 */
	public static <V extends Visitor> Connection<V>
		makeConnection(FrameDecoder<V> frameDecoder, String host, Method method, int port, V asyncVisitor, boolean log)
		throws UnknownHostException, IOException
	{
		switch (method)
		{
			case tp:
				return makeTPConnection(frameDecoder, host, asyncVisitor, log);
			case tps:
				return makeTPSConnection(frameDecoder, host, asyncVisitor, log);
			case http:
				return makeHTTPConnection(frameDecoder, host, asyncVisitor, log);
			case https:
				return makeHTTPSConnection(frameDecoder, host, asyncVisitor, log);
			default:
				throw new IllegalArgumentException("Unknown connection method");
		}
	}

	/**
	 * A convenience method that creates a plain {@link Connection} connected to
	 * a server specified by a host name.
	 * 
	 * @see #makeConnection(FrameDecoder, URI, Visitor, boolean)
	 * @return the {@link Connection} instance conected to a server described by
	 *         the connection URI
	 * @throws UnknownHostException
	 *             if the server cannot be found
	 * @throws IOException
	 *             on a I/O error
	 */
	public static <V extends Visitor> Connection<V>
		makeTPConnection(FrameDecoder<V> frameDecoder, String host, V asyncVisitor, boolean log)
		throws UnknownHostException, IOException
	{
		return makeTPConnection(
			frameDecoder,
			host,
			Method.tp.defaultPort,
			asyncVisitor,
			log);
	}

	/**
	 * A convenience method that creates a plain {@link Connection} connected to
	 * a server specified by a host name and non-default port.
	 * 
	 * @see #makeConnection(FrameDecoder, URI, Visitor, boolean)
	 * @return the {@link Connection} instance conected to a server described by
	 *         the connection URI
	 * @throws UnknownHostException
	 *             if the server cannot be found
	 * @throws IOException
	 *             on a I/O error
	 */
	public static <V extends Visitor> Connection<V>
		makeTPConnection(FrameDecoder<V> frameDecoder, String host, int port, V asyncVisitor, boolean log)
		throws UnknownHostException, IOException
	{
		return new Connection<V>(
			frameDecoder,
			new Socket(host, port),
			asyncVisitor,
			log);
	}

	/**
	 * A convenience method that creates a secure (by SSL/TLS)
	 * {@link Connection} connected to a server specified by a host name.
	 * 
	 * @see #makeConnection(FrameDecoder, URI, Visitor, boolean)
	 * @return the {@link Connection} instance conected to a server described by
	 *         the connection URI
	 * @throws UnknownHostException
	 *             if the server cannot be found
	 * @throws IOException
	 *             on a I/O error
	 */
	public static <V extends Visitor> Connection<V>
		makeTPSConnection(FrameDecoder<V> frameDecoder, String host, V asyncVisitor, boolean log)
		throws UnknownHostException, IOException
	{
		return makeTPSConnection(
			frameDecoder,
			host,
			Method.tps.defaultPort,
			asyncVisitor,
			log);
	}

	/**
	 * A convenience method that creates a secure (by SSL/TLS)
	 * {@link Connection} connected to a server specified by a host name and
	 * non-default port.
	 * 
	 * @see #makeConnection(FrameDecoder, URI, Visitor, boolean)
	 * @return the {@link Connection} instance conected to a server described by
	 *         the connection URI
	 * @throws UnknownHostException
	 *             if the server cannot be found
	 * @throws IOException
	 *             on a I/O error
	 */
	public static <V extends Visitor> Connection<V>
		makeTPSConnection(FrameDecoder<V> frameDecoder, String host, int port, V asyncVisitor, boolean log)
		throws UnknownHostException, IOException
	{
		return new Connection<V>(
			frameDecoder,
			SSLSocketFactory.getDefault().createSocket(host, port),
			asyncVisitor,
			log);
	}

	/**
	 * A convenience method that creates a plain {@link Connection} via HTTP
	 * tunnel connected to a server specified by a host name.
	 * 
	 * @see #makeConnection(FrameDecoder, URI, Visitor, boolean)
	 * @return the {@link Connection} instance conected to a server described by
	 *         the connection URI
	 * @throws UnknownHostException
	 *             if the server cannot be found
	 * @throws IOException
	 *             on a I/O error
	 */
	public static <V extends Visitor> Connection<V>
		makeHTTPConnection(FrameDecoder<V> frameDecoder, String host, V asyncVisitor, boolean log)
		throws UnknownHostException, IOException
	{
		return makeHTTPConnection(
			frameDecoder,
			host,
			Method.http.defaultPort,
			asyncVisitor,
			log);
	}

	/**
	 * A convenience method that creates a plain {@link Connection} via HTTP
	 * tunnel connected to a server specified by a host name and non-default
	 * port.
	 * 
	 * @see #makeConnection(FrameDecoder, URI, Visitor, boolean)
	 * @return the {@link Connection} instance conected to a server described by
	 *         the connection URI
	 * @throws UnknownHostException
	 *             if the server cannot be found
	 * @throws IOException
	 *             on a I/O error
	 */
	@SuppressWarnings("unused")
	public static <V extends Visitor> Connection<V>
		makeHTTPConnection(FrameDecoder<V> frameDecoder, String host, int port, V asyncVisitor, boolean log)
		throws UnknownHostException, IOException
	{
		throw new UnsupportedOperationException("Not yet implemented");
	}

	/**
	 * A convenience method that creates a secure (by SSL/TLS)
	 * {@link Connection} via HTTPS tunnel connected to a server specified by a
	 * host name.
	 * 
	 * @see #makeConnection(FrameDecoder, URI, Visitor, boolean)
	 * @return the {@link Connection} instance conected to a server described by
	 *         the connection URI
	 * @throws UnknownHostException
	 *             if the server cannot be found
	 * @throws IOException
	 *             on a I/O error
	 */
	public static <V extends Visitor> Connection<V>
		makeHTTPSConnection(FrameDecoder<V> frameDecoder, String host, V asyncVisitor, boolean log)
		throws UnknownHostException, IOException
	{
		return makeHTTPSConnection(
			frameDecoder,
			host,
			Method.https.defaultPort,
			asyncVisitor,
			log);
	}

	/**
	 * A convenience method that creates a secure (by SSL/TLS)
	 * {@link Connection} via HTTPS tunnel connected to a server specified by a
	 * host name and non-default port.
	 * 
	 * @see #makeConnection(FrameDecoder, URI, Visitor, boolean)
	 * @return the {@link Connection} instance conected to a server described by
	 *         the connection URI
	 * @throws UnknownHostException
	 *             if the server cannot be found
	 * @throws IOException
	 *             on a I/O error
	 */
	@SuppressWarnings("unused")
	public static <V extends Visitor> Connection<V>
		makeHTTPSConnection(FrameDecoder<V> frameDecoder, String host, int port, V asyncVisitor, boolean log)
		throws UnknownHostException, IOException
	{
		throw new UnsupportedOperationException("Not yet implemented");
	}

	private final Object lockSend=new Object();
	private final Object lockRecv=new Object();
	private final FrameDecoder<V> frameDecoder;
	private final Socket socket;
	private final V asyncVisitor;
	private final InputStream in;
	private final TPInputStream tpin;
	private final OutputStream out;
	private final TPOutputStream tpout;
	private final boolean log;
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
	 * @param log
	 *            if {@literal true}, this connection will log outgoing and
	 *            incoming {@link Frame}s
	 * @throws IOException
	 *             on any I/O error during connection setup
	 */
	public Connection(FrameDecoder<V> frameDecoder, Socket socket, V asyncVisitor, boolean log) throws IOException
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
		this.log=log;
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
	 * Asynchronously sends a {@link Frame} to the server via this connection.
	 * 
	 * @param frame
	 *            the {@link Frame} to send
	 * @throws IOException
	 *             on any I/O error
	 */
	public void sendFrame(Frame<V> frame) throws IOException
	{
		synchronized (lockSend)
		{
			//FIXME: add proper logging
			if (log)
				System.err.printf("%s: Sending frame %d (%s)%n", getClass().getName(), frame.getFrameType(), frame.toString());
			TPOutputStream tpout=getOutputStream();
			frame.write(tpout, this);
			tpout.flush();
		}
	}

	/**
	 * Synchronously reads (and returns) next {@link Frame} from this
	 * connection; it will block if the frame is not immediately available. Will
	 * return {@literal null} if there are no more frames to read (the
	 * connection was gracefully closed).
	 * 
	 * @return next {@link Frame} of {@literal null} on end of stream
	 * @throws EOFException
	 *             if the connection is closed in the middle of frame
	 * @throws IOException
	 *             on any other I/O error
	 * @throws TPException
	 *             on a Thousand Parsec protocol error
	 */
	public Frame<V> receiveFrame() throws EOFException, IOException, TPException
	{
		synchronized (lockRecv)
		{
			while (true)
			{
				Frame.Header h=Frame.Header.readHeader(getInputStream(), getCompatibility());
				if (h == null)
					return null;
				else
				{
					Frame<V> frame=frameDecoder.decodeFrame(h.id, getInputStream(h.length));
					frame.setSequenceNumber(h.seq);

					boolean async=frame.getSequenceNumber() == 0;
					//FIXME: add proper logging
					if (log)
						if (async)
							System.err.printf("%s: Received asynchronous frame seq %d, type %d (%s)%n", getClass().getName(), frame.getSequenceNumber(), frame.getFrameType(), frame.toString());
						else
							System.err.printf("%s: Received frame seq %d, type %d (%s)%n", getClass().getName(), frame.getSequenceNumber(), frame.getFrameType(), frame.toString());
					if (async)
						frame.visit(asyncVisitor);
					else
						return frame;
				}
			}
		}
	}

	/**
	 * Synchronously reads next {@link Frame} from this connection and sends it
	 * to the given {@link Visitor}; it will block if the frame is not
	 * immediately available. Will throw {@link EOFException} if there are no
	 * more frames to read (it is assumed that the caller expects the frame to
	 * be there, so it's abnormal to reach end of stream here).
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
	public void receiveFrame(V visitor) throws EOFException, IOException, TPException
	{
		Frame<V> frame=receiveFrame();
		if (frame == null)
			throw new EOFException();
		frame.visit(visitor);
	}

	/**
	 * Synchronously reads {@link Frame}s from this connection until end of
	 * stream and sends them to the given {@link Visitor}.
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
	public void receiveAllFrames(V visitor) throws EOFException, IOException, TPException
	{
		Frame<V> frame;
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
	 * when it quits.
	 * 
	 * @param visitor
	 *            the {@link Visitor} which will receive incoming frames
	 * @return a {@link Future} that represents the asynchronous task
	 */
	public Future<Void> receiveAllFramesAsync(final V visitor)
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
