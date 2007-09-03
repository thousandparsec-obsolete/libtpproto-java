package net.thousandparsec.netlib;

import java.io.IOException;

/**
 * This is an inerface for frame decoders automatically generated for a specific
 * protocol version.
 * 
 * @see Connection
 * @author ksobolewski
 */
public interface FrameDecoder<F extends FrameDecoder<F, V>, V extends Visitor<F, V>>
{
	int getCompatibility();

	Frame<F, V> decodeFrame(int id, TPDataInput in) throws IOException;
}
