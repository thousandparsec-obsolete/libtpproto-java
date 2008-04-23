package net.thousandparsec.netlib;

import java.io.IOException;

/**
 * An interface of objects that can be written to a TP protocol
 * {@link TPDataOutput output}.
 * <p>
 * Please note that the {@link Visitor} type is not necessary to write data to a
 * stream (from inside an object), and that's why it is not a type parameter of
 * this interface.
 * 
 * @author ksobolewski
 */
public interface Writable
{
	/**
	 * Calculates the byte length of this object in bytes as encoded for the TP
	 * protocol.
	 * 
	 * @return byte length of this object
	 */
	int findByteLength();

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
	void write(TPDataOutput out, Connection<?> conn) throws IOException;
}
