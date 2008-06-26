package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Board
 */
public class Board extends Response
{
	public static final int FRAME_TYPE=17;

	protected Board(int id)
	{
		super(id);
	}

	public Board()
	{
		super(FRAME_TYPE);
	}

	/**
	 * Board ID
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

	/**
	 * The name of the board.
	 */
	private String name=new String();

	public String getName()
	{
		return this.name;
	}

	public void setName(String value)
	{
		this.name=value;
	}

	private String description=new String();

	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(String value)
	{
		this.description=value;
	}

	/**
	 * The number of messages on the Board.
	 */
	private int messages;

	public int getMessages()
	{
		return this.messages;
	}

	public void setMessages(int value)
	{
		this.messages=value;
	}

	/**
	 * The time at which this Board was last modified.
	 */
	private long modtime;

	public long getModtime()
	{
		return this.modtime;
	}

	public void setModtime(long value)
	{
		this.modtime=value;
	}
        
        public void visit(Visitor visitor) throws TPException
        {
            System.out.println("warning: visit method called in Board.java");
            visit((TP03Visitor)visitor);
        }
	public void visit(TP03Visitor visitor) throws TPException
	{
		visitor.frame(this);
	}

	public int findByteLength()
	{
		return super.findByteLength()
			 + 4
			 + findByteLength(this.name)
			 + findByteLength(this.description)
			 + 4
			 + 8;
	}

	public void write(TPDataOutput out, Connection conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.id);
		out.writeString(this.name);
		out.writeString(this.description);
		out.writeInteger(this.messages);
		out.writeInteger(this.modtime);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	Board(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.id=in.readInteger32();
		this.name=in.readString();
		this.description=in.readString();
		this.messages=in.readInteger32();
		this.modtime=in.readInteger64();
	}

	public String toString()
	{
		return "{Board"
                    +"; id: "
                    +String.valueOf(this.id)
                    +"; name: "
                    +String.valueOf(this.name)
                    +"; description: "
                    +String.valueOf(this.description)
                    +"; messages: "
                    +String.valueOf(this.messages)
                    +"; modtime: "
                    +String.valueOf(this.modtime)
                    +"; super: "
                    +super.toString()
                    +"}";

	}

}
