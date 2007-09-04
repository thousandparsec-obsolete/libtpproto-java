package net.thousandparsec.netlib.test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.thousandparsec.netlib.Connection;
import net.thousandparsec.netlib.Frame;
import net.thousandparsec.netlib.tp03.Connect;
import net.thousandparsec.netlib.tp03.Fail;
import net.thousandparsec.netlib.tp03.Login;
import net.thousandparsec.netlib.tp03.Okay;
import net.thousandparsec.netlib.tp03.Ping;
import net.thousandparsec.netlib.tp03.TP03Decoder;
import net.thousandparsec.netlib.tp03.TP03Visitor;

public class TestConect extends TP03Visitor implements Callable<Void>
{
	public static void main(String... args) throws UnknownHostException, IOException, URISyntaxException
	{
		Connection<TP03Visitor> conn=Connection.makeConnection(new TP03Decoder(), new URI("tp://guest:guest@demo1.thousandparsec.net/tp"));

		ExecutorService exec=Executors.newSingleThreadExecutor();
		exec.submit(new TestConect(conn));

		Connect connect=new Connect();
		connect.setString("libtpproto-java-test");
		conn.sendFrame(connect);
		Login login=new Login();
		login.setUsername("guest");
		login.setPassword("guest");
		conn.sendFrame(login);
		conn.sendFrame(new Ping());

		exec.shutdown();
		conn.close();
	}

	private final Connection<TP03Visitor> conn;

	public TestConect(Connection<TP03Visitor> conn)
	{
		this.conn=conn;
	}

	public Void call() throws Exception
	{
		try
		{
			Frame<TP03Visitor> frame;
			while ((frame=conn.receiveFrame()) != null)
				frame.visit(this);
		}
		catch (Exception ex)
		{
			ex.printStackTrace(System.err);
			throw ex;
		}
		return null;
	}

	@Override
	public void unhandledFrame(Frame<TP03Visitor> frame)
	{
		System.out.println("Got frame: "+frame);
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
}
