package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Get Board IDs
 */
public class GetBoardIDs extends GetIDSequence
{
	protected GetBoardIDs(int id)
	{
		super(id);
	}

	public GetBoardIDs()
	{
		super(35);
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
	GetBoardIDs(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

}
