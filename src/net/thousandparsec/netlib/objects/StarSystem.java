package net.thousandparsec.netlib.objects;

import java.io.IOException;

import net.thousandparsec.netlib.Connection;
import net.thousandparsec.netlib.TPDataInput;
import net.thousandparsec.netlib.TPDataOutput;
import net.thousandparsec.netlib.TPException;
import net.thousandparsec.netlib.Visitor;

public class StarSystem<V extends Visitor<V>> extends GameObject<V>
{
	protected StarSystem()
	{
		super(2);
	}

	StarSystem(TPDataInput in)
	{
		super(2, in);
	}

	protected StarSystem(StarSystem<?> copy)
	{
		this();
	}

	@Override
	public StarSystem<V> copy()
	{
		return new StarSystem<V>(this);
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