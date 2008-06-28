package net.thousandparsec.netlib;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * A default implementation of {@link TPDataOutput} interface, which delegates
 * calls to an underlying {@link DataOutputStream}.
 * 
 * @author ksobolewski
 */
public class TPOutputStream implements TPDataOutput
{
	private final DataOutputStream out;

	public TPOutputStream(OutputStream out)
	{
		this.out=new DataOutputStream(out);
	}

	public void writeInteger(byte b) throws IOException
	{
		out.writeByte(b);
	}

	public void writeInteger(int i) throws IOException
	{
		out.writeInt(i);
	}

	public void writeInteger(long l) throws IOException
	{
		out.writeLong(l);
	}

	public void writeCharacter(byte[] b) throws IOException
	{
		out.write(b, 0, b.length);
	}

	public void writeCharacter(byte[] b, int offset, int length) throws IOException
	{
		out.write(b, offset, length);
	}

	public void writeString(String s) throws IOException
	{
		byte[] bytes=s.getBytes("utf-8");
		out.writeInt(bytes.length);
		this.writeCharacter(bytes);
	}

	public void flush() throws IOException
	{
		out.flush();
	}
}
