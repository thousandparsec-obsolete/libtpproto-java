package net.thousandparsec.util;

import java.util.Iterator;

/**
 * A base class for custom {@link Iterator}s which faciliates computation model
 * similar to {@code yield} constructs from other languages. The only method
 * required to be implemented is {@link #fetchNext()}, which is supposed to
 * look for the next element, in whatever way applicable. If this method
 * determines that there's nothing to return, it should call {@link #finished()}
 * (the return value is ignored after that).
 * <p>
 * This iterator is by definition read-only and its {@link #remove()} method
 * always throws {@link UnsupportedOperationException}. That's because this
 * iterator's "logical" and "psysical" positions do not match and it would be
 * rather difficult to track which element should be removed by
 * {@link #remove()}.
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
