package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Redirect
 */
public class Redirect extends Response
{
	public static final int FRAME_TYPE=24;

	protected Redirect(int id)
	{
		super(id);
	}

	public Redirect()
	{
		super(FRAME_TYPE);
	}

	/**
	 */
	private String URI=new String();

	public String getURI()
	{
		return this.URI;
	}

	public void setURI(String value)
	{
		this.URI=value;
	}

	public void visit(Visitor visitor) throws TPException
        {
            System.out.println("warning: visit method called in Redirect.java");
            visit((TP03Visitor)visitor);
        }
	public void visit(TP03Visitor visitor) throws TPException
	{
		visitor.frame(this);
	}

	
	public int findByteLength()
	{
		return super.findByteLength()
			 + findByteLength(this.URI);
	}

	
	public void write(TPDataOutput out, Connection conn) throws IOException
	{
		super.write(out, conn);
		out.writeString(this.URI);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	
	Redirect(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.URI=in.readString();
	}

	
	public String toString()
	{
		return "{Redirect"
                    + "; URI: "
                    + String.valueOf(this.URI)
                    + "; super:"
                    + super.toString()
                    + "}";
		
	}

}
