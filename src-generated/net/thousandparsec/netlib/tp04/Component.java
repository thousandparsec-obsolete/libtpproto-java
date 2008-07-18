package net.thousandparsec.netlib.tp04;

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

	/**
	 * component ID
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

	public static class CatlistType extends TPObject<TP04Visitor>
	{
		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		public CatlistType()
		{
		}

		private int catid;

		public int getCatid()
		{
			return this.catid;
		}

		public void setCatid(int value)
		{
			this.catid=value;
		}

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + 4;
		}

		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			out.writeInteger(this.catid);
		}

		/**
		 * A convenience constructor for easy initialisation of non-read only fields.
		 */
		public CatlistType(int catid)
		{
			setCatid(catid);
		}

		/**
		 * A copy constructor for (among others) deep-copying groups and lists.
		 */
		public CatlistType(CatlistType copy)
		{
			setCatid(copy.getCatid());
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		CatlistType(TPDataInput in) throws IOException
		{
			this.catid=in.readInteger32();
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{CatlistType");
			buf.append("; catid: ");
			buf.append(String.valueOf(this.catid));
			buf.append("}");
			return buf.toString();
		}

	}

	private java.util.List<CatlistType> catlist=new java.util.ArrayList<CatlistType>();

	public java.util.List<CatlistType> getCatlist()
	{
		return this.catlist;
	}

	@SuppressWarnings("unused")
	private void setCatlist(java.util.List<CatlistType> value)
	{
		for (CatlistType object : value)
			this.catlist.add(new CatlistType(object));
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

	public static class PropertiesType extends TPObject<TP04Visitor>
	{
		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		public PropertiesType()
		{
		}

		/**
		 * Property ID
		 */
		private int propertyid;

		public int getPropertyid()
		{
			return this.propertyid;
		}

		public void setPropertyid(int value)
		{
			this.propertyid=value;
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
			out.writeInteger(this.propertyid);
			out.writeString(this.valuefunc);
		}

		/**
		 * A convenience constructor for easy initialisation of non-read only fields.
		 */
		public PropertiesType(int propertyid, String valuefunc)
		{
			setPropertyid(propertyid);
			setValuefunc(valuefunc);
		}

		/**
		 * A copy constructor for (among others) deep-copying groups and lists.
		 */
		public PropertiesType(PropertiesType copy)
		{
			setPropertyid(copy.getPropertyid());
			setValuefunc(copy.getValuefunc());
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		PropertiesType(TPDataInput in) throws IOException
		{
			this.propertyid=in.readInteger32();
			this.valuefunc=in.readString();
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{PropertiesType");
			buf.append("; propertyid: ");
			buf.append(String.valueOf(this.propertyid));
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
	public void visit(TP04Visitor visitor) throws TPException
	{
		visitor.frame(this);
	}

	@Override
	public int findByteLength()
	{
		return super.findByteLength()
			 + 4
			 + 8
			 + findByteLength(this.catlist)
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
		out.writeInteger(this.catlist.size());
		for (CatlistType object : this.catlist)
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
	Component(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.id=in.readInteger32();
		this.modtime=in.readInteger64();
		this.catlist.clear();
		for (int length=in.readInteger32(); length > 0; length--)
			this.catlist.add(new CatlistType(in));
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
		buf.append("; catlist: ");
		buf.append(String.valueOf(this.catlist));
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
