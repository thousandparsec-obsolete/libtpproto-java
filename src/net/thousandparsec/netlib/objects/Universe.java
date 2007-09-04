package net.thousandparsec.netlib.objects;

import java.io.IOException;

import net.thousandparsec.netlib.Connection;
import net.thousandparsec.netlib.TPDataInput;
import net.thousandparsec.netlib.TPDataOutput;
import net.thousandparsec.netlib.Visitor;

public class Universe<V extends Visitor<V>> extends GameObject<V>
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

	private int age;

	public int getAge()
	{
		return age;
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

	public void visit(V v)
	{
		v.gameObject(this);
	}
}
