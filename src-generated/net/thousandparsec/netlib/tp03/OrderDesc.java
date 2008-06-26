package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Order Description
 */
public class OrderDesc extends Response
{
	public static final int FRAME_TYPE=9;

	protected OrderDesc(int id)
	{
		super(id);
	}

	public OrderDesc()
	{
		super(FRAME_TYPE);
	}

	/**
	 * order type
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

	/**
	 */
	private String description=new String();

	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(String value)
	{
		this.description=value;
	}

	public static class ParametersType extends TPObject
	{
		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		public ParametersType()
		{
		}

		/**
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

		/**
		 * argument type ID
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

		/**
		 */
		private String description=new String();

		public String getDescription()
		{
			return this.description;
		}

		public void setDescription(String value)
		{
			this.description=value;
		}

		public int findByteLength()
		{
			return super.findByteLength()
				 + findByteLength(this.name)
				 + 4
				 + findByteLength(this.description);
		}

		public void write(TPDataOutput out, Connection conn) throws IOException
		{
			out.writeString(this.name);
			out.writeInteger(this.type);
			out.writeString(this.description);
		}

		/**
		 * A convenience constructor for easy initialisation of non-read only fields.
		 */
		public ParametersType(String name, int type, String description)
		{
			setName(name);
			setType(type);
			setDescription(description);
		}

		/**
		 * A copy constructor for (among others) deep-copying groups and lists.
		 */
		public ParametersType(ParametersType copy)
		{
			setName(copy.getName());
			setType(copy.getType());
			setDescription(copy.getDescription());
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		ParametersType(TPDataInput in) throws IOException
		{
			this.name=in.readString();
			this.type=in.readInteger32();
			this.description=in.readString();
		}

		public String toString()
		{
			
			return "{ParametersType"
                            + "; name: "
                            + String.valueOf(this.name)
                            + "; type: "
                            + String.valueOf(this.type)
                            + "; description: "
                            + String.valueOf(this.description)
                            + "}";
                            
		}

	}

	private java.util.Vector parameters = new java.util.Vector();
	public java.util.Vector getParameters()
	{
		return this.parameters;
	}

	private void setParameters(java.util.Vector value)
	{
                for (int i = 0; i < value.size(); i ++){
                    this.parameters.addElement(new ParametersType((ParametersType)value.elementAt(i)));
                }

	}

	/**
	 * The time at which this order description was last modified.
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
            System.out.println("warning: visit method called in OrderDesc.java in OrderDesc.java");
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
			 + findByteLength(this.parameters)
			 + 8;
	}

	public void write(TPDataOutput out, Connection conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.id);
		out.writeString(this.name);
		out.writeString(this.description);
		out.writeInteger(this.parameters.size());
                for (int i = 0; i < parameters.size(); i++){
                    ((ParametersType)parameters.elementAt(i)).write(out, conn);
                }

		out.writeInteger(this.modtime);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	OrderDesc(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.id=in.readInteger32();
		this.name=in.readString();
		this.description=in.readString();
		this.parameters.removeAllElements();
		for (int length=in.readInteger32(); length > 0; length--)
			this.parameters.addElement(new ParametersType(in));
		this.modtime=in.readInteger64();
	}

	public String toString()
	{
		
		return "{OrderDesc"
                    + "; id: "
                    + String.valueOf(this.id)
                    + "; name: "
                    + String.valueOf(this.name)
                    + "; description: "
                    + String.valueOf(this.description)
                    + "; parameters: "
                    + String.valueOf(this.parameters)
                    + "; modtime: "
                    + String.valueOf(this.modtime)
                    + "; super:"
                    + super.toString()
                    + "}";
		
	}

}
