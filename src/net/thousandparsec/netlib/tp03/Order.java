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

	private void setTurns(int value)
	{
		this.turns=value;
	}

	/**
	 * Resources needed to complete this order.
	 */
	public static class ResourcesType extends TPObject
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

		private void setAmount(int value)
		{
			this.amount=value;
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

		ResourcesType(TPDataInput in) throws IOException
		{
			this.id=in.readInteger32();
			this.amount=in.readInteger32();
		}

		public String toString()
		{
			
			return "{ResourcesType"
                            + "; id: "
                            + String.valueOf(this.id)
                            + "; amount: "
                            + String.valueOf(this.amount)
                            + "}";
			
		}

	}

	
        private java.util.Vector resources = new java.util.Vector();
	public java.util.Vector getResources()
	{
                return resources;
		//return java.util.Collections.unmodifiableList(resources);
	}

	private void setResources(java.util.Vector value)
	{
                for (int i = 0; i < value.size(); i++){
                    this.resources.addElement(new ResourcesType((ResourcesType)value.elementAt(i)));
                }
		
	}

	/**
	 * extra data, required by the order is appended to the end
	 */
	private byte[] orderparams=new byte[0];
        
	public java.util.Vector getOrderparams(OrderDesc template) throws TPException
	{
		try
		{
                        if (template.getId() != getOtype())
				throw new TPException("ParameterSet id does not match frame's parameter set id: "+ template.getId() + " != " + getOtype());
			TPDataInput in=new TPInputStream(new java.io.ByteArrayInputStream(this.orderparams));
			//java.util.List<OrderParams> ret=new java.util.ArrayList<OrderParams>();
                        java.util.Vector ret = new java.util.Vector();
			for (int i = 0; i < template.getParameters().size(); i++){
                           OrderDesc.ParametersType template0  = (OrderDesc.ParametersType)template.getParameters().elementAt(i);
                           int id = template0.getType();
                            ret.addElement(OrderParams.create(id, in));
                            
                        }
			return ret;
		}
		catch (IOException ex)
		{
			//rather unlikely, unless you pass a wrong template and hit EOFException
			throw new TPException(ex);
		}
	}

	
	private void setOrderparams(OrderParams value)
	{
		throw new RuntimeException();
	}
        public void visit(Visitor visitor) throws TPException
        {
            System.out.println("warning: visit method called in Order.java");
            visit((TP03Visitor)visitor);
        }
	
	public void visit(TP03Visitor visitor) throws TPException
	{
		visitor.frame(this);
	}

	
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

	
	public void write(TPDataOutput out, Connection conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.id);
		out.writeInteger(this.slot);
		out.writeInteger(this.otype);
		out.writeInteger(this.turns);
		out.writeInteger(this.resources.size());
                for (int i = 0; i < resources.size(); i++){
                    ((ResourcesType)resources.elementAt(i)).write(out, conn);
                }
		out.writeCharacter(this.orderparams);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	Order(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.id=in.readInteger32();
		this.slot=in.readInteger32();
		this.otype=in.readInteger32();
		this.turns=in.readInteger32();
		this.resources.removeAllElements();
		for (int length=in.readInteger32(); length > 0; length--)
			this.resources.addElement(new ResourcesType(in));
		//indirect: drain the rest of frame and decode later
		this.orderparams=in.drainFrame();
	}

	public String toString()
	{
		
		return "{Order"
                    + "; id: "
                    + String.valueOf(this.id)
                    + "; slot: "
                    + String.valueOf(this.slot)
                    + "; otype: "
                    + String.valueOf(this.otype)
                    + "; turns: "
                    + String.valueOf(this.turns)
                    + "; resources: "
                    + String.valueOf(this.resources)
                    + "; orderparams: "
                    + "<indirect>"
                    + "; super:"
                    + super.toString()
                    + "}";
		
	}

}