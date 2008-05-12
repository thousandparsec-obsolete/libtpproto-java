package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Remove Message
 */
public class RemoveMessage extends GetMessage
{
	public static final int FRAME_TYPE=21;

	protected RemoveMessage(int id)
	{
		super(id);
	}

	public RemoveMessage()
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
	
	RemoveMessage(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

	
	public String toString()
	{
		return "{RemoveMessage"
                    + "; super:"
                    + super.toString()
                    + "}";
		
	}

}
