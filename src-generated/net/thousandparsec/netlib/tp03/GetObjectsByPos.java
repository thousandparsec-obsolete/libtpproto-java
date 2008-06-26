package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Get Objects by Position
 */
public class GetObjectsByPos extends Request
{
	public static final int FRAME_TYPE=6;

	protected GetObjectsByPos(int id)
	{
		super(id);
	}

	public GetObjectsByPos()
	{
		super(FRAME_TYPE);
	}
        public void visit(Visitor visitor) throws TPException
        {
            System.out.println("warning: visit method called in GetObjectsByPos");
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

	GetObjectsByPos(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

	public String toString()
	{
		return "{GetObjectsByPos"
                    + "; super:"
                    + super.toString()
                    + "}";
		
	}

}
