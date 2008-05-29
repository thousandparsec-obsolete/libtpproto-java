package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * List Of Resource Description IDs
 */
public class ResourceIDs extends IDSequence
{
	public static final int FRAME_TYPE=38;

	protected ResourceIDs(int id)
	{
		super(id);
	}

	public ResourceIDs()
	{
		super(FRAME_TYPE);
	}

        public void visit(Visitor visitor) throws TPException
        {
            System.out.println("warning: visit method called in ResourceIDs.java");
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
	
	ResourceIDs(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

	
	public String toString()
	{
		return "{ResourceIDs"
                    + "; super:"
                    + super.toString()
                    + "}";
		
	}

}
