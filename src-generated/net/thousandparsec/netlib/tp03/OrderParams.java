package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * These are the parameters for Orders and OrderDescriptions.
 */
public class OrderParams extends TPObject implements Visitable
{
	private final int id;

	protected OrderParams(int id)
	{
		this.id=id;
	}

	OrderParams(int id, TPDataInput in)
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

	/**
	 * Coordinates in absolute space. (Relative to the center of the Universe)
	 */
	public static class OrderParamAbsSpaceCoords extends OrderParams
	{
		public static final int PARAM_TYPE=0;

		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		protected OrderParamAbsSpaceCoords(int id)
		{
			super(id);
		}

		public static class PosType extends TPObject
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

			public int findByteLength()
			{
				return super.findByteLength()
					 + 8
					 + 8
					 + 8;
			}

			public void write(TPDataOutput out, Connection conn) throws IOException
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
			 * A copy constructor for (among others) deep-copying groups and lists.
			 */
			public PosType(PosType copy)
			{
				setX(copy.getX());
				setY(copy.getY());
				setZ(copy.getZ());
			}

			/**
			 * A special "internal" constructor that reads contents from a stream.
			 */

			PosType(TPDataInput in) throws IOException
			{
				this.x=in.readInteger64();
				this.y=in.readInteger64();
				this.z=in.readInteger64();
			}

			public String toString()
			{
				return "{PosType"
                                    + "; x: "
                                    + String.valueOf(this.x)
                                    + "; y: "
                                    + String.valueOf(this.y)
                                    + "; z: "
                                    + String.valueOf(this.z)
                                    + "}";
				
			}

		}

		private PosType pos=new PosType();

		public PosType getPos()
		{
			return this.pos;
		}

		public void setPos(PosType value)
		{
			this.pos=new PosType(value);
		}

		public int findByteLength()
		{
			return super.findByteLength()
				 + findByteLength(this.pos);
		}

		public void write(TPDataOutput out, Connection conn) throws IOException
		{
			super.write(out, conn);
			this.pos.write(out, conn);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */

		OrderParamAbsSpaceCoords(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.pos=new PosType(in);
		}

		public String toString()
		{
			return "{OrderParamAbsSpaceCoords"
                            + "; pos: "
                            + String.valueOf(this.pos)
                            + "}";
			
		}

		public void visit(TP03Visitor visitor) throws TPException
		{
			visitor.orderParams(this);
		}

	}

	/**
	 * The number of turns before something happens.
	 */
	public static class OrderParamTime extends OrderParams
	{
		public static final int PARAM_TYPE=1;

		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		protected OrderParamTime(int id)
		{
			super(id);
		}

		/**
		 * Number of turns
		 */
		private int turns;

		public int getTurns()
		{
			return this.turns;
		}

		public void setTurns(int value)
		{
			this.turns=value;
		}

		/**
		 * Maximum number of turns
		 */
		private int maxtime;

		public int getMaxtime()
		{
			return this.maxtime;
		}

		private void setMaxtime(int value)
		{
			this.maxtime=value;
		}

		public int findByteLength()
		{
			return super.findByteLength()
				 + 4
				 + 4;
		}

		public void write(TPDataOutput out, Connection conn) throws IOException
		{
			super.write(out, conn);
			out.writeInteger(this.turns);
			out.writeInteger(this.maxtime);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */

		OrderParamTime(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.turns=in.readInteger32();
			this.maxtime=in.readInteger32();
		}

		public String toString()
		{
			return "{OrderParamTime"
                            + "; turns: "
                            + String.valueOf(this.turns)
                            + "; maxtime: "
                            + String.valueOf(this.maxtime)
                            + "}";
			
		}

		public void visit(TP03Visitor visitor) throws TPException
		{
			visitor.orderParams(this);
		}

	}

	/**
	 * An object's ID number.
	 */
	public static class OrderParamObject extends OrderParams
	{
		public static final int PARAM_TYPE=2;

		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		protected OrderParamObject(int id)
		{
			super(id);
		}

