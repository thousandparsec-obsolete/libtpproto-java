package net.thousandparsec.netlib.test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

import net.thousandparsec.netlib.Connection;
import net.thousandparsec.netlib.Frame;
import net.thousandparsec.netlib.TPException;
import net.thousandparsec.netlib.objects.GameObject;
import net.thousandparsec.netlib.objects.Universe;
import net.thousandparsec.netlib.tp03.Connect;
import net.thousandparsec.netlib.tp03.Fail;
import net.thousandparsec.netlib.tp03.GetObjectsByID;
import net.thousandparsec.netlib.tp03.Login;
import net.thousandparsec.netlib.tp03.Object;
import net.thousandparsec.netlib.tp03.Okay;
import net.thousandparsec.netlib.tp03.Ping;
import net.thousandparsec.netlib.tp03.TP03Decoder;
import net.thousandparsec.netlib.tp03.TP03Visitor;
import net.thousandparsec.netlib.tp03.GetWithID.IdsType;

public class TestConect extends TP03Visitor
{
	public static void main(String... args) throws UnknownHostException, IOException, URISyntaxException
	{
		Connection<TP03Visitor> conn=Connection.makeConnection(
			new TP03Decoder(),
			new URI(args.length > 0 ? args[0] : "tp://guest:guest@demo1.thousandparsec.net/tp"));
		new TestConect(conn).start();
	}

	private final Connection<TP03Visitor> conn;

	public TestConect(Connection<TP03Visitor> conn)
	{
		this.conn=conn;
	}

	private void start() throws UnknownHostException, IOException
	{
		conn.receiveFramesAsync(this);

		Connect connect=new Connect();
		connect.setString("libtpproto-java-test");
		conn.sendFrame(connect);

		Login login=new Login();
		login.setUsername("guest");
		login.setPassword("guest");
		conn.sendFrame(login);

		conn.sendFrame(new Ping());

		GetObjectsByID getObj=new GetObjectsByID();
		getObj.getIds().add(new IdsType(0));
		conn.sendFrame(getObj);

		conn.close();
	}

	@Override
	public void unhandledFrame(Frame<TP03Visitor> frame)
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
	public void unhandledGameObject(GameObject<TP03Visitor> object)
	{
		System.out.printf("Got game object: %s%n", object);
	}

	@Override
	public void gameObject(Universe<TP03Visitor> object)
	{
		System.out.printf("Got Universe: age %d%n", object.getAge());
	}
}
