package net.thousandparsec.netlib.generator;

import java.io.File;
import java.io.IOException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * This handler generates a class for a packet. The class has properties as
 * described by the {@code structure} tag ({@link StructureHandler}),
 * constructors and implementations of abstract methods inherited from
 * {@link net.thousandparsec.netlib.Frame} ({@link net.thousandparsec.netlib.Frame#findByteLength()},
 * {@link net.thousandparsec.netlib.Frame#write(net.thousandparsec.netlib.TPDataOutput, net.thousandparsec.netlib.Connection)}
 * and
 * {@link net.thousandparsec.netlib.Frame#visit(net.thousandparsec.netlib.Visitor)}).
 * The {@code longname} element's contents is converted to a comment before the
 * class declaration.
 * 
 * @author ksobolewski
 */
class PacketHandler extends StructuredElementHandler<ProtocolHandler>
{
	protected final String packetName;
	private boolean classdefWritten=false;

	PacketHandler(ProtocolHandler parent, File targetDir, int id, String basePacket, String packetName) throws SAXException
	{
		super(parent);
		this.packetName=packetName;

		try
		{
			parent.parent.generator.startPacket(targetDir, id, basePacket, packetName);
		}
		catch (IOException ex)
		{
			throw new SAXException(ex);
		}
	}

	private void ensurePacketType() throws IOException
	{
		if (!classdefWritten)
		{
			parent.parent.generator.startPacketType();
			classdefWritten=true;
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
				//TODO: use description too (need to somehow coalesce the comments if both are present)
				if (localName.equals("longname"))
					pushHandler(new TextCommentHandler(this, this.parent.parent.generator, 0, 0));
				else if (localName.equals("structure"))
				{
					ensurePacketType();
					pushHandler(new StructureHandler<PacketHandler>(this, parent.parent, 1));
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
				ensurePacketType();
				parent.parent.generator.endPacket(getProperties());
			}
			super.endElement(uri, localName, name);
		}
		catch (IOException ex)
		{
			throw new SAXException(ex);
		}
	}
}
