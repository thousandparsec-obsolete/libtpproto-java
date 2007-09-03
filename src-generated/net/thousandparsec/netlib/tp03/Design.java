package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Design Frame, Add Design Frame, Modify Design
 */
public class Design extends Response
{
	protected Design(int id)
	{
		super(id);
	}

	public Design()
	{
		super(48);
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
	 * The time at which this design was last modified.
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

	public static class CategoriesType extends TPObject<TP03Decoder, TP03Visitor>
	{
		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		public CategoriesType()
		{
		}

		private int category;

		public int getCategory()
		{
			return this.category;
		}

		public void setCategory(int value)
		{
			this.category=value;
		}

		@Override
		public int findByteLength()
		{
			return 0
				 + 4;
		}

		public void write(TPDataOutput out, Connection<TP03Decoder, TP03Visitor> conn) throws IOException
		{
			out.writeInteger(this.category);
		}

		/**
		 * A convenience constructor for easy initialisation of non-read only fields.
		 */
		public CategoriesType(int category)
		{
			setCategory(category);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		@SuppressWarnings("unused")
		CategoriesType(TPDataInput in) throws IOException
		{
			this.category=in.readInteger32();
		}

	}

	private java.util.List<CategoriesType> categories=new java.util.ArrayList<CategoriesType>();

	public java.util.List<CategoriesType> getCategories()
	{
		return this.categories;
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

	private int usage;

	public int getUsage()
	{
		return this.usage;
	}

	public void setUsage(int value)
	{
		this.usage=value;
	}

	private int owner;

	public int getOwner()
	{
		return this.owner;
	}

	public void setOwner(int value)
	{
		this.owner=value;
	}

	public static class ComponentsType extends TPObject<TP03Decoder, TP03Visitor>
	{
		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		public ComponentsType()
		{
		}

		private int componentid;

		public int getComponentid()
		{
			return this.componentid;
		}

		public void setComponentid(int value)
		{
			this.componentid=value;
		}

		private int componentnum;

		public int getComponentnum()
		{
			return this.componentnum;
		}

		public void setComponentnum(int value)
		{
			this.componentnum=value;
		}

		@Override
		public int findByteLength()
		{
			return 0
				 + 4
				 + 4;
		}

		public void write(TPDataOutput out, Connection<TP03Decoder, TP03Visitor> conn) throws IOException
		{
			out.writeInteger(this.componentid);
			out.writeInteger(this.componentnum);
		}

		/**
		 * A convenience constructor for easy initialisation of non-read only fields.
		 */
		public ComponentsType(int componentid, int componentnum)
		{
			setComponentid(componentid);
			setComponentnum(componentnum);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		@SuppressWarnings("unused")
		ComponentsType(TPDataInput in) throws IOException
		{
			this.componentid=in.readInteger32();
			this.componentnum=in.readInteger32();
		}

	}

	private java.util.List<ComponentsType> components=new java.util.ArrayList<ComponentsType>();

	public java.util.List<ComponentsType> getComponents()
	{
		return this.components;
	}

	private String feedback=new String();

	public String getFeedback()
	{
		return this.feedback;
	}

	public void setFeedback(String value)
	{
		this.feedback=value;
	}

	public static class PropertiesType extends TPObject<TP03Decoder, TP03Visitor>
	{
		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		public PropertiesType()
		{
		}

		/**
		 * property id
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

		private String value=new String();

		public String getValue()
		{
			return this.value;
		}

		public void setValue(String value)
		{
			this.value=value;
		}

		@Override
		public int findByteLength()
		{
			return 0
				 + 4
				 + findByteLength(this.value);
		}

		public void write(TPDataOutput out, Connection<TP03Decoder, TP03Visitor> conn) throws IOException
		{
			out.writeInteger(this.id);
			out.writeString(this.value);
		}

		/**
		 * A convenience constructor for easy initialisation of non-read only fields.
		 */
		public PropertiesType(int id, String value)
		{
			setId(id);
			setValue(value);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		@SuppressWarnings("unused")
		PropertiesType(TPDataInput in) throws IOException
		{
			this.id=in.readInteger32();
			this.value=in.readString();
		}

	}

	private java.util.List<PropertiesType> properties=new java.util.ArrayList<PropertiesType>();

	public java.util.List<PropertiesType> getProperties()
	{
		return this.properties;
	}

	@Override
	public void visit(TP03Visitor visitor)
	{
		visitor.handle(this);
	}

	@Override
	public int findByteLength()
	{
		return super.findByteLength()
			 + 4
			 + 8
			 + findByteLength(this.categories)
			 + findByteLength(this.name)
			 + findByteLength(this.description)
			 + 4
			 + 4
			 + findByteLength(this.components)
			 + findByteLength(this.feedback)
			 + findByteLength(this.properties);
	}

	@Override
	public void write(TPDataOutput out, Connection<TP03Decoder, TP03Visitor> conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.id);
		out.writeInteger(this.modtime);
		out.writeInteger(this.categories.size());
		for (CategoriesType object : this.categories)
			object.write(out, conn);
		out.writeString(this.name);
		out.writeString(this.description);
		out.writeInteger(this.usage);
		out.writeInteger(this.owner);
		out.writeInteger(this.components.size());
		for (ComponentsType object : this.components)
			object.write(out, conn);
		out.writeString(this.feedback);
		out.writeInteger(this.properties.size());
		for (PropertiesType object : this.properties)
			object.write(out, conn);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	@SuppressWarnings("unused")
	Design(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.id=in.readInteger32();
		this.modtime=in.readInteger64();
		this.categories.clear();
		for (int length=in.readInteger32(); length > 0; length--)
			this.categories.add(new CategoriesType(in));
		this.name=in.readString();
		this.description=in.readString();
		this.usage=in.readInteger32();
		this.owner=in.readInteger32();
		this.components.clear();
		for (int length=in.readInteger32(); length > 0; length--)
			this.components.add(new ComponentsType(in));
		this.feedback=in.readString();
		this.properties.clear();
		for (int length=in.readInteger32(); length > 0; length--)
			this.properties.add(new PropertiesType(in));
	}

}
