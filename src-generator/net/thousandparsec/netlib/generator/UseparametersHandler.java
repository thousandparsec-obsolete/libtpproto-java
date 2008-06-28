package net.thousandparsec.netlib.generator;

import java.util.LinkedList;
import java.util.List;

import net.thousandparsec.netlib.generator.UseparametersTypeField.SearchPathElement;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

class UseparametersHandler extends PropertyHandler
{
	private StringBuilder typefieldCollector;
	private String indirectFrame;
	private String indirectFrameCheckField;
	private List<SearchPathElement> searchPath=new LinkedList<SearchPathElement>();
	private boolean handlingTypeframe;

	UseparametersHandler(StructureHandler<?> parent, String ref)
	{
		super(parent, Property.PropertyType.useparameters, ref, null, 0, false);
	}

	protected void addSearchPathElement(SearchPathElement element)
	{
		//add at the beginning, because we receive path elements in reverse order
		searchPath.add(0, element);
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
				handlingTypeframe=true;
			}
		}
		else if (getDepth() == 1 && handlingTypeframe)
		{
			super.startElement(uri, localName, name, atts);
			if (localName.equals("getfield"))
				pushHandler(new TypeFrameHandler(this, this, SearchPathElement.Type.FIELD, atts));
			else if (localName.equals("getlist"))
				pushHandler(new TypeFrameHandler(this, this, SearchPathElement.Type.LIST, atts));
		}
		else
			super.startElement(uri, localName, name, atts);
	}

	@Override
	public void endElement(String uri, String localName, String name) throws SAXException
	{
		if (getDepth() == 1 && localName.equals("typefield"))
		{
			//keep the collector for later use
			setValueSybtype(typefieldCollector.toString());
		}
		else if (getDepth() == 1 && localName.equals("typeframe"))
		{
			handlingTypeframe=false;
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
