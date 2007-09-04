package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Get Object IDs by Container
 */
public class GetObjectIDsByContainer extends Request
{
	protected GetObjectIDsByContainer(int id)
	{
		super(id);
	}

	public GetObjectIDsByContainer()
	{
		super(30);
	}

	/**
	 * The Object that is the container.
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

	@Override
	public void visit(TP03Visitor visitor) throws TPException
	{
		visitor.frame(this);
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
		out.writeInteger(this.id);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	@SuppressWarnings("unused")
	GetObjectIDsByContainer(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.id=in.readInteger32();
	}

}
