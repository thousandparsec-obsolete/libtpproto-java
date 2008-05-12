package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Get Object IDs by Container
 */
public class GetObjectIDsByContainer extends Request
{
	public static final int FRAME_TYPE=30;

	protected GetObjectIDsByContainer(int id)
	{
		super(id);
	}

	public GetObjectIDsByContainer()
	{
		super(FRAME_TYPE);
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

	public void visit(TP03Visitor visitor) throws TPException
	{
		visitor.frame(this);
	}

	public int findByteLength()
	{
		return super.findByteLength()
			 + 4;
	}

	public void write(TPDataOutput out, Connection conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.id);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	GetObjectIDsByContainer(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.id=in.readInteger32();
	}

	public String toString()
	{
		return "{GetObjectIDsByContainer"
                    + "; id: "
                    + String.valueOf(this.id)
                    + "; super:"
                    + super.toString()
                    + "}";
		
	}

}