		/**
		 * The object's Id number.
		 */
		private int objectid;

		public int getObjectid()
		{
			return this.objectid;
		}

		public void setObjectid(int value)
		{
			this.objectid=value;
		}

		public int findByteLength()
		{
			return super.findByteLength()
				 + 4;
		}

		public void write(TPDataOutput out, Connection conn) throws IOException
		{
			super.write(out, conn);
			out.writeInteger(this.objectid);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */

		OrderParamObject(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.objectid=in.readInteger32();
		}

		public String toString()
		{
			return "{OrderParamObject"
                            + "; objectid: "
                            + String.valueOf(this.objectid)
                            + "}";
			
		}

		public void visit(TP03Visitor visitor) throws TPException
		{
			visitor.orderParams(this);
		}

	}

	/**
	 * A player's ID number.
	 */
	public static class OrderParamPlayer extends OrderParams
	{
		public static final int PARAM_TYPE=3;

		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		protected OrderParamPlayer(int id)
		{
			super(id);
		}

		/**
		 * The player's Id number.
		 */
		private int playerid;

		public int getPlayerid()
		{
			return this.playerid;
		}

		public void setPlayerid(int value)
		{
			this.playerid=value;
		}

		/**
		 * Mask for not allowed player Ids (On bits are NOT allowed to be chosen)
		 */
		public enum Mask
		{
			$none$(-1),

			/**
			 * Allies
			 */
			allies(0x01),

			/**
			 * Trading Partners
			 */
			tradingpartners(0x02),

			/**
			 * Neutral
			 */
			neutral(0x04),

			/**
			 * Enemies
			 */
			enemies(0x08),

			/**
			 * Non-players
			 */
			nonplayers(0x10),

			;
			public final int value;
			private Mask(int value)
			{
				this.value=value;
			}
		}

		private int mask;

		public int getMask()
		{
			return this.mask;
		}

		private void setMask(int value)
		{
			this.mask=value;
		}

		public int findByteLength()
		{
			return super.findByteLength()
				 + 4
				 + 4;
		}

		public void write(TPDataOutput out, Connection conn) throws IOException
		{
			super.write(out, conn);
			out.writeInteger(this.playerid);
			out.writeInteger(this.mask);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */

		OrderParamPlayer(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.playerid=in.readInteger32();
			this.mask=in.readInteger32();
		}

		public String toString()
		{
			return "{OrderParamPlayer"
                            + "; playerid: "
                            + String.valueOf(this.playerid)
                            + "; mask: "
                            + String.valueOf(this.mask)
                            + "}";
                            
		}

		public void visit(TP03Visitor visitor) throws TPException
		{
			visitor.orderParams(this);
		}

	}

	/**
	 * Coordinates relative to an object
	 */
	public static class OrderParamRelSpaceCoords extends OrderParams
	{
		public static final int PARAM_TYPE=4;

		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		protected OrderParamRelSpaceCoords(int id)
		{
			super(id);
		}

		/**
		 * The object's Id number.
		 */
		private int objectid;

		public int getObjectid()
		{
			return this.objectid;
		}

		public void setObjectid(int value)
		{
			this.objectid=value;
		}

		public static class RelposType extends TPObject
		{
			/**
			 * A default constructor which initialises properties to their defaults.
			 */
			public RelposType()
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

			public int findByteLength()
			{
				return super.findByteLength()
					 + 8
					 + 8
					 + 8;
			}

			public void write(TPDataOutput out, Connection conn) throws IOException
			{
				out.writeInteger(this.x);
				out.writeInteger(this.y);
				out.writeInteger(this.z);
			}

			/**
			 * A convenience constructor for easy initialisation of non-read only fields.
			 */
			public RelposType(long x, long y, long z)
			{
				setX(x);
				setY(y);
				setZ(z);
			}

			/**
			 * A copy constructor for (among others) deep-copying groups and lists.
			 */
			public RelposType(RelposType copy)
			{
				setX(copy.getX());
				setY(copy.getY());
				setZ(copy.getZ());
			}

