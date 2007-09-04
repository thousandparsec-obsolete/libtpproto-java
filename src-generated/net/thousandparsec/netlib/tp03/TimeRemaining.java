package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Time Remaining
 */
public class TimeRemaining extends Response
{
	protected TimeRemaining(int id)
	{
		super(id);
	}

	public TimeRemaining()
	{
		super(15);
	}

	/**
	 * The time in seconds before the next end of turn starts
	 */
	private int time;

	public int getTime()
	{
		return this.time;
	}

	public void setTime(int value)
	{
		this.time=value;
	}

	@Override
	public void visit(TP03Visitor visitor)
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
		out.writeInteger(this.time);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	@SuppressWarnings("unused")
	TimeRemaining(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.time=in.readInteger32();
	}

}
