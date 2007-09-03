package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Get Features
 */
public class GetFeatures extends Request
{
	protected GetFeatures(int id)
	{
		super(id);
	}

	public GetFeatures()
	{
		super(25);
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
	GetFeatures(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

}
