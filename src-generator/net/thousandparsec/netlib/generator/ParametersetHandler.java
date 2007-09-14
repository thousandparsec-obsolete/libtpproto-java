package net.thousandparsec.netlib.generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * This handler generates a class for a parameterset; this class is a base class
 * for classes for individual parameters. The generation process is very similar
 * to that of packets - the same {@link StructureHandler} is used.
 * 
 * @author ksobolewski
 */
public class ParametersetHandler extends StackedHandler<ProtocolHandler>
{
	private final File targetDir;
	private final String name;
	private final List<NamedEntity> parameters;
	private boolean classdefWritten=false;

	private void ensureParameterSetType() throws IOException
	{
		if (!classdefWritten)
		{
			parent.parent.generator.startParameterSetType();
			classdefWritten=true;
		}
	}

	public ParametersetHandler(ProtocolHandler parent, File targetDir, String name) throws SAXException
	{
		super(parent);
		this.targetDir=targetDir;
		this.name=name;
		this.parameters=new ArrayList<NamedEntity>();

		try
		{
			parent.parent.generator.startParameterSet(targetDir, name);
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
			if (getDepth() == 0)
			{
				super.startElement(uri, localName, name, atts);
				if (localName.equals("description"))
					pushHandler(new TextCommentHandler(this, parent.parent.generator, 0, 0));
				else if (localName.equals("parameter"))
				{
					ensureParameterSetType();
					String parameterName=atts.getValue("name");
					parameterName=parameterName.substring(0, 1).toUpperCase()+parameterName.substring(1);
					int id=Integer.parseInt(atts.getValue("type"));
					parameters.add(new NamedEntity(this.name+"."+parameterName, id));
					pushHandler(new ParameterHandler(this, parameterName, id));
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
			{
				ensureParameterSetType();
				parent.addEntityGroup(this.name.substring(0, 1).toLowerCase()+this.name.substring(1), parameters);
				parent.parent.generator.endParameterSet(targetDir, parameters);
			}
			super.endElement(uri, localName, name);
		}
		catch (IOException ex)
		{
			throw new SAXException(ex);
		}
	}
}
