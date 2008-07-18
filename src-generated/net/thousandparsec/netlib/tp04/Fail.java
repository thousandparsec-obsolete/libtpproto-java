package net.thousandparsec.netlib.tp04;

import net.thousandparsec.netlib.*;

import java.io.IOException;

public class Fail extends Response
{
	public static final int FRAME_TYPE=1;

	protected Fail(int id)
	{
		super(id);
	}

	public Fail()
	{
		super(FRAME_TYPE);
	}

	/**
	 * Text message of the error.
	 */
	public enum Code
	{
		$none$(-1),

		/**
		 * Protocol Error, Something went wrong with the protocol
		 */
		Protocol(0),

		/**
		 * Frame Error, One of the frames sent was bad or corrupted
		 */
		Frame(1),

		/**
		 * Unavailable Permanently, This operation is unavailable
		 */
		UnavailablePermanently(2),

		/**
		 * Unavailable Temporarily, This operation is unavailable at this moment
		 */
		UnavailableTemporarily(3),

		/**
		 * No Such Thing, The object/order/message does not exist
		 */
		NoSuchThing(4),

		/**
		 * Permission Denied, You don't have permission to do this operation
		 */
		PermissionDenied(5),

		/**
		 * Object/order/message has gone.
		 */
		Gone(6),

		/**
		 * The frame version is not supported
		 */
		FrameVersionNotSupported(7),

		/**
		 * The requested reply is too big
		 */
		RequestTooBig(8),

		;
		public final int value;
		private Code(int value)
		{
			this.value=value;
		}
	}

	private Code code=Code.$none$;

	public Code getCode()
	{
		return this.code;
	}

	public void setCode(Code value)
	{
		this.code=value;
	}

	/**
	 * Text message of the error.
	 */
	private String result=new String();

	public String getResult()
	{
		return this.result;
	}

	public void setResult(String value)
	{
		this.result=value;
	}

	/**
	 * A list of references that this error relates to.
	 */
	public static class ReferencesType extends TPObject<TP04Visitor>
	{
		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		public ReferencesType()
		{
		}

		/**
		 * type of thing being referenced
		 */
		private int type;

		public int getType()
		{
			return this.type;
		}

		public void setType(int value)
		{
			this.type=value;
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
				 + 4
				 + 4;
		}

		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			out.writeInteger(this.type);
			out.writeInteger(this.id);
		}

		/**
		 * A convenience constructor for easy initialisation of non-read only fields.
		 */
		public ReferencesType(int type, int id)
		{
			setType(type);
			setId(id);
		}

		/**
		 * A copy constructor for (among others) deep-copying groups and lists.
		 */
		public ReferencesType(ReferencesType copy)
		{
			setType(copy.getType());
			setId(copy.getId());
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		ReferencesType(TPDataInput in) throws IOException
		{
			this.type=in.readInteger32();
			this.id=in.readInteger32();
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{ReferencesType");
			buf.append("; type: ");
			buf.append(String.valueOf(this.type));
			buf.append("; id: ");
			buf.append(String.valueOf(this.id));
			buf.append("}");
			return buf.toString();
		}

	}

	private java.util.List<ReferencesType> references=new java.util.ArrayList<ReferencesType>();

	public java.util.List<ReferencesType> getReferences()
	{
		return this.references;
	}

	@SuppressWarnings("unused")
	private void setReferences(java.util.List<ReferencesType> value)
	{
		for (ReferencesType object : value)
			this.references.add(new ReferencesType(object));
	}

	@Override
	public void visit(TP04Visitor visitor) throws TPException
	{
		visitor.frame(this);
	}

	@Override
	public int findByteLength()
	{
		return super.findByteLength()
			 + 4
			 + findByteLength(this.result)
			 + findByteLength(this.references);
	}

	@Override
	public void write(TPDataOutput out, Connection<?> conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.code.value);
		out.writeString(this.result);
		out.writeInteger(this.references.size());
		for (ReferencesType object : this.references)
			object.write(out, conn);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	Fail(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		code: {
			int value=in.readInteger32();
			for (Code e : Code.values())
				if (e.value == value)
				{
					this.code=e;
					break code;
				}
			throw new IOException("Invalid value for enum 'code': "+value);
		}
		this.result=in.readString();
		this.references.clear();
		for (int length=in.readInteger32(); length > 0; length--)
			this.references.add(new ReferencesType(in));
	}

	@Override
	public String toString()
	{
		StringBuilder buf=new StringBuilder();
		buf.append("{Fail");
		buf.append("; code: ");
		buf.append(String.valueOf(this.code));
		buf.append("; result: ");
		buf.append(String.valueOf(this.result));
		buf.append("; references: ");
		buf.append(String.valueOf(this.references));
		buf.append("; super:").append(super.toString());
		buf.append("}");
		return buf.toString();
	}

}