			/**
			 * A special "internal" constructor that reads contents from a stream.
			 */

			RelposType(TPDataInput in) throws IOException
			{
				this.x=in.readInteger64();
				this.y=in.readInteger64();
				this.z=in.readInteger64();
			}

			public String toString()
			{
				return "{RelposType"
                                    + "; x: "
                                    + String.valueOf(this.x)
                                    + "; y: "
                                    + String.valueOf(this.y)
                                    + "; z: "
                                    + String.valueOf(this.z)
                                    + "}";
				
			}

		}

		private RelposType relpos=new RelposType();

		public RelposType getRelpos()
		{
			return this.relpos;
		}

		public void setRelpos(RelposType value)
		{
			this.relpos=new RelposType(value);
		}

		public int findByteLength()
		{
			return super.findByteLength()
				 + 4
				 + findByteLength(this.relpos);
		}

		public void write(TPDataOutput out, Connection conn) throws IOException
		{
			super.write(out, conn);
			out.writeInteger(this.objectid);
			this.relpos.write(out, conn);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */

		OrderParamRelSpaceCoords(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.objectid=in.readInteger32();
			this.relpos=new RelposType(in);
		}

		public String toString()
		{
			return "{OrderParamRelSpaceCoords"
                            + "; objectid: "
                            + String.valueOf(this.objectid)
                            + "; relpos: "
                            + String.valueOf(this.relpos)
                            + "}";
                            
		}

		public void visit(TP03Visitor visitor) throws TPException
		{
			visitor.orderParams(this);
		}

	}

	/**
	 * A number value from a range.
	 */
	public static class OrderParamRange extends OrderParams
	{
		public static final int PARAM_TYPE=5;

		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		protected OrderParamRange(int id)
		{
			super(id);
		}

		/**
		 * The Value
		 */
		private int value;

		public int getValue()
		{
			return this.value;
		}

		public void setValue(int value)
		{
			this.value=value;
		}

		/**
		 * The minimum value the value can take
		 */
		private int minvalue;

		public int getMinvalue()
		{
			return this.minvalue;
		}

		private void setMinvalue(int value)
		{
			this.minvalue=value;
		}

		/**
		 * The maximum value the value can take
		 */
		private int maxvalue;

		public int getMaxvalue()
		{
			return this.maxvalue;
		}

		private void setMaxvalue(int value)
		{
			this.maxvalue=value;
		}

		/**
		 * The amount to increment by (resolution)
		 */
		private int increment;

		public int getIncrement()
		{
			return this.increment;
		}

		private void setIncrement(int value)
		{
			this.increment=value;
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
			super.write(out, conn);
			out.writeInteger(this.value);
			out.writeInteger(this.minvalue);
			out.writeInteger(this.maxvalue);
			out.writeInteger(this.increment);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */

		OrderParamRange(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.value=in.readInteger32();
			this.minvalue=in.readInteger32();
			this.maxvalue=in.readInteger32();
			this.increment=in.readInteger32();
		}

		public String toString()
		{
			return "{OrderParamRange"
                            + "; value: "
                            + String.valueOf(this.value)
                            + "; minvalue: "
                            + String.valueOf(this.minvalue)
                            + "; maxvalue: "
                            + String.valueOf(this.maxvalue)
                            + "; increment: "
                            + String.valueOf(this.increment)
                            + "}";
			
		}

		public void visit(TP03Visitor visitor) throws TPException
		{
			visitor.orderParams(this);
		}

	}

	/**
	 * A in which numerous items can be selected.
	 */
	public static class OrderParamList extends OrderParams
	{
		public static final int PARAM_TYPE=6;

		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		protected OrderParamList(int id)
		{
			super(id);
		}

		/**
		 * A list of the items which can be chosen.
		 */
		public static class PossibleselectionsType extends TPObject
		{
			/**
			 * A default constructor which initialises properties to their defaults.
			 */
			public PossibleselectionsType()
			{
			}

			/**
			 * ID of what can be selected
			 */
			private int id;

