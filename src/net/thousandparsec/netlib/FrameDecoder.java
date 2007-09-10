package net.thousandparsec.netlib;

import java.io.IOException;
import java.net.URI;
import java.net.UnknownHostException;

/**
 * This is an interface for frame decoders automatically generated for a specific
 * protocol version.
 * 
 * @see Connection
 * @author ksobolewski
 */
public interface FrameDecoder<V extends Visitor>
{
	int getCompatibility();

	/**
	 * A convenience method that creates a {@link Connection} connected to a
	 * server specified in a {@link URI}m using this decoder's protocol
	 * version. If {@code autologin} is {@literal true}, then the user-info
	 * part of the URI is used to automatically login to the server before
	 * returning the connection; if the user-info part is not present, it will
	 * throw {@link TPException}. See
	 * {@link Connection#makeConnection(FrameDecoder, URI, Visitor, boolean)}
	 * for details.
	 * 
	 * @param serverUri
	 *            the connection parameters encoded as a {@link URI}
	 * @param autologin
	 *            if {@literal true}, it will use user-info part of the
	 *            {@link URI} to automatically login to the server
	 * @param asyncVisitor
	 *            a {@link Visitor} that will accept asynchronous frames sent by
	 *            the server
	 * @see Connection#makeConnection(FrameDecoder, URI, Visitor, boolean)
	 */
	Connection<V> makeConnection(URI serverUri, boolean autologin, V asyncVisitor, boolean log) throws UnknownHostException, IOException, TPException;

	Frame<V> decodeFrame(int id, TPDataInput in) throws IOException;
}
