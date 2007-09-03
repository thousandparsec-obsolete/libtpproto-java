package net.thousandparsec.netlib.generator;

import java.io.IOException;

import org.xml.sax.SAXException;

/**
 * This handler takes all text nodes encountered below the element it was
 * created for and routes them to a {@link Appendable sink}. The
 * sink may be a {@link StringBuilder}, for example.
 * 
 * @author ksobolewski
 */
class TextCollectorHandler extends StackedHandler<StackedHandler<?>>
{
	private final Appendable sink;

	TextCollectorHandler(StackedHandler<?> parent, Appendable sink)
	{
		super(parent);
		this.sink=sink;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException
	{
		try
		{
			sink.append(new CharArraySequence(ch, start, length));
			super.characters(ch, start, length);
		}
		catch (IOException ex)
		{
			throw new SAXException(ex);
		}
	}

	private static class CharArraySequence implements CharSequence
	{
		private final char[] chars;
		private final int start;
		private final int length;

		CharArraySequence(char[] chars, int start, int length)
		{
			this.chars=chars;
			this.start=start;
			this.length=length;
		}

		public char charAt(int index)
		{
			return chars[start + index];
		}

		public int length()
		{
			return length;
		}

		public CharSequence subSequence(int start, int end)
		{
			return new CharArraySequence(chars, this.start + start, end - start);
		}
	}
}
