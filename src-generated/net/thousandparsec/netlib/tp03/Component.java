package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Component
 */
public class Component extends Response
{
	public static final int FRAME_TYPE=55;

	protected Component(int id)
	{
		super(id);
	}

	public Component()
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
	 * The time at which this component was last modified.
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

	public static class CategoriesType extends TPObject<TP03Visitor>
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
			return super.findByteLength()
				 + 4;
		}

		public void write(TPDataOutput out, Connection<?> conn) throws IOException
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
		 * A copy constructor for (among others) deep-copying groups and lists.
		 */
		public CategoriesType(CategoriesType copy)
		{
			setCategory(copy.getCategory());
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		@SuppressWarnings("unused")
		CategoriesType(TPDataInput in) throws IOException
		{
			this.category=in.readInteger32();
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{CategoriesType");
			buf.append("; category: ");
			buf.append(String.valueOf(this.category));
			buf.append("}");
			return buf.toString();
		}

	}

	private java.util.List<CategoriesType> categories=new java.util.ArrayList<CategoriesType>();

	public java.util.List<CategoriesType> getCategories()
	{
		return this.categories;
	}

	@SuppressWarnings("unused")
	private void setCategories(java.util.List<CategoriesType> value)
	{
		for (CategoriesType object : value)
			this.categories.add(new CategoriesType(object));
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

	/**
	 * The requirements for this component, see the TPCL document for more information.
	 */
	private String requirements=new String();

	public String getRequirements()
	{
		return this.requirements;
	}

	public void setRequirements(String value)
	{
		this.requirements=value;
	}

	public static class PropertiesType extends TPObject<TP03Visitor>
	{
		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		public PropertiesType()
		{
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
		 * The value of this property, see the TPCL document for more information.
		 */
		private String valuefunc=new String();

		public String getValuefunc()
		{
			return this.valuefunc;
		}

		public void setValuefunc(String value)
		{
			this.valuefunc=value;
		}

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + 4
				 + findByteLength(this.valuefunc);
		}

		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			out.writeInteger(this.id);
			out.writeString(this.valuefunc);
		}

		/**
		 * A convenience constructor for easy initialisation of non-read only fields.
		 */
		public PropertiesType(int id, String valuefunc)
		{
			setId(id);
			setValuefunc(valuefunc);
		}

		/**
		 * A copy constructor for (among others) deep-copying groups and lists.
		 */
		public PropertiesType(PropertiesType copy)
		{
			setId(copy.getId());
			setValuefunc(copy.getValuefunc());
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		@SuppressWarnings("unused")
		PropertiesType(TPDataInput in) throws IOException
		{
			this.id=in.readInteger32();
			this.valuefunc=in.readString();
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{PropertiesType");
			buf.append("; id: ");
			buf.append(String.valueOf(this.id));
			buf.append("; valuefunc: ");
			buf.append(String.valueOf(this.valuefunc));
			buf.append("}");
			return buf.toString();
		}

	}

	private java.util.List<PropertiesType> properties=new java.util.ArrayList<PropertiesType>();

	public java.util.List<PropertiesType> getProperties()
	{
		return this.properties;
	}

	@SuppressWarnings("unused")
	private void setProperties(java.util.List<PropertiesType> value)
	{
		for (PropertiesType object : value)
			this.properties.add(new PropertiesType(object));
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
			 + 8
			 + findByteLength(this.categories)
			 + findByteLength(this.name)
			 + findByteLength(this.description)
			 + findByteLength(this.requirements)
			 + findByteLength(this.properties);
	}

	@Override
	public void write(TPDataOutput out, Connection<?> conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.id);
		out.writeInteger(this.modtime);
		out.writeInteger(this.categories.size());
		for (CategoriesType object : this.categories)
			object.write(out, conn);
		out.writeString(this.name);
		out.writeString(this.description);
		out.writeString(this.requirements);
		out.writeInteger(this.properties.size());
		for (PropertiesType object : this.properties)
			object.write(out, conn);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	@SuppressWarnings("unused")
	Component(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.id=in.readInteger32();
		this.modtime=in.readInteger64();
		this.categories.clear();
		for (int length=in.readInteger32(); length > 0; length--)
			this.categories.add(new CategoriesType(in));
		this.name=in.readString();
		this.description=in.readString();
		this.requirements=in.readString();
		this.properties.clear();
		for (int length=in.readInteger32(); length > 0; length--)
			this.properties.add(new PropertiesType(in));
	}

	@Override
	public String toString()
	{
		StringBuilder buf=new StringBuilder();
		buf.append("{Component");
		buf.append("; id: ");
		buf.append(String.valueOf(this.id));
		buf.append("; modtime: ");
		buf.append(String.valueOf(this.modtime));
		buf.append("; categories: ");
		buf.append(String.valueOf(this.categories));
		buf.append("; name: ");
		buf.append(String.valueOf(this.name));
		buf.append("; description: ");
		buf.append(String.valueOf(this.description));
		buf.append("; requirements: ");
		buf.append(String.valueOf(this.requirements));
		buf.append("; properties: ");
		buf.append(String.valueOf(this.properties));
		buf.append("; super:").append(super.toString());
		buf.append("}");
		return buf.toString();
	}

}
