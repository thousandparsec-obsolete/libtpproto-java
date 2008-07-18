package net.thousandparsec.netlib.tp04;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Design Frame
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

	/**
	 * Design ID
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

	private int inuse;

	public int getInuse()
	{
		return this.inuse;
	}

	public void setInuse(int value)
	{
		this.inuse=value;
	}

	/**
	 * owner of the design
	 */
	private int owner;

	public int getOwner()
	{
		return this.owner;
	}

	public void setOwner(int value)
	{
		this.owner=value;
	}

	public static class ComponentsType extends TPObject<TP04Visitor>
	{
		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		public ComponentsType()
		{
		}

		/**
		 * component id
		 */
		private int componentid;

		public int getComponentid()
		{
			return this.componentid;
		}

		public void setComponentid(int value)
		{
			this.componentid=value;
		}

		/**
		 * the number of components
		 */
		private int compquant;

		public int getCompquant()
		{
			return this.compquant;
		}

		public void setCompquant(int value)
		{
			this.compquant=value;
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
			out.writeInteger(this.compquant);
		}

		/**
		 * A convenience constructor for easy initialisation of non-read only fields.
		 */
		public ComponentsType(int componentid, int compquant)
		{
			setComponentid(componentid);
			setCompquant(compquant);
		}

		/**
		 * A copy constructor for (among others) deep-copying groups and lists.
		 */
		public ComponentsType(ComponentsType copy)
		{
			setComponentid(copy.getComponentid());
			setCompquant(copy.getCompquant());
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		ComponentsType(TPDataInput in) throws IOException
		{
			this.componentid=in.readInteger32();
			this.compquant=in.readInteger32();
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{ComponentsType");
			buf.append("; componentid: ");
			buf.append(String.valueOf(this.componentid));
			buf.append("; compquant: ");
			buf.append(String.valueOf(this.compquant));
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

	public static class PropertiesType extends TPObject<TP04Visitor>
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
		private int propertyid;

		public int getPropertyid()
		{
			return this.propertyid;
		}

		public void setPropertyid(int value)
		{
			this.propertyid=value;
		}

		private String displaystring=new String();

		public String getDisplaystring()
		{
			return this.displaystring;
		}

		public void setDisplaystring(String value)
		{
			this.displaystring=value;
		}

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + 4
				 + findByteLength(this.displaystring);
		}

		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			out.writeInteger(this.propertyid);
			out.writeString(this.displaystring);
		}

		/**
		 * A convenience constructor for easy initialisation of non-read only fields.
		 */
		public PropertiesType(int propertyid, String displaystring)
		{
			setPropertyid(propertyid);
			setDisplaystring(displaystring);
		}

		/**
		 * A copy constructor for (among others) deep-copying groups and lists.
		 */
		public PropertiesType(PropertiesType copy)
		{
			setPropertyid(copy.getPropertyid());
			setDisplaystring(copy.getDisplaystring());
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		PropertiesType(TPDataInput in) throws IOException
		{
			this.propertyid=in.readInteger32();
			this.displaystring=in.readString();
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{PropertiesType");
			buf.append("; propertyid: ");
			buf.append(String.valueOf(this.propertyid));
			buf.append("; displaystring: ");
			buf.append(String.valueOf(this.displaystring));
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
		out.writeInteger(this.catlist.size());
		for (CatlistType object : this.catlist)
			object.write(out, conn);
		out.writeString(this.name);
		out.writeString(this.description);
		out.writeInteger(this.inuse);
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
		this.catlist.clear();
		for (int length=in.readInteger32(); length > 0; length--)
			this.catlist.add(new CatlistType(in));
		this.name=in.readString();
		this.description=in.readString();
		this.inuse=in.readInteger32();
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
		buf.append("; catlist: ");
		buf.append(String.valueOf(this.catlist));
		buf.append("; name: ");
		buf.append(String.valueOf(this.name));
		buf.append("; description: ");
		buf.append(String.valueOf(this.description));
		buf.append("; inuse: ");
		buf.append(String.valueOf(this.inuse));
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
