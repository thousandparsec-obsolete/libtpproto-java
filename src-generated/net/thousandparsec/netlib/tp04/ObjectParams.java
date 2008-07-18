package net.thousandparsec.netlib.tp04;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * These are the parameters for Objects.
 */
public class ObjectParams extends TPObject<TP04Visitor> implements Visitable<TP04Visitor>
{
	private final int id;

	protected ObjectParams(int id)
	{
		this.id=id;
	}

	@SuppressWarnings("unused")
	ObjectParams(int id, TPDataInput in) throws IOException
	{
		this(id);
		//nothing to read
	}

	public int getParameterType()
	{
		return id;
	}

	@Override
	public int findByteLength()
	{
		return 0;
	}

	public void write(TPDataOutput out, Connection<?> conn) throws IOException
	{
		//NOP
	}

	public void visit(TP04Visitor visitor) throws TPException
	{
		throw new RuntimeException();
	}

	/**
	 * A vector for the position.
	 */
	public static class ObjectParamPosition3d extends ObjectParams
	{
		public static final int PARAM_TYPE=0;

		/**
		 * A default constructor for subclasses, which initialises properties to their defaults.
		 */
		protected ObjectParamPosition3d(int id)
		{
			super(id);
		}

		/**
		 * A default constructor for general public, which initialises properties to their defaults.
		 */
		public ObjectParamPosition3d()
		{
			super(PARAM_TYPE);
		}

		public static class PositionType extends TPObject<TP04Visitor>
		{
			/**
			 * A default constructor which initialises properties to their defaults.
			 */
			public PositionType()
			{
			}

			private long x;

			public long getX()
			{
				return this.x;
			}

			@SuppressWarnings("unused")
			private void setX(long value)
			{
				this.x=value;
			}

			private long y;

			public long getY()
			{
				return this.y;
			}

			@SuppressWarnings("unused")
			private void setY(long value)
			{
				this.y=value;
			}

			private long z;

			public long getZ()
			{
				return this.z;
			}

			@SuppressWarnings("unused")
			private void setZ(long value)
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
			 * A copy constructor for (among others) deep-copying groups and lists.
			 */
			public PositionType(PositionType copy)
			{
				setX(copy.getX());
				setY(copy.getY());
				setZ(copy.getZ());
			}

			/**
			 * A special "internal" constructor that reads contents from a stream.
			 */
			PositionType(TPDataInput in) throws IOException
			{
				this.x=in.readInteger64();
				this.y=in.readInteger64();
				this.z=in.readInteger64();
			}

			@Override
			public String toString()
			{
				StringBuilder buf=new StringBuilder();
				buf.append("{PositionType");
				buf.append("; x: ");
				buf.append(String.valueOf(this.x));
				buf.append("; y: ");
				buf.append(String.valueOf(this.y));
				buf.append("; z: ");
				buf.append(String.valueOf(this.z));
				buf.append("}");
				return buf.toString();
			}

		}

		private PositionType position=new PositionType();

		public PositionType getPosition()
		{
			return this.position;
		}

		/**
		 * NOTE: this method does not copy the value object.
		 */
		public void setPosition(PositionType value)
		{
			this.position=value;
		}

		/**
		 * The object ID this position is relative to.
		 */
		private int relative;

		public int getRelative()
		{
			return this.relative;
		}

		@SuppressWarnings("unused")
		private void setRelative(int value)
		{
			this.relative=value;
		}

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + findByteLength(this.position)
				 + 4;
		}

		@Override
		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			super.write(out, conn);
			this.position.write(out, conn);
			out.writeInteger(this.relative);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		ObjectParamPosition3d(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.position=new PositionType(in);
			this.relative=in.readInteger32();
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{ObjectParamPosition3d");
			buf.append("; position: ");
			buf.append(String.valueOf(this.position));
			buf.append("; relative: ");
			buf.append(String.valueOf(this.relative));
			buf.append("}");
			return buf.toString();
		}

		@Override
		public void visit(TP04Visitor visitor) throws TPException
		{
			visitor.objectParams(this);
		}

	}

	/**
	 * A vector for the velocity.
	 */
	public static class ObjectParamVelocity3d extends ObjectParams
	{
		public static final int PARAM_TYPE=1;

		/**
		 * A default constructor for subclasses, which initialises properties to their defaults.
		 */
		protected ObjectParamVelocity3d(int id)
		{
			super(id);
		}

