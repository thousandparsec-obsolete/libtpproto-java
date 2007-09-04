package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Get Objects by ID
 */
public class GetObjectsByID extends GetWithID
{
	protected GetObjectsByID(int id)
	{
		super(id);
	}

	public GetObjectsByID()
	{
		super(5);
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
	GetObjectsByID(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

}
