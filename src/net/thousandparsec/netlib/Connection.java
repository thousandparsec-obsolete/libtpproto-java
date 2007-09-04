package net.thousandparsec.netlib;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

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
	 * Sends a {@link Frame} to the server via this connection.
	 * 
	 * @param frame
	 *            the {@link Frame} to send
	 * @throws IOException
	 *             on any I/O error
	 */
	public void sendFrame(Frame<V> frame) throws IOException
	{
		TPOutputStream tpout=getOutputStream();
		frame.write(tpout, this);
		tpout.flush();
	}

	/**
	 * Reads (and returns) next {@link Frame} from this connection. Will return
	 * {@code null} if there are no more frames (the connection was gracefully
	 * closed).
	 * 
	 * @return next {@link Frame} of {@code null} on end of stream
	 * @throws EOFException
	 *             if the connection is closed in the middle of frame
	 * @throws IOException
	 *             on any other I/O error
	 */
	public Frame<V> receiveFrame() throws EOFException, IOException
	{
		Frame.Header h=Frame.Header.readHeader(getInputStream(), getCompatibility());
		return h == null ? null : frameDecoder.decodeFrame(h.id, getInputStream(h.length));
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
		socket.shutdownOutput();
	}
}
