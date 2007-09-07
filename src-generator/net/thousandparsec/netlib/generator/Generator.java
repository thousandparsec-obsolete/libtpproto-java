package net.thousandparsec.netlib.generator;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * The generator's main class is itself a {@link StackedHandler} for the highest
 * level (even above the topmost {@code protocol} element). This class houses
 * the {@link #generator} field which should be used by other handlers to
 * generate output (in correct order).
 * 
 * @author ksobolewski
 */
public class Generator extends StackedHandler<StackedHandler<?>>
{
	public static void main(String... args) throws SAXException, IOException
	{
		if (args.length < 2)
		{
			System.out.println("Usage:");
			System.out.println("$0 <protocol-def> <target-dir>");
			System.out.println("where <protocol-def> is an XML file with protocol definition");
			System.out.println("and <target-dir> is base directory to which source file will be written.");
			System.exit(1);
		}

		XMLReader reader=XMLReaderFactory.createXMLReader();
		reader.setContentHandler(new Generator(args[1], new JavaOutputGenerator()));
		//use systemId, not InputStream, as this way it finds the DTD correctly
		reader.parse(new InputSource(args[0]));
	}

	private final File targetDir;
	final OutputGenerator generator;

	private Generator(String targetDir, OutputGenerator generator)
	{
		super(null);
		this.targetDir=new File(targetDir);
		this.generator=generator;
	}

	@Override
	public void startElement(String uri, String localName, String name, Attributes atts) throws SAXException
	{
		if (getDepth() == 0)
		{
			super.startElement(uri, localName, name, atts);
			if (localName.equals("protocol"))
			{
				Matcher m=Pattern.compile("TP([0-9]+)").matcher(atts.getValue("version"));
				if (!m.matches())
					throw new IllegalArgumentException("/protocol/@version");
				pushHandler(new ProtocolHandler(this, targetDir, Integer.parseInt(m.group(1))));
			}
		}
		else
			super.startElement(uri, localName, name, atts);
	}
}
