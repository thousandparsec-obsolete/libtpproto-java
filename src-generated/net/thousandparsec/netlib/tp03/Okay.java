package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

public class Okay extends Response
{
	public static final int FRAME_TYPE=0;

	protected Okay(int id)
	{
		super(id);
	}

	public Okay()
	{
		super(FRAME_TYPE);
	}

	/**
	 * The string can be safely ignored (However it may contain useful information for debugging purposes).
	 */
	private String result=new String();

	public String getResult()
	{
		return this.result;
	}

	public void setResult(String value)
	{
		this.result=value;
	}

	@Override
	public void visit(TP03Visitor visitor) throws TPException
	{
		visitor.frame(this);
	}

	@Override
	public int findByteLength()
	{
		return super.findByteLength()
			 + findByteLength(this.result);
	}

	@Override
	public void write(TPDataOutput out, Connection<?> conn) throws IOException
	{
		super.write(out, conn);
		out.writeString(this.result);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	@SuppressWarnings("unused")
	Okay(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.result=in.readString();
	}

}
