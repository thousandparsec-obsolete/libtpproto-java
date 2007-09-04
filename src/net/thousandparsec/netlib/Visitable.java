package net.thousandparsec.netlib;

/**
 * An interface for receivers of visitor/double-dispatch pattern:
 * implementations should simply call a method of the visitor (parametric type
 * {@code V}) which matches the implementing class (usually it's as asimple as
 * {@code v.handle(this);}).
 * 
 * @author ksobolewski
 */
public interface Visitable<V extends Visitor<V>>
{
	void visit(V v);
}