		/**
		 * A default constructor for general public, which initialises properties to their defaults.
		 */
		public ObjectParamVelocity3d()
		{
			super(PARAM_TYPE);
		}

		public static class VelocityType extends TPObject<TP04Visitor>
		{
			/**
			 * A default constructor which initialises properties to their defaults.
			 */
			public VelocityType()
			{
			}

			private long x;

			public long getX()
			{
				return this.x;
			}

			@SuppressWarnings("unused")
			private void setX(long value)
			{
				this.x=value;
			}

			private long y;

			public long getY()
			{
				return this.y;
			}

			@SuppressWarnings("unused")
			private void setY(long value)
			{
				this.y=value;
			}

			private long z;

			public long getZ()
			{
				return this.z;
			}

			@SuppressWarnings("unused")
			private void setZ(long value)
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
			 * A copy constructor for (among others) deep-copying groups and lists.
			 */
			public VelocityType(VelocityType copy)
			{
				setX(copy.getX());
				setY(copy.getY());
				setZ(copy.getZ());
			}

			/**
			 * A special "internal" constructor that reads contents from a stream.
			 */
			VelocityType(TPDataInput in) throws IOException
			{
				this.x=in.readInteger64();
				this.y=in.readInteger64();
				this.z=in.readInteger64();
			}

			@Override
			public String toString()
			{
				StringBuilder buf=new StringBuilder();
				buf.append("{VelocityType");
				buf.append("; x: ");
				buf.append(String.valueOf(this.x));
				buf.append("; y: ");
				buf.append(String.valueOf(this.y));
				buf.append("; z: ");
				buf.append(String.valueOf(this.z));
				buf.append("}");
				return buf.toString();
			}

		}

		private VelocityType velocity=new VelocityType();

		public VelocityType getVelocity()
		{
			return this.velocity;
		}

		/**
		 * NOTE: this method does not copy the value object.
		 */
		public void setVelocity(VelocityType value)
		{
			this.velocity=value;
		}

		/**
		 * The object ID this vector is relative to.
		 */
		private int relative;

		public int getRelative()
		{
			return this.relative;
		}

		@SuppressWarnings("unused")
		private void setRelative(int value)
		{
			this.relative=value;
		}

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + findByteLength(this.velocity)
				 + 4;
		}

		@Override
		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			super.write(out, conn);
			this.velocity.write(out, conn);
			out.writeInteger(this.relative);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		ObjectParamVelocity3d(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.velocity=new VelocityType(in);
			this.relative=in.readInteger32();
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{ObjectParamVelocity3d");
			buf.append("; velocity: ");
			buf.append(String.valueOf(this.velocity));
			buf.append("; relative: ");
			buf.append(String.valueOf(this.relative));
			buf.append("}");
			return buf.toString();
		}

		@Override
		public void visit(TP04Visitor visitor) throws TPException
		{
			visitor.objectParams(this);
		}

	}

	/**
	 * A vector for the acceleration.
	 */
	public static class ObjectParamAcceleration3d extends ObjectParams
	{
		public static final int PARAM_TYPE=2;

		/**
		 * A default constructor for subclasses, which initialises properties to their defaults.
		 */
		protected ObjectParamAcceleration3d(int id)
		{
			super(id);
		}

		/**
		 * A default constructor for general public, which initialises properties to their defaults.
		 */
		public ObjectParamAcceleration3d()
		{
			super(PARAM_TYPE);
		}

		public static class VectorType extends TPObject<TP04Visitor>
		{
			/**
			 * A default constructor which initialises properties to their defaults.
			 */
			public VectorType()
			{
			}

			private long x;

			public long getX()
			{
				return this.x;
			}

			@SuppressWarnings("unused")
			private void setX(long value)
			{
				this.x=value;
			}

			private long y;

			public long getY()
			{
				return this.y;
			}

			@SuppressWarnings("unused")
			private void setY(long value)
			{
				this.y=value;
			}

			private long z;

			public long getZ()
			{
				return this.z;
			}

			@SuppressWarnings("unused")
			private void setZ(long value)
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
			 * A copy constructor for (among others) deep-copying groups and lists.
			 */
			public VectorType(VectorType copy)
			{
				setX(copy.getX());
				setY(copy.getY());
				setZ(copy.getZ());
			}

