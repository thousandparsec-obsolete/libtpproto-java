package net.thousandparsec.netlib;

import java.io.EOFException;
import java.io.IOException;
import java.util.Arrays;

/**
 * A base class for classes representing protocol's frames. This class also
 * takes care of parsing and writing frame headers, as this requires
 * hand-crafted code, which can't be automatically generated. Other classes (for
 * frames and objects) are generated from protocol description in XML file.
 * <p>
 * Each frame has an {@link #getFrameType() id}, but it is not recommended to
 * use this to recognise different frame types; the proper way is to use the
 * visitor/double-dispatch pattern by subclassing a {@link Visitor} subclass
 * specific for the protocol version (which is the parametric type {@code V}).
 * 
 * @see Connection
 * @author ksobolewski
 */
public abstract class Frame<V extends Visitor> extends TPObject<V> implements Visitable<V>
{
	private final int id;
	private int seq;

	protected Frame(int id)
	{
		this.id=id;
	}

	protected Frame(int id, TPDataInput in)
	{
		this(id);
	}

	public void write(TPDataOutput out, Connection<?> conn) throws IOException
	{
		switch (conn.getCompatibility())
		{
			case 3:
				//magic
				out.writeCharacter(getStringBytes(String.format("TP%02d", conn.getCompatibility())));
				//sequence
				out.writeInteger(this.seq=conn.getNextFrameSequence());
				//type
				out.writeInteger(id);
				//payload length
				out.writeInteger(findByteLength());
				//(data should be written by subclass)
				break;

			case 4:
				//magic
				out.writeCharacter(getStringBytes("TP"));
				//protocolversion
				out.writeInteger((byte)4);
				//frameversion
				out.writeInteger((byte)0); //?
				//sequence
				out.writeInteger(this.seq=conn.getNextFrameSequence());
				//type
				out.writeInteger(id);
				//payload length
				out.writeInteger(findByteLength());
				//(data should be written by subclass)
				break;

			default:
				throw new IllegalArgumentException("Unsupported protocol version: "+conn.getCompatibility());
		}
	}

	/**
	 * Calculates the byte length of this frame's payload (data) in bytes as
	 * encoded for the TP protocol.
	 * 
	 * @return byte length of this object
	 */
	@Override
	public int findByteLength()
	{
		//no payload here
		return 0;
	}

	public final int getFrameType()
	{
		return id;
	}

	void setSequenceNumber(int seq)
	{
		this.seq=seq;
	}

	/**
	 * Returns this frame's sequence number; for an outgoing frame this is the
	 * last sequence number assigned to it by the {@link Connection}; for an
	 * incoming frame it is the sequence number as sent by the server, equal to
	 * the sequence number of the frame to which it is a response to.
	 * 
	 * @return this frame's sequence number
	 */
	public final int getSequenceNumber()
	{
		return seq;
	}

	static class Header
	{
		private static final byte[] MAGIC_TP03=new byte[] {'T', 'P', '0', '3'};
		private static final byte[] MAGIC_TP04=new byte[] {'T', 'P', 4};

		private static boolean checkMagic(TPInputStream in, byte[] buf, byte[] template) throws IOException
		{
			try
			{
				in.readCharacter(buf, 0, template.length);
				if (!Arrays.equals(buf, template))
					throw new IOException("Not a start of a frame");
				return true;
			}
			catch (EOFException ex)
			{
				//exceptions for flow control are bad, yes, but it's the only way
				//EOF on magic means that the connection was properly closed
				return false;
			}
		}

		static Header readHeader(TPInputStream in, int compat) throws IOException
		{
			int framever;
			int seq;
			int id;
			int length;

			byte[] buf=new byte[4];
			switch (compat)
			{
				case 3:
					//magic
					if (!checkMagic(in, buf, MAGIC_TP03))
						return null;
					//frameversion (not in this compat)
					framever=-1;
					//sequence
					seq=in.readInteger32();
					//type
					id=in.readInteger32();
					//payload length
					length=in.readInteger32();
					break;

				case 4:
					//magic
					if (!checkMagic(in, buf, MAGIC_TP04))
						return null;
					//frameversion (not in this compat)
					framever=in.readInteger32();
					//sequence
					seq=in.readInteger32();
					//type
					id=in.readInteger32();
					//payload length
					length=in.readInteger32();
					break;

				default:
					throw new IllegalArgumentException("Unsupported protocol version: "+compat);
			}

			return new Header(compat, framever, seq, id, length);
		}

		final int compat;
		final int framever;
		final int seq;
		final int id;
		final int length;

		private Header(int compat, int framever, int seq, int id, int length)
		{
			this.compat=compat;
			this.framever=framever;
			this.seq=seq;
			this.id=id;
			this.length=length;
		}
	}
}
