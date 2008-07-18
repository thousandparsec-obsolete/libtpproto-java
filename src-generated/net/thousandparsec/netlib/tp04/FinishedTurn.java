package net.thousandparsec.netlib.tp04;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Finished Turn
 */
public class FinishedTurn extends Request
{
	public static final int FRAME_TYPE=63;

	protected FinishedTurn(int id)
	{
		super(id);
	}

	public FinishedTurn()
	{
		super(FRAME_TYPE);
	}

	@Override
	public void visit(TP04Visitor visitor) throws TPException
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
	FinishedTurn(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

	@Override
	public String toString()
	{
		StringBuilder buf=new StringBuilder();
		buf.append("{FinishedTurn");
		buf.append("; super:").append(super.toString());
		buf.append("}");
		return buf.toString();
	}

}
