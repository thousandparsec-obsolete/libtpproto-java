package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Get Property
 */
public class GetProperty extends GetWithID
{
	public static final int FRAME_TYPE=58;

	protected GetProperty(int id)
	{
		super(id);
	}

	public GetProperty()
	{
		super(FRAME_TYPE);
	}
        public void visit(Visitor visitor) throws TPException
        {
            System.out.println("warning: visit method called in GetProperty.java");
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
	GetProperty(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

	public String toString()
	{
		
		return "{GetProperty"
                    + "; super:"
                    + super.toString()
                    + "}";
		
	}

}
