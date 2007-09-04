package net.thousandparsec.netlib.generator;

import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * This class implements a "logical" (live) concatenation of two {@link List}s.
 * The resulting {@link List} looks like the first list with the second list
 * attached (concatenated) at the end. All operations are supported, including
 * modifications (which "write through" to the underlying lists). Addition at
 * the end results in addition to the second list. Additions at specified index
 * result in additions to the list that "owns" that index ({@code underlyingList =
 * index <= list1.size() ? list1 : list2} and
 * {@code underlyingListIndex = index <= list1.size() ? index : index -
 * list1.size()}; there is uncertainty on the edge of the two lists - whether to
 * add at the end of first or at the beginnig of second? This implementation
 * chooses end of first, as appending to lists is usually faster).
 * <p>
 * Of course modifications of (parts) of this list are supported only if the
 * underlying lists support them.
 * <p>
 * If any of the underlying lists is modified during any of the operations, the
 * result is undefined.
 * <p>
 * (This class is taken verbatim from jezuch-utils project by the same author)
 * 
 * @author ksobolewski
 */
public class ConcatenationList<E> extends AbstractList<E>
{
	private final List<E> list1;
	private final List<E> list2;

	public ConcatenationList(List<E> list1, List<E> list2)
	{
		this.list1=list1;
		this.list2=list2;
	}

	@Override
	public boolean add(E o)
	{
		return list2.add(o);
	}

	@Override
	public void add(int index, E element)
	{
		int size1=list1.size();
		//append to first instead of prepend to second in case of ambiguity
		if (index <= size1)
			list1.add(index, element);
		else
			list2.add(index - size1, element);
	}

	@Override
	public boolean addAll(Collection<? extends E> c)
	{
		return list2.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c)
	{
		int size1=list1.size();
		//append to first instead of prepend to second in case of ambiguity
		return index <= size1
			? list1.addAll(index, c)
			: list2.addAll(index - size1, c);
	}

	@Override
	public void clear()
	{
		list1.clear();
		list2.clear();
	}

	@Override
	public boolean contains(Object o)
	{
		return list1.contains(o) || list2.contains(o);
	}

	@Override
	public E get(int index)
	{
		int size1=list1.size();
		return index < size1
			? list1.get(index)
			: list2.get(index - size1);
	}

	@Override
	public int indexOf(Object o)
	{
		int ret=list1.indexOf(o);
		if (ret != -1)
			return ret;
		ret=list2.indexOf(o);
		return ret == -1
			? -1
			: ret + list1.size();
	}

	@Override
	public boolean isEmpty()
	{
		return list1.isEmpty() && list2.isEmpty();
	}

	@Override
	public Iterator<E> iterator()
	{
		return listIterator();
	}

	@Override
	public int lastIndexOf(Object o)
	{
		int ret=list2.lastIndexOf(o);
		if (ret != -1)
			return ret + list1.size();
		return list1.indexOf(o);
	}

	@Override
	public ListIterator<E> listIterator(final int index)
	{
		final List<E> list1=this.list1;
		final List<E> list2=this.list2;
		return new ListIterator<E>()
			{
				private final ListIterator<E> i1;
				private final ListIterator<E> i2;
				private ListIterator<E> current;

				//pseudo-constructor
				{
					int size1=list1.size();
					i1=index < size1
						? list1.listIterator(index)
						: list1.listIterator(size1);
					i2=index < size1
						? list2.listIterator(0)
						: list2.listIterator(index - size1);
					current=index < size1
						? i1
						: i2;
				}

				public void add(E o)
				{
					current.add(o);
				}

				public boolean hasNext()
				{
					return current == i1
						? i1.hasNext() || i2.hasNext()
						: i2.hasNext();
				}

				public boolean hasPrevious()
				{
					return current == i1
						? i1.hasPrevious()
						: i2.hasPrevious() || i1.hasPrevious();
				}

				public E next()
				{
					if (current == i1)
					{
						if (!i1.hasNext())
							current=i2;
						return current.next();
					}
					else
						return current.next();
				}

				public int nextIndex()
				{
					return current == i1
						? i1.nextIndex()
						: i2.nextIndex() + list1.size();
				}

				public E previous()
				{
					if (current == i1)
						return i1.previous();
					else
					{
						if (!i2.hasPrevious())
							current=i1;
						return current.previous();
					}
				}

				public int previousIndex()
				{
					return current == i1
						? i1.previousIndex()
						: i2.previousIndex() + list1.size();
				}

				public void remove()
				{
					current.remove();
				}

				public void set(E o)
				{
					current.set(o);
				}
			};
	}

	@Override
	public boolean remove(Object o)
	{
		return list1.remove(o) || list2.remove(o);
	}

	@Override
	public E remove(int index)
	{
		int size1=list1.size();
		return index < size1
			? list1.remove(index)
			: list2.remove(index - size1);
	}

	@Override
	public boolean removeAll(Collection<?> c)
	{
		//note | instead of ||
		return list1.removeAll(c) | list2.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c)
	{
		//note | instead of ||
		return list1.retainAll(c) | list2.retainAll(c);
	}

	@Override
	public E set(int index, E element)
	{
		int size1=list1.size();
		return index < size1
			? list1.set(index, element)
			: list2.set(index - size1, element);
	}

	@Override
	public int size()
	{
		return list1.size() + list2.size();
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex)
	{
		int size1=list1.size();
		return toIndex < size1
			//first half:
			? list1.subList(fromIndex, toIndex)
			: fromIndex >= size1
				//second half:
				? list2.subList(fromIndex - size1, toIndex - size1)
				//spanning both halves:
				: new ConcatenationList<E>(
					list1.subList(fromIndex, size1),
					list2.subList(0, toIndex - size1));
	}
}
