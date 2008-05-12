package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * These are the "parameters" that define structures of Objects.
 */
public class ObjectParams extends TPObject implements Visitable
{
	private final int id;

	protected ObjectParams(int id)
	{
		this.id=id;
	}

	ObjectParams(int id, TPDataInput in)
	{
		this(id);
		//nothing to read
	}

	public int getParameterType()
	{
		return id;
	}

	public int findByteLength()
	{
		return 0;
	}

	public void write(TPDataOutput out, Connection conn) throws IOException
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
		 * A default constructor which initialises properties to their defaults.
		 */
		protected Universe(int id)
		{
			super(id);
		}

		private int age;

		public int getAge()
		{
			return this.age;
		}

		private void setAge(int value)
		{
			this.age=value;
		}

		public int findByteLength()
		{
			return super.findByteLength()
				 + 4;
		}

		public void write(TPDataOutput out, Connection conn) throws IOException
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

		public String toString()
		{
			
			return "{Universe"
                            + "; age: "
                            + String.valueOf(this.age)
                            + "}";
			
		}

		public void visit(TP03Visitor visitor) throws TPException
		{
			visitor.objectParams(this);
		}

	}

	public static class Galaxy extends ObjectParams
	{
		public static final int PARAM_TYPE=1;

		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		protected Galaxy(int id)
		{
			super(id);
		}

		public int findByteLength()
		{
			return super.findByteLength();
		}

		public void write(TPDataOutput out, Connection conn) throws IOException
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

		public String toString()
		{
			
			return "{Galaxy"
                            + "}";
			
		}

		public void visit(TP03Visitor visitor) throws TPException
		{
			visitor.objectParams(this);
		}

	}

	public static class StarSystem extends ObjectParams
	{
		public static final int PARAM_TYPE=2;

		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		protected StarSystem(int id)
		{
			super(id);
		}

		public int findByteLength()
		{
			return super.findByteLength();
		}

		public void write(TPDataOutput out, Connection conn) throws IOException
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

		public String toString()
		{
			
			return "{StarSystem"
                            + "}";
                            
		}

		public void visit(TP03Visitor visitor) throws TPException
		{
			visitor.objectParams(this);
		}

	}

	public static class Planet extends ObjectParams
	{
		public static final int PARAM_TYPE=3;

		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		protected Planet(int id)
		{
			super(id);
		}

		private int owner;

		public int getOwner()
		{
			return this.owner;
		}

		private void setOwner(int value)
		{
			this.owner=value;
		}

		public static class ResourcesType extends TPObject
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

			private void setId(int value)
			{
				this.id=value;
			}

			private int units;

			public int getUnits()
			{
				return this.units;
			}

			private void setUnits(int value)
			{
				this.units=value;
			}

			private int unitsminable;

			public int getUnitsminable()
			{
				return this.unitsminable;
			}

			private void setUnitsminable(int value)
			{
				this.unitsminable=value;
			}

			private int unitsinaccessible;

			public int getUnitsinaccessible()
			{
				return this.unitsinaccessible;
			}

			private void setUnitsinaccessible(int value)
			{
				this.unitsinaccessible=value;
			}

			public int findByteLength()
			{
				return super.findByteLength()
					 + 4
					 + 4
					 + 4
					 + 4;
			}

			public void write(TPDataOutput out, Connection conn) throws IOException
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

			public String toString()
			{
				
				return "{ResourcesType"
                                    + "; id: "
                                    + String.valueOf(this.id)
                                    + "; units: "
                                    + String.valueOf(this.units)
                                    + "; unitsminable: "
                                    + String.valueOf(this.unitsminable)
                                    + "; unitsinaccessible: "
                                    + String.valueOf(this.unitsinaccessible)
                                    + "}";
				
			}

		}

		private java.util.Vector resources = new java.util.Vector();
		public java.util.Vector getResources()
		{
			return this.resources;
		}


		private void setResources(java.util.Vector value)
		{
                        for (int i = 0; i < value.size(); i++){
                            this.resources.addElement(new ResourcesType((ResourcesType)value.elementAt(i)));
                        }

		}


		public int findByteLength()
		{
			return super.findByteLength()
				 + 4
				 + findByteLength(this.resources);
		}

		public void write(TPDataOutput out, Connection conn) throws IOException
		{
			super.write(out, conn);
			out.writeInteger(this.owner);
			out.writeInteger(this.resources.size());
                        for (int i =0; i < this.resources.size(); i++){
                            ((ResourcesType)this.resources.elementAt(i)).write(out, conn);
                        }

		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */

		Planet(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.owner=in.readInteger32();
			this.resources.removeAllElements();
			for (int length=in.readInteger32(); length > 0; length--)
				this.resources.addElement(new ResourcesType(in));
		}

		public String toString()
		{
			
			return "{Planet"
                            + "; owner: "
                            + String.valueOf(this.owner)
                            + "; resources: "
                            + String.valueOf(this.resources)
                            + "}";
			
		}

		public void visit(TP03Visitor visitor) throws TPException
		{
			visitor.objectParams(this);
		}

	}

	public static class Fleet extends ObjectParams
	{
		public static final int PARAM_TYPE=4;

		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		protected Fleet(int id)
		{
			super(id);
		}

		private int owner;

		public int getOwner()
		{
			return this.owner;
		}

		private void setOwner(int value)
		{
			this.owner=value;
		}

		public static class ShipsType extends TPObject
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

			private void setType(int value)
			{
				this.type=value;
			}

			private int count;

			public int getCount()
			{
				return this.count;
			}

			private void setCount(int value)
			{
				this.count=value;
			}

			public int findByteLength()
			{
				return super.findByteLength()
					 + 4
					 + 4;
			}

			public void write(TPDataOutput out, Connection conn) throws IOException
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

			public String toString()
			{
				
				return "{ShipsType"
                                    + "; type: "
                                    + String.valueOf(this.type)
                                    + "; count: "
                                    + String.valueOf(this.count)
                                    + "}";
				
			}

		}

		
                private java.util.Vector ships = new java.util.Vector();
		public java.util.Vector getShips()
		{
			return this.ships;
		}


		private void setShips(java.util.Vector value)
		{
                        for (int i = 0; i < value.size(); i++){
                            this.ships.addElement(new ShipsType((ShipsType)value.elementAt(i)));
                        }

		}

		private int damage;

		public int getDamage()
		{
			return this.damage;
		}


		private void setDamage(int value)
		{
			this.damage=value;
		}


		public int findByteLength()
		{
			return super.findByteLength()
				 + 4
				 + findByteLength(this.ships)
				 + 4;
		}

		public void write(TPDataOutput out, Connection conn) throws IOException
		{
			super.write(out, conn);
			out.writeInteger(this.owner);
			out.writeInteger(this.ships.size());
                        for (int i = 0; i < this.ships.size(); i++){
                            ((ShipsType)this.ships.elementAt(i)).write(out, conn);
                        }
			
			out.writeInteger(this.damage);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */

		Fleet(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.owner=in.readInteger32();
			this.ships.removeAllElements();
			for (int length=in.readInteger32(); length > 0; length--)
				this.ships.addElement(new ShipsType(in));
			this.damage=in.readInteger32();
		}


		public String toString()
		{
			
			return "{Fleet"
                            + "; owner: "
                            + String.valueOf(this.owner)
                            + "; ships: "
                            + String.valueOf(this.ships)
                            + "; damage: "
                            + String.valueOf(this.damage)
                            + "}";
			
		}

		public void visit(TP03Visitor visitor) throws TPException
		{
			visitor.objectParams(this);
		}

	}

	public String toString()
	{
		
		return "{ObjectParams"
                    + "}";
		
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
			//this is necessary for marshall/unmarshall tests
			case -1: return new ObjectParams(id, in);
			default: throw new IllegalArgumentException("Invalid ObjectParams id: "+id);
		}
	}

}