			public int getId()
			{
				return this.id;
			}

			private void setId(int value)
			{
				this.id=value;
			}

			/**
			 * The name of the item
			 */
			private String name=new String();

			public String getName()
			{
				return this.name;
			}

			private void setName(String value)
			{
				this.name=value;
			}

			/**
			 * The maximum number of this item which can be selected
			 */
			private int maxnum;

			public int getMaxnum()
			{
				return this.maxnum;
			}

			private void setMaxnum(int value)
			{
				this.maxnum=value;
			}

			public int findByteLength()
			{
				return super.findByteLength()
					 + 4
					 + findByteLength(this.name)
					 + 4;
			}

			public void write(TPDataOutput out, Connection conn) throws IOException
			{
				out.writeInteger(this.id);
				out.writeString(this.name);
				out.writeInteger(this.maxnum);
			}

			/**
			 * A copy constructor for (among others) deep-copying groups and lists.
			 */
			public PossibleselectionsType(PossibleselectionsType copy)
			{
				setId(copy.getId());
				setName(copy.getName());
				setMaxnum(copy.getMaxnum());
			}

			/**
			 * A special "internal" constructor that reads contents from a stream.
			 */

			PossibleselectionsType(TPDataInput in) throws IOException
			{
				this.id=in.readInteger32();
				this.name=in.readString();
				this.maxnum=in.readInteger32();
			}

			public String toString()
			{
				return "{PossibleselectionsType"
                                    + "; id: "
                                    + String.valueOf(this.id)
                                    + "; name: "
                                    + String.valueOf(this.name)
                                    + "; maxnum: "
                                    + String.valueOf(this.maxnum)
                                    + "}";
				
			}

		}

		private java.util.Vector possibleselections = new java.util.Vector();
		public java.util.Vector getPossibleselections()
		{
                        return possibleselections;
			
		}

		private void setPossibleselections(java.util.Vector value)
		{
                        for (int i = 0; i < value.size(); i ++){
                            this.possibleselections.addElement(new PossibleselectionsType((PossibleselectionsType)value.elementAt(i)));
                        }
			
		}

		/**
		 * A list of the items which have been selected.
		 */
		public static class SelectionType extends TPObject
		{
			/**
			 * A default constructor which initialises properties to their defaults.
			 */
			public SelectionType()
			{
			}

			/**
			 * ID of what is selected
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
			 * The number of this item which have been selected
			 */
			private int number;

			public int getNumber()
			{
				return this.number;
			}

			public void setNumber(int value)
			{
				this.number=value;
			}

			public int findByteLength()
			{
				return super.findByteLength()
					 + 4
					 + 4;
			}

			public void write(TPDataOutput out, Connection conn) throws IOException
			{
				out.writeInteger(this.id);
				out.writeInteger(this.number);
			}

			/**
			 * A convenience constructor for easy initialisation of non-read only fields.
			 */
			public SelectionType(int id, int number)
			{
				setId(id);
				setNumber(number);
			}

			/**
			 * A copy constructor for (among others) deep-copying groups and lists.
			 */
			public SelectionType(SelectionType copy)
			{
				setId(copy.getId());
				setNumber(copy.getNumber());
			}

			/**
			 * A special "internal" constructor that reads contents from a stream.
			 */

			SelectionType(TPDataInput in) throws IOException
			{
				this.id=in.readInteger32();
				this.number=in.readInteger32();
			}

			public String toString()
			{
				return "{SelectionType"
                                    + "; id: "
                                    + String.valueOf(this.id)
                                    + "; number: "
                                    + String.valueOf(this.number)
                                    + "}";
				
			}

		}

		private java.util.Vector selection=new java.util.Vector();

		public java.util.Vector getSelection()
		{
			return this.selection;
		}

		private void setSelection(java.util.Vector value)
		{
                        for (int i =0; i < value.size(); i++){
                            this.selection.addElement(new SelectionType((SelectionType)value.elementAt(i)));
                        }
			
		}

