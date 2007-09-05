package net.thousandparsec.netlib;

import java.io.IOException;

/**
 * This is an inerface for frame decoders automatically generated for a specific
 * protocol version.
 * 
 * @see Connection
 * @author ksobolewski
 */
public interface FrameDecoder<V extends Visitor>
{
	int getCompatibility();

	Frame<V> decodeFrame(int id, TPDataInput in) throws IOException;
}
