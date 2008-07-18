package net.thousandparsec.netlib.tp04;

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

	/**
	 * Explains why this frame was sent.
	 */
	public enum Reason
	{
		$none$(-1),

		/**
		 * This frame was requested.
		 */
		Requested(0x0),

		/**
		 * Turn timer has been started
		 */
		TimerStarted(0x1),

		/**
		 * Advanced warning of the turn timer expiring.
		 */
		TimerWarning(0x2),

		/**
		 * All players have finished and the turn has ended.
		 */
		AllFinished(0x3),

		/**
		 * Threshold of players have finished, the timer for the remaining players has started.
		 */
		ThresholdFinishedTimerStarted(0x4),

		/**
		 * End of turn started.
		 */
		EndOfTurnStarted(0x5),

		;
		public final int value;
		private Reason(int value)
		{
			this.value=value;
		}
	}

	private Reason reason=Reason.$none$;

	public Reason getReason()
	{
		return this.reason;
	}

	public void setReason(Reason value)
	{
		this.reason=value;
	}

	@Override
	public void visit(TP04Visitor visitor) throws TPException
	{
		visitor.frame(this);
	}

	@Override
	public int findByteLength()
	{
		return super.findByteLength()
			 + 4
			 + 4;
	}

	@Override
	public void write(TPDataOutput out, Connection<?> conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.time);
		out.writeInteger(this.reason.value);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	TimeRemaining(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.time=in.readInteger32();
		reason: {
			int value=in.readInteger32();
			for (Reason e : Reason.values())
				if (e.value == value)
				{
					this.reason=e;
					break reason;
				}
			throw new IOException("Invalid value for enum 'reason': "+value);
		}
	}

	@Override
	public String toString()
	{
		StringBuilder buf=new StringBuilder();
		buf.append("{TimeRemaining");
		buf.append("; time: ");
		buf.append(String.valueOf(this.time));
		buf.append("; reason: ");
		buf.append(String.valueOf(this.reason));
		buf.append("; super:").append(super.toString());
		buf.append("}");
		return buf.toString();
	}

}
