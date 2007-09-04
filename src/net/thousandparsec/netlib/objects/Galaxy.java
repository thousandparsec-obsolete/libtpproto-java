package net.thousandparsec.netlib.objects;

import java.io.IOException;

import net.thousandparsec.netlib.Connection;
import net.thousandparsec.netlib.TPDataInput;
import net.thousandparsec.netlib.TPDataOutput;
import net.thousandparsec.netlib.Visitor;

public class Galaxy<V extends Visitor<V>> extends GameObject<V>
{
	protected Galaxy()
	{
		super(1);
	}

	Galaxy(TPDataInput in)
	{
		super(1, in);
	}

	protected Galaxy(Galaxy<?> copy)
	{
		this();
	}

	@Override
	public Galaxy<V> copy()
	{
		return new Galaxy<V>(this);
	}

	@Override
	public int findByteLength()
	{
		return super.findByteLength();
	}

	@Override
	public void write(TPDataOutput out, Connection<?> conn) throws IOException
	{
		super.write(out, conn);
	}

	public void visit(V v)
	{
		v.gameObject(this);
	}
}
