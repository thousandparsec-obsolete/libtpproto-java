package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Order
 */
public class Order extends Response
{
	protected Order(int id)
	{
		super(id);
	}

	public Order()
	{
		super(11);
	}

	/**
	 * Object ID of the order is on/to be placed on
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
	 * Slot number of the order/to be put in.
	 */
	private int slot;

	public int getSlot()
	{
		return this.slot;
	}

	public void setSlot(int value)
	{
		this.slot=value;
	}

	private int type;

	public int getType()
	{
		return this.type;
	}

	public void setType(int value)
	{
		this.type=value;
	}

	/**
	 * The number of turns the order will take.
	 */
	private int turns;

	public int getTurns()
	{
		return this.turns;
	}

	/**
	 * Resources needed to complete this order.
	 */
	public static class ResourcesType extends TPObject<TP03Visitor>
	{
		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		public ResourcesType()
		{
		}

		/**
		 * Identifier of the resource.
		 */
		private int id;

		public int getId()
		{
			return this.id;
		}

		/**
		 * The units of that resource required
		 */
		private int amount;

		public int getAmount()
		{
			return this.amount;
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
			out.writeInteger(this.amount);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		@SuppressWarnings("unused")
		ResourcesType(TPDataInput in) throws IOException
		{
			this.id=in.readInteger32();
			this.amount=in.readInteger32();
		}

	}

	private java.util.List<ResourcesType> resources=new java.util.ArrayList<ResourcesType>();

	public java.util.List<ResourcesType> getResources()
	{
		return java.util.Collections.unmodifiableList(resources);
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
			 + 4
			 + 4
			 + findByteLength(this.resources);
	}

	@Override
	public void write(TPDataOutput out, Connection<?> conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.id);
		out.writeInteger(this.slot);
		out.writeInteger(this.type);
		out.writeInteger(this.turns);
		out.writeInteger(this.resources.size());
		for (ResourcesType object : this.resources)
			object.write(out, conn);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	@SuppressWarnings("unused")
	Order(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.id=in.readInteger32();
		this.slot=in.readInteger32();
		this.type=in.readInteger32();
		this.turns=in.readInteger32();
		this.resources.clear();
		for (int length=in.readInteger32(); length > 0; length--)
			this.resources.add(new ResourcesType(in));
	}

}
