package net.thousandparsec.netlib.tp04;

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
	public static class SlotsType extends TPObject<TP04Visitor>
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

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + 4;
		}

		public void write(TPDataOutput out, Connection<?> conn) throws IOException
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

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{SlotsType");
			buf.append("; slot: ");
			buf.append(String.valueOf(this.slot));
			buf.append("}");
			return buf.toString();
		}

	}

	private java.util.List<SlotsType> slots=new java.util.ArrayList<SlotsType>();

	public java.util.List<SlotsType> getSlots()
	{
		return this.slots;
	}

	@SuppressWarnings("unused")
	private void setSlots(java.util.List<SlotsType> value)
	{
		for (SlotsType object : value)
			this.slots.add(new SlotsType(object));
	}

	@Override
	public void visit(TP04Visitor visitor) throws TPException
	{
		//NOP (not a leaf class)
	}

	@Override
	public int findByteLength()
	{
		return super.findByteLength()
			 + 4
			 + findByteLength(this.slots);
	}

	@Override
	public void write(TPDataOutput out, Connection<?> conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.id);
		out.writeInteger(this.slots.size());
		for (SlotsType object : this.slots)
			object.write(out, conn);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	GetWithIDSlot(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.id=in.readInteger32();
		this.slots.clear();
		for (int length=in.readInteger32(); length > 0; length--)
			this.slots.add(new SlotsType(in));
	}

	@Override
	public String toString()
	{
		StringBuilder buf=new StringBuilder();
		buf.append("{GetWithIDSlot");
		buf.append("; id: ");
		buf.append(String.valueOf(this.id));
		buf.append("; slots: ");
		buf.append(String.valueOf(this.slots));
		buf.append("; super:").append(super.toString());
		buf.append("}");
		return buf.toString();
	}

}
