package net.thousandparsec.netlib.generator;

import java.io.IOException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * This handler generates an inner class for one parameter from its
 * {@code ./usestruct/structure} element.
 * 
 * @author ksobolewski
 */
public class ParameterHandler extends StructuredElementHandler<ParametersetHandler>
{
	private final String name;
	private boolean underUsestruct;

	ParameterHandler(ParametersetHandler parent, String name, int id) throws SAXException
	{
		super(parent);
		this.name=name;

		try
		{
			parent.parent.parent.generator.startInnerType(1, this.name);
		}
		catch (IOException ex)
		{
			throw new SAXException(ex);
		}
	}

	@Override
	public void startElement(String uri, String localName, String name, Attributes atts) throws SAXException
	{
		try
		{
			//depth 1: structure element is below usestruct, but we're lazy and don't create UsestructHandler ;)
			//the thing is, there's also descstruct, which seems to be useless, but has structure too
			if (getDepth() == 0)
			{
				super.startElement(uri, localName, name, atts);
				if (name.equals("description"))
					pushHandler(new TextCommentHandler(this, parent.parent.parent.generator, 1, 1));
				else if (name.equals("usestruct"))
					underUsestruct=true;
			}
			else if (getDepth() == 1 && underUsestruct)
			{
				super.startElement(uri, localName, name, atts);
				pushHandler(new StructureHandler<ParameterHandler>(this, parent.parent.parent, 2));
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
			if (getDepth() == 1 && name.equals("usestruct"))
				underUsestruct=false;
			else if (getDepth() == 0)
				parent.parent.parent.generator.endInnerType(1, this.name, getProperties());
			super.endElement(uri, localName, name);
		}
		catch (IOException ex)
		{
			throw new SAXException(ex);
		}
	}
}