			/**
			 * A special "internal" constructor that reads contents from a stream.
			 */
			VectorType(TPDataInput in) throws IOException
			{
				this.x=in.readInteger64();
				this.y=in.readInteger64();
				this.z=in.readInteger64();
			}

			@Override
			public String toString()
			{
				StringBuilder buf=new StringBuilder();
				buf.append("{VectorType");
				buf.append("; x: ");
				buf.append(String.valueOf(this.x));
				buf.append("; y: ");
				buf.append(String.valueOf(this.y));
				buf.append("; z: ");
				buf.append(String.valueOf(this.z));
				buf.append("}");
				return buf.toString();
			}

		}

		private VectorType vector=new VectorType();

		public VectorType getVector()
		{
			return this.vector;
		}

		/**
		 * NOTE: this method does not copy the value object.
		 */
		public void setVector(VectorType value)
		{
			this.vector=value;
		}

		/**
		 * The object ID this vector is relative to.
		 */
		private int relative;

		public int getRelative()
		{
			return this.relative;
		}

		@SuppressWarnings("unused")
		private void setRelative(int value)
		{
			this.relative=value;
		}

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + findByteLength(this.vector)
				 + 4;
		}

		@Override
		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			super.write(out, conn);
			this.vector.write(out, conn);
			out.writeInteger(this.relative);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		ObjectParamAcceleration3d(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.vector=new VectorType(in);
			this.relative=in.readInteger32();
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{ObjectParamAcceleration3d");
			buf.append("; vector: ");
			buf.append(String.valueOf(this.vector));
			buf.append("; relative: ");
			buf.append(String.valueOf(this.relative));
			buf.append("}");
			return buf.toString();
		}

		@Override
		public void visit(TP04Visitor visitor) throws TPException
		{
			visitor.objectParams(this);
		}

	}

	/**
	 * Object is bound to ('in') an Object.
	 */
	public static class ObjectParamBoundPosition extends ObjectParams
	{
		public static final int PARAM_TYPE=3;

		/**
		 * A default constructor for subclasses, which initialises properties to their defaults.
		 */
		protected ObjectParamBoundPosition(int id)
		{
			super(id);
		}

		/**
		 * A default constructor for general public, which initialises properties to their defaults.
		 */
		public ObjectParamBoundPosition()
		{
			super(PARAM_TYPE);
		}

		/**
		 * The slot in the parent object this object is in.
		 */
		private int slot;

		public int getSlot()
		{
			return this.slot;
		}

		@SuppressWarnings("unused")
		private void setSlot(int value)
		{
			this.slot=value;
		}

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + 4;
		}

		@Override
		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			super.write(out, conn);
			out.writeInteger(this.slot);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		ObjectParamBoundPosition(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.slot=in.readInteger32();
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{ObjectParamBoundPosition");
			buf.append("; slot: ");
			buf.append(String.valueOf(this.slot));
			buf.append("}");
			return buf.toString();
		}

		@Override
		public void visit(TP04Visitor visitor) throws TPException
		{
			visitor.objectParams(this);
		}

	}

	/**
	 * An order queue.
	 */
	public static class ObjectParamOrderQueue extends ObjectParams
	{
		public static final int PARAM_TYPE=4;

		/**
		 * A default constructor for subclasses, which initialises properties to their defaults.
		 */
		protected ObjectParamOrderQueue(int id)
		{
			super(id);
		}

		/**
		 * A default constructor for general public, which initialises properties to their defaults.
		 */
		public ObjectParamOrderQueue()
		{
			super(PARAM_TYPE);
		}

		/**
		 * The ID number of the queue.
		 */
		private int queueid;

		public int getQueueid()
		{
			return this.queueid;
		}

		@SuppressWarnings("unused")
		private void setQueueid(int value)
		{
			this.queueid=value;
		}

		/**
		 * The number of orders in the queue.
		 */
		private int numorders;

		public int getNumorders()
		{
			return this.numorders;
		}

		@SuppressWarnings("unused")
		private void setNumorders(int value)
		{
			this.numorders=value;
		}

		/**
		 * A list of order types that can be put in this queue.
		 */
		public static class OrdertypesType extends TPObject<TP04Visitor>
		{
			/**
			 * A default constructor which initialises properties to their defaults.
			 */
			public OrdertypesType()
			{
			}

			/**
			 * The number of the order type.
			 */
			private int ordertype;

