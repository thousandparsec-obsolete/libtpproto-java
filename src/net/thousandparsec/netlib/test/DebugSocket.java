package net.thousandparsec.netlib.test;

import java.io.IOException;
import java.net.Socket;

class DebugSocket extends Socket
{
	DebugSocket() throws IOException
	{
		super();
		bind(null);
		connect(getLocalSocketAddress());
	}
}
