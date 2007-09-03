package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Get Message
 */
public class GetMessage extends GetWithIDSlot
{
	protected GetMessage(int id)
	{
		super(id);
	}

	public GetMessage()
	{
		super(18);
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
	GetMessage(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

}
