package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Design Frame, Add Design Frame, Modify Design
 */
public class Design extends Response
{
	public static final int FRAME_TYPE=48;

	protected Design(int id)
	{
		super(id);
	}

	public Design()
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

	public static class ComponentsType extends TPObject<TP03Visitor>
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
			return super.findByteLength()
				 + 4
				 + 4;
		}

		public void write(TPDataOutput out, Connection<?> conn) throws IOException
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
		 * A copy constructor for (among others) deep-copying groups and lists.
		 */
		public ComponentsType(ComponentsType copy)
		{
			setComponentid(copy.getComponentid());
			setComponentnum(copy.getComponentnum());
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		ComponentsType(TPDataInput in) throws IOException
		{
			this.componentid=in.readInteger32();
			this.componentnum=in.readInteger32();
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{ComponentsType");
			buf.append("; componentid: ");
			buf.append(String.valueOf(this.componentid));
			buf.append("; componentnum: ");
			buf.append(String.valueOf(this.componentnum));
			buf.append("}");
			return buf.toString();
		}

	}

	private java.util.List<ComponentsType> components=new java.util.ArrayList<ComponentsType>();

	public java.util.List<ComponentsType> getComponents()
	{
		return this.components;
	}

	@SuppressWarnings("unused")
	private void setComponents(java.util.List<ComponentsType> value)
	{
		for (ComponentsType object : value)
			this.components.add(new ComponentsType(object));
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

	public static class PropertiesType extends TPObject<TP03Visitor>
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
			return super.findByteLength()
				 + 4
				 + findByteLength(this.value);
		}

		public void write(TPDataOutput out, Connection<?> conn) throws IOException
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
		 * A copy constructor for (among others) deep-copying groups and lists.
		 */
		public PropertiesType(PropertiesType copy)
		{
			setId(copy.getId());
			setValue(copy.getValue());
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		PropertiesType(TPDataInput in) throws IOException
		{
			this.id=in.readInteger32();
			this.value=in.readString();
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{PropertiesType");
			buf.append("; id: ");
			buf.append(String.valueOf(this.id));
			buf.append("; value: ");
			buf.append(String.valueOf(this.value));
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
			 + 4
			 + 4
			 + findByteLength(this.components)
			 + findByteLength(this.feedback)
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

	@Override
	public String toString()
	{
		StringBuilder buf=new StringBuilder();
		buf.append("{Design");
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
		buf.append("; usage: ");
		buf.append(String.valueOf(this.usage));
		buf.append("; owner: ");
		buf.append(String.valueOf(this.owner));
		buf.append("; components: ");
		buf.append(String.valueOf(this.components));
		buf.append("; feedback: ");
		buf.append(String.valueOf(this.feedback));
		buf.append("; properties: ");
		buf.append(String.valueOf(this.properties));
		buf.append("; super:").append(super.toString());
		buf.append("}");
		return buf.toString();
	}

}
