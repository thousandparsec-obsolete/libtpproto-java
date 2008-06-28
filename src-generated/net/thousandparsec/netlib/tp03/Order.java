package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Order
 */
public class Order extends Response
{
	public static final int FRAME_TYPE=11;

	protected Order(int id)
	{
		super(id);
	}

	public Order()
	{
		super(FRAME_TYPE);
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

	private int otype;

	public int getOtype()
	{
		return this.otype;
	}

	public void setOtype(int value)
	{
		this.otype=value;
	}

	/**
	 * The number of turns the order will take.
	 */
	private int turns;

	public int getTurns()
	{
		return this.turns;
	}

	@SuppressWarnings("unused")
	private void setTurns(int value)
	{
		this.turns=value;
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

		@SuppressWarnings("unused")
		private void setId(int value)
		{
			this.id=value;
		}

		/**
		 * The units of that resource required
		 */
		private int amount;

		public int getAmount()
		{
			return this.amount;
		}

		@SuppressWarnings("unused")
		private void setAmount(int value)
		{
			this.amount=value;
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
		 * A copy constructor for (among others) deep-copying groups and lists.
		 */
		public ResourcesType(ResourcesType copy)
		{
			setId(copy.getId());
			setAmount(copy.getAmount());
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

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{ResourcesType");
			buf.append("; id: ");
			buf.append(String.valueOf(this.id));
			buf.append("; amount: ");
			buf.append(String.valueOf(this.amount));
			buf.append("}");
			return buf.toString();
		}

	}

	private java.util.List<ResourcesType> resources=new java.util.ArrayList<ResourcesType>();

	public java.util.List<ResourcesType> getResources()
	{
		return java.util.Collections.unmodifiableList(resources);
	}

	@SuppressWarnings("unused")
	private void setResources(java.util.List<ResourcesType> value)
	{
		for (ResourcesType object : value)
			this.resources.add(new ResourcesType(object));
	}

	/**
	 * extra data, required by the order is appended to the end
	 */
	private byte[] orderparams=new byte[0];

	public java.util.List<OrderParams> getOrderparams(OrderDesc template) throws TPException
	{
		try
		{
			if (template.getId() != getOtype())
				throw new TPException(String.format("ParameterSet id does not match frame's parameter set id: %d != %d", template.getId(), getOtype()));
			TPDataInput in=new TPInputStream(new java.io.ByteArrayInputStream(this.orderparams));
			java.util.List<OrderParams> ret=new java.util.ArrayList<OrderParams>();
			for (OrderDesc.ParametersType template0 : template.getParameters())
			{
				ret.add(OrderParams.create(template0.getType(), in));
			}
			return ret;
		}
		catch (IOException ex)
		{
			//rather unlikely, unless you pass a wrong template and hit EOFException
			throw new TPException(ex);
		}
	}

	public void setOrderparams(OrderParams value)
	{
		try
		{
			java.io.ByteArrayOutputStream bout=new java.io.ByteArrayOutputStream();
			TPOutputStream out=new TPOutputStream(bout);
			value.write(out, null);
			out.flush();
			bout.close();
			this.orderparams=bout.toByteArray();
		}
		catch (IOException fatal)
		{
			//this should not happen with ByteArrayOutputStream
			throw new RuntimeException(fatal);
		}
	}

	@Override
	public void visit(TP03Visitor visitor) throws TPException
	{
		visitor.frame(this);
	}

	@Override
	public int findByteLength()
	{
		return super.findByteLength()
			 + 4
			 + 4
			 + 4
			 + 4
			 + findByteLength(this.resources)
			 + this.orderparams.length;
	}

	@Override
	public void write(TPDataOutput out, Connection<?> conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.id);
		out.writeInteger(this.slot);
		out.writeInteger(this.otype);
		out.writeInteger(this.turns);
		out.writeInteger(this.resources.size());
		for (ResourcesType object : this.resources)
			object.write(out, conn);
		out.writeCharacter(this.orderparams);
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
		this.otype=in.readInteger32();
		this.turns=in.readInteger32();
		this.resources.clear();
		for (int length=in.readInteger32(); length > 0; length--)
			this.resources.add(new ResourcesType(in));
		//indirect: drain the rest of frame and decode later
		this.orderparams=in.drainFrame();
	}

	@Override
	public String toString()
	{
		StringBuilder buf=new StringBuilder();
		buf.append("{Order");
		buf.append("; id: ");
		buf.append(String.valueOf(this.id));
		buf.append("; slot: ");
		buf.append(String.valueOf(this.slot));
		buf.append("; otype: ");
		buf.append(String.valueOf(this.otype));
		buf.append("; turns: ");
		buf.append(String.valueOf(this.turns));
		buf.append("; resources: ");
		buf.append(String.valueOf(this.resources));
		buf.append("; orderparams: ");
		buf.append("<indirect>");
		buf.append("; super:").append(super.toString());
		buf.append("}");
		return buf.toString();
	}

}
