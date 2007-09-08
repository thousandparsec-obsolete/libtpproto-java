package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Get Message
 */
public class GetMessage extends GetWithIDSlot
{
	public static final int FRAME_TYPE=18;

	protected GetMessage(int id)
	{
		super(id);
	}

	public GetMessage()
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
	GetMessage(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

	@Override
	public String toString()
	{
		StringBuilder buf=new StringBuilder();
		buf.append("{GetMessage");
		buf.append("; super:").append(super.toString());
		buf.append("}");
		return buf.toString();
	}

}
