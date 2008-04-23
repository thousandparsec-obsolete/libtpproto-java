package net.thousandparsec.netlib;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A {@link FilterInputStream} that guards an underlying input stream from
 * reading more than is allowed; it is used to prevent the objects from reading
 * past the end of frame [similar posibility to detect underflows, when not
 * entire frame has been read, is more difficult to squeeze into
 * {@link InputStream} interface and requires some additional assumptions done
 * by the client code - maybe possibly like calling
 * {@link InputStream#mark(int)} at the end of each frame and testing remaining
 * limit in the implementation in this class - but this approach would create
 * unnecessary pushback buffer creation in other implementations].
 * 
 * @author ksobolewski
 */
class LimitInputStream extends FilterInputStream
{
	private int limit;

	protected LimitInputStream(InputStream in, int limit)
	{
		super(in);
		this.limit=limit;
	}

	private void checkLimit() throws IOException
	{
		if (limit == 0)
			throw new IOException("Read limit exceeded on stream");
	}

	
	public int read() throws IOException
	{
		checkLimit();
		limit--;
		return super.read();
	}

	
	public int read(byte b[]) throws IOException
	{
		checkLimit();
		int ret=read(b, 0, Math.min(b.length, limit));
		limit -= ret;
		return ret;
	}

	
	public int read(byte b[], int off, int len) throws IOException
	{
		checkLimit();
		int ret=super.read(b, off, Math.min(len, limit));
		limit -= ret;
		return ret;
	}

	
	public long skip(long n) throws IOException
	{
		checkLimit();
		//limit is int, so Math.min() never returns more than allowable for int - cast is safe
		int ret=(int)super.skip(Math.min(n, limit));
		limit -= ret;
		return ret;
	}

	/**
	 * Reads all bytes remaining to the limit and returns them as a {@code byte}
	 * array.
	 * 
	 * @return the array of bytes remaining to the limit
	 * @throws IOException
	 *             on I/O error
	 */
	public byte[] drain() throws IOException
	{
		byte[] ret=new byte[limit];
		if (limit != 0)
			read(ret);
		return ret;
	}
}