			public int getOrdertype()
			{
				return this.ordertype;
			}

			@SuppressWarnings("unused")
			private void setOrdertype(int value)
			{
				this.ordertype=value;
			}

			@Override
			public int findByteLength()
			{
				return super.findByteLength()
					 + 4;
			}

			public void write(TPDataOutput out, Connection<?> conn) throws IOException
			{
				out.writeInteger(this.ordertype);
			}

			/**
			 * A copy constructor for (among others) deep-copying groups and lists.
			 */
			public OrdertypesType(OrdertypesType copy)
			{
				setOrdertype(copy.getOrdertype());
			}

			/**
			 * A special "internal" constructor that reads contents from a stream.
			 */
			OrdertypesType(TPDataInput in) throws IOException
			{
				this.ordertype=in.readInteger32();
			}

			@Override
			public String toString()
			{
				StringBuilder buf=new StringBuilder();
				buf.append("{OrdertypesType");
				buf.append("; ordertype: ");
				buf.append(String.valueOf(this.ordertype));
				buf.append("}");
				return buf.toString();
			}

		}

		private java.util.List<OrdertypesType> ordertypes=new java.util.ArrayList<OrdertypesType>();

		public java.util.List<OrdertypesType> getOrdertypes()
		{
			return this.ordertypes;
		}

		@SuppressWarnings("unused")
		private void setOrdertypes(java.util.List<OrdertypesType> value)
		{
			for (OrdertypesType object : value)
				this.ordertypes.add(new OrdertypesType(object));
		}

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + 4
				 + 4
				 + findByteLength(this.ordertypes);
		}

		@Override
		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			super.write(out, conn);
			out.writeInteger(this.queueid);
			out.writeInteger(this.numorders);
			out.writeInteger(this.ordertypes.size());
			for (OrdertypesType object : this.ordertypes)
				object.write(out, conn);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		ObjectParamOrderQueue(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.queueid=in.readInteger32();
			this.numorders=in.readInteger32();
			this.ordertypes.clear();
			for (int length=in.readInteger32(); length > 0; length--)
				this.ordertypes.add(new OrdertypesType(in));
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{ObjectParamOrderQueue");
			buf.append("; queueid: ");
			buf.append(String.valueOf(this.queueid));
			buf.append("; numorders: ");
			buf.append(String.valueOf(this.numorders));
			buf.append("; ordertypes: ");
			buf.append(String.valueOf(this.ordertypes));
			buf.append("}");
			return buf.toString();
		}

		@Override
		public void visit(TP04Visitor visitor) throws TPException
		{
			visitor.objectParams(this);
		}

	}

	/**
	 * A list of resources.
	 */
	public static class ObjectParamResourceList extends ObjectParams
	{
		public static final int PARAM_TYPE=5;

		/**
		 * A default constructor for subclasses, which initialises properties to their defaults.
		 */
		protected ObjectParamResourceList(int id)
		{
			super(id);
		}

		/**
		 * A default constructor for general public, which initialises properties to their defaults.
		 */
		public ObjectParamResourceList()
		{
			super(PARAM_TYPE);
		}

		/**
		 * A list of resources.
		 */
		public static class ResourcesType extends TPObject<TP04Visitor>
		{
			/**
			 * A default constructor which initialises properties to their defaults.
			 */
			public ResourcesType()
			{
			}

			/**
			 * The Resource ID
			 */
			private int resourceid;

			public int getResourceid()
			{
				return this.resourceid;
			}

			@SuppressWarnings("unused")
			private void setResourceid(int value)
			{
				this.resourceid=value;
			}

			/**
			 * The amount on hand currently.
			 */
			private int stored;

			public int getStored()
			{
				return this.stored;
			}

			@SuppressWarnings("unused")
			private void setStored(int value)
			{
				this.stored=value;
			}

			/**
			 * The amount of the resource that is minable or creatable.
			 */
			private int minable;

			public int getMinable()
			{
				return this.minable;
			}

			@SuppressWarnings("unused")
			private void setMinable(int value)
			{
				this.minable=value;
			}

			/**
			 * The amount that is not yet minable or creatable.
			 */
			private int unavailable;

			public int getUnavailable()
			{
				return this.unavailable;
			}

			@SuppressWarnings("unused")
			private void setUnavailable(int value)
			{
				this.unavailable=value;
			}

