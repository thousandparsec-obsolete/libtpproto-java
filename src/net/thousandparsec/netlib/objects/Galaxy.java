package net.thousandparsec.netlib.objects;

import java.io.IOException;

import net.thousandparsec.netlib.Connection;
import net.thousandparsec.netlib.TPDataInput;
import net.thousandparsec.netlib.TPDataOutput;
import net.thousandparsec.netlib.TPException;
import net.thousandparsec.netlib.Visitor;

public class Galaxy<V extends Visitor> extends GameObject<V>
{
	public static final int OBJECT_ID=1;

	protected Galaxy()
	{
		super(OBJECT_ID);
	}

	Galaxy(TPDataInput in)
	{
		super(OBJECT_ID, in);
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

	public void visit(V v) throws TPException
	{
		v.gameObject(this);
	}
}
