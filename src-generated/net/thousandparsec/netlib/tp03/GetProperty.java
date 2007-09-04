package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Get Property
 */
public class GetProperty extends GetWithID
{
	protected GetProperty(int id)
	{
		super(id);
	}

	public GetProperty()
	{
		super(58);
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
	GetProperty(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

}
