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
	public static class ModtimesType extends TPObject
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

		public int findByteLength()
		{
			return super.findByteLength()
				 + 4
				 + 8;
		}

		public void write(TPDataOutput out, Connection conn) throws IOException
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

		ModtimesType(TPDataInput in) throws IOException
		{
			this.id=in.readInteger32();
			this.modtime=in.readInteger64();
		}

		public String toString()
		{
			
			return "{ModtimesType"
                            + "; id: "
                            + String.valueOf(this.id)
                            + "; modtime: "
                            + String.valueOf(this.modtime)
                            + "}";
			
		}

	}

	
        private java.util.Vector modtimes = new java.util.Vector();
	public java.util.Vector getModtimes()
	{
		return this.modtimes;
	}

	private void setModtimes(java.util.Vector value)
	{
                for (int i = 0; i < value.size(); i++){
                    this.modtimes.addElement(new ModtimesType((ModtimesType)value.elementAt(i)));
                }

	}

	public void visit(TP03Visitor visitor) throws TPException
	{
		//NOP (not a leaf class)
	}

	public int findByteLength()
	{
		return super.findByteLength()
			 + 4
			 + 4
			 + findByteLength(this.modtimes);
	}

	public void write(TPDataOutput out, Connection conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.key);
		out.writeInteger(this.remaining);
		out.writeInteger(this.modtimes.size());
                for (int i = 0; i < modtimes.size(); i++){
                    ((ModtimesType)modtimes.elementAt(i)).write(out, conn);
                }

	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */

	IDSequence(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.key=in.readInteger32();
		this.remaining=in.readInteger32();
		this.modtimes.removeAllElements();
		for (int length=in.readInteger32(); length > 0; length--)
			this.modtimes.addElement(new ModtimesType(in));
	}

	public String toString()
	{
		
		return "{IDSequence"
                    + "; key: "
                    + String.valueOf(this.key)
                    + "; remaining: "
                    + String.valueOf(this.remaining)
                    + "; modtimes: "
                    + String.valueOf(this.modtimes)
                    + "; super:"
                    + super.toString()
                    + "}";
		
	}

}
