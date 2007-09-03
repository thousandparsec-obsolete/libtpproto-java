package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * List of Design IDs
 */
public class DesignIDs extends IDSequence
{
	protected DesignIDs(int id)
	{
		super(id);
	}

	public DesignIDs()
	{
		super(53);
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
	public void write(TPDataOutput out, Connection<TP03Decoder, TP03Visitor> conn) throws IOException
	{
		super.write(out, conn);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	@SuppressWarnings("unused")
	DesignIDs(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

}
