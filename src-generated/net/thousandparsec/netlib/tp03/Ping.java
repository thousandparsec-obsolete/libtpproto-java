package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Ping
 */
public class Ping extends Request
{
	public static final int FRAME_TYPE=27;

	protected Ping(int id)
	{
		super(id);
	}

	public Ping()
	{
		super(FRAME_TYPE);
	}

	        public void visit(Visitor visitor) throws TPException
        {
            System.out.println("warning: visit method called in Ping.java");
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
	
	Ping(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

	
	public String toString()
	{
		return "{Ping"
                    + "; super:"
                    + super.toString()
                    + "}";
		
	}

}
