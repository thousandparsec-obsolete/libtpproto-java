package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Remove Order
 */
public class RemoveOrder extends GetWithIDSlot
{
	public static final int FRAME_TYPE=13;

	protected RemoveOrder(int id)
	{
		super(id);
	}

	public RemoveOrder()
	{
		super(FRAME_TYPE);
	}

	@Override
	public void visit(TP03Visitor visitor) throws TPException
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
	RemoveOrder(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

	@Override
	public String toString()
	{
		StringBuilder buf=new StringBuilder();
		buf.append("{RemoveOrder");
		buf.append("; super:").append(super.toString());
		buf.append("}");
		return buf.toString();
	}

}
