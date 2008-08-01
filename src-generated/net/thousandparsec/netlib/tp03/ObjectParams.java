package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * These are the "parameters" that define structures of Objects.
 */
public class ObjectParams extends TPObject<TP03Visitor> implements Visitable<TP03Visitor>
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

	public void visit(TP03Visitor visitor) throws TPException
	{
		throw new RuntimeException();
	}

	public static class Universe extends ObjectParams
	{
		public static final int PARAM_TYPE=0;

		/**
		 * A default constructor for subclasses, which initialises properties to their defaults.
		 */
		protected Universe(int id)
		{
			super(id);
		}

		/**
		 * A default constructor for general public, which initialises properties to their defaults.
		 */
		public Universe()
		{
			super(PARAM_TYPE);
		}

		private int age;

		public int getAge()
		{
			return this.age;
		}

		@SuppressWarnings("unused")
		private void setAge(int value)
		{
			this.age=value;
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
			out.writeInteger(this.age);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		Universe(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.age=in.readInteger32();
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{Universe");
			buf.append("; age: ");
			buf.append(String.valueOf(this.age));
			buf.append("}");
			return buf.toString();
		}

		@Override
		public void visit(TP03Visitor visitor) throws TPException
		{
			visitor.objectParams(this);
		}

	}

	public static class Galaxy extends ObjectParams
	{
		public static final int PARAM_TYPE=1;

		/**
		 * A default constructor for subclasses, which initialises properties to their defaults.
		 */
		protected Galaxy(int id)
		{
			super(id);
		}

		/**
		 * A default constructor for general public, which initialises properties to their defaults.
		 */
		public Galaxy()
		{
			super(PARAM_TYPE);
		}

		@Override
		public int findByteLength()
		{
			return super.findByteLength();
		}

		@Override
		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			super.write(out, conn);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		Galaxy(int id, TPDataInput in) throws IOException
		{
			super(id, in);
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{Galaxy");
			buf.append("}");
			return buf.toString();
		}

		@Override
		public void visit(TP03Visitor visitor) throws TPException
		{
			visitor.objectParams(this);
		}

	}

	public static class StarSystem extends ObjectParams
	{
		public static final int PARAM_TYPE=2;

		/**
		 * A default constructor for subclasses, which initialises properties to their defaults.
		 */
		protected StarSystem(int id)
		{
			super(id);
		}

		/**
		 * A default constructor for general public, which initialises properties to their defaults.
		 */
		public StarSystem()
		{
			super(PARAM_TYPE);
		}

		@Override
		public int findByteLength()
		{
			return super.findByteLength();
		}

		@Override
		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			super.write(out, conn);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		StarSystem(int id, TPDataInput in) throws IOException
		{
			super(id, in);
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{StarSystem");
			buf.append("}");
			return buf.toString();
		}

		@Override
		public void visit(TP03Visitor visitor) throws TPException
		{
			visitor.objectParams(this);
		}

	}

	public static class Planet extends ObjectParams
	{
		public static final int PARAM_TYPE=3;

		/**
		 * A default constructor for subclasses, which initialises properties to their defaults.
		 */
		protected Planet(int id)
		{
			super(id);
		}

		/**
		 * A default constructor for general public, which initialises properties to their defaults.
		 */
		public Planet()
		{
			super(PARAM_TYPE);
		}

		private int owner;

		public int getOwner()
		{
			return this.owner;
		}

		@SuppressWarnings("unused")
		private void setOwner(int value)
		{
			this.owner=value;
		}

		public static class ResourcesType extends TPObject<TP03Visitor>
		{
			/**
			 * A default constructor which initialises properties to their defaults.
			 */
			public ResourcesType()
			{
			}

			private int id;

			public int getId()
			{
				return this.id;
			}

			@SuppressWarnings("unused")
			private void setId(int value)
			{
				this.id=value;
			}

			private int units;

			public int getUnits()
			{
				return this.units;
			}

			@SuppressWarnings("unused")
			private void setUnits(int value)
			{
				this.units=value;
			}

			private int unitsminable;

			public int getUnitsminable()
			{
				return this.unitsminable;
			}

			@SuppressWarnings("unused")
			private void setUnitsminable(int value)
			{
				this.unitsminable=value;
			}

			private int unitsinaccessible;

			public int getUnitsinaccessible()
			{
				return this.unitsinaccessible;
			}

			@SuppressWarnings("unused")
			private void setUnitsinaccessible(int value)
			{
				this.unitsinaccessible=value;
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
				out.writeInteger(this.id);
				out.writeInteger(this.units);
				out.writeInteger(this.unitsminable);
				out.writeInteger(this.unitsinaccessible);
			}

			/**
			 * A copy constructor for (among others) deep-copying groups and lists.
			 */
			public ResourcesType(ResourcesType copy)
			{
				setId(copy.getId());
				setUnits(copy.getUnits());
				setUnitsminable(copy.getUnitsminable());
				setUnitsinaccessible(copy.getUnitsinaccessible());
			}

			/**
			 * A special "internal" constructor that reads contents from a stream.
			 */
			ResourcesType(TPDataInput in) throws IOException
			{
				this.id=in.readInteger32();
				this.units=in.readInteger32();
				this.unitsminable=in.readInteger32();
				this.unitsinaccessible=in.readInteger32();
			}

			@Override
			public String toString()
			{
				StringBuilder buf=new StringBuilder();
				buf.append("{ResourcesType");
				buf.append("; id: ");
				buf.append(String.valueOf(this.id));
				buf.append("; units: ");
				buf.append(String.valueOf(this.units));
				buf.append("; unitsminable: ");
				buf.append(String.valueOf(this.unitsminable));
				buf.append("; unitsinaccessible: ");
				buf.append(String.valueOf(this.unitsinaccessible));
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
				 + 4
				 + findByteLength(this.resources);
		}

		@Override
		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			super.write(out, conn);
			out.writeInteger(this.owner);
			out.writeInteger(this.resources.size());
			for (ResourcesType object : this.resources)
				object.write(out, conn);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		Planet(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.owner=in.readInteger32();
			this.resources.clear();
			for (int length=in.readInteger32(); length > 0; length--)
				this.resources.add(new ResourcesType(in));
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{Planet");
			buf.append("; owner: ");
			buf.append(String.valueOf(this.owner));
			buf.append("; resources: ");
			buf.append(String.valueOf(this.resources));
			buf.append("}");
			return buf.toString();
		}

		@Override
		public void visit(TP03Visitor visitor) throws TPException
		{
			visitor.objectParams(this);
		}

	}

	public static class Fleet extends ObjectParams
	{
		public static final int PARAM_TYPE=4;

		/**
		 * A default constructor for subclasses, which initialises properties to their defaults.
		 */
		protected Fleet(int id)
		{
			super(id);
		}

		/**
		 * A default constructor for general public, which initialises properties to their defaults.
		 */
		public Fleet()
		{
			super(PARAM_TYPE);
		}

		private int owner;

		public int getOwner()
		{
			return this.owner;
		}

		@SuppressWarnings("unused")
		private void setOwner(int value)
		{
			this.owner=value;
		}

		public static class ShipsType extends TPObject<TP03Visitor>
		{
			/**
			 * A default constructor which initialises properties to their defaults.
			 */
			public ShipsType()
			{
			}

			private int type;

			public int getType()
			{
				return this.type;
			}

			@SuppressWarnings("unused")
			private void setType(int value)
			{
				this.type=value;
			}

			private int count;

			public int getCount()
			{
				return this.count;
			}

			@SuppressWarnings("unused")
			private void setCount(int value)
			{
				this.count=value;
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
				out.writeInteger(this.type);
				out.writeInteger(this.count);
			}

			/**
			 * A copy constructor for (among others) deep-copying groups and lists.
			 */
			public ShipsType(ShipsType copy)
			{
				setType(copy.getType());
				setCount(copy.getCount());
			}

			/**
			 * A special "internal" constructor that reads contents from a stream.
			 */
			ShipsType(TPDataInput in) throws IOException
			{
				this.type=in.readInteger32();
				this.count=in.readInteger32();
			}

			@Override
			public String toString()
			{
				StringBuilder buf=new StringBuilder();
				buf.append("{ShipsType");
				buf.append("; type: ");
				buf.append(String.valueOf(this.type));
				buf.append("; count: ");
				buf.append(String.valueOf(this.count));
				buf.append("}");
				return buf.toString();
			}

		}

		private java.util.List<ShipsType> ships=new java.util.ArrayList<ShipsType>();

		public java.util.List<ShipsType> getShips()
		{
			return this.ships;
		}

		@SuppressWarnings("unused")
		private void setShips(java.util.List<ShipsType> value)
		{
			for (ShipsType object : value)
				this.ships.add(new ShipsType(object));
		}

		private int damage;

		public int getDamage()
		{
			return this.damage;
		}

		@SuppressWarnings("unused")
		private void setDamage(int value)
		{
			this.damage=value;
		}

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + 4
				 + findByteLength(this.ships)
				 + 4;
		}

		@Override
		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			super.write(out, conn);
			out.writeInteger(this.owner);
			out.writeInteger(this.ships.size());
			for (ShipsType object : this.ships)
				object.write(out, conn);
			out.writeInteger(this.damage);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		Fleet(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.owner=in.readInteger32();
			this.ships.clear();
			for (int length=in.readInteger32(); length > 0; length--)
				this.ships.add(new ShipsType(in));
			this.damage=in.readInteger32();
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{Fleet");
			buf.append("; owner: ");
			buf.append(String.valueOf(this.owner));
			buf.append("; ships: ");
			buf.append(String.valueOf(this.ships));
			buf.append("; damage: ");
			buf.append(String.valueOf(this.damage));
			buf.append("}");
			return buf.toString();
		}

		@Override
		public void visit(TP03Visitor visitor) throws TPException
		{
			visitor.objectParams(this);
		}

	}

	/**
	 * 
				The Wormhole is a top level object that links to locations together.
				It was added as a quick hack to make the Risk ruleset a little easier to play.
	 * 
				It has 3 int64 arguments which are the "other end" of the wormhole.
			
	 */
	public static class Wormhole extends ObjectParams
	{
		public static final int PARAM_TYPE=5;

		/**
		 * A default constructor for subclasses, which initialises properties to their defaults.
		 */
		protected Wormhole(int id)
		{
			super(id);
		}

		/**
		 * A default constructor for general public, which initialises properties to their defaults.
		 */
		public Wormhole()
		{
			super(PARAM_TYPE);
		}

		private long endx;

		public long getEndx()
		{
			return this.endx;
		}

		@SuppressWarnings("unused")
		private void setEndx(long value)
		{
			this.endx=value;
		}

		private long endy;

		public long getEndy()
		{
			return this.endy;
		}

		@SuppressWarnings("unused")
		private void setEndy(long value)
		{
			this.endy=value;
		}

		private long endz;

		public long getEndz()
		{
			return this.endz;
		}

		@SuppressWarnings("unused")
		private void setEndz(long value)
		{
			this.endz=value;
		}

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + 8
				 + 8
				 + 8;
		}

		@Override
		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			super.write(out, conn);
			out.writeInteger(this.endx);
			out.writeInteger(this.endy);
			out.writeInteger(this.endz);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		Wormhole(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.endx=in.readInteger64();
			this.endy=in.readInteger64();
			this.endz=in.readInteger64();
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{Wormhole");
			buf.append("; endx: ");
			buf.append(String.valueOf(this.endx));
			buf.append("; endy: ");
			buf.append(String.valueOf(this.endy));
			buf.append("; endz: ");
			buf.append(String.valueOf(this.endz));
			buf.append("}");
			return buf.toString();
		}

		@Override
		public void visit(TP03Visitor visitor) throws TPException
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
			case ObjectParams.Universe.PARAM_TYPE: return new ObjectParams.Universe(id, in);
			case ObjectParams.Galaxy.PARAM_TYPE: return new ObjectParams.Galaxy(id, in);
			case ObjectParams.StarSystem.PARAM_TYPE: return new ObjectParams.StarSystem(id, in);
			case ObjectParams.Planet.PARAM_TYPE: return new ObjectParams.Planet(id, in);
			case ObjectParams.Fleet.PARAM_TYPE: return new ObjectParams.Fleet(id, in);
			case ObjectParams.Wormhole.PARAM_TYPE: return new ObjectParams.Wormhole(id, in);
			//this is necessary for marshall/unmarshall tests
			case -1: return new ObjectParams(id, in);
			default: throw new IllegalArgumentException("Invalid ObjectParams id: "+id);
		}
	}

}
