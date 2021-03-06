package net.thousandparsec.netlib.test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import net.thousandparsec.netlib.Connection;
import net.thousandparsec.netlib.DefaultConnectionListener;
import net.thousandparsec.netlib.Frame;
import net.thousandparsec.netlib.TPException;
import net.thousandparsec.netlib.tp03.Fail;
import net.thousandparsec.netlib.tp03.GetObjectsByID;
import net.thousandparsec.netlib.tp03.Object;
import net.thousandparsec.netlib.tp03.ObjectParams;
import net.thousandparsec.netlib.tp03.Okay;
import net.thousandparsec.netlib.tp03.Ping;
import net.thousandparsec.netlib.tp03.TP03Decoder;
import net.thousandparsec.netlib.tp03.TP03Visitor;
import net.thousandparsec.netlib.tp03.GetWithID.IdsType;
import net.thousandparsec.netlib.tp03.ObjectParams.Universe;

public class TestConnect extends TP03Visitor
{
	public static void main(String... args) throws UnknownHostException, IOException, URISyntaxException, InterruptedException, TPException
	{
		TP03Decoder decoder=new TP03Decoder();
		Connection<TP03Visitor> conn=decoder.makeConnection(
			new URI(args.length > 0 ? args[0] : "tp://guest:guest@demo1.thousandparsec.net/tp"),
			true, new TP03Visitor(false));
		conn.addConnectionListener(new DefaultConnectionListener<TP03Visitor>());
		new TestConnect(conn).start();
	}

	private final Connection<TP03Visitor> conn;

	public TestConnect(Connection<TP03Visitor> conn)
	{
		this.conn=conn;
	}

	private void start() throws UnknownHostException, IOException, InterruptedException
	{
		Future<Void> asyncTask=conn.receiveAllFramesAsync(this);

		try
		{
			conn.sendFrame(new Ping());

			GetObjectsByID getObj=new GetObjectsByID();
			//zero for top-level object is a typical magic number
			getObj.getIds().add(new IdsType(0));
			conn.sendFrame(getObj);
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
		System.out.printf("Got frame: %s%n",frame);
	}

	@Override
	public void frame(Okay frame)
	{
		System.out.printf("OK: %s%n", frame.getResult());
	}

	@Override
	public void frame(Fail frame)
	{
		System.out.printf("Fail: %d (%s)%n", frame.getCode().value, frame.getResult());
	}

	@Override
	public void frame(Object frame) throws TPException
	{
		frame.getObject().visit(this);
	}

	@Override
	public void unhandledObjectParams(ObjectParams object)
	{
		System.out.printf("Got game object: %s%n", object);
	}

	@Override
	public void objectParams(Universe object)
	{
		System.out.printf("Got Universe: age %d%n", object.getAge());
	}
}
