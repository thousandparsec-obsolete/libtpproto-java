package net.thousandparsec.netlib.objects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.thousandparsec.netlib.Connection;
import net.thousandparsec.netlib.TPDataInput;
import net.thousandparsec.netlib.TPDataOutput;
import net.thousandparsec.netlib.Visitor;
import net.thousandparsec.netlib.Writable;

public class Fleet<V extends Visitor<V>> extends GameObject<V>
{
	protected Fleet()
	{
		super(0);
	}

	Fleet(TPDataInput in) throws IOException
	{
		super(0, in);
		this.owner=in.readInteger32();
		for (int length=in.readInteger32(); length > 0; length--)
			this.ships.add(new ShipsType(in));
		this.damage=in.readInteger32();
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

	public void visit(V v)
	{
		v.gameObject(this);
	}
}
