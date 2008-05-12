package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

public abstract class Response extends Frame
{
	protected Response(int id)
	{
		super(id);
	}

	public void visit(TP03Visitor visitor) throws TPException
	{
		//NOP (not a leaf class)
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
	
	Response(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

	
	public String toString()
	{
		return "{Response"
                    + "}";
		
	}

}
