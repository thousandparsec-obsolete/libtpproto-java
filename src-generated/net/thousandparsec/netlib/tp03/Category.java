package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Category Frame, Add Category
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


	public void visit(TP03Visitor visitor) throws TPException
	{
		visitor.frame(this);
	}


	public int findByteLength()
	{
		return super.findByteLength()
			 + 4
			 + 8
			 + findByteLength(this.name)
			 + findByteLength(this.description);
	}


	public void write(TPDataOutput out, Connection conn) throws IOException
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


	public String toString()
	{
		return "{Category"
                    +"; id: "
                    +String.valueOf(this.id)
                    +"; modtime: "
                    +String.valueOf(this.modtime)
                    +"; name: "
                    +String.valueOf(this.name)
                    +"; description: "
                    +String.valueOf(this.description)
                    +"; super:"
                    +super.toString()
                    +"}";
		
	}

}
