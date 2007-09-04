package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

public class OrderInsert extends Order
{
	protected OrderInsert(int id)
	{
		super(id);
	}

	public OrderInsert()
	{
		super(12);
	}

	@Override
	public void visit(TP03Visitor visitor)
	{
		visitor.frame(this);
	}

	@Override
	public int findByteLength()
	{
		return super.findByteLength();
	}

	@Override
	public void write(TPDataOutput out, Connection<?> conn) throws IOException
	{
		super.write(out, conn);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	@SuppressWarnings("unused")
	OrderInsert(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

}
