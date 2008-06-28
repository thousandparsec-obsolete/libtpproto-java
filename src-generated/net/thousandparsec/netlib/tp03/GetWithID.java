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
	public static class IdsType extends TPObject<TP03Visitor>
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

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + 4;
		}

		public void write(TPDataOutput out, Connection<?> conn) throws IOException
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

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{IdsType");
			buf.append("; id: ");
			buf.append(String.valueOf(this.id));
			buf.append("}");
			return buf.toString();
		}

	}

	private java.util.List<IdsType> ids=new java.util.ArrayList<IdsType>();

	public java.util.List<IdsType> getIds()
	{
		return this.ids;
	}

	@SuppressWarnings("unused")
	private void setIds(java.util.List<IdsType> value)
	{
		for (IdsType object : value)
			this.ids.add(new IdsType(object));
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
			 + findByteLength(this.ids);
	}

	@Override
	public void write(TPDataOutput out, Connection<?> conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.ids.size());
		for (IdsType object : this.ids)
			object.write(out, conn);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	GetWithID(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.ids.clear();
		for (int length=in.readInteger32(); length > 0; length--)
			this.ids.add(new IdsType(in));
	}

	@Override
	public String toString()
	{
		StringBuilder buf=new StringBuilder();
		buf.append("{GetWithID");
		buf.append("; ids: ");
		buf.append(String.valueOf(this.ids));
		buf.append("; super:").append(super.toString());
		buf.append("}");
		return buf.toString();
	}

}
