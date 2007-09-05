package net.thousandparsec.netlib.tp03;

import java.io.IOException;

import java.net.URI;
import java.net.UnknownHostException;

import net.thousandparsec.netlib.*;

public class TP03Decoder implements FrameDecoder<TP03Visitor>
{
	private static final TP03Visitor CHECK_LOGIN_VISITOR=new TP03Visitor()
		{
			@Override
			public void unhandledFrame(Frame<?> frame) throws TPException
			{
				throw new TPException(String.format("Unexpected frame type %d", frame.getFrameType()));
			}

			@Override
			public void frame(Okay frame)
			{
				//all's good, capt'n!
			}

			@Override
			public void frame(Fail frame) throws TPException
			{
				throw new TPException(String.format("Server said 'Fail': %d (%s)", frame.getCode().value, frame.getResult()));
			}
		};

	public Connection<TP03Visitor>
		makeConnection(URI serverUri, boolean autologin)
		throws UnknownHostException, IOException, TPException
	{
		Connection<TP03Visitor> connection=Connection.makeConnection(this, serverUri);
		if (autologin)
		{
			String userInfo=serverUri.getUserInfo();
			if (userInfo == null)
				throw new TPException("Autologin enabled but no login info provided in the URI");
			String[] data=userInfo.split(":", -1);
			if (data.length != 2)
				throw new TPException("Autologin enabled but login info provided in the URI is invalid");

			Connect connect=new Connect();
			connect.setString("libtpproto-java-test");
			connection.sendFrame(connect, CHECK_LOGIN_VISITOR);
			Login login=new Login();
			login.setUsername(data[0]);
			login.setPassword(data[1]);
			connection.sendFrame(login, CHECK_LOGIN_VISITOR);
			//if we're here, all's fine!
		}
		return connection;
	}

	public Frame<TP03Visitor> decodeFrame(int id, TPDataInput in) throws IOException
	{
		switch (id)
		{
			case 0: return new Okay(id, in);
			case 1: return new Fail(id, in);
			case 2: return new Sequence(id, in);
			case 24: return new Redirect(id, in);
			case 3: return new Connect(id, in);
			case 4: return new Login(id, in);
			case 62: return new CreateAccount(id, in);
			case 25: return new GetFeatures(id, in);
			case 26: return new Features(id, in);
			case 27: return new Ping(id, in);
			case 5: return new GetObjectsByID(id, in);
			case 6: return new GetObjectsByPos(id, in);
			case 7: return new Object(id, in);
			case 28: return new GetObjectIDs(id, in);
			case 29: return new GetObjectIDsByPos(id, in);
			case 30: return new GetObjectIDsByContainer(id, in);
			case 31: return new ObjectIDs(id, in);
			case 8: return new GetOrderDesc(id, in);
			case 9: return new OrderDesc(id, in);
			case 32: return new GetOrderDescIDs(id, in);
			case 33: return new OrderDescIDs(id, in);
			case 10: return new GetOrder(id, in);
			case 13: return new RemoveOrder(id, in);
			case 11: return new Order(id, in);
			case 12: return new OrderInsert(id, in);
			case 34: return new OrderProbe(id, in);
			case 14: return new GetTimeRemaining(id, in);
			case 15: return new TimeRemaining(id, in);
			case 16: return new GetBoards(id, in);
			case 17: return new Board(id, in);
			case 35: return new GetBoardIDs(id, in);
			case 36: return new BoardIDs(id, in);
			case 18: return new GetMessage(id, in);
			case 19: return new Message(id, in);
			case 20: return new PostMessage(id, in);
			case 21: return new RemoveMessage(id, in);
			case 22: return new GetResource(id, in);
			case 23: return new Resource(id, in);
			case 37: return new GetResourceIDs(id, in);
			case 38: return new ResourceIDs(id, in);
			case 39: return new GetPlayer(id, in);
			case 40: return new Player(id, in);
			case 41: return new GetCategory(id, in);
			case 44: return new RemoveCategory(id, in);
			case 42: return new Category(id, in);
			case 43: return new AddCategory(id, in);
			case 45: return new GetCategoryIDs(id, in);
			case 46: return new CategoryIDs(id, in);
			case 47: return new GetDesign(id, in);
			case 51: return new RemoveDesign(id, in);
			case 48: return new Design(id, in);
			case 49: return new AddDesign(id, in);
			case 50: return new ModifyDesign(id, in);
			case 52: return new GetDesignIDs(id, in);
			case 53: return new DesignIDs(id, in);
			case 54: return new GetComponent(id, in);
			case 55: return new Component(id, in);
			case 56: return new GetComponentIDs(id, in);
			case 57: return new ComponentIDs(id, in);
			case 58: return new GetProperty(id, in);
			case 59: return new Property(id, in);
			case 60: return new GetPropertyIDs(id, in);
			case 61: return new PropertyIDs(id, in);
			default: throw new IllegalArgumentException("Invalid Frame id: "+id);
		}
	}

	public int getCompatibility()
	{
		return 3;
	}
}
