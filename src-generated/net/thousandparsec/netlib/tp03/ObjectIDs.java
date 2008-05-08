package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * List of Object IDs
 */
public class ObjectIDs extends IDSequence
{
	public static final int FRAME_TYPE=31;

	protected ObjectIDs(int id)
	{
		super(id);
	}

	public ObjectIDs()
	{
		super(FRAME_TYPE);
	}

	public void visit(TP03Visitor visitor) throws TPException
	{
		visitor.frame(this);
	}

	public int findByteLength()
	{
		return super.findByteLength();
	}

	
	public void write(TPDataOutput out, Connection conn) throws IOException
	{
		super.write(out, conn);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */

	ObjectIDs(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}

	public String toString()
	{
		StringBuilder buf=new StringBuilder();
		buf.append("{ObjectIDs");
		buf.append("; super:").append(super.toString());
		buf.append("}");
		return buf.toString();
	}

}
