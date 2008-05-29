package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Get Property IDs
 */
public class GetPropertyIDs extends GetIDSequence
{
	public static final int FRAME_TYPE=60;

	protected GetPropertyIDs(int id)
	{
		super(id);
	}

	public GetPropertyIDs()
	{
		super(FRAME_TYPE);
	}
        public void visit(Visitor visitor) throws TPException
        {
            System.out.println("warning: visit method called in GetPropertyIDs.java");
            visit(visitor);
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

	GetPropertyIDs(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

	public String toString()
	{
		
		return "{GetPropertyIDs"
                    + "; super:"
                    + super.toString()
                    + "}";
		
	}

}
