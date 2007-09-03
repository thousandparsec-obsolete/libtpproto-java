package net.thousandparsec.netlib;

import java.io.IOException;

/**
 * An interface of objects that can be written to a TP protocol
 * {@link TPDataOutput output}.
 * 
 * @author ksobolewski
 */
public interface Writable<F extends FrameDecoder<F, V>, V extends Visitor<F, V>>
{
	/**
	 * Writes this object as a stream of primitive values to the given
	 * {@link TPDataOutput target}.
	 * 
	 * @param out
	 *            the data sink to write to
	 * @param conn
	 *            the {@link Connection} that created the output stream
	 * @throws IOException
	 *             on I/O error
	 */
	void write(TPDataOutput out, Connection<F, V> conn) throws IOException;
}
