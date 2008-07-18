package net.thousandparsec.netlib.tp04;

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

	/**
	 * Resource ID
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

	private String singularunit=new String();

	public String getSingularunit()
	{
		return this.singularunit;
	}

	public void setSingularunit(String value)
	{
		this.singularunit=value;
	}

	private String pluralunit=new String();

	public String getPluralunit()
	{
		return this.pluralunit;
	}

	public void setPluralunit(String value)
	{
		this.pluralunit=value;
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

	/**
	 * 0 for not applicable
	 */
	private int weight;

	public int getWeight()
	{
		return this.weight;
	}

	public void setWeight(int value)
	{
		this.weight=value;
	}

	/**
	 * 0 for not applicable
	 */
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
	public void visit(TP04Visitor visitor) throws TPException
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
			 + findByteLength(this.singularunit)
			 + findByteLength(this.pluralunit)
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
		out.writeString(this.singularunit);
		out.writeString(this.pluralunit);
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
		this.singularunit=in.readString();
		this.pluralunit=in.readString();
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
		buf.append("; singularunit: ");
		buf.append(String.valueOf(this.singularunit));
		buf.append("; pluralunit: ");
		buf.append(String.valueOf(this.pluralunit));
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
