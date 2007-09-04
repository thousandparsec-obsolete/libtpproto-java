package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * List of Property IDs
 */
public class PropertyIDs extends IDSequence
{
	protected PropertyIDs(int id)
	{
		super(id);
	}

	public PropertyIDs()
	{
		super(61);
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
	PropertyIDs(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

}
