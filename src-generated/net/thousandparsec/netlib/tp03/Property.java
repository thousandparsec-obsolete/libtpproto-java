package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Property
 */
public class Property extends Response
{
	public static final int FRAME_ID=59;

	protected Property(int id)
	{
		super(id);
	}

	public Property()
	{
		super(FRAME_ID);
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
			 + 4
			 + findByteLength(this.name)
			 + findByteLength(this.displayname)
			 + findByteLength(this.description)
			 + findByteLength(this.calculatefunc)
			 + findByteLength(this.requirementfunc);
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
	@SuppressWarnings("unused")
	Property(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.id=in.readInteger32();
		this.modtime=in.readInteger64();
		this.categories.clear();
		for (int length=in.readInteger32(); length > 0; length--)
			this.categories.add(new CategoriesType(in));
		this.rank=in.readInteger32();
		this.name=in.readString();
		this.displayname=in.readString();
		this.description=in.readString();
		this.calculatefunc=in.readString();
		this.requirementfunc=in.readString();
	}

}
