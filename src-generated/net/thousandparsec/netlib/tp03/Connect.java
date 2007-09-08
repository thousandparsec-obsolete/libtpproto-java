package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Connect
 */
public class Connect extends Request
{
	public static final int FRAME_TYPE=3;

	protected Connect(int id)
	{
		super(id);
	}

	public Connect()
	{
		super(FRAME_TYPE);
	}

	/**
	 * 
				The client identification string can be any string but will mostly
	 * 
				used to produce stats of who uses which client. The server may return 
	 * 
				either a OK, Fail or Redirect frame.
	 * 
			
	 */
	private String string=new String();

	public String getString()
	{
		return this.string;
	}

	public void setString(String value)
	{
		this.string=value;
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
			 + findByteLength(this.string);
	}

	@Override
	public void write(TPDataOutput out, Connection<?> conn) throws IOException
	{
		super.write(out, conn);
		out.writeString(this.string);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	@SuppressWarnings("unused")
	Connect(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.string=in.readString();
	}

	@Override
	public String toString()
	{
		StringBuilder buf=new StringBuilder();
		buf.append("{Connect");
		buf.append("; string: ");
		buf.append(String.valueOf(this.string));
		buf.append("; super:").append(super.toString());
		buf.append("}");
		return buf.toString();
	}

}
