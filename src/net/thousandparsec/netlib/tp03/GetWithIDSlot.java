package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Get (something) using ID and slot
 */
public abstract class GetWithIDSlot extends Request
{
	protected GetWithIDSlot(int id)
	{
		super(id);
	}

	/**
	 * ID of the base thing.
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
	 * The slots on the thing to get.
	 */
	public static class SlotsType extends TPObject
	{
		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		public SlotsType()
		{
		}

		private int slot;

		public int getSlot()
		{
			return this.slot;
		}

		public void setSlot(int value)
		{
			this.slot=value;
		}

		public int findByteLength()
		{
			return super.findByteLength()
				 + 4;
		}

		public void write(TPDataOutput out, Connection conn) throws IOException
		{
			out.writeInteger(this.slot);
		}

		/**
		 * A convenience constructor for easy initialisation of non-read only fields.
		 */
		public SlotsType(int slot)
		{
			setSlot(slot);
		}

		/**
		 * A copy constructor for (among others) deep-copying groups and lists.
		 */
		public SlotsType(SlotsType copy)
		{
			setSlot(copy.getSlot());
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */

		SlotsType(TPDataInput in) throws IOException
		{
			this.slot=in.readInteger32();
		}

		public String toString()
		{
			
			return "{SlotsType"
                            + "; slot: "
                            + String.valueOf(this.slot)
                            + "}";
                            
			
		}

	}

	
        private java.util.Vector slots = new java.util.Vector();
	public java.util.Vector getSlots()
	{
		return this.slots;
	}

	private void setSlots(java.util.Vector value)
	{
                for(int i = 0; i < value.size(); i++){
                    this.slots.addElement(new SlotsType((SlotsType)value.elementAt(i)));
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
			 + findByteLength(this.slots);
	}

	public void write(TPDataOutput out, Connection conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.id);
		out.writeInteger(this.slots.size());
                for ( int i = 0; i < this.slots.size(); i ++){
                    ((SlotsType)slots.elementAt(i)).write(out, conn);
                }

	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	GetWithIDSlot(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.id=in.readInteger32();
		this.slots.removeAllElements();
		for (int length=in.readInteger32(); length > 0; length--)
			this.slots.addElement(new SlotsType(in));
	}

	public String toString()
	{
		
		return "{GetWithIDSlot"
                    + "; id: "
                    + String.valueOf(this.id)
                    + "; slots: "
                    + String.valueOf(this.slots)
                    + "; super:"
                    + super.toString()
                    + "}";
		
	}

}
