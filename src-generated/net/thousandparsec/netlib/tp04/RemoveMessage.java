package net.thousandparsec.netlib.tp04;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Remove Message
 */
public class RemoveMessage extends GetMessage
{
	public static final int FRAME_TYPE=21;

	protected RemoveMessage(int id)
	{
		super(id);
	}

	public RemoveMessage()
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
	RemoveMessage(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

	@Override
	public String toString()
	{
		StringBuilder buf=new StringBuilder();
		buf.append("{RemoveMessage");
		buf.append("; super:").append(super.toString());
		buf.append("}");
		return buf.toString();
	}

}
