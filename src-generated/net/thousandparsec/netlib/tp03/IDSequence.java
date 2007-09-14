package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * ID Sequence
 */
public abstract class IDSequence extends Response
{
	protected IDSequence(int id)
	{
		super(id);
	}

	/**
	 * the sequence key
	 */
	private int key;

	public int getKey()
	{
		return this.key;
	}

	public void setKey(int value)
	{
		this.key=value;
	}

	/**
	 * the number of IDs remaining
	 */
	private int remaining;

	public int getRemaining()
	{
		return this.remaining;
	}

	public void setRemaining(int value)
	{
		this.remaining=value;
	}

	/**
	 * Modification Times of each ID
	 */
	public static class ModtimesType extends TPObject<TP03Visitor>
	{
		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		public ModtimesType()
		{
		}

		/**
		 * the ID
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
		 * The time at which the thing which this ID referes to was last modified.
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

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + 4
				 + 8;
		}

		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			out.writeInteger(this.id);
			out.writeInteger(this.modtime);
		}

		/**
		 * A convenience constructor for easy initialisation of non-read only fields.
		 */
		public ModtimesType(int id, long modtime)
		{
			setId(id);
			setModtime(modtime);
		}

		/**
		 * A copy constructor for (among others) deep-copying groups and lists.
		 */
		public ModtimesType(ModtimesType copy)
		{
			setId(copy.getId());
			setModtime(copy.getModtime());
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		@SuppressWarnings("unused")
		ModtimesType(TPDataInput in) throws IOException
		{
			this.id=in.readInteger32();
			this.modtime=in.readInteger64();
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{ModtimesType");
			buf.append("; id: ");
			buf.append(String.valueOf(this.id));
			buf.append("; modtime: ");
			buf.append(String.valueOf(this.modtime));
			buf.append("}");
			return buf.toString();
		}

	}

	private java.util.List<ModtimesType> modtimes=new java.util.ArrayList<ModtimesType>();

	public java.util.List<ModtimesType> getModtimes()
	{
		return this.modtimes;
	}

	@SuppressWarnings("unused")
	private void setModtimes(java.util.List<ModtimesType> value)
	{
		for (ModtimesType object : value)
			this.modtimes.add(new ModtimesType(object));
	}

	@Override
	public void visit(TP03Visitor visitor) throws TPException
	{
		//NOP (not a leaf class)
	}

	@Override
	public int findByteLength()
	{
		return super.findByteLength()
			 + 4
			 + 4
			 + findByteLength(this.modtimes);
	}

	@Override
	public void write(TPDataOutput out, Connection<?> conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.key);
		out.writeInteger(this.remaining);
		out.writeInteger(this.modtimes.size());
		for (ModtimesType object : this.modtimes)
			object.write(out, conn);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	@SuppressWarnings("unused")
	IDSequence(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.key=in.readInteger32();
		this.remaining=in.readInteger32();
		this.modtimes.clear();
		for (int length=in.readInteger32(); length > 0; length--)
			this.modtimes.add(new ModtimesType(in));
	}

	@Override
	public String toString()
	{
		StringBuilder buf=new StringBuilder();
		buf.append("{IDSequence");
		buf.append("; key: ");
		buf.append(String.valueOf(this.key));
		buf.append("; remaining: ");
		buf.append(String.valueOf(this.remaining));
		buf.append("; modtimes: ");
		buf.append(String.valueOf(this.modtimes));
		buf.append("; super:").append(super.toString());
		buf.append("}");
		return buf.toString();
	}

}
