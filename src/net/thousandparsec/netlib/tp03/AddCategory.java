package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Add Category
 */
public class AddCategory extends Category
{
	public static final int FRAME_TYPE=43;

	protected AddCategory(int id)
	{
		super(id);
	}

	public AddCategory()
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

	AddCategory(int id, TPDataInput in) throws IOException
	{
		super(id, in);
	}


	public String toString()
	{
                return "(AddCategory; super:" +super.toString()+")";
	}

}