package net.thousandparsec.netlib.objects;

import java.io.IOException;
import java.util.List;

import net.thousandparsec.netlib.Connection;
import net.thousandparsec.netlib.TPDataInput;
import net.thousandparsec.netlib.TPDataOutput;
import net.thousandparsec.netlib.Writable;

public abstract class GameObject implements Writable
{
	public static GameObject createGameObject(int id, TPDataInput in) throws IOException
	{
		switch (id)
		{
			case 0: return new Universe(in);
			case 1: return new Galaxy(in);
			case 2: return new StarSystem(in);
			case 3: return new Planet(in);
			case 4: return new Fleet(in);
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
