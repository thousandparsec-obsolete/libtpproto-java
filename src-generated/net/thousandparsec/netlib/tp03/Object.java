package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Object
 */
public class Object extends Response
{
	protected Object(int id)
	{
		super(id);
	}

	public Object()
	{
		super(7);
	}

	/**
	 * The unquie identifier of the object.
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
	 * The type of the object.
	 */
	private int otype;

	public int getOtype()
	{
		return this.otype;
	}

	public void setOtype(int value)
	{
		this.otype=value;
	}

	/**
	 * The name of the object.
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

	/**
	 * The size of the object (diameter).
	 */
	private long size;

	public long getSize()
	{
		return this.size;
	}

	public void setSize(long value)
	{
		this.size=value;
	}

	public static class PosType extends TPObject<TP03Visitor>
	{
		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		public PosType()
		{
		}

		private long x;

		public long getX()
		{
			return this.x;
		}

		public void setX(long value)
		{
			this.x=value;
		}

		private long y;

		public long getY()
		{
			return this.y;
		}

		public void setY(long value)
		{
			this.y=value;
		}

		private long z;

		public long getZ()
		{
			return this.z;
		}

		public void setZ(long value)
		{
			this.z=value;
		}

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + 8
				 + 8
				 + 8;
		}

		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			out.writeInteger(this.x);
			out.writeInteger(this.y);
			out.writeInteger(this.z);
		}

		/**
		 * A convenience constructor for easy initialisation of non-read only fields.
		 */
		public PosType(long x, long y, long z)
		{
			setX(x);
			setY(y);
			setZ(z);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		@SuppressWarnings("unused")
		PosType(TPDataInput in) throws IOException
		{
			this.x=in.readInteger64();
			this.y=in.readInteger64();
			this.z=in.readInteger64();
		}

	}

	private PosType pos=new PosType();

	public PosType getPos()
	{
		return this.pos;
	}

	public void setPos(PosType value)
	{
		this.pos=value;
	}

	public static class VelType extends TPObject<TP03Visitor>
	{
		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		public VelType()
		{
		}

		private long x;

		public long getX()
		{
			return this.x;
		}

		public void setX(long value)
		{
			this.x=value;
		}

		private long y;

		public long getY()
		{
			return this.y;
		}

		public void setY(long value)
		{
			this.y=value;
		}

		private long z;

		public long getZ()
		{
			return this.z;
		}

		public void setZ(long value)
		{
			this.z=value;
		}

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + 8
				 + 8
				 + 8;
		}

		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			out.writeInteger(this.x);
			out.writeInteger(this.y);
			out.writeInteger(this.z);
		}

		/**
		 * A convenience constructor for easy initialisation of non-read only fields.
		 */
		public VelType(long x, long y, long z)
		{
			setX(x);
			setY(y);
			setZ(z);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		@SuppressWarnings("unused")
		VelType(TPDataInput in) throws IOException
		{
			this.x=in.readInteger64();
			this.y=in.readInteger64();
			this.z=in.readInteger64();
		}

	}

	private VelType vel=new VelType();

	public VelType getVel()
	{
		return this.vel;
	}

	public void setVel(VelType value)
	{
		this.vel=value;
	}

	/**
	 * IDs of the objects contained by this object.
	 */
	public static class ContainsType extends TPObject<TP03Visitor>
	{
		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		public ContainsType()
		{
		}

		/**
		 * the IDs
		 */
		private int ID;

		public int getID()
		{
			return this.ID;
		}

		public void setID(int value)
		{
			this.ID=value;
		}

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + 4;
		}

		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			out.writeInteger(this.ID);
		}

		/**
		 * A convenience constructor for easy initialisation of non-read only fields.
		 */
		public ContainsType(int ID)
		{
			setID(ID);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		@SuppressWarnings("unused")
		ContainsType(TPDataInput in) throws IOException
		{
			this.ID=in.readInteger32();
		}

	}

	private java.util.List<ContainsType> contains=new java.util.ArrayList<ContainsType>();

	public java.util.List<ContainsType> getContains()
	{
		return this.contains;
	}

	/**
	 * The order types that a player can send to this object.
	 */
	public static class OrdertypesType extends TPObject<TP03Visitor>
	{
		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		public OrdertypesType()
		{
		}

		/**
		 * the IDs
		 */
		private int ID;

		public int getID()
		{
			return this.ID;
		}

		public void setID(int value)
		{
			this.ID=value;
		}

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + 4;
		}

		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			out.writeInteger(this.ID);
		}

		/**
		 * A convenience constructor for easy initialisation of non-read only fields.
		 */
		public OrdertypesType(int ID)
		{
			setID(ID);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		@SuppressWarnings("unused")
		OrdertypesType(TPDataInput in) throws IOException
		{
			this.ID=in.readInteger32();
		}

	}

	private java.util.List<OrdertypesType> ordertypes=new java.util.ArrayList<OrdertypesType>();

	public java.util.List<OrdertypesType> getOrdertypes()
	{
		return this.ordertypes;
	}

	/**
	 * number of orders currently 
	 * on this object
	 */
	private int orders;

	public int getOrders()
	{
		return this.orders;
	}

	public void setOrders(int value)
	{
		this.orders=value;
	}

	/**
	 * The time at which this object was last modified.
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

	private byte[] padding=new byte[8];

	public byte[] getPadding()
	{
		return this.padding.clone();
	}

	public void setPadding(byte[] value)
	{
		System.arraycopy(value, 0, this.padding, 0, this.padding.length);
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
			 + 4
			 + findByteLength(this.name)
			 + 8
			 + findByteLength(this.pos)
			 + findByteLength(this.vel)
			 + findByteLength(this.contains)
			 + findByteLength(this.ordertypes)
			 + 4
			 + 8
			 + 8;
	}

	@Override
	public void write(TPDataOutput out, Connection<?> conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.id);
		out.writeInteger(this.otype);
		out.writeString(this.name);
		out.writeInteger(this.size);
		this.pos.write(out, conn);
		this.vel.write(out, conn);
		out.writeInteger(this.contains.size());
		for (ContainsType object : this.contains)
			object.write(out, conn);
		out.writeInteger(this.ordertypes.size());
		for (OrdertypesType object : this.ordertypes)
			object.write(out, conn);
		out.writeInteger(this.orders);
		out.writeInteger(this.modtime);
		out.writeCharacter(this.padding);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	@SuppressWarnings("unused")
	Object(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.id=in.readInteger32();
		this.otype=in.readInteger32();
		this.name=in.readString();
		this.size=in.readInteger64();
		this.pos=new PosType(in);
		this.vel=new VelType(in);
		this.contains.clear();
		for (int length=in.readInteger32(); length > 0; length--)
			this.contains.add(new ContainsType(in));
		this.ordertypes.clear();
		for (int length=in.readInteger32(); length > 0; length--)
			this.ordertypes.add(new OrdertypesType(in));
		this.orders=in.readInteger32();
		this.modtime=in.readInteger64();
		in.readCharacter(this.padding);
	}

}
