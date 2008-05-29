package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Property
 */
public class Property extends Response
{
	public static final int FRAME_TYPE=59;

	protected Property(int id)
	{
		super(id);
	}

	public Property()
	{
		super(FRAME_TYPE);
	}

	/**
	 * property ID
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
	 * The time at which this property was last modified.
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
                            + "; category: "
                            + String.valueOf(this.category)
                            + "}";
			
		}

	}

	private java.util.Vector categories=new java.util.Vector();

	public java.util.Vector getCategories()
	{
		return this.categories;
	}

	
	private void setCategories(java.util.Vector value)
	{
                for (int i = 0; i < value.size(); i ++){
                    this.categories.addElement(new CategoriesType((CategoriesType)value.elementAt(i)));
                }

	}

	private int rank;

	public int getRank()
	{
		return this.rank;
	}

	public void setRank(int value)
	{
		this.rank=value;
	}

	/**
	 * A valid TPCL identifier for this property.
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

	private String displayname=new String();

	public String getDisplayname()
	{
		return this.displayname;
	}

	public void setDisplayname(String value)
	{
		this.displayname=value;
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
	 * Calculates the value of this funcion, see the TPCL document for more information.
	 */
	private String calculatefunc=new String();

	public String getCalculatefunc()
	{
		return this.calculatefunc;
	}

	public void setCalculatefunc(String value)
	{
		this.calculatefunc=value;
	}

	/**
	 * Checks the requirements for this property, see the TPCL document for more information.
	 */
	private String requirementfunc=new String();

	public String getRequirementfunc()
	{
		return this.requirementfunc;
	}

	public void setRequirementfunc(String value)
	{
		this.requirementfunc=value;
	}
        public void visit(Visitor visitor) throws TPException
        {
            System.out.println("warning: visit method called in Property.java");
            visit(visitor);
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
			 + 4
			 + findByteLength(this.name)
			 + findByteLength(this.displayname)
			 + findByteLength(this.description)
			 + findByteLength(this.calculatefunc)
			 + findByteLength(this.requirementfunc);
	}

	
	public void write(TPDataOutput out, Connection conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.id);
		out.writeInteger(this.modtime);
		out.writeInteger(this.categories.size());
                for (int i =0; i < categories.size(); i ++){
                    ((CategoriesType)categories.elementAt(i)).write(out, conn);
                }
		out.writeInteger(this.rank);
		out.writeString(this.name);
		out.writeString(this.displayname);
		out.writeString(this.description);
		out.writeString(this.calculatefunc);
		out.writeString(this.requirementfunc);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	
	Property(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.id=in.readInteger32();
		this.modtime=in.readInteger64();
		this.categories.removeAllElements();
		for (int length=in.readInteger32(); length > 0; length--)
			this.categories.addElement(new CategoriesType(in));
		this.rank=in.readInteger32();
		this.name=in.readString();
		this.displayname=in.readString();
		this.description=in.readString();
		this.calculatefunc=in.readString();
		this.requirementfunc=in.readString();
	}

	
	public String toString()
	{
		return "{Property"
                    + "; id: "
                    + String.valueOf(this.id)
                    + "; modtime: "
                    + String.valueOf(this.modtime)
                    + "; categories: "
                    + String.valueOf(this.categories)
                    + "; rank: "
                    + String.valueOf(this.rank)
                    + "; name: "
                    + String.valueOf(this.name)
                    + "; displayname: "
                    + String.valueOf(this.displayname)
                    + "; description: "
                    + String.valueOf(this.description)
                    + "; calculatefunc: "
                    + String.valueOf(this.calculatefunc)
                    + "; requirementfunc: "
                    + String.valueOf(this.requirementfunc)
                    + "; super:"
                    + super.toString()
                    + "}";
		
	}

}
