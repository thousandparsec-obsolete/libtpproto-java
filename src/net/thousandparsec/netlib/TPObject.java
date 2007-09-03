package net.thousandparsec.netlib;

import java.io.UnsupportedEncodingException;
import java.util.List;

public abstract class TPObject<F extends FrameDecoder<F, V>, V extends Visitor<F, V>> implements Writable<F, V>
{
	/**
	 * @param s
	 *            the {@link String}
	 * @return byte representation of this string as encoded for the TP protocol
	 */
	protected static byte[] getStringBytes(String s)
	{
		try
		{
			return s.getBytes("utf-8");
		}
		catch (UnsupportedEncodingException ex)
		{
			throw new Error(ex);
		}
	}

	protected static int findByteLength(byte[] bytes)
	{
		return bytes.length + 4;
	}

	protected static int findByteLength(String string)
	{
		return findByteLength(getStringBytes(string));
	}

	protected static int findByteLength(TPObject<?, ?> object)
	{
		return object.findByteLength();
	}

	protected static int findByteLength(List<? extends TPObject<?, ?>> objects)
	{
		int total=0;
		for (TPObject<?, ?> object : objects)
			total += findByteLength(object);
		return total + 4;
	}

	/**
	 * Calculates the byte length of this object in bytes as encoded for the TP
	 * protocol.
	 * 
	 * @return byte length of this object
	 */
	public abstract int findByteLength();
}
