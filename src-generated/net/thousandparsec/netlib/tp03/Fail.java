package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

public class Fail extends Response
{
	protected Fail(int id)
	{
		super(id);
	}

	public Fail()
	{
		super(1);
	}

	/**
	 * Text message of the error.
	 */
	public enum Code
	{
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

		;
		public final int value;
		private Code(int value)
		{
			this.value=value;
		}
	}

	private Code code;

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
			 + findByteLength(this.result);
	}

	@Override
	public void write(TPDataOutput out, Connection<TP03Decoder, TP03Visitor> conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.code.value);
		out.writeString(this.result);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	@SuppressWarnings("unused")
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
	}

}
