package net.thousandparsec.netlib.generator;

import java.io.IOException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * This handler handles the {@code enumeration} type tag under a
 * {@code structure} element. It creates an {@code enum} nested inside the
 * packet's class. The enum's name is taken from the parent class ({@link PropertyHandler})
 * and converted to a camel-case form. The enum's values are named after their
 * {@code name} attributes; the {@code id} attribute is used as the enum value's
 * numeric value (it is expected to be a number, put is processed as a
 * {@link String}). The numeric value's type itself (if applicable) is inferred
 * from the {@code enumeration} element's {@code size} attribute.
 * 
 * @author ksobolewski
 */
class EnumerationHandler extends PropertyHandler
{
	private String lastName, lastValue;
	private String camelName;

	EnumerationHandler(StructureHandler<?> parent, String valueType, int size, String style, boolean readOnly)
	{
		super(parent, Property.PropertyType.enumeration, valueType, valueType, style.equals("mask") ? -size : size, readOnly);
	}

	@Override
	public void startElement(String uri, String localName, String name, Attributes atts) throws SAXException
	{
		try
		{
			//depth 0 -> ./values (or ./name and other stuff)
			//depth 1 -> ./values/value (no other choice)
			if (getDepth() == 0 && localName.equals("values"))
			{
				super.startElement(uri, localName, name, atts);
				parent.generator.generator.startEnumeration(parent.level, camelName, getValueSubtype());
			}
			else if (getDepth() == 1)
			{
				super.startElement(uri, localName, name, atts);
				lastName=atts.getValue("name");
				lastValue=atts.getValue("id");
				pushHandler(new TextCommentHandler(this, parent.generator.generator, parent.level + 1));
			}
			else
				super.startElement(uri, localName, name, atts);
		}
		catch (IOException ex)
		{
			throw new SAXException(ex);
		}
	}

	@Override
	public void endElement(String uri, String localName, String name) throws SAXException
	{
		try
		{
			//at end the depths are larger by 1
			if (getDepth() == 2)
			{
				super.endElement(uri, localName, name);
				parent.generator.generator.generateEnumerationValue(parent.level, lastName, lastValue);
			}
			else if (getDepth() == 1)
			{
				super.endElement(uri, localName, name);
				if (localName.equals("name"))
					setValueType(camelName=getName().substring(0, 1).toUpperCase()+getName().substring(1));
				else if (localName.equals("values"))
					parent.generator.generator.endEnumeration(parent.level, camelName, getValueSubtype());
			}
			else
				super.endElement(uri, localName, name);
		}
		catch (IOException ex)
		{
			throw new SAXException(ex);
		}
	}
}
