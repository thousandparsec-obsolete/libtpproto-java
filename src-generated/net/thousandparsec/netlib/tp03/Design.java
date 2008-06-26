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
                for(int i = 0; i < value.size(); i++){
                    this.categories.addElement(new CategoriesType((CategoriesType)value.elementAt(i)));
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

	public static class ComponentsType extends TPObject
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


		public int findByteLength()
		{
			return super.findByteLength()
				 + 4
				 + 4;
		}

		public void write(TPDataOutput out, Connection conn) throws IOException
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


		public String toString()
		{
			return "{ComponentsType"
                            +"; componentid: "
                            +String.valueOf(this.componentid)
                            +"; componentnum: "
                            +String.valueOf(this.componentnum)
                            +"}";
			
		}

	}

	private java.util.Vector components = new java.util.Vector();
	public java.util.Vector getComponents()
	{
		return this.components;
	}


	private void setComponents(java.util.Vector value)
	{
		for(int i = 0; i < value.size(); i ++){
                    this.components.addElement(new ComponentsType((ComponentsType)value.elementAt(i)));
                }

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

	public static class PropertiesType extends TPObject
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

		public int findByteLength()
		{
			return super.findByteLength()
				 + 4
				 + findByteLength(this.value);
		}

		public void write(TPDataOutput out, Connection conn) throws IOException
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

		public String toString()
		{
			return "{PropertiesType"
                            +"; id: "
                            +String.valueOf(this.id)
                            +"; value: "
                            +String.valueOf(this.value)
                            +"}";
			
		}

	}

	private java.util.Vector properties = new java.util.Vector();
	public java.util.Vector getProperties()
	{
		return this.properties;
	}

	private void setProperties(java.util.Vector value)
	{
                for (int i = 0; i < value.size(); i++){
                    this.properties.addElement(new PropertiesType((PropertiesType)value.elementAt(i)));
                }

	}
        public void visit(Visitor visitor) throws TPException
        {
            System.out.println("warning: visit method called in Design.java");
            visit((TP03Visitor)visitor);
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
			 + 4
			 + 4
			 + findByteLength(this.components)
			 + findByteLength(this.feedback)
			 + findByteLength(this.properties);
	}


	public void write(TPDataOutput out, Connection conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.id);
		out.writeInteger(this.modtime);
		out.writeInteger(this.categories.size());
                for(int i = 0; i < categories.size(); i++){
                    ((CategoriesType)categories.elementAt(i)).write(out, conn);
                }
		out.writeString(this.name);
		out.writeString(this.description);
		out.writeInteger(this.usage);
		out.writeInteger(this.owner);
		out.writeInteger(this.components.size());
		for(int i = 0; i < components.size(); i++){
                    ((ComponentsType)components.elementAt(i)).write(out, conn);
                }
		out.writeString(this.feedback);
		out.writeInteger(this.properties.size());
                for(int i = 0; i < properties.size(); i ++){
                    ((PropertiesType)properties.elementAt(i)).write(out, conn);
                }

	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */

	Design(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.id=in.readInteger32();
		this.modtime=in.readInteger64();
		this.categories.removeAllElements();
		for (int length=in.readInteger32(); length > 0; length--)
			this.categories.addElement(new CategoriesType(in));
		this.name=in.readString();
		this.description=in.readString();
		this.usage=in.readInteger32();
		this.owner=in.readInteger32();
		this.components.removeAllElements();
		for (int length=in.readInteger32(); length > 0; length--)
			this.components.addElement(new ComponentsType(in));
		this.feedback=in.readString();
		this.properties.removeAllElements();
		for (int length=in.readInteger32(); length > 0; length--)
			this.properties.addElement(new PropertiesType(in));
	}

	public String toString()
	{
		return "{Design"
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
                    +"; usage: "
                    +String.valueOf(this.usage)
                    +"; owner: "
                    +String.valueOf(this.owner)
                    +"; components: "
                    +String.valueOf(this.components)
                    +"; feedback: "
                    +String.valueOf(this.feedback)
                    +"; properties: "
                    +String.valueOf(this.properties)
                    +"; super:"
                    +super.toString()
                    +"}";
		
	}

}
