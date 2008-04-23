package net.thousandparsec.netlib;

import java.io.UnsupportedEncodingException;
//import java.util.List;
import java.util.Vector;

//public abstract class TPObject<V extends Visitor> implements Writable
public abstract class TPObject implements Writable
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

	protected static int findByteLength(Writable object)
	{
		return object.findByteLength();
	}

	//protected static int findByteLength(List<? extends Writable> objects)
        protected static int findByteLength(Vector objects)
	{
		int total=0;
                Writable object;
                for(int i = 0; i < objects.size(); i++){
                    object = (Writable)objects.elementAt(i);
                    total+= findByteLength(object);
                }
		/*for (Writable object : objects)
			total += findByteLength(object);*/
		return total + 4;
	}

	public int findByteLength()
	{
		return 0;
	}
}
