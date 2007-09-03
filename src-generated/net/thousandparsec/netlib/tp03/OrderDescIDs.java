package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * List of Order Description IDs
 */
public class OrderDescIDs extends IDSequence
{
	protected OrderDescIDs(int id)
	{
		super(id);
	}

	public OrderDescIDs()
	{
		super(33);
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
	OrderDescIDs(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

}
