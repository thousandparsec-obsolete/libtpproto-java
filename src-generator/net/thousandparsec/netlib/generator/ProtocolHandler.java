package net.thousandparsec.netlib.generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	private final List<Packet> packets;

	ProtocolHandler(Generator parent, File targetDir, int compat) throws SAXException
	{
		super(parent);
		this.targetDir=targetDir;
		this.packets=new ArrayList<Packet>();

		try
		{
			parent.generator.startProtocol(compat);
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
			if (localName.equals("packet"))
			{
				String packetName=atts.getValue("name");
				if (!packetName.equals("Header"))
				{
					String id=atts.getValue("id");
					int intId=id == null ? -1 : Integer.parseInt(id);
					packets.add(new Packet(packetName, intId));
					String base=atts.getValue("base");
					if ("Header".equals(base))
						base=null;
					pushHandler(new PacketHandler(this, targetDir, packetName, intId, base));
				}
			}
		}
		else if (localName.equals("parameterset"))
		{
			super.startElement(uri, localName, name, atts);
			pushHandler(new ParametersetHandler(this));
		}
		else
			super.startElement(uri, localName, name, atts);
	}

	@Override
	public void endElement(String uri, String localName, String name) throws SAXException
	{
		if (getDepth() == 0)
			try
			{
				parent.generator.endProtocol(targetDir, packets);
			}
			catch (IOException ex)
			{
				throw new SAXException(ex);
			}
		super.endElement(uri, localName, name);
	}
}
