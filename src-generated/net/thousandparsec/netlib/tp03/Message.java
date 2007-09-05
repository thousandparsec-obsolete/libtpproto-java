package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Message Frame
 */
public class Message extends Response
{
	public static final int FRAME_TYPE=19;

	protected Message(int id)
	{
		super(id);
	}

	public Message()
	{
		super(FRAME_TYPE);
	}

	/**
	 * The Board ID of the message is on.
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
	 * The slot that the message is in.
	 */
	private int slot;

	public int getSlot()
	{
		return this.slot;
	}

	public void setSlot(int value)
	{
		this.slot=value;
	}

	public static class MsgtypelistType extends TPObject<TP03Visitor>
	{
		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		public MsgtypelistType()
		{
		}

		/**
		 * As of TP03 is it no longer used.
		 */
		private int msgtype;

		public int getMsgtype()
		{
			return this.msgtype;
		}

		public void setMsgtype(int value)
		{
			this.msgtype=value;
		}

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + 4;
		}

		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			out.writeInteger(this.msgtype);
		}

		/**
		 * A convenience constructor for easy initialisation of non-read only fields.
		 */
		public MsgtypelistType(int msgtype)
		{
			setMsgtype(msgtype);
		}

		/**
		 * A copy constructor for (among others) deep-copying groups and lists.
		 */
		public MsgtypelistType(MsgtypelistType copy)
		{
			setMsgtype(copy.getMsgtype());
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		@SuppressWarnings("unused")
		MsgtypelistType(TPDataInput in) throws IOException
		{
			this.msgtype=in.readInteger32();
		}

	}

	private java.util.List<MsgtypelistType> msgtypelist=new java.util.ArrayList<MsgtypelistType>();

	public java.util.List<MsgtypelistType> getMsgtypelist()
	{
		return this.msgtypelist;
	}

	@SuppressWarnings("unused")
	private void setMsgtypelist(java.util.List<MsgtypelistType> value)
	{
		for (MsgtypelistType object : value)
			this.msgtypelist.add(new MsgtypelistType(object));
	}

	private String subject=new String();

	public String getSubject()
	{
		return this.subject;
	}

	public void setSubject(String value)
	{
		this.subject=value;
	}

	/**
	 * a Formatted String
	 */
	private String body=new String();

	public String getBody()
	{
		return this.body;
	}

	public void setBody(String value)
	{
		this.body=value;
	}

	private int turn;

	public int getTurn()
	{
		return this.turn;
	}

	public void setTurn(int value)
	{
		this.turn=value;
	}

	/**
	 * A list of as described in the Generic Reference System
	 */
	public static class ReferencesType extends TPObject<TP03Visitor>
	{
		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		public ReferencesType()
		{
		}

		/**
		 * type of thing being referenced
		 */
		private int type;

		public int getType()
		{
			return this.type;
		}

		public void setType(int value)
		{
			this.type=value;
		}

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
		public int findByteLength()
		{
			return super.findByteLength()
				 + 4
				 + 4;
		}

		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			out.writeInteger(this.type);
			out.writeInteger(this.id);
		}

		/**
		 * A convenience constructor for easy initialisation of non-read only fields.
		 */
		public ReferencesType(int type, int id)
		{
			setType(type);
			setId(id);
		}

		/**
		 * A copy constructor for (among others) deep-copying groups and lists.
		 */
		public ReferencesType(ReferencesType copy)
		{
			setType(copy.getType());
			setId(copy.getId());
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		@SuppressWarnings("unused")
		ReferencesType(TPDataInput in) throws IOException
		{
			this.type=in.readInteger32();
			this.id=in.readInteger32();
		}

	}

	private java.util.List<ReferencesType> references=new java.util.ArrayList<ReferencesType>();

	public java.util.List<ReferencesType> getReferences()
	{
		return this.references;
	}

	@SuppressWarnings("unused")
	private void setReferences(java.util.List<ReferencesType> value)
	{
		for (ReferencesType object : value)
			this.references.add(new ReferencesType(object));
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
			 + 4
			 + 4
			 + findByteLength(this.msgtypelist)
			 + findByteLength(this.subject)
			 + findByteLength(this.body)
			 + 4
			 + findByteLength(this.references);
	}

	@Override
	public void write(TPDataOutput out, Connection<?> conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.id);
		out.writeInteger(this.slot);
		out.writeInteger(this.msgtypelist.size());
		for (MsgtypelistType object : this.msgtypelist)
			object.write(out, conn);
		out.writeString(this.subject);
		out.writeString(this.body);
		out.writeInteger(this.turn);
		out.writeInteger(this.references.size());
		for (ReferencesType object : this.references)
			object.write(out, conn);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	@SuppressWarnings("unused")
	Message(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.id=in.readInteger32();
		this.slot=in.readInteger32();
		this.msgtypelist.clear();
		for (int length=in.readInteger32(); length > 0; length--)
			this.msgtypelist.add(new MsgtypelistType(in));
		this.subject=in.readString();
		this.body=in.readString();
		this.turn=in.readInteger32();
		this.references.clear();
		for (int length=in.readInteger32(); length > 0; length--)
			this.references.add(new ReferencesType(in));
	}

}
