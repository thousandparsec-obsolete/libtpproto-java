/*
 *   jezuch.utils.* - a library of nifty utility classes by jezuch
 *   Copyright (C) 2006 Krzysztof Sobolewski <jezuch@interia.pl>
 *
 *   This library is free software; you can redistribute it and/or
 *   modify it under the terms of the GNU Lesser General Public
 *   License as published by the Free Software Foundation; either
 *   version 2.1 of the License, or (at your option) any later version.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *   Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the Free Software
 *   Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package net.thousandparsec.util;

/**
 * A very simple generic aggregator class that holds two references: the "left"
 * component, and the "right" component. For extreme simplicity these components
 * are accessed via {@code public final} fields, and not accessor methods. If
 * you can accept generic declarations quickly getting out of control you can
 * generate "tuples" of more than two components by chaining pairs (lust like in
 * Lisp, Prolog etc.), for example
 * {@code Pair<String, Pair<Integer, Pair<Double, String>>>} would be a "tuple"
 * of four elements (the third component would be accessed by
 * {@code tuple.right.right.left}).
 * <p>
 * (This class is taken alost verbatim from jezuch-utils project by the same
 * author)
 * 
 * @author ksobolewski
 */
public class Pair<L, R>
{
	/**
	 * Convenience static method that exploits type inference to avoid excessive
	 * amount of necessary declarations.
	 * 
	 * @param <L>
	 *            the type of the left component
	 * @param <R>
	 *            the type of the right component
	 * @param left
	 *            the left component
	 * @param right
	 *            the right component
	 * @return a {@link Pair} containing the given left and right components
	 */
	public static <L, R> Pair<L, R> make(L left, R right)
	{
		return new Pair<L, R>(left, right);
	}

	/**
	 * The left component of this {@link Pair}.
	 */
	public final L left;

	/**
	 * The right component of this {@link Pair}.
	 */
	public final R right;

	/**
	 * Creates a {@link Pair} containing given left and right components.
	 * @param left the left component
	 * @param right the right component
	 */
	public Pair(L left, R right)
	{
		this.left=left;
		this.right=right;
	}

	/**
	 * Creates a {@link Pair} containing the same components as the given pair.
	 * @param pair the pair to copy
	 */
	public Pair(Pair<? extends L, ? extends R> pair)
	{
		this(pair.left, pair.right);
	}

	/**
	 * Checks for equality with another {@link Object}. The {@link Pair} is
	 * equal to another {@link Object} if that {@link Object} is a {@link Pair}
	 * too with left and right components equal to respective components of this
	 * {@link Pair} (even if the declared types are different). {@literal null}
	 * component is equal to {@literal null}.
	 * 
	 * @param o
	 *            the other {@link Object} to compare
	 * @return true if the other {@link Object} is a {@link Pair} with
	 *         components equal to respective components of this pair.
	 */
	
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (!(o instanceof Pair))
			return false;
		Pair<?, ?> p=(Pair<?, ?>)o;
		return (this.left == null ? p.left == null : this.left.equals(p.left))
			&& (this.right == null ? p.right == null : this.right.equals(p.right));
	}

	/**
	 * Returns a hash code of this {@link Pair}, which is a combination of has
	 * codes of its components. {@literal null} component's hash code is 0.
	 * 
	 * @return a combination of hash codes if this {@link Pair}'s components
	 */
	
	public int hashCode()
	{
		return (left == null ? 0 : left.hashCode()) * 31 + (right == null ? 0 : right.hashCode());
	}

	
	public String toString()
	{
		return "("+left+", "+right+")";
	}
}
