package net.thousandparsec.netlib.objects;

import java.io.IOException;

import net.thousandparsec.netlib.Connection;
import net.thousandparsec.netlib.TPDataInput;
import net.thousandparsec.netlib.TPDataOutput;
import net.thousandparsec.netlib.TPException;
import net.thousandparsec.netlib.Visitor;

public class NullObject<V extends Visitor> extends GameObject<V>
{
	static final int OBJECT_TYPE=-1;

	public NullObject()
	{
		super(OBJECT_TYPE);
	}

	NullObject(TPDataInput in)
	{
		super(OBJECT_TYPE, in);
	}

	protected NullObject(NullObject<?> copy)
	{
		this();
	}

	@Override
	public NullObject<V> copy()
	{
		return new NullObject<V>(this);
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
		v.unhandledGameObject(this);
	}
}
