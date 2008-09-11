package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Get Design IDs
 */
public class GetDesignIDs extends GetIDSequence
{
	public static final int FRAME_TYPE=52;

	protected GetDesignIDs(int id)
	{
		super(id);
	}

	public GetDesignIDs()
	{
		super(FRAME_TYPE);
	}
        public void visit(Visitor visitor) throws TPException
        {
            System.out.println("warning: visit method called in GetDesignIDs");
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

	GetDesignIDs(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

	public String toString()
	{
		return "{GetDesignIDs"
                    + "; super:"
                    + super.toString()
                    + "}";
		
	}

}
