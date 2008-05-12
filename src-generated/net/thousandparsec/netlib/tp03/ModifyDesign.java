package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Modify Design
 */
public class ModifyDesign extends Design
{
	public static final int FRAME_TYPE=50;

	protected ModifyDesign(int id)
	{
		super(id);
	}

	public ModifyDesign()
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

	ModifyDesign(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

	public String toString()
	{
		
		return "{ModifyDesign"
                    + "; super:"
                    + super.toString()
                    + "}";
                    
	}

}
