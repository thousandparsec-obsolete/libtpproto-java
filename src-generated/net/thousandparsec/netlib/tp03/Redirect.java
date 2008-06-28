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

	@Override
	public void visit(TP03Visitor visitor) throws TPException
	{
		visitor.frame(this);
	}

	@Override
	public int findByteLength()
	{
		return super.findByteLength()
			 + findByteLength(this.URI);
	}

	@Override
	public void write(TPDataOutput out, Connection<?> conn) throws IOException
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

	@Override
	public String toString()
	{
		StringBuilder buf=new StringBuilder();
		buf.append("{Redirect");
		buf.append("; URI: ");
		buf.append(String.valueOf(this.URI));
		buf.append("; super:").append(super.toString());
		buf.append("}");
		return buf.toString();
	}

}
