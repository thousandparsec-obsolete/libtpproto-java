package net.thousandparsec.netlib.generator;

import net.thousandparsec.netlib.generator.UseparametersTypeField.SearchPathElement;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

class TypeFrameHandler extends StackedHandler<StackedHandler<?>>
{
	private final UseparametersHandler root;
	private final UseparametersTypeField.SearchPathElement.Type pathType;
	private final String property;
	private boolean isFinal=true;

	TypeFrameHandler(StackedHandler<?> parent, UseparametersHandler root, UseparametersTypeField.SearchPathElement.Type pathType, Attributes atts)
	{
		super(parent);
		this.root=root;
		this.pathType=pathType;

		this.property=atts.getValue("name");
	}

	@Override
	public void startElement(String uri, String localName, String name, Attributes atts) throws SAXException
	{
		if (getDepth() == 0)
		{
			super.startElement(uri, localName, name, atts);
			if (localName.equals("getfield"))
			{
				pushHandler(new TypeFrameHandler(this, root, SearchPathElement.Type.FIELD, atts));
				isFinal=false;
			}
			else if (localName.equals("getlist"))
			{
				pushHandler(new TypeFrameHandler(this, root, SearchPathElement.Type.LIST, atts));
				isFinal=false;
			}
		}
		else
			super.startElement(uri, localName, name, atts);
	}

	@Override
	public void endElement(String uri, String localName, String name) throws SAXException
	{
		super.endElement(uri, localName, name);

		if (isFinal && pathType != SearchPathElement.Type.FIELD)
			throw new SAXException("Final search path element can't be a list");

		if (getDepth() == -1)
			root.addSearchPathElement(new SearchPathElement(isFinal ? SearchPathElement.Type.FINAL : pathType, property));
	}
}
