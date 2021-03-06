package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Time Remaining
 */
public class TimeRemaining extends Response
{
	public static final int FRAME_TYPE=15;

	protected TimeRemaining(int id)
	{
		super(id);
	}

	public TimeRemaining()
	{
		super(FRAME_TYPE);
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
		out.writeInteger(this.time);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	TimeRemaining(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.time=in.readInteger32();
	}

	@Override
	public String toString()
	{
		StringBuilder buf=new StringBuilder();
		buf.append("{TimeRemaining");
		buf.append("; time: ");
		buf.append(String.valueOf(this.time));
		buf.append("; super:").append(super.toString());
		buf.append("}");
		return buf.toString();
	}

}
