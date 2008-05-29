package net.thousandparsec.netlib;

import java.io.IOException;

/**
 * An interface for writing TP protocol primitives. The methods provide a way to
 * write several primitive types, such as 8-bit integer(s), 32-bit integer,
 * 64-bit integer and a {@link String} (properly encoded). The integer values
 * can be either signed or unsigned or semi-signed (where there's only one
 * negative value, {@literal -1}, encoded as a maximum allowable value for a
 * given type) as their binary representation is always the same (but remember
 * that Java's integer types are always signed, so you have to treat unsigned
 * values carefully).
 * 
 * @author ksobolewski
 */
public interface TPDataOutput
{
	/**
	 * Writes one 8-bit integer.
	 * 
	 * @param b
	 *            the integer to write
	 * @throws IOException
	 *             on I/O error
	 */
	void writeInteger(byte b) throws IOException;

	/**
	 * Writes one 32-bit integer.
	 * 
	 * @param i
	 *            the integer to write
	 * @throws IOException
	 *             on I/O error
	 */
	void writeInteger(int i) throws IOException;

	/**
	 * Writes one 64-bit integer.
	 * 
	 * @param l
	 *            the integer to write
	 * @throws IOException
	 *             on I/O error
	 */
	void writeInteger(long l) throws IOException;

	/**
	 * Writes an array of 8-bit integers.
	 * 
	 * @param b
	 *            the integers to write
	 * @throws IOException
	 *             on I/O error
	 */
	void writeCharacter(byte[] b) throws IOException;

	/**
	 * Writes a portion of an array of 8-bit integers.
	 * 
	 * @param b
	 *            the integers to write
	 * @param offset
	 *            index of the integer to start from
	 * @param length
	 *            the number of integers to write
	 * @throws IOException
	 *             on I/O error
	 */
	void writeCharacter(byte[] b, int offset, int length) throws IOException;

	/**
	 * Writes a {@link String} properly encoded for the TP protocol: 32-bit
	 * header with the string length followed by a byte stream of the
	 * {@link String} in UTF-8 encoding.
	 * 
	 * @param s
	 *            the {@link String} to write
	 * @throws IOException
	 *             on I/O error
	 */
	void writeString(String s) throws IOException;
}
