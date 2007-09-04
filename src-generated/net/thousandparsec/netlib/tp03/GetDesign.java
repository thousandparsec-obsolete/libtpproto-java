package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Get Design
 */
public class GetDesign extends GetWithID
{
	protected GetDesign(int id)
	{
		super(id);
	}

	public GetDesign()
	{
		super(47);
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
	GetDesign(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

}
