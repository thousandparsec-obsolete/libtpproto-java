package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Modify Design
 */
public class ModifyDesign extends Design
{
	protected ModifyDesign(int id)
	{
		super(id);
	}

	public ModifyDesign()
	{
		super(50);
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
	ModifyDesign(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

}
