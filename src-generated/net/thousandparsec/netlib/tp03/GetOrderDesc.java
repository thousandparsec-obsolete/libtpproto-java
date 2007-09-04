package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Get Order Description
 */
public class GetOrderDesc extends GetWithID
{
	protected GetOrderDesc(int id)
	{
		super(id);
	}

	public GetOrderDesc()
	{
		super(8);
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
	GetOrderDesc(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

}
