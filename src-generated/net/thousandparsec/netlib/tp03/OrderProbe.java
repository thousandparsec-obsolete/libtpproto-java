package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Probe Order
 */
public class OrderProbe extends Order
{
	public static final int FRAME_TYPE=34;

	protected OrderProbe(int id)
	{
		super(id);
	}

	public OrderProbe()
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
	@SuppressWarnings("unused")
	OrderProbe(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

}
