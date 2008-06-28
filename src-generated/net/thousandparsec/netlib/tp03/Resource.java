package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Resource Description
 */
public class Resource extends Response
{
	public static final int FRAME_TYPE=23;

	protected Resource(int id)
	{
		super(id);
	}

	public Resource()
	{
		super(FRAME_TYPE);
	}

	private int id;

	public int getId()
	{
		return this.id;
	}

	public void setId(int value)
	{
		this.id=value;
	}

	private String singularname=new String();

	public String getSingularname()
	{
		return this.singularname;
	}

	public void setSingularname(String value)
	{
		this.singularname=value;
	}

	private String pluralname=new String();

	public String getPluralname()
	{
		return this.pluralname;
	}

	public void setPluralname(String value)
	{
		this.pluralname=value;
	}

	private String singularunitname=new String();

	public String getSingularunitname()
	{
		return this.singularunitname;
	}

	public void setSingularunitname(String value)
	{
		this.singularunitname=value;
	}

	private String pluralunitname=new String();

	public String getPluralunitname()
	{
		return this.pluralunitname;
	}

	public void setPluralunitname(String value)
	{
		this.pluralunitname=value;
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

	private int weight;

	public int getWeight()
	{
		return this.weight;
	}

	public void setWeight(int value)
	{
		this.weight=value;
	}

	private int size;

	public int getSize()
	{
		return this.size;
	}

	public void setSize(int value)
	{
		this.size=value;
	}

	/**
	 * The time at which this resource was last modified.
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

	@Override
	public void visit(TP03Visitor visitor) throws TPException
	{
		visitor.frame(this);
	}

	@Override
	public int findByteLength()
	{
		return super.findByteLength()
			 + 4
			 + findByteLength(this.singularname)
			 + findByteLength(this.pluralname)
			 + findByteLength(this.singularunitname)
			 + findByteLength(this.pluralunitname)
			 + findByteLength(this.description)
			 + 4
			 + 4
			 + 8;
	}

	@Override
	public void write(TPDataOutput out, Connection<?> conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.id);
		out.writeString(this.singularname);
		out.writeString(this.pluralname);
		out.writeString(this.singularunitname);
		out.writeString(this.pluralunitname);
		out.writeString(this.description);
		out.writeInteger(this.weight);
		out.writeInteger(this.size);
		out.writeInteger(this.modtime);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	Resource(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.id=in.readInteger32();
		this.singularname=in.readString();
		this.pluralname=in.readString();
		this.singularunitname=in.readString();
		this.pluralunitname=in.readString();
		this.description=in.readString();
		this.weight=in.readInteger32();
		this.size=in.readInteger32();
		this.modtime=in.readInteger64();
	}

	@Override
	public String toString()
	{
		StringBuilder buf=new StringBuilder();
		buf.append("{Resource");
		buf.append("; id: ");
		buf.append(String.valueOf(this.id));
		buf.append("; singularname: ");
		buf.append(String.valueOf(this.singularname));
		buf.append("; pluralname: ");
		buf.append(String.valueOf(this.pluralname));
		buf.append("; singularunitname: ");
		buf.append(String.valueOf(this.singularunitname));
		buf.append("; pluralunitname: ");
		buf.append(String.valueOf(this.pluralunitname));
		buf.append("; description: ");
		buf.append(String.valueOf(this.description));
		buf.append("; weight: ");
		buf.append(String.valueOf(this.weight));
		buf.append("; size: ");
		buf.append(String.valueOf(this.size));
		buf.append("; modtime: ");
		buf.append(String.valueOf(this.modtime));
		buf.append("; super:").append(super.toString());
		buf.append("}");
		return buf.toString();
	}

}
