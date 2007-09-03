package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Get Design IDs
 */
public class GetDesignIDs extends GetIDSequence
{
	protected GetDesignIDs(int id)
	{
		super(id);
	}

	public GetDesignIDs()
	{
		super(52);
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
	GetDesignIDs(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

}