		public int findByteLength()
		{
			return super.findByteLength()
				 + findByteLength(this.possibleselections)
				 + findByteLength(this.selection);
		}

		public void write(TPDataOutput out, Connection conn) throws IOException
		{
			super.write(out, conn);
			out.writeInteger(this.possibleselections.size());
                        for (int i = 0; i < possibleselections.size(); i++){
                            ((PossibleselectionsType)possibleselections.elementAt(i)).write(out, conn);
                        }
			out.writeInteger(this.selection.size());
			for (int i =0; i < selection.size(); i ++){
                            ((SelectionType)selection.elementAt(i)).write(out, conn);
                        }

		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */

		OrderParamList(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.possibleselections.removeAllElements();
			for (int length=in.readInteger32(); length > 0; length--)
				this.possibleselections.addElement(new PossibleselectionsType(in));
			this.selection.removeAllElements();
			for (int length=in.readInteger32(); length > 0; length--)
				this.selection.addElement(new SelectionType(in));
		}

		public String toString()
		{
			return "{OrderParamList"
                            + "; possibleselections: "
                            + String.valueOf(this.possibleselections)
                            + "; selection: "
                            + String.valueOf(this.selection)
                            + "}";
			
		}

		public void visit(TP03Visitor visitor) throws TPException
		{
			visitor.orderParams(this);
		}

	}

	/**
	 * A textual string.
	 */
	public static class OrderParamString extends OrderParams
	{
		public static final int PARAM_TYPE=7;

		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		protected OrderParamString(int id)
		{
			super(id);
		}

		/**
		 * The maximum length of the string
		 */
		private int maxlength;

		public int getMaxlength()
		{
			return this.maxlength;
		}

		private void setMaxlength(int value)
		{
			this.maxlength=value;
		}

		private String string=new String();

		public String getString()
		{
			return this.string;
		}

		public void setString(String value)
		{
			this.string=value;
		}

		public int findByteLength()
		{
			return super.findByteLength()
				 + 4
				 + findByteLength(this.string);
		}

		public void write(TPDataOutput out, Connection conn) throws IOException
		{
			super.write(out, conn);
			out.writeInteger(this.maxlength);
			out.writeString(this.string);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */

		OrderParamString(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.maxlength=in.readInteger32();
			this.string=in.readString();
		}

		public String toString()
		{
			return "{OrderParamString"
                            + "; maxlength: "
                            + String.valueOf(this.maxlength)
                            + "; string: "
                            + String.valueOf(this.string)
                            + "}";
			
		}

		public void visit(TP03Visitor visitor) throws TPException
		{
			visitor.orderParams(this);
		}

	}

	/**
	 * A reference to something.
	 */
	public static class OrderParamReference extends OrderParams
	{
		public static final int PARAM_TYPE=8;

		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		protected OrderParamReference(int id)
		{
			super(id);
		}

		/**
		 * The generic reference to something.
		 */
		private int reference;

		public int getReference()
		{
			return this.reference;
		}

		public void setReference(int value)
		{
			this.reference=value;
		}

		/**
		 * A list of allowed valid reference types.
		 */
		public static class AllowedType extends TPObject
		{
			/**
			 * A default constructor which initialises properties to their defaults.
			 */
			public AllowedType()
			{
			}

			/**
			 * Valid reference type
			 */
			private int reftype;

			public int getReftype()
			{
				return this.reftype;
			}

			private void setReftype(int value)
			{
				this.reftype=value;
			}

			public int findByteLength()
			{
				return super.findByteLength()
					 + 4;
			}

			public void write(TPDataOutput out, Connection conn) throws IOException
			{
				out.writeInteger(this.reftype);
			}

			/**
			 * A copy constructor for (among others) deep-copying groups and lists.
			 */
			public AllowedType(AllowedType copy)
			{
				setReftype(copy.getReftype());
			}

			/**
			 * A special "internal" constructor that reads contents from a stream.
			 */
			
