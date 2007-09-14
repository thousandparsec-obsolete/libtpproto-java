package net.thousandparsec.netlib.generator;

import java.io.IOException;

import org.xml.sax.SAXException;

/**
 * This handler processes only text nodes under the element for which it was
 * created. First it generates start of the comment and for each text node it
 * adds a line; fonally it closes the comment when the lement ends.
 * 
 * @author ksobolewski
 */
class TextCommentHandler extends StackedHandler<StackedHandler<?>>
{
	private final OutputGenerator generator;
	private final int level;
	private final int correction;

	TextCommentHandler(StackedHandler<?> parent, OutputGenerator generator, int level, int correction) throws IOException
	{
		super(parent);
		this.generator=generator;
		this.level=level;
		this.correction=correction;

		generator.startComment(level, correction);
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException
	{
		try
		{
			generator.continueComment(level, correction, ch, start, length);
			super.characters(ch, start, length);
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
			generator.endComment(level, correction);
			super.endElement(uri, localName, name);
		}
		catch (IOException ex)
		{
			throw new SAXException(ex);
		}
	}
}