			@Override
			public int findByteLength()
			{
				return super.findByteLength()
					 + 4
					 + 4
					 + 4
					 + 4;
			}

			public void write(TPDataOutput out, Connection<?> conn) throws IOException
			{
				out.writeInteger(this.resourceid);
				out.writeInteger(this.stored);
				out.writeInteger(this.minable);
				out.writeInteger(this.unavailable);
			}

			/**
			 * A copy constructor for (among others) deep-copying groups and lists.
			 */
			public ResourcesType(ResourcesType copy)
			{
				setResourceid(copy.getResourceid());
				setStored(copy.getStored());
				setMinable(copy.getMinable());
				setUnavailable(copy.getUnavailable());
			}

			/**
			 * A special "internal" constructor that reads contents from a stream.
			 */
			ResourcesType(TPDataInput in) throws IOException
			{
				this.resourceid=in.readInteger32();
				this.stored=in.readInteger32();
				this.minable=in.readInteger32();
				this.unavailable=in.readInteger32();
			}

			@Override
			public String toString()
			{
				StringBuilder buf=new StringBuilder();
				buf.append("{ResourcesType");
				buf.append("; resourceid: ");
				buf.append(String.valueOf(this.resourceid));
				buf.append("; stored: ");
				buf.append(String.valueOf(this.stored));
				buf.append("; minable: ");
				buf.append(String.valueOf(this.minable));
				buf.append("; unavailable: ");
				buf.append(String.valueOf(this.unavailable));
				buf.append("}");
				return buf.toString();
			}

		}

		private java.util.List<ResourcesType> resources=new java.util.ArrayList<ResourcesType>();

		public java.util.List<ResourcesType> getResources()
		{
			return this.resources;
		}

		@SuppressWarnings("unused")
		private void setResources(java.util.List<ResourcesType> value)
		{
			for (ResourcesType object : value)
				this.resources.add(new ResourcesType(object));
		}

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + findByteLength(this.resources);
		}

		@Override
		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			super.write(out, conn);
			out.writeInteger(this.resources.size());
			for (ResourcesType object : this.resources)
				object.write(out, conn);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		ObjectParamResourceList(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.resources.clear();
			for (int length=in.readInteger32(); length > 0; length--)
				this.resources.add(new ResourcesType(in));
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{ObjectParamResourceList");
			buf.append("; resources: ");
			buf.append(String.valueOf(this.resources));
			buf.append("}");
			return buf.toString();
		}

		@Override
		public void visit(TP04Visitor visitor) throws TPException
		{
			visitor.objectParams(this);
		}

	}

	/**
	 * A generic reference to something.
	 */
	public static class ObjectParmReference extends ObjectParams
	{
		public static final int PARAM_TYPE=6;

		/**
		 * A default constructor for subclasses, which initialises properties to their defaults.
		 */
		protected ObjectParmReference(int id)
		{
			super(id);
		}

		/**
		 * A default constructor for general public, which initialises properties to their defaults.
		 */
		public ObjectParmReference()
		{
			super(PARAM_TYPE);
		}

		/**
		 * type of thing being referenced
		 */
		private int type;

		public int getType()
		{
			return this.type;
		}

		public void setType(int value)
		{
			this.type=value;
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

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + 4
				 + 4;
		}

		@Override
		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			super.write(out, conn);
			out.writeInteger(this.type);
			out.writeInteger(this.id);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		ObjectParmReference(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.type=in.readInteger32();
			this.id=in.readInteger32();
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{ObjectParmReference");
			buf.append("; type: ");
			buf.append(String.valueOf(this.type));
			buf.append("; id: ");
			buf.append(String.valueOf(this.id));
			buf.append("}");
			return buf.toString();
		}

		@Override
		public void visit(TP04Visitor visitor) throws TPException
		{
			visitor.objectParams(this);
		}

	}

	/**
	 * Gives a list of references and how many of each of them.
	 */
	public static class ObjectParamReferenceQuantityList extends ObjectParams
	{
		public static final int PARAM_TYPE=7;

		/**
		 * A default constructor for subclasses, which initialises properties to their defaults.
		 */
		protected ObjectParamReferenceQuantityList(int id)
		{
			super(id);
		}

		/**
		 * A default constructor for general public, which initialises properties to their defaults.
		 */
		public ObjectParamReferenceQuantityList()
		{
			super(PARAM_TYPE);
		}

