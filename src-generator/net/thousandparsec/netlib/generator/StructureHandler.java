package net.thousandparsec.netlib.generator;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * This handler processes the {@code structure} element, recognising type
 * elements (with the help of {@link PropertyType} enum) and creates proper
 * handler for the type element. This handler can have parents of different
 * types so the parent type is a generic type extending plain
 * {@link StackedHandler}; to preserve the link to the {@link Generator} an
 * additional reference to a {@link PacketHandler} for a {@code packet} element
 * containing this {@code structure} element is required. Another field,
 * {@link #level}, tracks the nesting level of the {@code structure} elements.
 * 
 * @author ksobolewski
 */
class StructureHandler<P extends StructuredElementHandler<?>> extends StackedHandler<P>
{
	static boolean booleanFromString(String s)
	{
		return "yes".equalsIgnoreCase(s) || "true".equalsIgnoreCase(s);
	}

	protected final PacketHandler packet;
	protected final int level;

	StructureHandler(P parent, PacketHandler packet, int level)
	{
		super(parent);
		this.packet=packet;
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
				pushHandler(PropertyType.valueOf(localName).makeHandler(this, packet, atts));
			}
			catch (IllegalArgumentException ex)
			{
				//FIXME: uncomment when able to handle useparameters and descparameter
				//throw new SAXException("Unknown property type: "+localName);
			}
		}
		else
			super.startElement(uri, localName, name, atts);
	}

	static enum PropertyType
	{
		character
		{
			@Override
			<P extends StructuredElementHandler<?>> PropertyHandler makeHandler(StructureHandler<P> parent, PacketHandler packet, Attributes xmlAtts) throws NumberFormatException, SAXException
			{
				int size=Integer.parseInt(xmlAtts.getValue("size"));
				return new PropertyHandler(parent, character, "byte[]", null, size, booleanFromString(xmlAtts.getValue("readonly")));
			}
		},
		integer
		{
			@Override
			<P extends StructuredElementHandler<?>> PropertyHandler makeHandler(StructureHandler<P> parent, PacketHandler packet, Attributes xmlAtts) throws NumberFormatException, SAXException
			{
				int size=Integer.parseInt(xmlAtts.getValue("size"));
				return new PropertyHandler(parent, integer, packet.parent.parent.generator.getIntegerTypeName(size), null, size / 8, booleanFromString(xmlAtts.getValue("readonly")));
			}
		},
		string
		{
			@Override
			<P extends StructuredElementHandler<?>> PropertyHandler makeHandler(StructureHandler<P> parent, PacketHandler packet, Attributes xmlAtts) throws NumberFormatException, SAXException
			{
				return new PropertyHandler(parent, string, "String", null, 0, booleanFromString(xmlAtts.getValue("readonly")));
			}
		},
		enumeration
		{
			@Override
			<P extends StructuredElementHandler<?>> PropertyHandler makeHandler(StructureHandler<P> parent, PacketHandler packet, Attributes xmlAtts) throws NumberFormatException, SAXException
			{
				int size=Integer.parseInt(xmlAtts.getValue("size"));
				return new EnumerationHandler(parent, packet.parent.parent.generator.getIntegerTypeName(size), size / 8, xmlAtts.getValue("style"), booleanFromString(xmlAtts.getValue("readonly")));
			}
		},
		list
		{
			@Override
			<P extends StructuredElementHandler<?>> PropertyHandler makeHandler(StructureHandler<P> parent, PacketHandler packet, Attributes xmlAtts) throws NumberFormatException, SAXException
			{
				return new GroupHandler(parent, list, booleanFromString(xmlAtts.getValue("readonly")));
			}
		},
		group
		{
			@Override
			<P extends StructuredElementHandler<?>> PropertyHandler makeHandler(StructureHandler<P> parent, PacketHandler packet, Attributes xmlAtts) throws NumberFormatException, SAXException
			{
				//DTD says that group is never read only, hmm
				return new GroupHandler(parent, group, booleanFromString(xmlAtts.getValue("readonly")));
			}
		};
//		useparameters,
//		descparameter

		abstract <P extends StructuredElementHandler<?>> PropertyHandler makeHandler(StructureHandler<P> parent, PacketHandler packet, Attributes xmlAtts) throws NumberFormatException, SAXException;
	}
}
