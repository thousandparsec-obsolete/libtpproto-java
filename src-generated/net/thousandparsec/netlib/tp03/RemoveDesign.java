package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Remove Design
 */
public class RemoveDesign extends GetDesign
{
	protected RemoveDesign(int id)
	{
		super(id);
	}

	public RemoveDesign()
	{
		super(51);
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
	RemoveDesign(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

}
