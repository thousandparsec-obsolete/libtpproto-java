package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

public class Sequence extends Response
{
	public static final int FRAME_TYPE=2;

	protected Sequence(int id)
	{
		super(id);
	}

	public Sequence()
	{
		super(FRAME_TYPE);
	}

	/**
	 * Number of frames which will follow this one.
	 */
	private int number;

	public int getNumber()
	{
		return this.number;
	}

	public void setNumber(int value)
	{
		this.number=value;
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
			 + 4;
	}

	@Override
	public void write(TPDataOutput out, Connection<?> conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.number);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	@SuppressWarnings("unused")
	Sequence(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.number=in.readInteger32();
	}

}
