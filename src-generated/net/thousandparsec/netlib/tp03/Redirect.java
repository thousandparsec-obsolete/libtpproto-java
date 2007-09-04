package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Redirect
 */
public class Redirect extends Response
{
	protected Redirect(int id)
	{
		super(id);
	}

	public Redirect()
	{
		super(24);
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
	public void visit(TP03Visitor visitor)
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
	@SuppressWarnings("unused")
	Redirect(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.URI=in.readString();
	}

}
