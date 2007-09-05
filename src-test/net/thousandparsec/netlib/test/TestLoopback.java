package net.thousandparsec.netlib.test;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import net.thousandparsec.netlib.Connection;
import net.thousandparsec.netlib.Frame;
import net.thousandparsec.netlib.FrameDecoder;
import net.thousandparsec.netlib.tp03.GetMessage;
import net.thousandparsec.netlib.tp03.Login;
import net.thousandparsec.netlib.tp03.TP03Decoder;
import net.thousandparsec.netlib.tp03.TP03Visitor;
import net.thousandparsec.netlib.tp03.GetWithIDSlot.SlotsType;

public class TestLoopback extends TP03Visitor
{
	public static void main(String[] args) throws IOException, InterruptedException
	{
		FrameDecoder<TP03Visitor> ff=new TP03Decoder();
		Connection<TP03Visitor> conn=new Connection<TP03Visitor>(ff, new DebugSocket());

		Future<Void> asyncTask=conn.receiveFramesAsync(new TestLoopback());

		try
		{
			conn.sendFrame(new Login());
			System.out.println("Frame sent");

			GetMessage object=new GetMessage();
			object.setId(5);
			object.getSlots().addAll(Arrays.asList(
				new SlotsType(13),
				new SlotsType(14),
				new SlotsType(15),
				new SlotsType(16),
				new SlotsType(17),
				new SlotsType(18)));
			for (int i=0; i < 100; i++)
			{
				conn.sendFrame(object);
				System.out.println("Frame sent");
			}
		}
		finally
		{
			try {conn.close();} catch (IOException ignore) {}
			//get the null from task just to see if there was an exception
			try
			{
				//but don't swallow the exception that could bring us to this finally block
				asyncTask.get();
			}
			catch (ExecutionException ex)
			{
				ex.getCause().printStackTrace(System.err);
			}
		}
	}

	@Override
	public void unhandledFrame(Frame<?> frame)
	{
		System.out.println("Unhandled frame: "+frame.getFrameType()+" ("+frame+")");
	}

	@Override
	public void frame(GetMessage o)
	{
		System.out.println("VISIT GetMessage!");
	}

	private static class DebugSocket extends Socket
	{
		DebugSocket() throws IOException
		{
			super();
			bind(null);
			connect(getLocalSocketAddress());
		}
	}
}
