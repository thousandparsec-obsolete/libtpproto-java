package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Remove Design
 */
public class RemoveDesign extends GetDesign
{
	public static final int FRAME_TYPE=51;

	protected RemoveDesign(int id)
	{
		super(id);
	}

	public RemoveDesign()
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
	
	RemoveDesign(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

	
	public String toString()
	{
		return "{RemoveDesign"
                    + "; super:"
                    + super.toString()
                    + "}";
		
	}

}
