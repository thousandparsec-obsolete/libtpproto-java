package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Post Message
 */
public class PostMessage extends Message
{
	public static final int FRAME_ID=20;

	protected PostMessage(int id)
	{
		super(id);
	}

	public PostMessage()
	{
		super(FRAME_ID);
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
	PostMessage(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

}
