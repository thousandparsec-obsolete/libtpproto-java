package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Get Resource Description IDs
 */
public class GetResourceIDs extends GetIDSequence
{
	public static final int FRAME_TYPE=37;

	protected GetResourceIDs(int id)
	{
		super(id);
	}

	public GetResourceIDs()
	{
		super(FRAME_TYPE);
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
	GetResourceIDs(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

}
