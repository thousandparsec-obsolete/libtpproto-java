package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Add Design
 */
public class AddDesign extends Design
{
	public static final int FRAME_TYPE=49;

	protected AddDesign(int id)
	{
		super(id);
	}

	public AddDesign()
	{
		super(FRAME_TYPE);
	}

	public void visit(TP03Visitor visitor) throws TPException
	{
		visitor.frame(this);
	}

	public int findByteLength()
	{
		return super.findByteLength();
	}

	public void write(TPDataOutput out, Connection conn) throws IOException
	{
		super.write(out, conn);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */

	AddDesign(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

	public String toString()
	{
		StringBuilder buf=new StringBuilder();
		buf.append("{AddDesign");
		buf.append("; super:").append(super.toString());
		buf.append("}");
		return buf.toString();
	}

}
