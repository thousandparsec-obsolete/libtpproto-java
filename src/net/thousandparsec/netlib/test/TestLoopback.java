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
import net.thousandparsec.netlib.tp03.Login;
import net.thousandparsec.netlib.tp03.Object;
import net.thousandparsec.netlib.tp03.TP03Decoder;
import net.thousandparsec.netlib.tp03.TP03Visitor;
import net.thousandparsec.netlib.tp03.Object.PosType;
import net.thousandparsec.netlib.tp03.Object.VelType;

public class TestLoopback extends TP03Visitor
{
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException
	{
		FrameDecoder<TP03Decoder, TP03Visitor> ff=new TP03Decoder();

		ExecutorService exec=Executors.newFixedThreadPool(2);
		final Connection<TP03Decoder, TP03Visitor> conn=new Connection<TP03Decoder, TP03Visitor>(ff, new DebugSocket());

		try
		{

			exec.submit(new Callable<Void>()
				{
					public Void call() throws Exception
					{
						conn.sendFrame(new Login());
						System.out.println("Frame sent");

						Object object=new Object();
						object.setId(50);
						object.setModtime(System.currentTimeMillis());
						object.setName("Dupa Blada - ¯ó³æ ¯ó³wia");
						object.setOrders(5);
						object.setOtype(13);
						object.setPos(new PosType(1, 2, 3));
						object.setVel(new VelType(30, 40, 50));
						object.getContains().addAll(Arrays.asList(new Object.ContainsType(13), new Object.ContainsType(14), new Object.ContainsType(15)));
						object.getOrdertypes().addAll(Arrays.asList(new Object.OrdertypesType(666), new Object.OrdertypesType(667), new Object.OrdertypesType(668), new Object.OrdertypesType(669)));
						object.setSize(10000000000l);
						object.setPadding(new byte[] {13, 13, 13, 13, 13, 13, 13, 13});
						for (int i=0; i < 100; i++)
						{
							conn.sendFrame(object);
							System.out.println("Frame sent");
						}

						conn.close();

						return null;
					}
				});

			Future<List<Frame<TP03Decoder, TP03Visitor>>> futureResult=exec.submit(new Callable<List<Frame<TP03Decoder, TP03Visitor>>>()
				{
					public List<Frame<TP03Decoder, TP03Visitor>> call() throws Exception
					{
						List<Frame<TP03Decoder, TP03Visitor>> ret=new ArrayList<Frame<TP03Decoder,TP03Visitor>>();
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

			List<Frame<TP03Decoder,TP03Visitor>> frames=futureResult.get();
			TP03Visitor visitor=new TestLoopback();
			for (Frame<TP03Decoder,TP03Visitor> frame : frames)
				frame.visit(visitor);
		}
		finally
		{
			try {conn.close();} catch (IOException ignore) {}
			exec.shutdown();
		}
	}

	@Override
	public void handleUnhandled(Frame<TP03Decoder, TP03Visitor> frame)
	{
		System.out.println("Unhandled frame: "+frame.getFrameType()+" ("+frame+")");
	}

	@Override
	public void handle(Object o)
	{
		System.out.println("VISIT Object!");
	}
}
