package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Get Order
 */
public class GetOrder extends GetWithIDSlot
{
	protected GetOrder(int id)
	{
		super(id);
	}

	public GetOrder()
	{
		super(10);
	}

	@Override
	public void visit(TP03Visitor visitor)
	{
		visitor.handle(this);
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
	GetOrder(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

}
