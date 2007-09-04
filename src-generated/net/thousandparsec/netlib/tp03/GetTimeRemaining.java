package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Get Time Remaining
 */
public class GetTimeRemaining extends Request
{
	protected GetTimeRemaining(int id)
	{
		super(id);
	}

	public GetTimeRemaining()
	{
		super(14);
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
	GetTimeRemaining(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

}
