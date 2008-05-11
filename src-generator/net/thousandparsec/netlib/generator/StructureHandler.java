package net.thousandparsec.netlib.generator;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * This handler processes the {@code structure} element, recognising type
 * elements (with the help of {@link Property.PropertyType} enum) and creates proper
 * handler for the type element. This handler can have parents of different
 * types so the parent type is a generic type extending plain
 * {@link StackedHandler}; to preserve the link to the {@link Generator} (top
 * level handler) an additional reference to the generator is required. Another
 * field, {@link #level}, tracks the nesting level of the {@code structure}
 * elements.
 * 
 * @author ksobolewski
 */
class StructureHandler<P extends StructuredElementHandler<?>> extends StackedHandler<P>
{
	protected final Generator generator;
	protected final int level;

	StructureHandler(P parent, Generator generator, int level)
	{
		super(parent);
		this.generator=generator;
		this.level=level;
	}

	@Override
	public void startElement(String uri, String localName, String name, Attributes atts) throws SAXException
	{
		if (getDepth() == 0)
		{
			super.startElement(uri, localName, name, atts);

			try
			{
				pushHandler(Property.PropertyType.valueOf(localName).makeHandler(this, generator, atts));
			}
			catch (IllegalArgumentException ex)
			{
				throw new SAXException("Unknown property type: "+localName);
			}
		}
		else
			super.startElement(uri, localName, name, atts);
	}
}
