package net.thousandparsec.netlib;

import net.thousandparsec.netlib.objects.Fleet;
import net.thousandparsec.netlib.objects.Galaxy;
import net.thousandparsec.netlib.objects.GameObject;
import net.thousandparsec.netlib.objects.Planet;
import net.thousandparsec.netlib.objects.StarSystem;
import net.thousandparsec.netlib.objects.Universe;

/**
 * A target in the visitor/double-dispatch pattern. The implementations of the
 * {@link Visitable} interface are supposed to call back to this class'
 * implementation and in the process reveal their concrete types. The visitor
 * subclass specific for a particular protocol version are autogenerated and
 * each method by default calls the {@link #unhandledFrame(Frame)} method,
 * which can be used to handle all frames in one place; overriden callback
 * methods which do not call superimplementation will not call this method, so
 * this method receives all {@link Frame}s not handled by the concrete
 * subclass.
 * 
 * @author ksobolewski
 */
public abstract class Visitor<V extends Visitor<V>>
{
	/* frames */
	public void unhandledFrame(Frame<V> frame)
	{
		//NOP here
	}

	/* game objects */
	public void unhandledGameObject(GameObject<V> object)
	{
		//NOP here
	}

	public void gameObject(Universe<V> object)
	{
		unhandledGameObject(object);
	}

	public void gameObject(Galaxy<V> object)
	{
		unhandledGameObject(object);
	}

	public void gameObject(StarSystem<V> object)
	{
		unhandledGameObject(object);
	}

	public void gameObject(Planet<V> object)
	{
		unhandledGameObject(object);
	}

	public void gameObject(Fleet<V> object)
	{
		unhandledGameObject(object);
	}
}
