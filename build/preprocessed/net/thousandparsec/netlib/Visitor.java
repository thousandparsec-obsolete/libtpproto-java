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
 * each method by default calls the {@link #unhandledFrame(Frame)} method, which
 * can be used to handle all frames in one place; overriden callback methods
 * which do not call superimplementation will not call this method, so normally
 * this method receives all {@link Frame}s not handled by the concrete
 * subclass. Similarily for {@link GameObject}s.
 * <p>
 * Note that the visitor is <em>not</em> parameterised with itself and so
 * "collective" handlers receive their objects with unknown visitor type, whuch
 * makes them unvisitable further. This is not a problem, though, because if you
 * want to handle some types specially, you should do it before it reaches this
 * stage; and if you want to always perform some action, whether it had been
 * handled or not, you can call this method explicitly, od call
 * {@code super.frame(frame)} (or {@code super.ganeObject(object)}), which will
 * route it there.
 * 
 * @author ksobolewski
 */
public abstract class Visitor
{
	private final boolean errorOnUnhandled;

	/**
	 * Creates a visitor that will not throw exception on unhandled frames and
	 * game objects.
	 * 
	 * @see #Visitor(boolean)
	 */
	public Visitor()
	{
		this(false);
	}

	/**
	 * Creates a visitor with specified behaviour on unhandled frames and game
	 * objects. If the {@code errorOnUnhandled} parameter is {@literal true},
	 * the default handler for {@link #unhandledFrame(Frame) frames} and
	 * {@link #unhandledGameObject(GameObject) game objects} will throw
	 * {@link TPException} with the message that this frame/object was
	 * unexpected.
	 * 
	 * @param errorOnUnhandled
	 *            whether to throw an exception on unhandled frames and objects .
	 */
	public Visitor(boolean errorOnUnhandled)
	{
		this.errorOnUnhandled=errorOnUnhandled;
	}

	/* frames */
	public void unhandledFrame(Frame<?> frame) throws TPException
	{
		if (errorOnUnhandled)
			throw new TPException(String.format("Unexpected frame: type %d (%s)", frame.getFrameType(), frame.toString()));
	}

	/* game objects */
	public void unhandledGameObject(GameObject<?> object) throws TPException
	{
		if (errorOnUnhandled)
			throw new TPException(String.format("Unexpected game object: type %d (%s)", object.getObjectType(), object.toString()));
	}

	public void gameObject(Universe<?> object) throws TPException
	{
		unhandledGameObject(object);
	}

	public void gameObject(Galaxy<?> object) throws TPException
	{
		unhandledGameObject(object);
	}

	public void gameObject(StarSystem<?> object) throws TPException
	{
		unhandledGameObject(object);
	}

	public void gameObject(Planet<?> object) throws TPException
	{
		unhandledGameObject(object);
	}

	public void gameObject(Fleet<?> object) throws TPException
	{
		unhandledGameObject(object);
	}
}