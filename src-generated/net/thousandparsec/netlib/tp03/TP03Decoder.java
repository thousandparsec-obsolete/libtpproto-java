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
			case Okay.FRAME_ID: return new Okay(id, in);
			case Fail.FRAME_ID: return new Fail(id, in);
			case Sequence.FRAME_ID: return new Sequence(id, in);
			case Redirect.FRAME_ID: return new Redirect(id, in);
			case Connect.FRAME_ID: return new Connect(id, in);
			case Login.FRAME_ID: return new Login(id, in);
			case CreateAccount.FRAME_ID: return new CreateAccount(id, in);
			case GetFeatures.FRAME_ID: return new GetFeatures(id, in);
			case Features.FRAME_ID: return new Features(id, in);
			case Ping.FRAME_ID: return new Ping(id, in);
			case GetObjectsByID.FRAME_ID: return new GetObjectsByID(id, in);
			case GetObjectsByPos.FRAME_ID: return new GetObjectsByPos(id, in);
			case Object.FRAME_ID: return new Object(id, in);
			case GetObjectIDs.FRAME_ID: return new GetObjectIDs(id, in);
			case GetObjectIDsByPos.FRAME_ID: return new GetObjectIDsByPos(id, in);
			case GetObjectIDsByContainer.FRAME_ID: return new GetObjectIDsByContainer(id, in);
			case ObjectIDs.FRAME_ID: return new ObjectIDs(id, in);
			case GetOrderDesc.FRAME_ID: return new GetOrderDesc(id, in);
			case OrderDesc.FRAME_ID: return new OrderDesc(id, in);
			case GetOrderDescIDs.FRAME_ID: return new GetOrderDescIDs(id, in);
			case OrderDescIDs.FRAME_ID: return new OrderDescIDs(id, in);
			case GetOrder.FRAME_ID: return new GetOrder(id, in);
			case RemoveOrder.FRAME_ID: return new RemoveOrder(id, in);
			case Order.FRAME_ID: return new Order(id, in);
			case OrderInsert.FRAME_ID: return new OrderInsert(id, in);
			case OrderProbe.FRAME_ID: return new OrderProbe(id, in);
			case GetTimeRemaining.FRAME_ID: return new GetTimeRemaining(id, in);
			case TimeRemaining.FRAME_ID: return new TimeRemaining(id, in);
			case GetBoards.FRAME_ID: return new GetBoards(id, in);
			case Board.FRAME_ID: return new Board(id, in);
			case GetBoardIDs.FRAME_ID: return new GetBoardIDs(id, in);
			case BoardIDs.FRAME_ID: return new BoardIDs(id, in);
			case GetMessage.FRAME_ID: return new GetMessage(id, in);
			case Message.FRAME_ID: return new Message(id, in);
			case PostMessage.FRAME_ID: return new PostMessage(id, in);
			case RemoveMessage.FRAME_ID: return new RemoveMessage(id, in);
			case GetResource.FRAME_ID: return new GetResource(id, in);
			case Resource.FRAME_ID: return new Resource(id, in);
			case GetResourceIDs.FRAME_ID: return new GetResourceIDs(id, in);
			case ResourceIDs.FRAME_ID: return new ResourceIDs(id, in);
			case GetPlayer.FRAME_ID: return new GetPlayer(id, in);
			case Player.FRAME_ID: return new Player(id, in);
			case GetCategory.FRAME_ID: return new GetCategory(id, in);
			case RemoveCategory.FRAME_ID: return new RemoveCategory(id, in);
			case Category.FRAME_ID: return new Category(id, in);
			case AddCategory.FRAME_ID: return new AddCategory(id, in);
			case GetCategoryIDs.FRAME_ID: return new GetCategoryIDs(id, in);
			case CategoryIDs.FRAME_ID: return new CategoryIDs(id, in);
			case GetDesign.FRAME_ID: return new GetDesign(id, in);
			case RemoveDesign.FRAME_ID: return new RemoveDesign(id, in);
			case Design.FRAME_ID: return new Design(id, in);
			case AddDesign.FRAME_ID: return new AddDesign(id, in);
			case ModifyDesign.FRAME_ID: return new ModifyDesign(id, in);
			case GetDesignIDs.FRAME_ID: return new GetDesignIDs(id, in);
			case DesignIDs.FRAME_ID: return new DesignIDs(id, in);
			case GetComponent.FRAME_ID: return new GetComponent(id, in);
			case Component.FRAME_ID: return new Component(id, in);
			case GetComponentIDs.FRAME_ID: return new GetComponentIDs(id, in);
			case ComponentIDs.FRAME_ID: return new ComponentIDs(id, in);
			case GetProperty.FRAME_ID: return new GetProperty(id, in);
			case Property.FRAME_ID: return new Property(id, in);
			case GetPropertyIDs.FRAME_ID: return new GetPropertyIDs(id, in);
			case PropertyIDs.FRAME_ID: return new PropertyIDs(id, in);
			default: throw new IllegalArgumentException("Invalid Frame id: "+id);
		}
	}

	public int getCompatibility()
	{
		return 3;
	}
}
