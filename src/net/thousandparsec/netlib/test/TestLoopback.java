package net.thousandparsec.netlib.test;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException
	{
		FrameDecoder<TP03Visitor> ff=new TP03Decoder();

		ExecutorService exec=Executors.newFixedThreadPool(2);
		final Connection<TP03Visitor> conn=new Connection<TP03Visitor>(ff, new DebugSocket());

		try
		{

			exec.submit(new Callable<Void>()
				{
					public Void call() throws Exception
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

						conn.close();

						return null;
					}
				});

			Future<List<Frame<TP03Visitor>>> futureResult=exec.submit(new Callable<List<Frame<TP03Visitor>>>()
				{
					public List<Frame<TP03Visitor>> call() throws Exception
					{
						List<Frame<TP03Visitor>> ret=new ArrayList<Frame<TP03Visitor>>();
						while (true)
							try
							{
								ret.add(conn.receiveFrame());
								System.out.println("Got frame");
							}
							catch (EOFException eof)
							{
								break;
							}
						return ret;
					}
				});

			List<Frame<TP03Visitor>> frames=futureResult.get();
			TP03Visitor visitor=new TestLoopback();
			for (Frame<TP03Visitor> frame : frames)
				frame.visit(visitor);
		}
		finally
		{
			try {conn.close();} catch (IOException ignore) {}
			exec.shutdown();
		}
	}

	@Override
	public void unhandledFrame(Frame<TP03Visitor> frame)
	{
		System.out.println("Unhandled frame: "+frame.getFrameType()+" ("+frame+")");
	}

	@Override
	public void frame(GetMessage o)
	{
		System.out.println("VISIT GetMessage!");
	}
}
