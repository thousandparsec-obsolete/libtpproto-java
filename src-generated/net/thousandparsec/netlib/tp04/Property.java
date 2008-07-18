package net.thousandparsec.netlib.tp04;

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

	/**
	 * rank of the property
	 */
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

	private String dispalyname=new String();

	public String getDispalyname()
	{
		return this.dispalyname;
	}

	public void setDispalyname(String value)
	{
		this.dispalyname=value;
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
			 + 4
			 + findByteLength(this.name)
			 + findByteLength(this.dispalyname)
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
		out.writeInteger(this.catlist.size());
		for (CatlistType object : this.catlist)
			object.write(out, conn);
		out.writeInteger(this.rank);
		out.writeString(this.name);
		out.writeString(this.dispalyname);
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
		this.catlist.clear();
		for (int length=in.readInteger32(); length > 0; length--)
			this.catlist.add(new CatlistType(in));
		this.rank=in.readInteger32();
		this.name=in.readString();
		this.dispalyname=in.readString();
		this.description=in.readString();
		this.calculatefunc=in.readString();
		this.requirementfunc=in.readString();
	}

	@Override
	public String toString()
	{
		StringBuilder buf=new StringBuilder();
		buf.append("{Property");
		buf.append("; id: ");
		buf.append(String.valueOf(this.id));
		buf.append("; modtime: ");
		buf.append(String.valueOf(this.modtime));
		buf.append("; catlist: ");
		buf.append(String.valueOf(this.catlist));
		buf.append("; rank: ");
		buf.append(String.valueOf(this.rank));
		buf.append("; name: ");
		buf.append(String.valueOf(this.name));
		buf.append("; dispalyname: ");
		buf.append(String.valueOf(this.dispalyname));
		buf.append("; description: ");
		buf.append(String.valueOf(this.description));
		buf.append("; calculatefunc: ");
		buf.append(String.valueOf(this.calculatefunc));
		buf.append("; requirementfunc: ");
		buf.append(String.valueOf(this.requirementfunc));
		buf.append("; super:").append(super.toString());
		buf.append("}");
		return buf.toString();
	}

}
