package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Get Boards
 */
public class GetBoards extends GetWithID
{
	public static final int FRAME_TYPE=16;

	protected GetBoards(int id)
	{
		super(id);
	}

	public GetBoards()
	{
		super(FRAME_TYPE);
	}
        public void visit(Visitor visitor) throws TPException
        {

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
	GetBoards(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

	public String toString()
	{
		return "{GetBoards"
                    + "; super:"
                    + super.toString()
                    + "}";
		
	}

}
