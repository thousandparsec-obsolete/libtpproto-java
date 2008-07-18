package net.thousandparsec.netlib.tp04;

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

	/**
	 * The timestamp from which the changes should be pulled.
	 */
	private long from;

	public long getFrom()
	{
		return this.from;
	}

	public void setFrom(long value)
	{
		this.from=value;
	}

	@Override
	public void visit(TP04Visitor visitor) throws TPException
	{
		//NOP (not a leaf class)
	}

	@Override
	public int findByteLength()
	{
		return super.findByteLength()
			 + 4
			 + 4
			 + 4
			 + 8;
	}

	@Override
	public void write(TPDataOutput out, Connection<?> conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.key);
		out.writeInteger(this.start);
		out.writeInteger(this.amount);
		out.writeInteger(this.from);
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
		this.from=in.readInteger64();
	}

	@Override
	public String toString()
	{
		StringBuilder buf=new StringBuilder();
		buf.append("{GetIDSequence");
		buf.append("; key: ");
		buf.append(String.valueOf(this.key));
		buf.append("; start: ");
		buf.append(String.valueOf(this.start));
		buf.append("; amount: ");
		buf.append(String.valueOf(this.amount));
		buf.append("; from: ");
		buf.append(String.valueOf(this.from));
		buf.append("; super:").append(super.toString());
		buf.append("}");
		return buf.toString();
	}

}
