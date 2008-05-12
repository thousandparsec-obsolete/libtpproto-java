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

	public static class MsgtypelistType extends TPObject
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

		public int findByteLength()
		{
			return super.findByteLength()
				 + 4;
		}

		public void write(TPDataOutput out, Connection conn) throws IOException
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

		MsgtypelistType(TPDataInput in) throws IOException
		{
			this.msgtype=in.readInteger32();
		}

		public String toString()
		{
			
			return "{MsgtypelistType"
                            + "; msgtype: "
                            + String.valueOf(this.msgtype)
                            + "}";
			
		}

	}

	private java.util.Vector msgtypelist = new java.util.Vector();
	public java.util.Vector getMsgtypelist()
	{
		return this.msgtypelist;
	}

	private void setMsgtypelist(java.util.Vector value)
	{
                for (int i = 0; i < value.size(); i++){
                    this.msgtypelist.addElement(new MsgtypelistType((MsgtypelistType)value.elementAt(i)));
                }

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
	public static class ReferencesType extends TPObject
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

		public int findByteLength()
		{
			return super.findByteLength()
				 + 4
				 + 4;
		}

		public void write(TPDataOutput out, Connection conn) throws IOException
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

		ReferencesType(TPDataInput in) throws IOException
		{
			this.type=in.readInteger32();
			this.id=in.readInteger32();
		}

		public String toString()
		{
			
			return "{ReferencesType"
                            + "; type: "
                            + String.valueOf(this.type)
                            + "; id: "
                            + String.valueOf(this.id)
                            + "}";
			
		}

	}

	
        private java.util.Vector references = new java.util.Vector();
	public java.util.Vector getReferences()
	{
		return this.references;
	}

	private void setReferences(java.util.Vector value)
	{
                for (int i = 0; i < value.size(); i++){
                    this.references.addElement(new ReferencesType((ReferencesType)value.elementAt(i)));
                }

	}

	public void visit(TP03Visitor visitor) throws TPException
	{
		visitor.frame(this);
	}

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

	public void write(TPDataOutput out, Connection conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.id);
		out.writeInteger(this.slot);
		out.writeInteger(this.msgtypelist.size());
                for (int i =0; i < msgtypelist.size(); i++){
                    ((MsgtypelistType)msgtypelist.elementAt(i)).write(out, conn);
                }

		out.writeString(this.subject);
		out.writeString(this.body);
		out.writeInteger(this.turn);
		out.writeInteger(this.references.size());
                for (int i = 0; i < references.size(); i++){
                    ((ReferencesType)references.elementAt(i)).write(out, conn);
                }

	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	Message(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.id=in.readInteger32();
		this.slot=in.readInteger32();
		this.msgtypelist.removeAllElements();
		for (int length=in.readInteger32(); length > 0; length--)
			this.msgtypelist.addElement(new MsgtypelistType(in));
		this.subject=in.readString();
		this.body=in.readString();
		this.turn=in.readInteger32();
		this.references.removeAllElements();
		for (int length=in.readInteger32(); length > 0; length--)
			this.references.addElement(new ReferencesType(in));
	}

	public String toString()
	{
		
		return "{Message"
                    + "; id: "
                    + String.valueOf(this.id)
                    + "; slot: "
                    + String.valueOf(this.slot)
                    + "; msgtypelist: "
                    + String.valueOf(this.msgtypelist)
                    + "; subject: "
                    + String.valueOf(this.subject)
                    + "; body: "
                    + String.valueOf(this.body)
                    + "; turn: "
                    + String.valueOf(this.turn)
                    + "; references: "
                    + String.valueOf(this.references)
                    + "; super:"
                    + super.toString()
                    + "}";
		
	}

}
