package net.thousandparsec.netlib.objects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.thousandparsec.netlib.Connection;
import net.thousandparsec.netlib.TPDataInput;
import net.thousandparsec.netlib.TPDataOutput;
import net.thousandparsec.netlib.TPException;
import net.thousandparsec.netlib.Visitor;
import net.thousandparsec.netlib.Writable;

public class Planet<V extends Visitor> extends GameObject<V>
{
	public static final int OBJECT_ID=3;

	protected Planet()
	{
		super(OBJECT_ID);
	}

	Planet(TPDataInput in) throws IOException
	{
		super(OBJECT_ID, in);
		this.owner=in.readInteger32();
		for (int length=in.readInteger32(); length > 0; length--)
			this.resources.add(new ResourcesType(in));
	}

	protected Planet(Planet<?> copy)
	{
		this();
		this.owner=copy.getOwner();
		for (ResourcesType object : copy.getResources())
			this.resources.add(new ResourcesType(object));
	}

	private int owner;

	public int getOwner()
	{
		return owner;
	}

	public static class ResourcesType implements Writable
	{
		ResourcesType(TPDataInput in) throws IOException
		{
			this.resourceId=in.readInteger32();
			this.units=in.readInteger32();
			this.unitsMinable=in.readInteger32();
			this.unitsInaccessible=in.readInteger32();
		}

		ResourcesType(ResourcesType copy)
		{
			this.resourceId=copy.getResourceId();
			this.units=copy.getUnits();
			this.unitsMinable=copy.getUnitsMinable();
			this.unitsInaccessible=copy.getUnitsInaccessible();
		}

		private int resourceId;

		public int getResourceId()
		{
			return resourceId;
		}

		private int units;

		public int getUnits()
		{
			return units;
		}

		private int unitsMinable;

		public int getUnitsMinable()
		{
			return unitsMinable;
		}

		private int unitsInaccessible;

		public int getUnitsInaccessible()
		{
			return unitsInaccessible;
		}

		public int findByteLength()
		{
			return 4
				+ 4
				+ 4
				+ 4;
		}

		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			out.writeInteger(resourceId);
			out.writeInteger(units);
			out.writeInteger(unitsMinable);
			out.writeInteger(unitsInaccessible);
		}
	}

	private List<ResourcesType> resources=new ArrayList<ResourcesType>();

	public List<ResourcesType> getResources()
	{
		return Collections.unmodifiableList(resources);
	}

	@Override
	public Planet<V> copy()
	{
		return new Planet<V>(this);
	}

	@Override
	public int findByteLength()
	{
		return super.findByteLength()
			+ 4
			+ findByteLength(resources);
	}

	@Override
	public void write(TPDataOutput out, Connection<?> conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.owner);
		out.writeInteger(this.resources.size());
		for (ResourcesType object : this.resources)
			object.write(out, conn);
	}

	public void visit(V v) throws TPException
	{
		v.gameObject(this);
	}
}
