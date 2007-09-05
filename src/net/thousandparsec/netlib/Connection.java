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
 * This class implements the client connection for the Thousand Parsec protocol.
 * The connection can send {@link Frame}s to the server and receive responses.
 * This class is parameterised by two types: the concrete {@link FrameDecoder}
 * used to decode frames in the incoming stream and a matching {@link Visitor}.
 * The frame decoder specifies which protocol version is supported by this
 * connection; unfortunately this is very rigid and to support another protocol
 * version the code has to be duplicated (reimplemented) with frame objects
 * generated for this protocol version.
 * 
 * @author ksobolewski
 */
public class Connection<V extends Visitor<V>>
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
	 * server specified in a URI as a {@link String}. The server part of the
	 * URI names the server to connect to; the scheme can be "tp" for plain
	 * conection, "tps" for secure (by SSL/TLS) connection, "http" for a plain
	 * connection via HTTP tunnel, or "https" for a secure connection via HTTPS
	 * tunnel; the port part overrides default port for the connection method.
	 * 
	 * @return the {@link Connection} instance conected to a server described by
	 *         the connection URI
	 * @throws URISyntaxException
	 *             if the URI string is not a valid URI
	 * @throws UnknownHostException
	 *             if the server cannot be found
	 * @throws IOException
	 *             on a I/O error
	 */
	public static <V extends Visitor<V>> Connection<V>
		makeConnection(FrameDecoder<V> frameDecoder, String serverUri)
		throws URISyntaxException, UnknownHostException, IOException
	{
		return makeConnection(frameDecoder, new URI(serverUri));
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
	public static <V extends Visitor<V>> Connection<V>
		makeConnection(FrameDecoder<V> frameDecoder, URI serverUri)
		throws UnknownHostException, IOException
	{
		//FIXME: make use of the authority and path parts if the URI to automagically login to a game?
		int port=serverUri.getPort();
		return port == -1
			? makeConnection(frameDecoder, serverUri.getHost(), Method.valueOf(serverUri.getScheme()))
			: makeConnection(frameDecoder, serverUri.getHost(), Method.valueOf(serverUri.getScheme()), port);
	}

	/**
	 * A convenience method that creates a {@link Connection} connected to a
	 * server specified by a host name and connection {@link Method}.
	 * 
	 * @see #makeConnection(FrameDecoder, URI)
	 * @return the {@link Connection} instance conected to a server described by
	 *         the connection URI
	 * @throws UnknownHostException
	 *             if the server cannot be found
	 * @throws IOException
	 *             on a I/O error
	 */
	public static <V extends Visitor<V>> Connection<V>
		makeConnection(FrameDecoder<V> frameDecoder, String host, Method method)
		throws UnknownHostException, IOException
	{
		return makeConnection(frameDecoder, host, method, method.defaultPort);
	}

	/**
	 * A convenience method that creates a {@link Connection} connected to a
	 * server specified by a host name and connection {@link Method} and a
	 * non-default port.
	 * 
	 * @see #makeConnection(FrameDecoder, URI)
	 * @return the {@link Connection} instance conected to a server described by
	 *         the connection URI
	 * @throws UnknownHostException
	 *             if the server cannot be found
	 * @throws IOException
	 *             on a I/O error
	 */
	public static <V extends Visitor<V>> Connection<V>
		makeConnection(FrameDecoder<V> frameDecoder, String host, Method method, int port)
		throws UnknownHostException, IOException
	{
		switch (method)
		{
			case tp:
				return makeTPConnection(frameDecoder, host);
			case tps:
				return makeTPSConnection(frameDecoder, host);
			case http:
				return makeHTTPConnection(frameDecoder, host);
			case https:
				return makeHTTPSConnection(frameDecoder, host);
			default:
				throw new IllegalArgumentException("Unknown connection method");
		}
	}

	/**
	 * A convenience method that creates a plain {@link Connection} connected to
	 * a server specified by a host name.
	 * 
	 * @see #makeConnection(FrameDecoder, URI)
	 * @return the {@link Connection} instance conected to a server described by
	 *         the connection URI
	 * @throws UnknownHostException
	 *             if the server cannot be found
	 * @throws IOException
	 *             on a I/O error
	 */
	public static <V extends Visitor<V>> Connection<V>
		makeTPConnection(FrameDecoder<V> frameDecoder, String host)
		throws UnknownHostException, IOException
	{
		return makeTPConnection(frameDecoder, host, Method.tp.defaultPort);
	}

	/**
	 * A convenience method that creates a plain {@link Connection} connected to
	 * a server specified by a host name and non-default port.
	 * 
	 * @see #makeConnection(FrameDecoder, URI)
	 * @return the {@link Connection} instance conected to a server described by
	 *         the connection URI
	 * @throws UnknownHostException
	 *             if the server cannot be found
	 * @throws IOException
	 *             on a I/O error
	 */
	public static <V extends Visitor<V>> Connection<V>
		makeTPConnection(FrameDecoder<V> frameDecoder, String host, int port)
		throws UnknownHostException, IOException
	{
		return new Connection<V>(frameDecoder, new Socket(host, port));
	}

	/**
	 * A convenience method that creates a secure (by SSL/TLS)
	 * {@link Connection} connected to a server specified by a host name.
	 * 
	 * @see #makeConnection(FrameDecoder, URI)
	 * @return the {@link Connection} instance conected to a server described by
	 *         the connection URI
	 * @throws UnknownHostException
	 *             if the server cannot be found
	 * @throws IOException
	 *             on a I/O error
	 */
	public static <V extends Visitor<V>> Connection<V>
		makeTPSConnection(FrameDecoder<V> frameDecoder, String host)
		throws UnknownHostException, IOException
	{
		return makeTPSConnection(frameDecoder, host, Method.tps.defaultPort);
	}

	/**
	 * A convenience method that creates a secure (by SSL/TLS)
	 * {@link Connection} connected to a server specified by a host name and
	 * non-default port.
	 * 
	 * @see #makeConnection(FrameDecoder, URI)
	 * @return the {@link Connection} instance conected to a server described by
	 *         the connection URI
	 * @throws UnknownHostException
	 *             if the server cannot be found
	 * @throws IOException
	 *             on a I/O error
	 */
	public static <V extends Visitor<V>> Connection<V>
		makeTPSConnection(FrameDecoder<V> frameDecoder, String host, int port)
		throws UnknownHostException, IOException
	{
		return new Connection<V>(frameDecoder, SSLSocketFactory.getDefault().createSocket(host, port));
	}

	/**
	 * A convenience method that creates a plain {@link Connection} via HTTP
	 * tunnel connected to a server specified by a host name.
	 * 
	 * @see #makeConnection(FrameDecoder, URI)
	 * @return the {@link Connection} instance conected to a server described by
	 *         the connection URI
	 * @throws UnknownHostException
	 *             if the server cannot be found
	 * @throws IOException
	 *             on a I/O error
	 */
	public static <V extends Visitor<V>> Connection<V>
		makeHTTPConnection(FrameDecoder<V> frameDecoder, String host)
		throws UnknownHostException, IOException
	{
		return makeHTTPConnection(frameDecoder, host, Method.http.defaultPort);
	}

	/**
	 * A convenience method that creates a plain {@link Connection} via HTTP
	 * tunnel connected to a server specified by a host name and non-default
	 * port.
	 * 
	 * @see #makeConnection(FrameDecoder, URI)
	 * @return the {@link Connection} instance conected to a server described by
	 *         the connection URI
	 * @throws UnknownHostException
	 *             if the server cannot be found
	 * @throws IOException
	 *             on a I/O error
	 */
	@SuppressWarnings("unused")
	public static <V extends Visitor<V>> Connection<V>
		makeHTTPConnection(FrameDecoder<V> frameDecoder, String host, int port)
		throws UnknownHostException, IOException
	{
//		return null;
		throw new UnsupportedOperationException("Not yet implemented");
	}

	/**
	 * A convenience method that creates a secure (by SSL/TLS)
	 * {@link Connection} via HTTPS tunnel connected to a server specified by a
	 * host name.
	 * 
	 * @see #makeConnection(FrameDecoder, URI)
	 * @return the {@link Connection} instance conected to a server described by
	 *         the connection URI
	 * @throws UnknownHostException
	 *             if the server cannot be found
	 * @throws IOException
	 *             on a I/O error
	 */
	public static <V extends Visitor<V>> Connection<V>
		makeHTTPSConnection(FrameDecoder<V> frameDecoder, String host)
		throws UnknownHostException, IOException
	{
		return makeHTTPSConnection(frameDecoder, host, Method.https.defaultPort);
	}

	/**
	 * A convenience method that creates a secure (by SSL/TLS)
	 * {@link Connection} via HTTPS tunnel connected to a server specified by a
	 * host name and non-default port.
	 * 
	 * @see #makeConnection(FrameDecoder, URI)
	 * @return the {@link Connection} instance conected to a server described by
	 *         the connection URI
	 * @throws UnknownHostException
	 *             if the server cannot be found
	 * @throws IOException
	 *             on a I/O error
	 */
	@SuppressWarnings("unused")
	public static <V extends Visitor<V>> Connection<V>
		makeHTTPSConnection(FrameDecoder<V> frameDecoder, String host, int port)
		throws UnknownHostException, IOException
	{
//		return null;
		throw new UnsupportedOperationException("Not yet implemented");
	}

	private final Object lockSend=new Object();
	private final Object lockRecv=new Object();
	private final FrameDecoder<V> frameDecoder;
	private final Socket socket;
	private final InputStream in;
	private final TPInputStream tpin;
	private final OutputStream out;
	private final TPOutputStream tpout;
	private int seq=0;

	/**
	 * Creates a new Thousand Parsec Protocol <em>client</em> connection which
	 * sends frames to the given {@link Socket}. The socket should be already
	 * connected and properly set up.
	 * 
	 * @param frameDecoder
	 *            the {@link FrameDecoder} used to create {@link Frame}s
	 *            encountered in the incoming stream
	 * @param socket
	 *            the {@link Socket} to use to send outgoing {@link Frame}s
	 * @throws IOException
	 *             on any I/O error during connection setup
	 */
	public Connection(FrameDecoder<V> frameDecoder, Socket socket) throws IOException
	{
		this.frameDecoder=frameDecoder;
		this.socket=socket;

		this.in=socket.getInputStream();
		this.tpin=new TPInputStream(this.in);
		this.out=socket.getOutputStream();
		this.tpout=new TPOutputStream(this.out);
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
			TPOutputStream tpout=getOutputStream();
			frame.write(tpout, this);
			tpout.flush();
		}
	}

	/**
	 * Synchronously sends a {@link Frame} to the server via this connection and
	 * sends a response to the specified {@link Visitor}. Note that what this
	 * does is very dumb: it simply waits for next frame from the server, so if
	 * the frame sent does not expect a response, you get stuck (becasue the
	 * operation is synchronised and you won't be able to send anything else),
	 * and if the response consists of more than one frame (like the "Sequence"
	 * response), this will only handle the first one.
	 * 
	 * @param frame
	 *            the {@link Frame} to send
	 * @param responseVisitor
	 *            the {@link Visitor} which will be used to accept the response
	 * @throws IOException
	 *             on any I/O error
	 * @throws TPException
	 *             thrown by the {@link Visitor}'s handler methods
	 */
	public void sendFrame(Frame<V> frame, V responseVisitor) throws IOException, TPException
	{
		/*
		 * synchronize to eliminate race conditions if there are many concurrent
		 * senders and receivers; in other words ensure that the next frame
		 * received will be the response to this frame
		 */
		synchronized (lockSend)
		{
			synchronized (lockRecv)
			{
				sendFrame(frame);
				receiveFrame().visit(responseVisitor);
			}
		}
	}

	/**
	 * Synchronously reads (and returns) next {@link Frame} from this
	 * connection; it will block if the frame is not immediately available. Will
	 * return {@code null} if there are no more frames (the connection was
	 * gracefully closed).
	 * 
	 * @return next {@link Frame} of {@code null} on end of stream
	 * @throws EOFException
	 *             if the connection is closed in the middle of frame
	 * @throws IOException
	 *             on any other I/O error
	 */
	public Frame<V> receiveFrame() throws EOFException, IOException
	{
		synchronized (lockRecv)
		{
			Frame.Header h=Frame.Header.readHeader(getInputStream(), getCompatibility());
			return h == null ? null : frameDecoder.decodeFrame(h.id, getInputStream(h.length));
		}
	}

	/**
	 * Synchronously reads {@link Frame}s from this connection until end of
	 * stream and sends them to the given {@link Visitor}.
	 * <p>
	 * Note that this is mostly incompatible with
	 * {@link #sendFrame(Frame, Visitor) visitor variant of sendFrame()},
	 * because of synchronisation; don't use them at the same time if you don't
	 * want unexpected behaviour.
	 * 
	 * @throws EOFException
	 *             if the connection is closed in the middle of frame
	 * @throws IOException
	 *             on any other I/O error
	 * @throws TPException
	 *             thrown by the {@link Visitor}'s handler methods
	 */
	public void receiveFrames(V visitor) throws EOFException, IOException, TPException
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
	 * <p>
	 * Note that this is mostly incompatible with
	 * {@link #sendFrame(Frame, Visitor) visitor variant of sendFrame()},
	 * because of synchronisation; don't use them at the same time if you don't
	 * want unexpected behaviour.
	 */
	public Future<Void> receiveFramesAsync(final V visitor)
	{
		final ExecutorService exec=Executors.newSingleThreadExecutor();
		return exec.submit(new Callable<Void>()
			{
				public Void call() throws Exception
				{
					try
					{
						receiveFrames(visitor);
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