			AllowedType(TPDataInput in) throws IOException
			{
				this.reftype=in.readInteger32();
			}

			 
			public String toString()
			{
				return "{AllowedType"
                                    + "; reftype: "
                                    + String.valueOf(this.reftype)
                                    + "}";
				
			}

		}

		private java.util.Vector allowed=new java.util.Vector();

		public java.util.Vector getAllowed()
		{
                        return allowed;
			
		}

		
		private void setAllowed(java.util.Vector value)
		{
                        for (int i = 0; i < value.size(); i++){
                            this.allowed.addElement(new AllowedType((AllowedType)value.elementAt(i)));
                        }
			
		}

		 
		public int findByteLength()
		{
			return super.findByteLength()
				 + 4
				 + findByteLength(this.allowed);
		}

		 
		public void write(TPDataOutput out, Connection conn) throws IOException
		{
			super.write(out, conn);
			out.writeInteger(this.reference);
			out.writeInteger(this.allowed.size());
                        for (int i =0; i < allowed.size(); i ++){
                            ((AllowedType)allowed.elementAt(i)).write(out, conn);
                        }

		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		
		OrderParamReference(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.reference=in.readInteger32();
			this.allowed.removeAllElements();
			for (int length=in.readInteger32(); length > 0; length--)
				this.allowed.addElement(new AllowedType(in));
		}

		 
		public String toString()
		{
			return "{OrderParamReference"
                            + "; reference: "
                            + String.valueOf(this.reference)
                            + "; allowed: "
                            + String.valueOf(this.allowed)
                            + "}";
			
		}

		 
		public void visit(TP03Visitor visitor) throws TPException
		{
			visitor.orderParams(this);
		}

	}

	/**
	 * A list of references to something.
	 */
	public static class OrderParamReferenceList extends OrderParams
	{
		public static final int PARAM_TYPE=9;

		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		protected OrderParamReferenceList(int id)
		{
			super(id);
		}

		/**
		 * The list of references.
		 */
		public static class ReferencesType extends TPObject
		{
			/**
			 * A default constructor which initialises properties to their defaults.
			 */
			public ReferencesType()
			{
			}

			/**
			 * The generic reference to something.
			 */
			private int reference;

			public int getReference()
			{
				return this.reference;
			}

			public void setReference(int value)
			{
				this.reference=value;
			}

			 
			public int findByteLength()
			{
				return super.findByteLength()
					 + 4;
			}

			public void write(TPDataOutput out, Connection conn) throws IOException
			{
				out.writeInteger(this.reference);
			}

			/**
			 * A convenience constructor for easy initialisation of non-read only fields.
			 */
			public ReferencesType(int reference)
			{
				setReference(reference);
			}

			/**
			 * A copy constructor for (among others) deep-copying groups and lists.
			 */
			public ReferencesType(ReferencesType copy)
			{
				setReference(copy.getReference());
			}

			/**
			 * A special "internal" constructor that reads contents from a stream.
			 */
			
			ReferencesType(TPDataInput in) throws IOException
			{
				this.reference=in.readInteger32();
			}

			 
			public String toString()
			{
				return "{ReferencesType"
                                    + "; reference: "
                                    + String.valueOf(this.reference)
                                    + "}";
				
			}

		}

		private java.util.Vector references=new java.util.Vector();

		public java.util.Vector getReferences()
		{
			return this.references;
		}

		
		private void setReferences(java.util.Vector value)
		{
                        for (int i =0; i < value.size(); i++){
                            this.references.addElement(new ReferencesType((ReferencesType)value.elementAt(i)));
                        }
		}

		/**
		 * A list of allowed valid reference types.
		 */
		public static class AllowedType extends TPObject
		{
			/**
			 * A default constructor which initialises properties to their defaults.
			 */
			public AllowedType()
			{
			}

			/**
			 * Valid reference type
			 */
			private int reftype;

			public int getReftype()
			{
				return this.reftype;
			}

			
			private void setReftype(int value)
			{
				this.reftype=value;
			}

			 
			public int findByteLength()
			{
				return super.findByteLength()
					 + 4;
			}

