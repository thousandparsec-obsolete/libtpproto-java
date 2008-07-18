package net.thousandparsec.netlib.tp04;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Category Frame
 */
public class Category extends Response
{
	public static final int FRAME_TYPE=42;

	protected Category(int id)
	{
		super(id);
	}

	public Category()
	{
		super(FRAME_TYPE);
	}

	/**
	 * Category ID
	 */
	private int id;

	public int getId()
	{
		return this.id;
	}

	public void setId(int value)
	{
		this.id=value;
	}

	/**
	 * The time at which this category was last modified.
	 */
	private long modtime;

	public long getModtime()
	{
		return this.modtime;
	}

	public void setModtime(long value)
	{
		this.modtime=value;
	}

	private String name=new String();

	public String getName()
	{
		return this.name;
	}

	public void setName(String value)
	{
		this.name=value;
	}

	private String description=new String();

	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(String value)
	{
		this.description=value;
	}

	@Override
	public void visit(TP04Visitor visitor) throws TPException
	{
		visitor.frame(this);
	}

	@Override
	public int findByteLength()
	{
		return super.findByteLength()
			 + 4
			 + 8
			 + findByteLength(this.name)
			 + findByteLength(this.description);
	}

	@Override
	public void write(TPDataOutput out, Connection<?> conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.id);
		out.writeInteger(this.modtime);
		out.writeString(this.name);
		out.writeString(this.description);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	Category(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.id=in.readInteger32();
		this.modtime=in.readInteger64();
		this.name=in.readString();
		this.description=in.readString();
	}

	@Override
	public String toString()
	{
		StringBuilder buf=new StringBuilder();
		buf.append("{Category");
		buf.append("; id: ");
		buf.append(String.valueOf(this.id));
		buf.append("; modtime: ");
		buf.append(String.valueOf(this.modtime));
		buf.append("; name: ");
		buf.append(String.valueOf(this.name));
		buf.append("; description: ");
		buf.append(String.valueOf(this.description));
		buf.append("; super:").append(super.toString());
		buf.append("}");
		return buf.toString();
	}

}
