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

	public static class CategoriesType extends TPObject
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

		public int findByteLength()
		{
			return super.findByteLength()
				 + 4;
		}

		public void write(TPDataOutput out, Connection conn) throws IOException
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

		public String toString()
		{
			return "{CategoriesType"
                            +"; category: "
                            +String.valueOf(this.category)
                            +"}";
			
		}

	}

	private java.util.Vector categories = new java.util.Vector();
	
        public java.util.Vector getCategories()
	{
		return this.categories;
	}

	private void setCategories(java.util.Vector value)
	{
                for (int i = 0; i < value.size(); i++){
                    categories.addElement(new CategoriesType((CategoriesType)value.elementAt(i)));
                }
		
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

	public static class PropertiesType extends TPObject
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

		public int findByteLength()
		{
			return super.findByteLength()
				 + 4
				 + findByteLength(this.valuefunc);
		}

		public void write(TPDataOutput out, Connection conn) throws IOException
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
		PropertiesType(TPDataInput in) throws IOException
		{
			this.id=in.readInteger32();
			this.valuefunc=in.readString();
		}

		public String toString()
		{
			return "{PropertiesType"
                            +"; id: "
                            +String.valueOf(this.id)
                            +"; valuefunc: "
                            +String.valueOf(this.valuefunc)
                            +"}";
			
		}

	}

	
        private java.util.Vector properties = new java.util.Vector();
	public java.util.Vector getProperties()
	{
		return this.properties;
	}

	//private void setProperties(java.util.List<PropertiesType> value)
        private void setProperties(java.util.Vector value)
	{
		for(int i = 0; i < value.size(); i ++){
                    this.properties.addElement(new PropertiesType((PropertiesType)value.elementAt(i)));
                }

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
			 + findByteLength(this.categories)
			 + findByteLength(this.name)
			 + findByteLength(this.description)
			 + findByteLength(this.requirements)
			 + findByteLength(this.properties);
	}

	public void write(TPDataOutput out, Connection conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.id);
		out.writeInteger(this.modtime);
		out.writeInteger(this.categories.size());
		for (int i = 0; i < categories.size(); i++){
                    ((CategoriesType)categories.elementAt(i)).write(out, conn);
                }

		out.writeString(this.name);
		out.writeString(this.description);
		out.writeString(this.requirements);
		out.writeInteger(this.properties.size());
                for (int i = 0; i < properties.size(); i++){
                    ((PropertiesType)properties.elementAt(i)).write(out, conn);
                }

	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	Component(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.id=in.readInteger32();
		this.modtime=in.readInteger64();
		this.categories.removeAllElements();
		for (int length=in.readInteger32(); length > 0; length--)
			this.categories.addElement(new CategoriesType(in));
		this.name=in.readString();
		this.description=in.readString();
		this.requirements=in.readString();
		this.properties.removeAllElements();
		for (int length=in.readInteger32(); length > 0; length--)
			this.properties.addElement(new PropertiesType(in));
	}

	public String toString()
	{
		return "{Component"
                    +"; id: "
                    +String.valueOf(this.id)
                    +"; modtime: "
                    +String.valueOf(this.modtime)
                    +"; categories: "
                    +String.valueOf(this.categories)
                    +"; name: "
                    +String.valueOf(this.name)
                    +"; description: "
                    +String.valueOf(this.description)
                    +"; requirements: "
                    +String.valueOf(this.requirements)
                    +"; properties: "
                    +String.valueOf(this.properties)
                    +"; super:"
                    +super.toString()
                    +"}";
		
	}

}
