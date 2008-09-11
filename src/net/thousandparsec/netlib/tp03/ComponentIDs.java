package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Component IDs list
 */
public class ComponentIDs extends IDSequence
{
	public static final int FRAME_TYPE=57;

	protected ComponentIDs(int id)
	{
		super(id);
	}

	public ComponentIDs()
	{
		super(FRAME_TYPE);
	}
        public void visit(Visitor visitor) throws TPException
        {
            System.out.println("warning: visit method called in ComponentIDs.java");
            visit((TP03Visitor)visitor);
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

	ComponentIDs(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

	public String toString()
	{
		return "{ComponentIDs"
                    +"; super:"
                    +super.toString()
                    +"}";
		
	}

}
