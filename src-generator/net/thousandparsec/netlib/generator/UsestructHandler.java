package net.thousandparsec.netlib.generator;

import java.io.IOException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

class UsestructHandler extends StructuredElementHandler<ParameterHandler>
{
	UsestructHandler(ParameterHandler parent) throws SAXException
	{
		super(parent);

		try
		{
			parent.parent.parent.parent.generator.startParameterStruct();
		}
		catch (IOException ex)
		{
			throw new SAXException(ex);
		}
	}

	@Override
	public void startElement(String uri, String localName, String name, Attributes atts) throws SAXException
	{
		if (getDepth() == 0)
		{
			super.startElement(uri, localName, name, atts);
			pushHandler(new StructureHandler<UsestructHandler>(this, parent.parent.parent.parent, 2));
		}
		else
			super.startElement(uri, localName, name, atts);
	}


	@Override
	public void endElement(String uri, String localName, String name) throws SAXException
	{
		try
		{
			if (getDepth() == 0)
			{
				super.endElement(uri, localName, name);
				parent.parent.parent.parent.generator.endParameterStruct(getProperties());
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
