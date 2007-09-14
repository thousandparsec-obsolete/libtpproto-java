package net.thousandparsec.netlib.generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * This handler processes the {@code protocol} element. It treats the
 * {@code Header} packet specially, ignoring it and clearing all base references
 * to it.
 * 
 * @author ksobolewski
 */
class ProtocolHandler extends StackedHandler<Generator>
{
	private final File targetDir;
	private final List<NamedEntity> packets;
	private final Map<String, List<NamedEntity>> entities;

	ProtocolHandler(Generator parent, File targetDir, int compat) throws SAXException
	{
		super(parent);
		this.targetDir=targetDir;
		this.packets=new ArrayList<NamedEntity>();
		this.entities=new HashMap<String, List<NamedEntity>>();

		try
		{
			parent.generator.startProtocol(compat);
		}
		catch (IOException ex)
		{
			throw new SAXException(ex);
		}
	}

	void addEntityGroup(String groupName, List<NamedEntity> group)
	{
		entities.put(groupName, group);
	}

	@Override
	public void startElement(String uri, String localName, String name, Attributes atts) throws SAXException
	{
		if (getDepth() == 0)
		{
			super.startElement(uri, localName, name, atts);
			if (localName.equals("packet"))
			{
				String packetName=atts.getValue("name");
				if (!packetName.equals("Header"))
				{
					String id=atts.getValue("id");
					int intId=id == null ? -1 : Integer.parseInt(id);
					packets.add(new NamedEntity(packetName, intId));
					String base=atts.getValue("base");
					if ("Header".equals(base))
						base=null;
					pushHandler(new PacketHandler(this, targetDir, intId, base, packetName));
				}
			}
			else if (localName.equals("parameterset"))
				pushHandler(new ParametersetHandler(this, targetDir, atts.getValue("name")));
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
				addEntityGroup("frame", packets);
				parent.generator.endProtocol(targetDir, entities);
			}
			super.endElement(uri, localName, name);
		}
		catch (IOException ex)
		{
			throw new SAXException(ex);
		}
	}
}
