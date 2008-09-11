package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Get Features
 */
public class GetFeatures extends Request
{
	public static final int FRAME_TYPE=25;

	protected GetFeatures(int id)
	{
		super(id);
	}

	public GetFeatures()
	{
		super(FRAME_TYPE);
	}
        public void visit(Visitor visitor) throws TPException
        {
            System.out.println("warning: visit method called GetGeatures.java");
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

	GetFeatures(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

	public String toString()
	{
		return "{GetFeatures"
                    + "; super:"
                    + super.toString()
                    + "}";
		
	}

}
