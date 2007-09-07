package net.thousandparsec.netlib.test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import net.thousandparsec.netlib.Connection;
import net.thousandparsec.netlib.Frame;
import net.thousandparsec.netlib.FrameDecoder;
import net.thousandparsec.netlib.tp03.TP03Decoder;
import net.thousandparsec.netlib.tp03.TP03Visitor;

public class TestLoopback extends TP03Visitor
{
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException, InterruptedException, InstantiationException, IllegalAccessException, ExecutionException
	{
		List<Frame<TP03Visitor>> frames=new ArrayList<Frame<TP03Visitor>>();
		for (Method m : TP03Visitor.class.getMethods())
		{
			if (m.getName().equals("frame"))
			{
				Class<?> fc=m.getParameterTypes()[0];
				//don't do this at home
				frames.add((Frame<TP03Visitor>)fc.newInstance());
			}
		}

		FrameDecoder<TP03Visitor> ff=new TP03Decoder();
		Connection<TP03Visitor> conn=new Connection<TP03Visitor>(ff, new DebugSocket());

		Future<Void> asyncTask=conn.receiveAllFramesAsync(new TestLoopback(frames));

		try
		{
			for (Frame<TP03Visitor> frame : frames)
			{
				conn.sendFrame(frame);
				assert !asyncTask.isDone();
				System.out.println("Sent frame: "+frame.getFrameType()+" ("+frame+")");
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace(System.err);
		}
		finally
		{
			try {conn.close();} catch (IOException ignore) {}
			//get the null from task just to see if there was an exception
			//exception thrown from here will mask any exceptions that could bring us here
			//but we don't mind, because exception means the test failed anyway
			asyncTask.get();
		}
	}

	private final List<Frame<TP03Visitor>> frames;

	public TestLoopback(List<Frame<TP03Visitor>> frames)
	{
		this.frames=frames;
	}

	@Override
	public void unhandledFrame(Frame<?> frame)
	{
		System.out.println("Received frame: "+frame.getFrameType()+" ("+frame+")");
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
