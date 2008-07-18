package net.thousandparsec.netlib.tp04;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Order Description
 */
public class OrderDesc extends Response
{
	public static final int FRAME_TYPE=9;

	protected OrderDesc(int id)
	{
		super(id);
	}

	public OrderDesc()
	{
		super(FRAME_TYPE);
	}

	/**
	 * order type
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
	 */
	private String name=new String();

	public String getName()
	{
		return this.name;
	}

	public void setName(String value)
	{
		this.name=value;
	}

	/**
	 */
	private String description=new String();

	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(String value)
	{
		this.description=value;
	}

	public static class ParametersType extends TPObject<TP04Visitor>
	{
		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		public ParametersType()
		{
		}

		/**
		 */
		private String name=new String();

		public String getName()
		{
			return this.name;
		}

		public void setName(String value)
		{
			this.name=value;
		}

		/**
		 * argument type ID
		 */
		private int type;

		public int getType()
		{
			return this.type;
		}

		public void setType(int value)
		{
			this.type=value;
		}

		/**
		 */
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
		public int findByteLength()
		{
			return super.findByteLength()
				 + findByteLength(this.name)
				 + 4
				 + findByteLength(this.description);
		}

		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			out.writeString(this.name);
			out.writeInteger(this.type);
			out.writeString(this.description);
		}

		/**
		 * A convenience constructor for easy initialisation of non-read only fields.
		 */
		public ParametersType(String name, int type, String description)
		{
			setName(name);
			setType(type);
			setDescription(description);
		}

		/**
		 * A copy constructor for (among others) deep-copying groups and lists.
		 */
		public ParametersType(ParametersType copy)
		{
			setName(copy.getName());
			setType(copy.getType());
			setDescription(copy.getDescription());
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		ParametersType(TPDataInput in) throws IOException
		{
			this.name=in.readString();
			this.type=in.readInteger32();
			this.description=in.readString();
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{ParametersType");
			buf.append("; name: ");
			buf.append(String.valueOf(this.name));
			buf.append("; type: ");
			buf.append(String.valueOf(this.type));
			buf.append("; description: ");
			buf.append(String.valueOf(this.description));
			buf.append("}");
			return buf.toString();
		}

	}

	private java.util.List<ParametersType> parameters=new java.util.ArrayList<ParametersType>();

	public java.util.List<ParametersType> getParameters()
	{
		return this.parameters;
	}

	@SuppressWarnings("unused")
	private void setParameters(java.util.List<ParametersType> value)
	{
		for (ParametersType object : value)
			this.parameters.add(new ParametersType(object));
	}

	/**
	 * The time at which this order description was last modified.
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
			 + findByteLength(this.name)
			 + findByteLength(this.description)
			 + findByteLength(this.parameters)
			 + 8;
	}

	@Override
	public void write(TPDataOutput out, Connection<?> conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.id);
		out.writeString(this.name);
		out.writeString(this.description);
		out.writeInteger(this.parameters.size());
		for (ParametersType object : this.parameters)
			object.write(out, conn);
		out.writeInteger(this.modtime);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	OrderDesc(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.id=in.readInteger32();
		this.name=in.readString();
		this.description=in.readString();
		this.parameters.clear();
		for (int length=in.readInteger32(); length > 0; length--)
			this.parameters.add(new ParametersType(in));
		this.modtime=in.readInteger64();
	}

	@Override
	public String toString()
	{
		StringBuilder buf=new StringBuilder();
		buf.append("{OrderDesc");
		buf.append("; id: ");
		buf.append(String.valueOf(this.id));
		buf.append("; name: ");
		buf.append(String.valueOf(this.name));
		buf.append("; description: ");
		buf.append(String.valueOf(this.description));
		buf.append("; parameters: ");
		buf.append(String.valueOf(this.parameters));
		buf.append("; modtime: ");
		buf.append(String.valueOf(this.modtime));
		buf.append("; super:").append(super.toString());
		buf.append("}");
		return buf.toString();
	}

}
