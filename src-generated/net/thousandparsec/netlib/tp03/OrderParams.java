package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * These are the parameters for Orders and OrderDescriptions.
 */
public class OrderParams extends TPObject<TP03Visitor> implements Visitable<TP03Visitor>
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

	/**
	 * Coordinates in absolute space. (Relative to the center of the Universe)
	 */
	public static class OrderParamAbsSpaceCoords extends OrderParams
	{
		public static final int PARAM_TYPE=0;

		/**
		 * A default constructor for subclasses, which initialises properties to their defaults.
		 */
		protected OrderParamAbsSpaceCoords(int id)
		{
			super(id);
		}

		/**
		 * A default constructor for general public, which initialises properties to their defaults.
		 */
		public OrderParamAbsSpaceCoords()
		{
			super(PARAM_TYPE);
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
			@SuppressWarnings("unused")
			PosType(TPDataInput in) throws IOException
			{
				this.x=in.readInteger64();
				this.y=in.readInteger64();
				this.z=in.readInteger64();
			}

			@Override
			public String toString()
			{
				StringBuilder buf=new StringBuilder();
				buf.append("{PosType");
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

		private PosType pos=new PosType();

		public PosType getPos()
		{
			return this.pos;
		}

		/**
		 * NOTE: this method does not copy the value object.
		 */
		public void setPos(PosType value)
		{
			this.pos=value;
		}

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + findByteLength(this.pos);
		}

		@Override
		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			super.write(out, conn);
			this.pos.write(out, conn);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		@SuppressWarnings("unused")
		OrderParamAbsSpaceCoords(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.pos=new PosType(in);
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{OrderParamAbsSpaceCoords");
			buf.append("; pos: ");
			buf.append(String.valueOf(this.pos));
			buf.append("}");
			return buf.toString();
		}

		@Override
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
		 * A default constructor for subclasses, which initialises properties to their defaults.
		 */
		protected OrderParamTime(int id)
		{
			super(id);
		}

		/**
		 * A default constructor for general public, which initialises properties to their defaults.
		 */
		public OrderParamTime()
		{
			super(PARAM_TYPE);
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

		@SuppressWarnings("unused")
		private void setMaxtime(int value)
		{
			this.maxtime=value;
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
			out.writeInteger(this.turns);
			out.writeInteger(this.maxtime);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		@SuppressWarnings("unused")
		OrderParamTime(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.turns=in.readInteger32();
			this.maxtime=in.readInteger32();
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{OrderParamTime");
			buf.append("; turns: ");
			buf.append(String.valueOf(this.turns));
			buf.append("; maxtime: ");
			buf.append(String.valueOf(this.maxtime));
			buf.append("}");
			return buf.toString();
		}

		@Override
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
		 * A default constructor for subclasses, which initialises properties to their defaults.
		 */
		protected OrderParamObject(int id)
		{
			super(id);
		}

		/**
		 * A default constructor for general public, which initialises properties to their defaults.
		 */
		public OrderParamObject()
		{
			super(PARAM_TYPE);
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
			out.writeInteger(this.objectid);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		@SuppressWarnings("unused")
		OrderParamObject(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.objectid=in.readInteger32();
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{OrderParamObject");
			buf.append("; objectid: ");
			buf.append(String.valueOf(this.objectid));
			buf.append("}");
			return buf.toString();
		}

		@Override
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
		 * A default constructor for subclasses, which initialises properties to their defaults.
		 */
		protected OrderParamPlayer(int id)
		{
			super(id);
		}

		/**
		 * A default constructor for general public, which initialises properties to their defaults.
		 */
		public OrderParamPlayer()
		{
			super(PARAM_TYPE);
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

		@SuppressWarnings("unused")
		private void setMask(int value)
		{
			this.mask=value;
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
			out.writeInteger(this.playerid);
			out.writeInteger(this.mask);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		@SuppressWarnings("unused")
		OrderParamPlayer(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.playerid=in.readInteger32();
			this.mask=in.readInteger32();
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{OrderParamPlayer");
			buf.append("; playerid: ");
			buf.append(String.valueOf(this.playerid));
			buf.append("; mask: ");
			buf.append(String.valueOf(this.mask));
			buf.append("}");
			return buf.toString();
		}

		@Override
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
		 * A default constructor for subclasses, which initialises properties to their defaults.
		 */
		protected OrderParamRelSpaceCoords(int id)
		{
			super(id);
		}

		/**
		 * A default constructor for general public, which initialises properties to their defaults.
		 */
		public OrderParamRelSpaceCoords()
		{
			super(PARAM_TYPE);
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

		public static class RelposType extends TPObject<TP03Visitor>
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
			@SuppressWarnings("unused")
			RelposType(TPDataInput in) throws IOException
			{
				this.x=in.readInteger64();
				this.y=in.readInteger64();
				this.z=in.readInteger64();
			}

			@Override
			public String toString()
			{
				StringBuilder buf=new StringBuilder();
				buf.append("{RelposType");
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

		private RelposType relpos=new RelposType();

		public RelposType getRelpos()
		{
			return this.relpos;
		}

		/**
		 * NOTE: this method does not copy the value object.
		 */
		public void setRelpos(RelposType value)
		{
			this.relpos=value;
		}

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + 4
				 + findByteLength(this.relpos);
		}

		@Override
		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			super.write(out, conn);
			out.writeInteger(this.objectid);
			this.relpos.write(out, conn);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		@SuppressWarnings("unused")
		OrderParamRelSpaceCoords(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.objectid=in.readInteger32();
			this.relpos=new RelposType(in);
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{OrderParamRelSpaceCoords");
			buf.append("; objectid: ");
			buf.append(String.valueOf(this.objectid));
			buf.append("; relpos: ");
			buf.append(String.valueOf(this.relpos));
			buf.append("}");
			return buf.toString();
		}

		@Override
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
		 * A default constructor for subclasses, which initialises properties to their defaults.
		 */
		protected OrderParamRange(int id)
		{
			super(id);
		}

		/**
		 * A default constructor for general public, which initialises properties to their defaults.
		 */
		public OrderParamRange()
		{
			super(PARAM_TYPE);
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

		@SuppressWarnings("unused")
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

		@SuppressWarnings("unused")
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

		@SuppressWarnings("unused")
		private void setIncrement(int value)
		{
			this.increment=value;
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

		@Override
		public void write(TPDataOutput out, Connection<?> conn) throws IOException
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
		@SuppressWarnings("unused")
		OrderParamRange(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.value=in.readInteger32();
			this.minvalue=in.readInteger32();
			this.maxvalue=in.readInteger32();
			this.increment=in.readInteger32();
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{OrderParamRange");
			buf.append("; value: ");
			buf.append(String.valueOf(this.value));
			buf.append("; minvalue: ");
			buf.append(String.valueOf(this.minvalue));
			buf.append("; maxvalue: ");
			buf.append(String.valueOf(this.maxvalue));
			buf.append("; increment: ");
			buf.append(String.valueOf(this.increment));
			buf.append("}");
			return buf.toString();
		}

		@Override
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
		 * A default constructor for subclasses, which initialises properties to their defaults.
		 */
		protected OrderParamList(int id)
		{
			super(id);
		}

		/**
		 * A default constructor for general public, which initialises properties to their defaults.
		 */
		public OrderParamList()
		{
			super(PARAM_TYPE);
		}

		/**
		 * A list of the items which can be chosen.
		 */
		public static class PossibleselectionsType extends TPObject<TP03Visitor>
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

			@SuppressWarnings("unused")
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

			@SuppressWarnings("unused")
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

			@SuppressWarnings("unused")
			private void setMaxnum(int value)
			{
				this.maxnum=value;
			}

			@Override
			public int findByteLength()
			{
				return super.findByteLength()
					 + 4
					 + findByteLength(this.name)
					 + 4;
			}

			public void write(TPDataOutput out, Connection<?> conn) throws IOException
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
			@SuppressWarnings("unused")
			PossibleselectionsType(TPDataInput in) throws IOException
			{
				this.id=in.readInteger32();
				this.name=in.readString();
				this.maxnum=in.readInteger32();
			}

			@Override
			public String toString()
			{
				StringBuilder buf=new StringBuilder();
				buf.append("{PossibleselectionsType");
				buf.append("; id: ");
				buf.append(String.valueOf(this.id));
				buf.append("; name: ");
				buf.append(String.valueOf(this.name));
				buf.append("; maxnum: ");
				buf.append(String.valueOf(this.maxnum));
				buf.append("}");
				return buf.toString();
			}

		}

		private java.util.List<PossibleselectionsType> possibleselections=new java.util.ArrayList<PossibleselectionsType>();

		public java.util.List<PossibleselectionsType> getPossibleselections()
		{
			return java.util.Collections.unmodifiableList(possibleselections);
		}

		@SuppressWarnings("unused")
		private void setPossibleselections(java.util.List<PossibleselectionsType> value)
		{
			for (PossibleselectionsType object : value)
				this.possibleselections.add(new PossibleselectionsType(object));
		}

		/**
		 * A list of the items which have been selected.
		 */
		public static class SelectionType extends TPObject<TP03Visitor>
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

			@Override
			public int findByteLength()
			{
				return super.findByteLength()
					 + 4
					 + 4;
			}

			public void write(TPDataOutput out, Connection<?> conn) throws IOException
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
			@SuppressWarnings("unused")
			SelectionType(TPDataInput in) throws IOException
			{
				this.id=in.readInteger32();
				this.number=in.readInteger32();
			}

			@Override
			public String toString()
			{
				StringBuilder buf=new StringBuilder();
				buf.append("{SelectionType");
				buf.append("; id: ");
				buf.append(String.valueOf(this.id));
				buf.append("; number: ");
				buf.append(String.valueOf(this.number));
				buf.append("}");
				return buf.toString();
			}

		}

		private java.util.List<SelectionType> selection=new java.util.ArrayList<SelectionType>();

		public java.util.List<SelectionType> getSelection()
		{
			return this.selection;
		}

		@SuppressWarnings("unused")
		private void setSelection(java.util.List<SelectionType> value)
		{
			for (SelectionType object : value)
				this.selection.add(new SelectionType(object));
		}

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + findByteLength(this.possibleselections)
				 + findByteLength(this.selection);
		}

		@Override
		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			super.write(out, conn);
			out.writeInteger(this.possibleselections.size());
			for (PossibleselectionsType object : this.possibleselections)
				object.write(out, conn);
			out.writeInteger(this.selection.size());
			for (SelectionType object : this.selection)
				object.write(out, conn);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		@SuppressWarnings("unused")
		OrderParamList(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.possibleselections.clear();
			for (int length=in.readInteger32(); length > 0; length--)
				this.possibleselections.add(new PossibleselectionsType(in));
			this.selection.clear();
			for (int length=in.readInteger32(); length > 0; length--)
				this.selection.add(new SelectionType(in));
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{OrderParamList");
			buf.append("; possibleselections: ");
			buf.append(String.valueOf(this.possibleselections));
			buf.append("; selection: ");
			buf.append(String.valueOf(this.selection));
			buf.append("}");
			return buf.toString();
		}

		@Override
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
		 * A default constructor for subclasses, which initialises properties to their defaults.
		 */
		protected OrderParamString(int id)
		{
			super(id);
		}

		/**
		 * A default constructor for general public, which initialises properties to their defaults.
		 */
		public OrderParamString()
		{
			super(PARAM_TYPE);
		}

		/**
		 * The maximum length of the string
		 */
		private int maxlength;

		public int getMaxlength()
		{
			return this.maxlength;
		}

		@SuppressWarnings("unused")
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

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + 4
				 + findByteLength(this.string);
		}

		@Override
		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			super.write(out, conn);
			out.writeInteger(this.maxlength);
			out.writeString(this.string);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		@SuppressWarnings("unused")
		OrderParamString(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.maxlength=in.readInteger32();
			this.string=in.readString();
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{OrderParamString");
			buf.append("; maxlength: ");
			buf.append(String.valueOf(this.maxlength));
			buf.append("; string: ");
			buf.append(String.valueOf(this.string));
			buf.append("}");
			return buf.toString();
		}

		@Override
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
		 * A default constructor for subclasses, which initialises properties to their defaults.
		 */
		protected OrderParamReference(int id)
		{
			super(id);
		}

		/**
		 * A default constructor for general public, which initialises properties to their defaults.
		 */
		public OrderParamReference()
		{
			super(PARAM_TYPE);
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
		public static class AllowedType extends TPObject<TP03Visitor>
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

			@SuppressWarnings("unused")
			private void setReftype(int value)
			{
				this.reftype=value;
			}

			@Override
			public int findByteLength()
			{
				return super.findByteLength()
					 + 4;
			}

			public void write(TPDataOutput out, Connection<?> conn) throws IOException
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
			@SuppressWarnings("unused")
			AllowedType(TPDataInput in) throws IOException
			{
				this.reftype=in.readInteger32();
			}

			@Override
			public String toString()
			{
				StringBuilder buf=new StringBuilder();
				buf.append("{AllowedType");
				buf.append("; reftype: ");
				buf.append(String.valueOf(this.reftype));
				buf.append("}");
				return buf.toString();
			}

		}

		private java.util.List<AllowedType> allowed=new java.util.ArrayList<AllowedType>();

		public java.util.List<AllowedType> getAllowed()
		{
			return java.util.Collections.unmodifiableList(allowed);
		}

		@SuppressWarnings("unused")
		private void setAllowed(java.util.List<AllowedType> value)
		{
			for (AllowedType object : value)
				this.allowed.add(new AllowedType(object));
		}

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + 4
				 + findByteLength(this.allowed);
		}

		@Override
		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			super.write(out, conn);
			out.writeInteger(this.reference);
			out.writeInteger(this.allowed.size());
			for (AllowedType object : this.allowed)
				object.write(out, conn);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		@SuppressWarnings("unused")
		OrderParamReference(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.reference=in.readInteger32();
			this.allowed.clear();
			for (int length=in.readInteger32(); length > 0; length--)
				this.allowed.add(new AllowedType(in));
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{OrderParamReference");
			buf.append("; reference: ");
			buf.append(String.valueOf(this.reference));
			buf.append("; allowed: ");
			buf.append(String.valueOf(this.allowed));
			buf.append("}");
			return buf.toString();
		}

		@Override
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
		 * A default constructor for subclasses, which initialises properties to their defaults.
		 */
		protected OrderParamReferenceList(int id)
		{
			super(id);
		}

		/**
		 * A default constructor for general public, which initialises properties to their defaults.
		 */
		public OrderParamReferenceList()
		{
			super(PARAM_TYPE);
		}

		/**
		 * The list of references.
		 */
		public static class ReferencesType extends TPObject<TP03Visitor>
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

			@Override
			public int findByteLength()
			{
				return super.findByteLength()
					 + 4;
			}

			public void write(TPDataOutput out, Connection<?> conn) throws IOException
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
			@SuppressWarnings("unused")
			ReferencesType(TPDataInput in) throws IOException
			{
				this.reference=in.readInteger32();
			}

			@Override
			public String toString()
			{
				StringBuilder buf=new StringBuilder();
				buf.append("{ReferencesType");
				buf.append("; reference: ");
				buf.append(String.valueOf(this.reference));
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

		/**
		 * A list of allowed valid reference types.
		 */
		public static class AllowedType extends TPObject<TP03Visitor>
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

			@SuppressWarnings("unused")
			private void setReftype(int value)
			{
				this.reftype=value;
			}

			@Override
			public int findByteLength()
			{
				return super.findByteLength()
					 + 4;
			}

			public void write(TPDataOutput out, Connection<?> conn) throws IOException
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
			@SuppressWarnings("unused")
			AllowedType(TPDataInput in) throws IOException
			{
				this.reftype=in.readInteger32();
			}

			@Override
			public String toString()
			{
				StringBuilder buf=new StringBuilder();
				buf.append("{AllowedType");
				buf.append("; reftype: ");
				buf.append(String.valueOf(this.reftype));
				buf.append("}");
				return buf.toString();
			}

		}

		private java.util.List<AllowedType> allowed=new java.util.ArrayList<AllowedType>();

		public java.util.List<AllowedType> getAllowed()
		{
			return java.util.Collections.unmodifiableList(allowed);
		}

		@SuppressWarnings("unused")
		private void setAllowed(java.util.List<AllowedType> value)
		{
			for (AllowedType object : value)
				this.allowed.add(new AllowedType(object));
		}

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + findByteLength(this.references)
				 + findByteLength(this.allowed);
		}

		@Override
		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			super.write(out, conn);
			out.writeInteger(this.references.size());
			for (ReferencesType object : this.references)
				object.write(out, conn);
			out.writeInteger(this.allowed.size());
			for (AllowedType object : this.allowed)
				object.write(out, conn);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		@SuppressWarnings("unused")
		OrderParamReferenceList(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.references.clear();
			for (int length=in.readInteger32(); length > 0; length--)
				this.references.add(new ReferencesType(in));
			this.allowed.clear();
			for (int length=in.readInteger32(); length > 0; length--)
				this.allowed.add(new AllowedType(in));
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{OrderParamReferenceList");
			buf.append("; references: ");
			buf.append(String.valueOf(this.references));
			buf.append("; allowed: ");
			buf.append(String.valueOf(this.allowed));
			buf.append("}");
			return buf.toString();
		}

		@Override
		public void visit(TP03Visitor visitor) throws TPException
		{
			visitor.orderParams(this);
		}

	}

	@Override
	public String toString()
	{
		StringBuilder buf=new StringBuilder();
		buf.append("{OrderParams");
		buf.append("}");
		return buf.toString();
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
