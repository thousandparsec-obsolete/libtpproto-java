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

public class Fleet<V extends Visitor> extends GameObject<V>
{
	public static final int OBJECT_TYPE=4;

	protected Fleet()
	{
		super(OBJECT_TYPE);
	}

	Fleet(TPDataInput in) throws IOException
	{
		super(OBJECT_TYPE, in);
		this.owner=in.readInteger32();
		for (int length=in.readInteger32(); length > 0; length--)
			this.ships.add(new ShipsType(in));
		this.damage=in.readInteger32();
	}

	protected Fleet(Fleet<?> copy)
	{
		this();
		this.owner=copy.getOwner();
		for (ShipsType object : copy.getShips())
			this.ships.add(new ShipsType(object));
		this.damage=copy.getDamage();
	}

	private int owner;

	public int getOwner()
	{
		return owner;
	}

	public static class ShipsType implements Writable
	{
		ShipsType(TPDataInput in) throws IOException
		{
			this.type=in.readInteger32();
			this.count=in.readInteger32();
		}

		ShipsType(ShipsType copy)
		{
			this.type=copy.type;
			this.count=copy.count;
		}

		private int type;

		public int getType()
		{
			return type;
		}

		private int count;

		public int getCount()
		{
			return count;
		}

		public int findByteLength()
		{
			return 4
				+ 4;
		}

		public void write(TPDataOutput out, Connection<?> conn) throws IOException
		{
			out.writeInteger(type);
			out.writeInteger(count);
		}
	}

	private List<ShipsType> ships=new ArrayList<ShipsType>();

	public List<ShipsType> getShips()
	{
		return Collections.unmodifiableList(ships);
	}

	private int damage;

	public int getDamage()
	{
		return damage;
	}

	@Override
	public Fleet<V> copy()
	{
		return new Fleet<V>(this);
	}

	@Override
	public int findByteLength()
	{
		return super.findByteLength()
			+ 4
			+ findByteLength(ships)
			+ 4;
	}

	@Override
	public void write(TPDataOutput out, Connection<?> conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.owner);
		out.writeInteger(this.ships.size());
		for (ShipsType object : this.ships)
			object.write(out, conn);
		out.writeInteger(this.damage);
	}

	public void visit(V v) throws TPException
	{
		v.gameObject(this);
	}
}
