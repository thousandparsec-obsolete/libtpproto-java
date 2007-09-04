package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Get Objects by Position
 */
public class GetObjectsByPos extends Request
{
	protected GetObjectsByPos(int id)
	{
		super(id);
	}

	public GetObjectsByPos()
	{
		super(6);
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
	GetObjectsByPos(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

}
