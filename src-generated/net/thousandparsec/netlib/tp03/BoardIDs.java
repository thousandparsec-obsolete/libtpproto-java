package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * List Of Board IDs
 */
public class BoardIDs extends IDSequence
{
	protected BoardIDs(int id)
	{
		super(id);
	}

	public BoardIDs()
	{
		super(36);
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
	BoardIDs(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

}
