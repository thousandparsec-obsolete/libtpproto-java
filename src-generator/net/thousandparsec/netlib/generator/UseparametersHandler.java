package net.thousandparsec.netlib.generator;

import java.util.List;

import net.thousandparsec.netlib.generator.StructureHandler.PropertyType;
import net.thousandparsec.netlib.generator.UseparametersTypeField.SearchPathElement;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

class UseparametersHandler extends PropertyHandler
{
	private StringBuilder typefieldCollector;
	private String indirectFrame;
	private String indirectFrameCheckField;
	private List<SearchPathElement> searchPath;

	UseparametersHandler(StructureHandler<?> parent, PropertyType useparameters, String ref)
	{
		super(parent, PropertyType.useparameters, 0, true);
	}

	@Override
	public void startElement(String uri, String localName, String name, Attributes atts) throws SAXException
	{
		if (getDepth() == 0)
		{
			super.startElement(uri, localName, name, atts);
			if (localName.equals("typefield"))
				pushHandler(new TextCollectorHandler(this, typefieldCollector=new StringBuilder()));
			else if (localName.equals("typeframe"))
			{
				indirectFrame=atts.getValue("name");
				indirectFrameCheckField=atts.getValue("idfield");
//				pushHandler(...)
			}
		}
		else
			super.startElement(uri, localName, name, atts);
	}

	@Override
	public void endElement(String uri, String localName, String name) throws SAXException
	{
		if (getDepth() == 1 && localName.equals("typefield"))
		{
			//actually NOP, keep the collector for later use
		}
		else if (getDepth() == 0)
		{
			setUseparametersTypeField(new UseparametersTypeField(
				typefieldCollector.toString(),
				indirectFrame,
				indirectFrameCheckField,
				searchPath));
		}
		super.endElement(uri, localName, name);
	}
}
