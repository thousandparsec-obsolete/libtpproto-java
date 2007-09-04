package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Component IDs list
 */
public class ComponentIDs extends IDSequence
{
	protected ComponentIDs(int id)
	{
		super(id);
	}

	public ComponentIDs()
	{
		super(57);
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
	ComponentIDs(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

}