		/**
		 * A list of as described in the Generic Reference System
		 */
		public static class ReferencesType extends TPObject<TP04Visitor>
		{
			/**
			 * A default constructor which initialises properties to their defaults.
			 */
			public ReferencesType()
			{
			}

			/**
			 * type of thing being referenced
			 */
			private int type;

			public int getType()
			{
				return this.type;
			}

			public void setType(int value)
			{
				this.type=value;
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

			private int number;

			public int getNumber()
			{
				return this.number;
			}

			public void setNumber(int value)
			{
				this.number=value;
			}

			@Override
			public int findByteLength()
			{
				return super.findByteLength()
					 + 4
					 + 4
					 + 4;
			}

			public void write(TPDataOutput out, Connection<?> conn) throws IOException
			{
				out.writeInteger(this.type);
				out.writeInteger(this.id);
				out.writeInteger(this.number);
			}

			/**
			 * A convenience constructor for easy initialisation of non-read only fields.
			 */
			public ReferencesType(int type, int id, int number)
			{
				setType(type);
				setId(id);
				setNumber(number);
			}

			/**
			 * A copy constructor for (among others) deep-copying groups and lists.
			 */
			public ReferencesType(ReferencesType copy)
			{
				setType(copy.getType());
				setId(copy.getId());
				setNumber(copy.getNumber());
			}

			/**
			 * A special "internal" constructor that reads contents from a stream.
			 */
			ReferencesType(TPDataInput in) throws IOException
			{
				this.type=in.readInteger32();
				this.id=in.readInteger32();
				this.number=in.readInteger32();
			}

			@Override
			public String toString()
			{
				StringBuilder buf=new StringBuilder();
				buf.append("{ReferencesType");
				buf.append("; type: ");
				buf.append(String.valueOf(this.type));
				buf.append("; id: ");
				buf.append(String.valueOf(this.id));
				buf.append("; number: ");
				buf.append(String.valueOf(this.number));
				buf.append("}");
				return buf.toString();
			}

		}

		private java.util.List<ReferencesType> references=new java.util.ArrayList<ReferencesType>();

		public java.util.List<ReferencesType> getReferences()
		{
			return this.references;
		}

		@SuppressWarnings("unused")
		private void setReferences(java.util.List<ReferencesType> value)
		{
			for (ReferencesType object : value)
				this.references.add(new ReferencesType(object));
		}

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + findByteLength(this.references);
		}

		@Override
		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			super.write(out, conn);
			out.writeInteger(this.references.size());
			for (ReferencesType object : this.references)
				object.write(out, conn);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		ObjectParamReferenceQuantityList(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.references.clear();
			for (int length=in.readInteger32(); length > 0; length--)
				this.references.add(new ReferencesType(in));
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{ObjectParamReferenceQuantityList");
			buf.append("; references: ");
			buf.append(String.valueOf(this.references));
			buf.append("}");
			return buf.toString();
		}

		@Override
		public void visit(TP04Visitor visitor) throws TPException
		{
			visitor.objectParams(this);
		}

	}

	/**
	 * An Integer, informational.
	 */
	public static class ObjectParamInteger extends ObjectParams
	{
		public static final int PARAM_TYPE=8;

		/**
		 * A default constructor for subclasses, which initialises properties to their defaults.
		 */
		protected ObjectParamInteger(int id)
		{
			super(id);
		}

		/**
		 * A default constructor for general public, which initialises properties to their defaults.
		 */
		public ObjectParamInteger()
		{
			super(PARAM_TYPE);
		}

		/**
		 * The value of the integer parameter.
		 */
		private int intvalue;

		public int getIntvalue()
		{
			return this.intvalue;
		}

		public void setIntvalue(int value)
		{
			this.intvalue=value;
		}

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + 4;
		}

		@Override
		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			super.write(out, conn);
			out.writeInteger(this.intvalue);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		ObjectParamInteger(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.intvalue=in.readInteger32();
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{ObjectParamInteger");
			buf.append("; intvalue: ");
			buf.append(String.valueOf(this.intvalue));
			buf.append("}");
			return buf.toString();
		}

		@Override
		public void visit(TP04Visitor visitor) throws TPException
		{
			visitor.objectParams(this);
		}

	}

