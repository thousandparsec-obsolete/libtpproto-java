package net.thousandparsec.netlib.tp04;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Object Description
 */
public class ObjectDesc extends Response
{
	public static final int FRAME_TYPE=68;

	protected ObjectDesc(int id)
	{
		super(id);
	}

	public ObjectDesc()
	{
		super(FRAME_TYPE);
	}

	/**
	 * object type
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
	 * Name of the object type.
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
	 * Description of the object type.
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

	/**
	 * The time at which this object description was last modified.
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

	public static class PropertygroupsType extends TPObject<TP04Visitor>
	{
		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		public PropertygroupsType()
		{
		}

		/**
		 * The ID for this Property Group.
		 */
		private int propertygroupid;

		public int getPropertygroupid()
		{
			return this.propertygroupid;
		}

		public void setPropertygroupid(int value)
		{
			this.propertygroupid=value;
		}

		/**
		 * Name of this Property Group.
		 */
		private String groupname=new String();

		public String getGroupname()
		{
			return this.groupname;
		}

		public void setGroupname(String value)
		{
			this.groupname=value;
		}

		/**
		 * Description of this Property Group.
		 */
		private String groupdescription=new String();

		public String getGroupdescription()
		{
			return this.groupdescription;
		}

		public void setGroupdescription(String value)
		{
			this.groupdescription=value;
		}

		public static class ParametersType extends TPObject<TP04Visitor>
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
			 * Description of this parameter.
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

			private ObjectParamsDesc extradata=new ObjectParamsDesc(-1);
			{
				//hackity hack :) [for tests only!]
				this.type=-1;
			}

			public ObjectParamsDesc getExtradata()
			{
				return this.extradata;
			}

			@SuppressWarnings("unused")
			private void setExtradata(ObjectParamsDesc value)
			{
				throw new RuntimeException();
			}

			@Override
			public int findByteLength()
			{
				return super.findByteLength()
					 + findByteLength(this.name)
					 + 4
					 + findByteLength(this.description)
					 + findByteLength(this.extradata);
			}

			public void write(TPDataOutput out, Connection<?> conn) throws IOException
			{
				out.writeString(this.name);
				out.writeInteger(this.type);
				out.writeString(this.description);
				this.extradata.write(out, conn);
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
				setExtradata(copy.getExtradata());
			}

			/**
			 * A special "internal" constructor that reads contents from a stream.
			 */
			ParametersType(TPDataInput in) throws IOException
			{
				this.name=in.readString();
				this.type=in.readInteger32();
				this.description=in.readString();
				this.extradata=ObjectParamsDesc.create(this.type, in);
			}

			@Override
			public String toString()
			{
				StringBuilder buf=new StringBuilder();
				buf.append("{ParametersType");
				buf.append("; name: ");
				buf.append(String.valueOf(this.name));
				buf.append("; type: ");
				buf.append(String.valueOf(this.type));
				buf.append("; description: ");
				buf.append(String.valueOf(this.description));
				buf.append("; extradata: ");
				buf.append(String.valueOf(this.extradata));
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

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + 4
				 + findByteLength(this.groupname)
				 + findByteLength(this.groupdescription)
				 + findByteLength(this.parameters);
		}

		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			out.writeInteger(this.propertygroupid);
			out.writeString(this.groupname);
			out.writeString(this.groupdescription);
			out.writeInteger(this.parameters.size());
			for (ParametersType object : this.parameters)
				object.write(out, conn);
		}

		/**
		 * A convenience constructor for easy initialisation of non-read only fields.
		 */
		public PropertygroupsType(int propertygroupid, String groupname, String groupdescription, java.util.List<ParametersType> parameters)
		{
			setPropertygroupid(propertygroupid);
			setGroupname(groupname);
			setGroupdescription(groupdescription);
			setParameters(parameters);
		}

		/**
		 * A copy constructor for (among others) deep-copying groups and lists.
		 */
		public PropertygroupsType(PropertygroupsType copy)
		{
			setPropertygroupid(copy.getPropertygroupid());
			setGroupname(copy.getGroupname());
			setGroupdescription(copy.getGroupdescription());
			setParameters(copy.getParameters());
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		PropertygroupsType(TPDataInput in) throws IOException
		{
			this.propertygroupid=in.readInteger32();
			this.groupname=in.readString();
			this.groupdescription=in.readString();
			this.parameters.clear();
			for (int length=in.readInteger32(); length > 0; length--)
				this.parameters.add(new ParametersType(in));
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{PropertygroupsType");
			buf.append("; propertygroupid: ");
			buf.append(String.valueOf(this.propertygroupid));
			buf.append("; groupname: ");
			buf.append(String.valueOf(this.groupname));
			buf.append("; groupdescription: ");
			buf.append(String.valueOf(this.groupdescription));
			buf.append("; parameters: ");
			buf.append(String.valueOf(this.parameters));
			buf.append("}");
			return buf.toString();
		}

	}

	private java.util.List<PropertygroupsType> propertygroups=new java.util.ArrayList<PropertygroupsType>();

	public java.util.List<PropertygroupsType> getPropertygroups()
	{
		return this.propertygroups;
	}

	@SuppressWarnings("unused")
	private void setPropertygroups(java.util.List<PropertygroupsType> value)
	{
		for (PropertygroupsType object : value)
			this.propertygroups.add(new PropertygroupsType(object));
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
			 + findByteLength(this.name)
			 + findByteLength(this.description)
			 + 8
			 + findByteLength(this.propertygroups);
	}

	@Override
	public void write(TPDataOutput out, Connection<?> conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.id);
		out.writeString(this.name);
		out.writeString(this.description);
		out.writeInteger(this.modtime);
		out.writeInteger(this.propertygroups.size());
		for (PropertygroupsType object : this.propertygroups)
			object.write(out, conn);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	ObjectDesc(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.id=in.readInteger32();
		this.name=in.readString();
		this.description=in.readString();
		this.modtime=in.readInteger64();
		this.propertygroups.clear();
		for (int length=in.readInteger32(); length > 0; length--)
			this.propertygroups.add(new PropertygroupsType(in));
	}

	@Override
	public String toString()
	{
		StringBuilder buf=new StringBuilder();
		buf.append("{ObjectDesc");
		buf.append("; id: ");
		buf.append(String.valueOf(this.id));
		buf.append("; name: ");
		buf.append(String.valueOf(this.name));
		buf.append("; description: ");
		buf.append(String.valueOf(this.description));
		buf.append("; modtime: ");
		buf.append(String.valueOf(this.modtime));
		buf.append("; propertygroups: ");
		buf.append(String.valueOf(this.propertygroups));
		buf.append("; super:").append(super.toString());
		buf.append("}");
		return buf.toString();
	}

}
