package net.thousandparsec.netlib.tp04;

import net.thousandparsec.netlib.*;

import java.io.IOException;

/**
 * Object
 */
public class Object extends Response
{
	public static final int FRAME_TYPE=7;

	protected Object(int id)
	{
		super(id);
	}

	public Object()
	{
		super(FRAME_TYPE);
	}

	/**
	 * The unquie identifier of the object.
	 */
	private int id;

	public int getId()
	{
		return this.id;
	}

	@SuppressWarnings("unused")
	private void setId(int value)
	{
		this.id=value;
	}

	/**
	 * The type of the object.
	 */
	private int otype;

	public int getOtype()
	{
		return this.otype;
	}

	@SuppressWarnings("unused")
	private void setOtype(int value)
	{
		this.otype=value;
	}

	/**
	 * The name of the object.
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
	 * A description of the object.
	 */
	private String desc=new String();

	public String getDesc()
	{
		return this.desc;
	}

	public void setDesc(String value)
	{
		this.desc=value;
	}

	/**
	 * The ID of the object that contains this object.
	 */
	private int parent;

	public int getParent()
	{
		return this.parent;
	}

	@SuppressWarnings("unused")
	private void setParent(int value)
	{
		this.parent=value;
	}

	/**
	 * IDs of the objects contained by this object.
	 */
	public static class ContainsType extends TPObject<TP04Visitor>
	{
		/**
		 * A default constructor which initialises properties to their defaults.
		 */
		public ContainsType()
		{
		}

		/**
		 * the ID
		 */
		private int id;

		public int getId()
		{
			return this.id;
		}

		@SuppressWarnings("unused")
		private void setId(int value)
		{
			this.id=value;
		}

		@Override
		public int findByteLength()
		{
			return super.findByteLength()
				 + 4;
		}

		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			out.writeInteger(this.id);
		}

		/**
		 * A copy constructor for (among others) deep-copying groups and lists.
		 */
		public ContainsType(ContainsType copy)
		{
			setId(copy.getId());
		}

		/**
		 * A special "internal" constructor that reads contents from a stream.
		 */
		ContainsType(TPDataInput in) throws IOException
		{
			this.id=in.readInteger32();
		}