	/**
	 * The diameter of the object.
	 */
	public static class ObjectParamSize extends ObjectParams
	{
		public static final int PARAM_TYPE=9;

		/**
		 * A default constructor for subclasses, which initialises properties to their defaults.
		 */
		protected ObjectParamSize(int id)
		{
			super(id);
		}

		/**
		 * A default constructor for general public, which initialises properties to their defaults.
		 */
		public ObjectParamSize()
		{
			super(PARAM_TYPE);
		}

		/**
		 * The size of the object (diameter).
		 */
		private long size;

		public long getSize()
		{
			return this.size;
		}

		@SuppressWarnings("unused")
		private void setSize(long value)
		{
			this.size=value;
		}

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + 8;
		}

		@Override
		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			super.write(out, conn);
			out.writeInteger(this.size);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		ObjectParamSize(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.size=in.readInteger64();
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{ObjectParamSize");
			buf.append("; size: ");
			buf.append(String.valueOf(this.size));
			buf.append("}");
			return buf.toString();
		}

		@Override
		public void visit(TP04Visitor visitor) throws TPException
		{
			visitor.objectParams(this);
		}

	}

	/**
	 * The url for the media for this object, either relative to the base url given in the Game frame, of absolute.
	 */
	public static class ObjectParamMedia extends ObjectParams
	{
		public static final int PARAM_TYPE=10;

		/**
		 * A default constructor for subclasses, which initialises properties to their defaults.
		 */
		protected ObjectParamMedia(int id)
		{
			super(id);
		}

		/**
		 * A default constructor for general public, which initialises properties to their defaults.
		 */
		public ObjectParamMedia()
		{
			super(PARAM_TYPE);
		}

		/**
		 * The url for the media.
		 */
		private String url=new String();

		public String getUrl()
		{
			return this.url;
		}

		@SuppressWarnings("unused")
		private void setUrl(String value)
		{
			this.url=value;
		}

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + findByteLength(this.url);
		}

		@Override
		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			super.write(out, conn);
			out.writeString(this.url);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		ObjectParamMedia(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.url=in.readString();
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{ObjectParamMedia");
			buf.append("; url: ");
			buf.append(String.valueOf(this.url));
			buf.append("}");
			return buf.toString();
		}

		@Override
		public void visit(TP04Visitor visitor) throws TPException
		{
			visitor.objectParams(this);
		}

	}

	@Override
	public String toString()
	{
		StringBuilder buf=new StringBuilder();
		buf.append("{ObjectParams");
		buf.append("}");
		return buf.toString();
	}

	public static ObjectParams create(int id, TPDataInput in) throws IOException
	{
		switch (id)
		{
			case ObjectParams.ObjectParamPosition3d.PARAM_TYPE: return new ObjectParams.ObjectParamPosition3d(id, in);
			case ObjectParams.ObjectParamVelocity3d.PARAM_TYPE: return new ObjectParams.ObjectParamVelocity3d(id, in);
			case ObjectParams.ObjectParamAcceleration3d.PARAM_TYPE: return new ObjectParams.ObjectParamAcceleration3d(id, in);
			case ObjectParams.ObjectParamBoundPosition.PARAM_TYPE: return new ObjectParams.ObjectParamBoundPosition(id, in);
			case ObjectParams.ObjectParamOrderQueue.PARAM_TYPE: return new ObjectParams.ObjectParamOrderQueue(id, in);
			case ObjectParams.ObjectParamResourceList.PARAM_TYPE: return new ObjectParams.ObjectParamResourceList(id, in);
			case ObjectParams.ObjectParmReference.PARAM_TYPE: return new ObjectParams.ObjectParmReference(id, in);
			case ObjectParams.ObjectParamReferenceQuantityList.PARAM_TYPE: return new ObjectParams.ObjectParamReferenceQuantityList(id, in);
			case ObjectParams.ObjectParamInteger.PARAM_TYPE: return new ObjectParams.ObjectParamInteger(id, in);
			case ObjectParams.ObjectParamSize.PARAM_TYPE: return new ObjectParams.ObjectParamSize(id, in);
			case ObjectParams.ObjectParamMedia.PARAM_TYPE: return new ObjectParams.ObjectParamMedia(id, in);
			//this is necessary for marshall/unmarshall tests
			case -1: return new ObjectParams(id, in);
			default: throw new IllegalArgumentException("Invalid ObjectParams id: "+id);
		}
	}

}
