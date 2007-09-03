package net.thousandparsec.netlib;

import java.io.IOException;

/**
 * An interface for reading TP protocol primitives. The methods provide a way to
 * read several primitive types, such as 8-bit integer(s), 32-bit integer,
 * 64-bit integer and a {@link String} (properly encoded). The integer values
 * can be either signed or unsigned or semi-signed (where there's only one
 * negative value, {@value -1}, encoded as a maximum allowable value for a
 * given type) as their binary representation is always the same (but remember
 * that Java's integer types are always signed, so you have to treat unsigned
 * values carefully).
 * 
 * @author ksobolewski
 */
public interface TPDataInput
{
	/**
	 * Reads one 8-bit integer
	 * @return
	 *            one integer from the source
	 * @throws IOException
	 *             on I/O error
	 */
	byte readInteger8() throws IOException;

	/**
	 * Reads one 32-bit integer
	 * @return
	 *            one integer from the source
	 * @throws IOException
	 *             on I/O error
	 */
	int readInteger32() throws IOException;

	/**
	 * Reads one 64-bit integer
	 * @return
	 *            one integer from the source
	 * @throws IOException
	 *             on I/O error
	 */
	long readInteger64() throws IOException;

	/**
	 * Reads an array of 8-bit integers to a byte buffer.
	 * 
	 * @param b
	 *            the buffer to write to
	 * @throws IOException
	 *             on I/O error
	 */
	void readCharacter(byte[] b) throws IOException;

	/**
	 * Reads an array of 8-bit integers to a portion of byte buffer.
	 * 
	 * @param b
	 *            the buffer to write to
	 * @param offset
	 *            index of the buffer to start write to
	 * @param length
	 *            the length of the buffer
	 * @throws IOException
	 *             on I/O error
	 */
	void readCharacter(byte[] b, int offset, int length) throws IOException;

	/**
	 * Reads a {@link String} as encoded for the TP protocol.
	 * 
	 * @return the decoded {@link String} from the source
	 * @throws IOException
	 *             on I/O error
	 */
	String readString() throws IOException;
}
