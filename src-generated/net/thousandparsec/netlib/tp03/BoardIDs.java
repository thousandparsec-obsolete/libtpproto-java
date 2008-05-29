package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * List Of Board IDs
 */
public class BoardIDs extends IDSequence
{
	public static final int FRAME_TYPE=36;

	protected BoardIDs(int id)
	{
		super(id);
	}

	public BoardIDs()
	{
		super(FRAME_TYPE);
	}
        public void visit(Visitor visitor) throws TPException
        {
            System.out.println("warning: visit method called in BoardIDs.java");
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

	BoardIDs(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}


	public String toString()
	{
		return "{BoardIDs"
                    +"; super:"
                    +super.toString()
                    +"}";
		
	}

}