			public void write(TPDataOutput out, Connection conn) throws IOException
			{
				out.writeInteger(this.reftype);
			}

			/**
			 * A copy constructor for (among others) deep-copying groups and lists.
			 */
			public AllowedType(AllowedType copy)
			{
				setReftype(copy.getReftype());
			}

			/**
			 * A special "internal" constructor that reads contents from a stream.
			 */
			
			AllowedType(TPDataInput in) throws IOException
			{
				this.reftype=in.readInteger32();
			}

			 
			public String toString()
			{
				return "{AllowedType"
                                    + "; reftype: "
                                    + String.valueOf(this.reftype)
                                    + "}";
				
			}

		}

		private java.util.Vector allowed=new java.util.Vector();

		public java.util.Vector getAllowed()
		{
                        return allowed;
			
		}

		
		private void setAllowed(java.util.Vector value)
		{
                        for (int i = 0; i < value.size(); i++){
                                this.allowed.addElement(new AllowedType((AllowedType)value.elementAt(i)));
                        }

		}

		 
		public int findByteLength()
		{
			return super.findByteLength()
				 + findByteLength(this.references)
				 + findByteLength(this.allowed);
		}

		 
		public void write(TPDataOutput out, Connection conn) throws IOException
		{
			super.write(out, conn);
			out.writeInteger(this.references.size());
                        for (int i = 0; i < references.size(); i ++){
                            ((ReferencesType)references.elementAt(i)).write(out, conn);
                        }
                        out.writeInteger(this.allowed.size());
			for (int i = 0; i < allowed.size(); i ++){
                            ((AllowedType)allowed.elementAt(i)).write(out, conn);
                        }

		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		
		OrderParamReferenceList(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.references.removeAllElements();
			for (int length=in.readInteger32(); length > 0; length--)
				this.references.addElement(new ReferencesType(in));
			this.allowed.removeAllElements();
			for (int length=in.readInteger32(); length > 0; length--)
				this.allowed.addElement(new AllowedType(in));
		}

		 
		public String toString()
		{
			return "{OrderParamReferenceList"
                            + "; references: "
                            + String.valueOf(this.references)
                            + "; allowed: "
                            + String.valueOf(this.allowed)
                            + "}";
			
		}

		 
		public void visit(TP03Visitor visitor) throws TPException
		{
			visitor.orderParams(this);
		}

	}

	 
	public String toString()
	{
		return "{OrderParams"
                    + "}";
		
	}

	public static OrderParams create(int id, TPDataInput in) throws IOException
	{
		switch (id)
		{
			case OrderParams.OrderParamAbsSpaceCoords.PARAM_TYPE: return new OrderParams.OrderParamAbsSpaceCoords(id, in);
			case OrderParams.OrderParamTime.PARAM_TYPE: return new OrderParams.OrderParamTime(id, in);
			case OrderParams.OrderParamObject.PARAM_TYPE: return new OrderParams.OrderParamObject(id, in);
			case OrderParams.OrderParamPlayer.PARAM_TYPE: return new OrderParams.OrderParamPlayer(id, in);
			case OrderParams.OrderParamRelSpaceCoords.PARAM_TYPE: return new OrderParams.OrderParamRelSpaceCoords(id, in);
			case OrderParams.OrderParamRange.PARAM_TYPE: return new OrderParams.OrderParamRange(id, in);
			case OrderParams.OrderParamList.PARAM_TYPE: return new OrderParams.OrderParamList(id, in);
			case OrderParams.OrderParamString.PARAM_TYPE: return new OrderParams.OrderParamString(id, in);
			case OrderParams.OrderParamReference.PARAM_TYPE: return new OrderParams.OrderParamReference(id, in);
			case OrderParams.OrderParamReferenceList.PARAM_TYPE: return new OrderParams.OrderParamReferenceList(id, in);
			//this is necessary for marshall/unmarshall tests
			case -1: return new OrderParams(id, in);
			default: throw new IllegalArgumentException("Invalid OrderParams id: "+id);
		}
	}

}
