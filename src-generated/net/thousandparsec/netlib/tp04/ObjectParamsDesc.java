package net.thousandparsec.netlib.tp04;

import net.thousandparsec.netlib.*;

import java.io.IOException;

public class ObjectParamsDesc extends TPObject<TP04Visitor> implements Visitable<TP04Visitor>
{
	private final int id;

	protected ObjectParamsDesc(int id)
	{
		this.id=id;
	}

	@SuppressWarnings("unused")
	ObjectParamsDesc(int id, TPDataInput in) throws IOException
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

	public void visit(TP04Visitor visitor) throws TPException
	{
		throw new RuntimeException();
	}

	public static class ObjectParamOrderQueue extends ObjectParamsDesc
	{
		public static final int PARAM_TYPE=4;

		/**
		 * A default constructor for subclasses, which initialises properties to their defaults.
		 */
		protected ObjectParamOrderQueue(int id)
		{
			super(id);
		}

		/**
		 * A default constructor for general public, which initialises properties to their defaults.
		 */
		public ObjectParamOrderQueue()
		{
			super(PARAM_TYPE);
		}

		/**
		 * The maximum number of slots that can be used in this queue.
		 */
		private int maxslots;

		public int getMaxslots()
		{
			return this.maxslots;
		}

		@SuppressWarnings("unused")
		private void setMaxslots(int value)
		{
			this.maxslots=value;
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
			out.writeInteger(this.maxslots);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		ObjectParamOrderQueue(int id, TPDataInput in) throws IOException
		{
			super(id, in);
			this.maxslots=in.readInteger32();
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{ObjectParamOrderQueue");
			buf.append("; maxslots: ");
			buf.append(String.valueOf(this.maxslots));
			buf.append("}");
			return buf.toString();
		}

		@Override
		public void visit(TP04Visitor visitor) throws TPException
		{
			visitor.objectParamsDesc(this);
		}

	}

	@Override
	public String toString()
	{
		StringBuilder buf=new StringBuilder();
		buf.append("{ObjectParamsDesc");
		buf.append("}");
		return buf.toString();
	}

	public static ObjectParamsDesc create(int id, TPDataInput in) throws IOException
	{
		switch (id)
		{
			case ObjectParamsDesc.ObjectParamOrderQueue.PARAM_TYPE: return new ObjectParamsDesc.ObjectParamOrderQueue(id, in);
			//this is necessary for marshall/unmarshall tests
			case -1: return new ObjectParamsDesc(id, in);
			default: throw new IllegalArgumentException("Invalid ObjectParamsDesc id: "+id);
		}
	}

}
