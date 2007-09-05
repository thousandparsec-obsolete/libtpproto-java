package net.thousandparsec.netlib.objects;

import java.io.IOException;

import net.thousandparsec.netlib.Connection;
import net.thousandparsec.netlib.TPDataInput;
import net.thousandparsec.netlib.TPDataOutput;
import net.thousandparsec.netlib.TPException;
import net.thousandparsec.netlib.Visitor;

public class Universe<V extends Visitor> extends GameObject<V>
{
	protected Universe()
	{
		super(0);
	}

	Universe(TPDataInput in) throws IOException
	{
		super(0, in);
		this.age=in.readInteger32();
	}

	protected Universe(Universe<?> copy)
	{
		this();
		this.age=copy.getAge();
	}

	private int age;

	public int getAge()
	{
		return age;
	}

	@Override
	public Universe<V> copy()
	{
		return new Universe<V>(this);
	}

	@Override
	public int findByteLength()
	{
		return super.findByteLength()
			+ 4;
	}

	@Override
	public void write(TPDataOutput out, Connection<?> conn) throws IOException
	{
		super.write(out, conn);
		out.writeInteger(this.age);
	}

	public void visit(V v) throws TPException
	{
		v.gameObject(this);
	}
}
