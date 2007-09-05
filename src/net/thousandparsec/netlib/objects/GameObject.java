package net.thousandparsec.netlib.objects;

import java.io.IOException;
import java.util.List;

import net.thousandparsec.netlib.Connection;
import net.thousandparsec.netlib.TPDataInput;
import net.thousandparsec.netlib.TPDataOutput;
import net.thousandparsec.netlib.Visitable;
import net.thousandparsec.netlib.Visitor;
import net.thousandparsec.netlib.Writable;

public abstract class GameObject<V extends Visitor> implements Writable, Visitable<V>
{
	public static <V extends Visitor> GameObject<V> createGameObject(int id, TPDataInput in) throws IOException
	{
		switch (id)
		{
			case 0: return new Universe<V>(in);
			case 1: return new Galaxy<V>(in);
			case 2: return new StarSystem<V>(in);
			case 3: return new Planet<V>(in);
			case 4: return new Fleet<V>(in);
			default: throw new IllegalArgumentException("Invalid GameObject id: "+id);
		}
	}

	protected static int findByteLength(List<? extends Writable> objects)
	{
		int total=0;
		for (Writable object : objects)
			total += object.findByteLength();
		return total + 4;
	}

	private final int id;

	protected GameObject(int id)
	{
		this.id=id;
	}

	GameObject(int id, TPDataInput in)
	{
		this(id);
		//nothing to read
	}

	public abstract GameObject<V> copy();

	public int getObjectType()
	{
		return id;
	}

	public int findByteLength()
	{
		return 0;
	}

	public void write(TPDataOutput out, Connection<?> conn) throws IOException
	{
		//NOP
	}
}
