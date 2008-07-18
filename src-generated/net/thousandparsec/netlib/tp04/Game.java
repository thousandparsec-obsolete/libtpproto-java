package net.thousandparsec.netlib.tp04;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Game
 */
public class Game extends Response
{
	public static final int FRAME_TYPE=66;

	protected Game(int id)
	{
		super(id);
	}

	public Game()
	{
		super(FRAME_TYPE);
	}

	/**
	 * The long name for the game.
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
	 * The key for the game.
	 */
	private String key=new String();

	public String getKey()
	{
		return this.key;
	}

	public void setKey(String value)
	{
		this.key=value;
	}

	/**
	 * Supported protocol versions.
	 */
	public static class TpType extends TPObject<TP04Visitor>
	{
		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		public TpType()
		{
		}

		private String proto=new String();

		public String getProto()
		{
			return this.proto;
		}

		public void setProto(String value)
		{
			this.proto=value;
		}

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + findByteLength(this.proto);
		}

		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			out.writeString(this.proto);
		}

		/**
		 * A convenience constructor for easy initialisation of non-read only fields.
		 */
		public TpType(String proto)
		{
			setProto(proto);
		}

		/**
		 * A copy constructor for (among others) deep-copying groups and lists.
		 */
		public TpType(TpType copy)
		{
			setProto(copy.getProto());
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		TpType(TPDataInput in) throws IOException
		{
			this.proto=in.readString();
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{TpType");
			buf.append("; proto: ");
			buf.append(String.valueOf(this.proto));
			buf.append("}");
			return buf.toString();
		}

	}

	private java.util.List<TpType> tp=new java.util.ArrayList<TpType>();

	public java.util.List<TpType> getTp()
	{
		return this.tp;
	}

	@SuppressWarnings("unused")
	private void setTp(java.util.List<TpType> value)
	{
		for (TpType object : value)
			this.tp.add(new TpType(object));
	}

	/**
	 * The version of the server.
	 */
	private String server=new String();

	public String getServer()
	{
		return this.server;
	}

	public void setServer(String value)
	{
		this.server=value;
	}

	/**
	 * The name of the server software.
	 */
	private String sertype=new String();

	public String getSertype()
	{
		return this.sertype;
	}

	public void setSertype(String value)
	{
		this.sertype=value;
	}

	/**
	 * The name of the ruleset.
	 */
	private String rule=new String();

	public String getRule()
	{
		return this.rule;
	}

	public void setRule(String value)
	{
		this.rule=value;
	}

	/**
	 * The version of the ruleset.
	 */
	private String rulever=new String();

	public String getRulever()
	{
		return this.rulever;
	}

	public void setRulever(String value)
	{
		this.rulever=value;
	}

	/**
	 * List of available dns/ip/port and type information for the server.
	 */
	public static class ConnectionsType extends TPObject<TP04Visitor>
	{
		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		public ConnectionsType()
		{
		}

		/**
		 * The type of connection (tp, tps,...)
		 */
		private String type=new String();

		public String getType()
		{
			return this.type;
		}

		public void setType(String value)
		{
			this.type=value;
		}

		/**
		 * Resolvable DNS name.
		 */
		private String dns=new String();

		public String getDns()
		{
			return this.dns;
		}

		public void setDns(String value)
		{
			this.dns=value;
		}

		/**
		 * IP Address to connect to.
		 */
		private String ip=new String();

		public String getIp()
		{
			return this.ip;
		}

		public void setIp(String value)
		{
			this.ip=value;
		}

		/**
		 * The port number to connect on.
		 */
		private int port;

		public int getPort()
		{
			return this.port;
		}

		public void setPort(int value)
		{
			this.port=value;
		}

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + findByteLength(this.type)
				 + findByteLength(this.dns)
				 + findByteLength(this.ip)
				 + 4;
		}

		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			out.writeString(this.type);
			out.writeString(this.dns);
			out.writeString(this.ip);
			out.writeInteger(this.port);
		}

		/**
		 * A convenience constructor for easy initialisation of non-read only fields.
		 */
		public ConnectionsType(String type, String dns, String ip, int port)
		{
			setType(type);
			setDns(dns);
			setIp(ip);
			setPort(port);
		}

		/**
		 * A copy constructor for (among others) deep-copying groups and lists.
		 */
		public ConnectionsType(ConnectionsType copy)
		{
			setType(copy.getType());
			setDns(copy.getDns());
			setIp(copy.getIp());
			setPort(copy.getPort());
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		ConnectionsType(TPDataInput in) throws IOException
		{
			this.type=in.readString();
			this.dns=in.readString();
			this.ip=in.readString();
			this.port=in.readInteger32();
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{ConnectionsType");
			buf.append("; type: ");
			buf.append(String.valueOf(this.type));
			buf.append("; dns: ");
			buf.append(String.valueOf(this.dns));
			buf.append("; ip: ");
			buf.append(String.valueOf(this.ip));
			buf.append("; port: ");
			buf.append(String.valueOf(this.port));
			buf.append("}");
			return buf.toString();
		}

	}

	private java.util.List<ConnectionsType> connections=new java.util.ArrayList<ConnectionsType>();

	public java.util.List<ConnectionsType> getConnections()
	{
		return this.connections;
	}

	@SuppressWarnings("unused")
	private void setConnections(java.util.List<ConnectionsType> value)
	{
		for (ConnectionsType object : value)
			this.connections.add(new ConnectionsType(object));
	}

	/**
	 * List of optional parameters.
	 */
	public static class ParametersType extends TPObject<TP04Visitor>
	{
		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		public ParametersType()
		{
		}

		/**
		 * The ID number for this parameter.
		 */
		public enum Paramid
		{
			$none$(-1),

			/**
			 * Number of players in the game.
			 */
			plys(1),

			/**
			 * Number of clients currently connected.
			 */
			cons(2),

			/**
			 * Number of 
			 * &
			 * quot;objects
			 * &
			 * quot; in the game universe.
			 */
			objs(3),

			/**
			 * Admin email address.
			 */
			admin(4),

			/**
			 * Comment about the game.
			 */
			cmt(5),

			/**
			 * Unix timestamp (GMT) when next turn is generated.
			 */
			next(6),

			/**
			 * Human readable name (long name) of the game.
			 */
			ln(7),

			/**
			 * Short (computer) name of the game.
			 */
			sn(8),

			/**
			 * The current turn number.
			 */
			turn(9),

			/**
			 * The time between turns in seconds.
			 */
			prd(10),

			;
			public final int value;
			private Paramid(int value)
			{
				this.value=value;
			}
		}

		private Paramid paramid=Paramid.$none$;

		public Paramid getParamid()
		{
			return this.paramid;
		}

		public void setParamid(Paramid value)
		{
			this.paramid=value;
		}

		/**
		 * The string value of this parameter, or empty if invalid.
		 */
		private String stringvalue=new String();

		public String getStringvalue()
		{
			return this.stringvalue;
		}

		public void setStringvalue(String value)
		{
			this.stringvalue=value;
		}

		/**
		 * The integer value of this parameter, or zero if invalid.
		 */
		private int intvalue;

		public int getIntvalue()
		{
			return this.intvalue;
		}

		public void setIntvalue(int value)
		{
			this.intvalue=value;
		}

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + 4
				 + findByteLength(this.stringvalue)
				 + 4;
		}

		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			out.writeInteger(this.paramid.value);
			out.writeString(this.stringvalue);
			out.writeInteger(this.intvalue);
		}

		/**
		 * A convenience constructor for easy initialisation of non-read only fields.
		 */
		public ParametersType(Paramid paramid, String stringvalue, int intvalue)
		{
			setParamid(paramid);
			setStringvalue(stringvalue);
			setIntvalue(intvalue);
		}

		/**
		 * A copy constructor for (among others) deep-copying groups and lists.
		 */
		public ParametersType(ParametersType copy)
		{
			setParamid(copy.getParamid());
			setStringvalue(copy.getStringvalue());
			setIntvalue(copy.getIntvalue());
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		ParametersType(TPDataInput in) throws IOException
		{
			paramid: {
				int value=in.readInteger32();
				for (Paramid e : Paramid.values())
					if (e.value == value)
					{
						this.paramid=e;
						break paramid;
					}
				throw new IOException("Invalid value for enum 'paramid': "+value);
			}
			this.stringvalue=in.readString();
			this.intvalue=in.readInteger32();
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{ParametersType");
			buf.append("; paramid: ");
			buf.append(String.valueOf(this.paramid));
			buf.append("; stringvalue: ");
			buf.append(String.valueOf(this.stringvalue));
			buf.append("; intvalue: ");
			buf.append(String.valueOf(this.intvalue));
			buf.append("}");
			return buf.toString();
		}

	}

	private java.util.List<ParametersType> parameters=new java.util.ArrayList<ParametersType>();

	public java.util.List<ParametersType> getParameters()
	{
		return this.parameters;
	}

	@SuppressWarnings("unused")
	private void setParameters(java.util.List<ParametersType> value)
	{
		for (ParametersType object : value)
			this.parameters.add(new ParametersType(object));
	}

	/**
	 * Specifies the base URL for media used in the game.
	 */
	private String mediabase=new String();

	public String getMediabase()
	{
		return this.mediabase;
	}

	public void setMediabase(String value)
	{
		this.mediabase=value;
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
			 + findByteLength(this.name)
			 + findByteLength(this.key)
			 + findByteLength(this.tp)
			 + findByteLength(this.server)
			 + findByteLength(this.sertype)
			 + findByteLength(this.rule)
			 + findByteLength(this.rulever)
			 + findByteLength(this.connections)
			 + findByteLength(this.parameters)
			 + findByteLength(this.mediabase);
	}

	@Override
	public void write(TPDataOutput out, Connection<?> conn) throws IOException
	{
		super.write(out, conn);
		out.writeString(this.name);
		out.writeString(this.key);
		out.writeInteger(this.tp.size());
		for (TpType object : this.tp)
			object.write(out, conn);
		out.writeString(this.server);
		out.writeString(this.sertype);
		out.writeString(this.rule);
		out.writeString(this.rulever);
		out.writeInteger(this.connections.size());
		for (ConnectionsType object : this.connections)
			object.write(out, conn);
		out.writeInteger(this.parameters.size());
		for (ParametersType object : this.parameters)
			object.write(out, conn);
		out.writeString(this.mediabase);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	Game(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.name=in.readString();
		this.key=in.readString();
		this.tp.clear();
		for (int length=in.readInteger32(); length > 0; length--)
			this.tp.add(new TpType(in));
		this.server=in.readString();
		this.sertype=in.readString();
		this.rule=in.readString();
		this.rulever=in.readString();
		this.connections.clear();
		for (int length=in.readInteger32(); length > 0; length--)
			this.connections.add(new ConnectionsType(in));
		this.parameters.clear();
		for (int length=in.readInteger32(); length > 0; length--)
			this.parameters.add(new ParametersType(in));
		this.mediabase=in.readString();
	}

	@Override
	public String toString()
	{
		StringBuilder buf=new StringBuilder();
		buf.append("{Game");
		buf.append("; name: ");
		buf.append(String.valueOf(this.name));
		buf.append("; key: ");
		buf.append(String.valueOf(this.key));
		buf.append("; tp: ");
		buf.append(String.valueOf(this.tp));
		buf.append("; server: ");
		buf.append(String.valueOf(this.server));
		buf.append("; sertype: ");
		buf.append(String.valueOf(this.sertype));
		buf.append("; rule: ");
		buf.append(String.valueOf(this.rule));
		buf.append("; rulever: ");
		buf.append(String.valueOf(this.rulever));
		buf.append("; connections: ");
		buf.append(String.valueOf(this.connections));
		buf.append("; parameters: ");
		buf.append(String.valueOf(this.parameters));
		buf.append("; mediabase: ");
		buf.append(String.valueOf(this.mediabase));
		buf.append("; super:").append(super.toString());
		buf.append("}");
		return buf.toString();
	}

}
