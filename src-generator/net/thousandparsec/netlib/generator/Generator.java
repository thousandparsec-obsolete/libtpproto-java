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
 * The main class of Thousand Parsec protocol description parser and generator.
 * <p>
 * The generator parses a protocol description file (given as the first
 * argument) and outputs implementation files to a directory (given as the
 * second argument). The target language is determined by an implementation of
 * {@link OutputGenerator} interface. This interface receives a stream of
 * "domain events", in a similar way it's done in SAX parsers; the generator
 * converts a stream of XML events of the protocol description file to events
 * such as "procotol start", "frame start", "frame end", "protocol end" etc.
 * <p>
 * The parsing is done by a stack of hierarchical
 * {@link org.xml.sax.ContentHandler}s, usually subclasses of
 * {@link StackedHandler} class, which simplifies handling of subelements. The
 * handlers are called in specific places of the XML astructure, from where the
 * apropriate methods of {@link OutputGenerator} are called. The generator's
 * main class is itself a {@link StackedHandler} for the highest level (even
 * above the topmost {@code protocol} element). This class houses the
 * {@link #generator} field which should be used by other handlers to generate
 * output (in correct order).
 * <p>
 * TODO: Allow implementations of {@link OutputGenerator} other than that for
 * Java.
 * 
 * @see OutputGenerator
 * @see StackedHandler
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
