package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * List of Property IDs
 */
public class PropertyIDs extends IDSequence
{
	public static final int FRAME_TYPE=61;

	protected PropertyIDs(int id)
	{
		super(id);
	}

	public PropertyIDs()
	{
		super(FRAME_TYPE);
	}
        public void visit(Visitor visitor) throws TPException
        {
            System.out.println("warning: visit method called in PropertyIDs.java");
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
	
	PropertyIDs(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

	
	public String toString()
	{
		return "{PropertyIDs"
                    + "; super:"
                    + super.toString()
                    + "}";
		
	}

}
