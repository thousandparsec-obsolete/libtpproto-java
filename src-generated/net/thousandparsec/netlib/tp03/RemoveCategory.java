package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Remove Category
 */
public class RemoveCategory extends GetCategory
{
	public static final int FRAME_TYPE=44;

	protected RemoveCategory(int id)
	{
		super(id);
	}

	public RemoveCategory()
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
	RemoveCategory(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

}
