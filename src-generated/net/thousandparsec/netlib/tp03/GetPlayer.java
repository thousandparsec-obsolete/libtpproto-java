package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Get Player Data
 */
public class GetPlayer extends GetWithID
{
	public static final int FRAME_TYPE=39;

	protected GetPlayer(int id)
	{
		super(id);
	}

	public GetPlayer()
	{
		super(FRAME_TYPE);
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
	GetPlayer(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}


	public String toString()
	{
		return "{GetPlayer"
                    + "; super:"
                    + super.toString()
                    + "}";
		
	}

}
