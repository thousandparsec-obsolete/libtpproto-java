package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * List of Category IDs
 */
public class CategoryIDs extends IDSequence
{
	protected CategoryIDs(int id)
	{
		super(id);
	}

	public CategoryIDs()
	{
		super(46);
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
	CategoryIDs(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

}
