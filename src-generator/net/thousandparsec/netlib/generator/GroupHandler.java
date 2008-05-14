package net.thousandparsec.netlib.generator;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * This handler is for type elements under a {@code structure} element which can
 * have additional structure, i.e.
 * {@link Property.PropertyType#group groups} and
 * {@link Property.PropertyType#list lists}. They are almost identical
 * (list is a sequence of groups) so this class handles both.
 * <p>
 * The generated code is (as in Java) a nested class with properties as
 * described by the {@code structure} tag (exactly the same was as in the packet
 * class). The name of the nested class is taken from the {@code longname} tag -
 * the long name is converted to whitespace-less camel-case form.
 * 
 * @author ksobolewski
 */
class GroupHandler extends PropertyHandler
{
	private static final Pattern PATTERN_SPACE=Pattern.compile("[^\\w0-9]+(.)?");

	/**
	 * Converts a non-alphanumeric-delimited String into a camel-cased String.
	 */
	private static String sanitizeLongName(CharSequence name)
	{
		Matcher m=PATTERN_SPACE.matcher(name);
		StringBuilder buf=new StringBuilder();
		int start=0;

		//first match is special
		if (m.find())
		{
			if (m.start() != 0)
			{
				buf.append(Character.toUpperCase(name.charAt(0)));
				buf.append(name.subSequence(1, m.start())); //preserve case
			}
			//camel back
			if (m.start(1) != -1)
				buf.append(Character.toUpperCase(name.charAt(m.start(1))));
			start=m.end();
		}
		else if (name.length() > 0)
		{
			buf.append(Character.toUpperCase(name.charAt(0)));
			start=1;
		}

		while (m.find())
		{
			buf.append(name.subSequence(start, m.start())); //preserve case
			//camel back
			if (m.start(1) != -1)
				buf.append(Character.toUpperCase(name.charAt(m.start(1))));
			start=m.end();
		}

		buf.append(name.subSequence(start, name.length())); //preserve case
		return buf.toString();
	}

	private String singularizeName(String plural)
	{
//		if (plural.endsWith("ies"))
//			plural=plural.substring(0, plural.length() - 3) + "y";
//		else if (plural.endsWith("s"))
//			plural=plural.substring(0, plural.length() - 1);
//		return plural.substring(0, 1).toUpperCase() + plural.substring(1);
		return plural.substring(0, 1).toUpperCase() + plural.substring(1) + "Type";
	}

	private StringBuilder longNameCollector;

	GroupHandler(StructureHandler<?> parent, Property.PropertyType type, boolean readOnly)
	{
		super(parent, type, 0, readOnly);
	}

	@Override
	public void startElement(String uri, String localName, String name, Attributes atts) throws SAXException
	{
		try
		{
			if (getDepth() == 0)
			{
				super.startElement(uri, localName, name, atts);
				if (localName.equals("longname"))
					pushHandler(new TextCollectorHandler(this, longNameCollector=new StringBuilder()));
				else if (localName.equals("structure"))
				{
					parent.generator.generator.startInnerType(parent.level, getValueType());
					pushHandler(new StructureHandler<GroupHandler>(this, parent.generator, parent.level + 1));
				}
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
			if (getDepth() == 1)
			{
				super.endElement(uri, localName, name);
				if (localName.equals("longname"))
				{
					//long name could be a useful source for a group type's name, but... it's not
					@SuppressWarnings("unused")
					String longName=sanitizeLongName(longNameCollector.toString());
					longNameCollector=null;
//					setValueType(longName.substring(0, 1).toUpperCase()+longName.substring(1));
				}
				else if (localName.equals("name"))
					//...so we use "singularised" (usually) plural name
					setValueType(singularizeName(getName()));
				else if (localName.equals("structure"))
					parent.generator.generator.endInnerType(parent.level, getValueType(), getProperties());
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