		@Override
		public String toString()
		{
			StringBuilder buf=new StringBuilder();
			buf.append("{ContainsType");
			buf.append("; id: ");
			buf.append(String.valueOf(this.id));
			buf.append("}");
			return buf.toString();
		}

	}

	private java.util.List<ContainsType> contains=new java.util.ArrayList<ContainsType>();

	public java.util.List<ContainsType> getContains()
	{
		return this.contains;
	}

	@SuppressWarnings("unused")
	private void setContains(java.util.List<ContainsType> value)
	{
		for (ContainsType object : value)
			this.contains.add(new ContainsType(object));
	}

	/**
	 * The time at which this object was last modified.
	 */
	private long modtime;

	public long getModtime()
	{
		return this.modtime;
	}

	@SuppressWarnings("unused")
	private void setModtime(long value)
	{
		this.modtime=value;
	}

	private byte[] padding=new byte[16];

	public byte[] getPadding()
	{
		return this.padding.clone();
	}

	public void setPadding(byte[] value)
	{
		System.arraycopy(value, 0, this.padding, 0, this.padding.length);
	}

	private byte[] parameters=new byte[0];

	public java.util.List<ObjectParams> getParameters(ObjectDesc template) throws TPException
	{
		try
		{
			if (template.getId() != getOtype())
				throw new TPException(String.format("ParameterSet id does not match frame's parameter set id: %d != %d", template.getId(), getOtype()));
			TPDataInput in=new TPInputStream(new java.io.ByteArrayInputStream(this.parameters));
			java.util.List<ObjectParams> ret=new java.util.ArrayList<ObjectParams>();
			for (ObjectDesc.PropertygroupsType template0 : template.getPropertygroups())
			{
				for (ObjectDesc.PropertygroupsType.ParametersType template1 : template0.getParameters())
				{
					ret.add(ObjectParams.create(template1.getType(), in));
				}
			}
			return ret;
		}
		catch (IOException ex)
		{
			//rather unlikely, unless you pass a wrong template and hit EOFException
			throw new TPException(ex);
		}
	}

	/**
	 * The order of parameters in the List has to be exactly the same as if returned by accompying getter, that is depth-first search of the template's structure.
	 * This method checks for underflows and overflows of the list and if the parameter's type matches the one expected by template.
	 */
	public void setParameters(java.util.List<ObjectParams> value, ObjectDesc template) throws TPException
	{
		try
		{
			if (template.getId() != getOtype())
				throw new TPException(String.format("ParameterSet id does not match frame's parameter set id: %d != %d", template.getId(), getOtype()));
			java.io.ByteArrayOutputStream bout=new java.io.ByteArrayOutputStream();
			TPOutputStream out=new TPOutputStream(bout);
			java.util.Iterator<ObjectParams> pit=value.iterator();
			for (ObjectDesc.PropertygroupsType template0 : template.getPropertygroups())
			{
				for (ObjectDesc.PropertygroupsType.ParametersType template1 : template0.getParameters())
				{
					if (!pit.hasNext())
						throw new TPException("Insufficient values for ParameterSet parameters");
					ObjectParams param=pit.next();
					if (template1.getType() != param.getParameterType())
						throw new TPException(String.format("Invalid parameter type; expected %d, got %d", template1.getType(), param.getParameterType()));
					param.write(out, null);
				}
			}
			if (pit.hasNext())
				throw new TPException("Too many values for ParameterSet parameters");
			out.close();
			this.parameters=bout.toByteArray();
		}
		catch (IOException fatal)
		{
			//this should not happen with ByteArrayOutputStream
			throw new RuntimeException(fatal);
		}
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
			 + 4
			 + findByteLength(this.name)
			 + findByteLength(this.desc)
			 + 4
			 + findByteLength(this.contains)
			 + 8
			 + 16
			 + this.parameters.length;
	}

	@Override
	public void write(TPDataOutput out, Connection<?> conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.id);
		out.writeInteger(this.otype);
		out.writeString(this.name);
		out.writeString(this.desc);
		out.writeInteger(this.parent);
		out.writeInteger(this.contains.size());
		for (ContainsType object : this.contains)
			object.write(out, conn);
		out.writeInteger(this.modtime);
		out.writeCharacter(this.padding);
		out.writeCharacter(this.parameters);
	}

	/**
	 * A special "internal" constructor that reads contents from a stream.
	 */
	Object(int id, TPDataInput in) throws IOException
	{
		super(id, in);
		this.id=in.readInteger32();
		this.otype=in.readInteger32();
		this.name=in.readString();
		this.desc=in.readString();
		this.parent=in.readInteger32();
		this.contains.clear();
		for (int length=in.readInteger32(); length > 0; length--)
			this.contains.add(new ContainsType(in));
		this.modtime=in.readInteger64();
		in.readCharacter(this.padding);
		//indirect: drain the rest of frame and decode later
		this.parameters=in.drainFrame();
	}

	@Override
	public String toString()
	{
		StringBuilder buf=new StringBuilder();
		buf.append("{Object");
		buf.append("; id: ");
		buf.append(String.valueOf(this.id));
		buf.append("; otype: ");
		buf.append(String.valueOf(this.otype));
		buf.append("; name: ");
		buf.append(String.valueOf(this.name));
		buf.append("; desc: ");
		buf.append(String.valueOf(this.desc));
		buf.append("; parent: ");
		buf.append(String.valueOf(this.parent));
		buf.append("; contains: ");
		buf.append(String.valueOf(this.contains));
		buf.append("; modtime: ");
		buf.append(String.valueOf(this.modtime));
		buf.append("; padding: ");
		buf.append(java.util.Arrays.toString(this.padding));
		buf.append("; parameters: ");
		buf.append("<indirect>");
		buf.append("; super:").append(super.toString());
		buf.append("}");
		return buf.toString();
	}

}
