package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Get (something) using IDs
 */
public abstract class GetWithID extends Request
{
	protected GetWithID(int id)
	{
		super(id);
	}

	/**
	 * The IDs to get.
	 */
	public static class IdsType extends TPObject
	{
		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		public IdsType()
		{
		}

		private int id;

		public int getId()
		{
			return this.id;
		}

		public void setId(int value)
		{
			this.id=value;
		}

		public int findByteLength()
		{
			return super.findByteLength()
				 + 4;
		}

		public void write(TPDataOutput out, Connection conn) throws IOException
		{
			out.writeInteger(this.id);
		}

		/**
		 * A convenience constructor for easy initialisation of non-read only fields.
		 */
		public IdsType(int id)
		{
			setId(id);
		}

		/**
		 * A copy constructor for (among others) deep-copying groups and lists.
		 */
		public IdsType(IdsType copy)
		{
			setId(copy.getId());
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */

		IdsType(TPDataInput in) throws IOException
		{
			this.id=in.readInteger32();
		}

		public String toString()
		{
			
			return "{IdsType"
                            + "; id: "
                            + String.valueOf(this.id)
                            + "}";
			
		}

	}

        private java.util.Vector ids = new java.util.Vector();
	public java.util.Vector getIds()
	{
		return this.ids;
	}

	private void setIds(java.util.Vector value)
	{
                for (int i = 0; i < value.size(); i++){
                    this.ids.addElement(new IdsType((IdsType)value.elementAt(i)));
                }

	}

	public void visit(TP03Visitor visitor) throws TPException
	{
		//NOP (not a leaf class)
	}

	public int findByteLength()
	{
		return super.findByteLength()
			 + findByteLength(this.ids);
	}

	public void write(TPDataOutput out, Connection conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.ids.size());
                for (int i=0; i < ids.size(); i++){
                    ((IdsType)ids.elementAt(i)).write(out, conn);
                }

	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	GetWithID(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.ids.removeAllElements();
		for (int length=in.readInteger32(); length > 0; length--)
			this.ids.addElement(new IdsType(in));
	}

	public String toString()
	{
		
		return "{GetWithID"
                    + "; ids: "
                    + String.valueOf(this.ids)
                    + "; super:"
                    + super.toString()
                    + "}";
		
	}

}
