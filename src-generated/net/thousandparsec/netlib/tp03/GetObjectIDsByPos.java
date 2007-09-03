package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Get Object IDs by Position
 */
public class GetObjectIDsByPos extends Request
{
	protected GetObjectIDsByPos(int id)
	{
		super(id);
	}

	public GetObjectIDsByPos()
	{
		super(29);
	}

	/**
	 * The center of a sphere.
	 */
	public static class PosType extends TPObject<TP03Decoder, TP03Visitor>
	{
		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		public PosType()
		{
		}

		private long x;

		public long getX()
		{
			return this.x;
		}

		public void setX(long value)
		{
			this.x=value;
		}

		private long y;

		public long getY()
		{
			return this.y;
		}

		public void setY(long value)
		{
			this.y=value;
		}

		private long z;

		public long getZ()
		{
			return this.z;
		}

		public void setZ(long value)
		{
			this.z=value;
		}

		@Override
		public int findByteLength()
		{
			return 0
				 + 8
				 + 8
				 + 8;
		}

		public void write(TPDataOutput out, Connection<TP03Decoder, TP03Visitor> conn) throws IOException
		{
			out.writeInteger(this.x);
			out.writeInteger(this.y);
			out.writeInteger(this.z);
		}

		/**
		 * A convenience constructor for easy initialisation of non-read only fields.
		 */
		public PosType(long x, long y, long z)
		{
			setX(x);
			setY(y);
			setZ(z);
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		@SuppressWarnings("unused")
		PosType(TPDataInput in) throws IOException
		{
			this.x=in.readInteger64();
			this.y=in.readInteger64();
			this.z=in.readInteger64();
		}

	}

	private PosType pos=new PosType();

	public PosType getPos()
	{
		return this.pos;
	}

	public void setPos(PosType value)
	{
		this.pos=value;
	}

	/**
	 * The radius of the sphere.
	 */
	private long r;

	public long getR()
	{
		return this.r;
	}

	public void setR(long value)
	{
		this.r=value;
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
			 + findByteLength(this.pos)
			 + 8;
	}

	@Override
	public void write(TPDataOutput out, Connection<TP03Decoder, TP03Visitor> conn) throws IOException
	{
		super.write(out, conn);
		this.pos.write(out, conn);
		out.writeInteger(this.r);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	@SuppressWarnings("unused")
	GetObjectIDsByPos(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.pos=new PosType(in);
		this.r=in.readInteger64();
	}

}
