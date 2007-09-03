package net.thousandparsec.netlib;

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
public abstract class Frame<F extends FrameDecoder<F, V>, V extends Visitor<F, V>> extends TPObject<F, V> implements Visitable<F, V>
{
	private final int id;

	protected Frame(int id)
	{
		this.id=id;
	}

	protected Frame(int id, TPDataInput in)
	{
		this(id);
	}

	public void write(TPDataOutput out, Connection<F, V> conn) throws IOException
	{
		switch (conn.getCompatibility())
		{
			case 3:
				//magic
				out.writeCharacter(getStringBytes(String.format("TP%02d", conn.getCompatibility())));
				//sequence
				out.writeInteger(conn.getNextFrameSequence());
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
				out.writeInteger(conn.getNextFrameSequence());
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

	static class Header
	{
		private static final byte[] MAGIC_TP03=new byte[] {'T', 'P', '0', '3'};
		private static final byte[] MAGIC_TP04=new byte[] {'T', 'P', 4};

		final int compat;
		final int framever;
		final int seq;
		final int id;
		final int length;

		Header(TPInputStream in, int compat) throws IOException
		{
			this.compat=compat;

			byte[] buf=new byte[4];
			switch (compat)
			{
				case 3:
					//magic
					in.readCharacter(buf, 0, MAGIC_TP03.length);
					if (!Arrays.equals(buf, MAGIC_TP03))
						throw new IOException("Not a start of a frame");
					//frameversion (not in this compat)
					this.framever=-1;
					//sequence
					this.seq=in.readInteger32();
					//type
					this.id=in.readInteger32();
					//payload length
					this.length=in.readInteger32();
					break;

				case 4:
					//magic
					in.readCharacter(buf, 0, MAGIC_TP04.length);
					if (!Arrays.equals(buf, MAGIC_TP04))
						throw new IOException("Not a start of a frame");
					//frameversion (not in this compat)
					this.framever=in.readInteger32();
					//sequence
					this.seq=in.readInteger32();
					//type
					this.id=in.readInteger32();
					//payload length
					this.length=in.readInteger32();
					break;

				default:
					throw new IllegalArgumentException("Unsupported protocol version: "+compat);
			}
		}
	}
}
