package net.thousandparsec.netlib.generator;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A stacked SAX {@link ContentHandler} which can handle one element and,
 * optionally, all (or part) of its subelements. The handler has a
 * {@link #parent} handler (except for the topmost element), which has a generic
 * parameter {@code P}, allowing for type-safe reference to the chain of parent
 * handlers (to, for example, avoid tedious and boring passing parameters in
 * constructors and fields and, intead, accessing them directly in parent(s)).
 * Child handler can be only one at a time, installed by
 * {@link #pushHandler(ContentHandler)} (which is declared as a
 * {@link ContentHandler}, but usually implemented as {@link StackedHandler})
 * and removed by {@link #popHandler()}. This handler delegates all methods
 * from {@link ContentHandler} to the child handler, in addition to its own
 * processing - for example it tracks the depth of elements (by incrementing on
 * {@link #startElement(String, String, String, Attributes)} and decrementing on
 * {@link #endElement(String, String, String)}), which can be used by
 * subclasses to tailor their decisions, and automatically pops itself from the
 * {@link #parent} handler if the element for which it was installed ends. For
 * it to work, the subclasses mush call {@code super.&lt;method&gt;()} in all
 * overridden {@link ContentHandler}'s methods.
 * 
 * @author ksobolewski
 */
class StackedHandler<P extends StackedHandler<?>> implements ContentHandler
{
	private static final ContentHandler NULL_HANDLER=new DefaultHandler();

	protected final P parent;
	private ContentHandler stack;
	private int depth;

	public StackedHandler(P parent)
	{
		this.parent=parent;
		this.stack=NULL_HANDLER;
		this.depth=0;
	}

	protected int getDepth()
	{
		return depth;
	}

	public void characters(char[] ch, int start, int length) throws SAXException
	{
		stack.characters(ch, start, length);
	}

	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException
	{
		stack.ignorableWhitespace(ch, start, length);
	}

	public void startDocument() throws SAXException
	{
		stack.startDocument();
	}

	public void endDocument() throws SAXException
	{
		stack.endDocument();
	}

	public void startElement(String uri, String localName, String name, Attributes atts) throws SAXException
	{
		stack.startElement(uri, localName, name, atts);
		depth++;
	}

	public void endElement(String uri, String localName, String name) throws SAXException
	{
		depth--;
		if (getDepth() == -1 && parent != null)
			parent.popHandler();
		else //if getDepth() == -1, then it will go to NULL_HANDLER anyway
			stack.endElement(uri, localName, name);
	}

	public void startPrefixMapping(String prefix, String uri) throws SAXException
	{
		stack.startPrefixMapping(prefix, uri);
	}

	public void endPrefixMapping(String prefix) throws SAXException
	{
		stack.endPrefixMapping(prefix);
	}

	public void processingInstruction(String target, String data) throws SAXException
	{
		stack.processingInstruction(target, data);
	}

	public void setDocumentLocator(Locator locator)
	{
		stack.setDocumentLocator(locator);
	}

	public void skippedEntity(String name) throws SAXException
	{
		stack.skippedEntity(name);
	}

	protected void pushHandler(ContentHandler h)
	{
		stack=h;
	}

	public void popHandler()
	{
		stack=NULL_HANDLER;
	}
}
