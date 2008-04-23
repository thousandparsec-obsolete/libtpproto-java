package net.thousandparsec.netlib;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A default implementation of {@link TPDataInput} interface, which delegates
 * calls to an underlying {@link DataInputStream}.
 * 
 * @author ksobolewski
 */
public class TPInputStream implements TPDataInput
{
	private final LimitInputStream orig;
	private final DataInputStream in;

	TPInputStream(LimitInputStream in)
	{
		this.orig=in;
		this.in=new DataInputStream(in);
	}

	public TPInputStream(InputStream in)
	{
		this.orig=null;
		this.in=new DataInputStream(in);
	}

	public byte readInteger8() throws IOException
	{
		return in.readByte();
	}

	public int readInteger32() throws IOException
	{
		return in.readInt();
	}

	public long readInteger64() throws IOException
	{
		return in.readLong();
	}

	public void readCharacter(byte[] b) throws IOException
	{
		in.readFully(b);
	}

	public void readCharacter(byte[] b, int offset, int length) throws IOException
	{
		in.readFully(b, offset, length);
	}

	public String readString() throws IOException
	{
		int length=readInteger32();
		byte[] buf=new byte[length];
		in.readFully(buf);
		return new String(buf, "utf-8");
	}

	public byte[] drainFrame() throws IOException
	{
		return orig == null ? null : orig.drain();
	}
}
