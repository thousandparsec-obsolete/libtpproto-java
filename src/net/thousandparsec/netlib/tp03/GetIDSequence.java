package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Get ID Sequence
 */
public abstract class GetIDSequence extends Request
{
	protected GetIDSequence(int id)
	{
		super(id);
	}

	/**
	 * the sequence key
	 */
	private int key;

	public int getKey()
	{
		return this.key;
	}

	public void setKey(int value)
	{
		this.key=value;
	}

	/**
	 * the starting number in the sequence
	 */
	private int start;

	public int getStart()
	{
		return this.start;
	}

	public void setStart(int value)
	{
		this.start=value;
	}

	/**
	 * the number of IDs to get
	 */
	private int amount;

	public int getAmount()
	{
		return this.amount;
	}

	public void setAmount(int value)
	{
		this.amount=value;
	}

	public void visit(TP03Visitor visitor) throws TPException
	{
		//NOP (not a leaf class)
	}

	public int findByteLength()
	{
		return super.findByteLength()
			 + 4
			 + 4
			 + 4;
	}

	public void write(TPDataOutput out, Connection conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.key);
		out.writeInteger(this.start);
		out.writeInteger(this.amount);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */

	GetIDSequence(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.key=in.readInteger32();
		this.start=in.readInteger32();
		this.amount=in.readInteger32();
	}

	public String toString()
	{
		return "{GetIDSequence"
                    + "; key: "
                    + String.valueOf(this.key)
                    + "; start: "
                    + String.valueOf(this.start)
                    + "; amount: "
                    + String.valueOf(this.amount)
                    + "; super:"
                    + super.toString()
                    + "}";
		
	}

}