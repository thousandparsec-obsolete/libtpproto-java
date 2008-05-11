package net.thousandparsec.netlib.generator;

import java.io.IOException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * This handler generates an inner class for one parameter from its
 * {@code ./usestruct/structure} and {@code ./descstruct/structure} elements.
 * 
 * @author ksobolewski
 */
class ParameterHandler extends StackedHandler<ParametersetHandler>
{
	protected final String name;
	protected final int id;
	protected boolean hasDescStruct=false;

	ParameterHandler(ParametersetHandler parent, String name, int id) throws SAXException
	{
		super(parent);
		this.name=name;
		this.id=id;

		try
		{
			parent.parent.parent.generator.startParameter(this.name, id);
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
					pushHandler(new TextCommentHandler(this, parent.parent.parent.generator, 1));
				else if (name.equals("usestruct"))
					pushHandler(new UsestructHandler(this));
				else if (name.equals("descstruct"))
				{
					hasDescStruct=true;
					pushHandler(new DescstructHandler(this));
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
			if (getDepth() == 0)
				parent.parent.parent.generator.endParameter(this.name);
			super.endElement(uri, localName, name);
		}
		catch (IOException ex)
		{
			throw new SAXException(ex);
		}
	}
}
