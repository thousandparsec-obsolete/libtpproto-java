package net.thousandparsec.util;

import java.util.Iterator;

/**
 * FIXME: javadoc
 * <p>
 * (This class is taken verbatim from jezuch-utils project by the same author)
 * 
 * @author ksobolewski
 */
public abstract class AbstractIterator<E> implements Iterator<E>
{
	private enum State {VIRGIN, IN_PROGRESS, FINISHED}

	private E next;
	private State state=State.VIRGIN;

	public AbstractIterator()
	{
	}

	public boolean hasNext()
	{
		switch (state)
		{
			case VIRGIN:
				//assume that by now all fields are initialised
				state=State.IN_PROGRESS;
				next=fetchNext();
				return state != State.FINISHED;
			case IN_PROGRESS:
				return true;
			case FINISHED:
			default:
				return false;
		}
	}

	public E next()
	{
		//call hasNext() to fetch the first element if hasNext() wasn't called before
		if (!hasNext())
			throw new IllegalStateException();
		E ret=next;
		next=fetchNext();
		return ret;
	}

	public void remove()
	{
		throw new UnsupportedOperationException();
	}

	protected final void finished()
	{
		state = State.FINISHED;
	}

	/**
	 * @return next element in the sequence; if the end of sequence is
	 * encountered, a call to {@link #finished()} is required - value returned
	 * from this method is ignored then.
	 */
	protected abstract E fetchNext();
}
