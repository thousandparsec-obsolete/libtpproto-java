package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

public abstract class Request extends Frame<TP03Decoder, TP03Visitor>
{
	protected Request(int id)
	{
		super(id);
	}

	public void visit(TP03Visitor visitor)
	{
		//NOP (not a leaf class)
	}

	@Override
	public int findByteLength()
	{
		return super.findByteLength();
	}

	@Override
	public void write(TPDataOutput out, Connection<TP03Decoder, TP03Visitor> conn) throws IOException
	{
		super.write(out, conn);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	@SuppressWarnings("unused")
	Request(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

}
