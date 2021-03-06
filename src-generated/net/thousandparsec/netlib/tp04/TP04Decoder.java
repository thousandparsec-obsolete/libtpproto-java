package net.thousandparsec.netlib.tp04;

import java.io.IOException;

import java.net.URI;
import java.net.UnknownHostException;

import net.thousandparsec.netlib.*;

public class TP04Decoder implements FrameDecoder<TP04Visitor>
{
	private static final TP04Visitor CHECK_LOGIN_VISITOR=new TP04Visitor()
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

	public Connection<TP04Visitor>
		makeConnection(URI serverUri, boolean autologin, TP04Visitor asyncVisitor)
		throws UnknownHostException, IOException, TPException
	{
		Connection<TP04Visitor> connection=Connection.makeConnection(this, serverUri, asyncVisitor);
		if (autologin)
		{
			String userInfo=serverUri.getUserInfo();
			if (userInfo == null)
				throw new TPException("Autologin enabled but no login info provided in the URI");
			String[] data=userInfo.split(":", -1);
			if (data.length != 2)
				throw new TPException("Autologin enabled but login info provided in the URI is invalid");

			SequentialConnection<TP04Visitor> seqConnection=new SimpleSequentialConnection<TP04Visitor>(connection);
			Connect connect=new Connect();
			connect.setString("libtpproto-java-test");
			seqConnection.sendFrame(connect, CHECK_LOGIN_VISITOR);
			Login login=new Login();
			login.setUsername(data[0]);
			login.setPassword(data[1]);
			seqConnection.sendFrame(login, CHECK_LOGIN_VISITOR);
			//if we're here, all's fine!
		}
		return connection;
	}

	public Frame<TP04Visitor> decodeFrame(int id, TPDataInput in) throws IOException
	{
		switch (id)
		{
			case Okay.FRAME_TYPE: return new Okay(id, in);
			case Fail.FRAME_TYPE: return new Fail(id, in);
			case Sequence.FRAME_TYPE: return new Sequence(id, in);
			case Redirect.FRAME_TYPE: return new Redirect(id, in);
			case Connect.FRAME_TYPE: return new Connect(id, in);
			case Login.FRAME_TYPE: return new Login(id, in);
			case CreateAccount.FRAME_TYPE: return new CreateAccount(id, in);
			case GetFeatures.FRAME_TYPE: return new GetFeatures(id, in);
			case Features.FRAME_TYPE: return new Features(id, in);
			case SetFilters.FRAME_TYPE: return new SetFilters(id, in);
			case Ping.FRAME_TYPE: return new Ping(id, in);
			case GetGames.FRAME_TYPE: return new GetGames(id, in);
			case Game.FRAME_TYPE: return new Game(id, in);
			case GetObjectDesc.FRAME_TYPE: return new GetObjectDesc(id, in);
			case ObjectDesc.FRAME_TYPE: return new ObjectDesc(id, in);
			case GetObjectDescIDs.FRAME_TYPE: return new GetObjectDescIDs(id, in);
			case ObjectDescIDs.FRAME_TYPE: return new ObjectDescIDs(id, in);
			case GetObjectsByID.FRAME_TYPE: return new GetObjectsByID(id, in);
			case GetObjectsByPos.FRAME_TYPE: return new GetObjectsByPos(id, in);
			case Object.FRAME_TYPE: return new Object(id, in);
			case ModifyObject.FRAME_TYPE: return new ModifyObject(id, in);
			case GetObjectIDs.FRAME_TYPE: return new GetObjectIDs(id, in);
			case GetObjectIDsByPos.FRAME_TYPE: return new GetObjectIDsByPos(id, in);
			case GetObjectIDsByContainer.FRAME_TYPE: return new GetObjectIDsByContainer(id, in);
			case ObjectIDs.FRAME_TYPE: return new ObjectIDs(id, in);
			case GetOrderDesc.FRAME_TYPE: return new GetOrderDesc(id, in);
			case OrderDesc.FRAME_TYPE: return new OrderDesc(id, in);
			case GetOrderDescIDs.FRAME_TYPE: return new GetOrderDescIDs(id, in);
			case OrderDescIDs.FRAME_TYPE: return new OrderDescIDs(id, in);
			case GetOrder.FRAME_TYPE: return new GetOrder(id, in);
			case RemoveOrder.FRAME_TYPE: return new RemoveOrder(id, in);
			case Order.FRAME_TYPE: return new Order(id, in);
			case OrderInsert.FRAME_TYPE: return new OrderInsert(id, in);
			case OrderProbe.FRAME_TYPE: return new OrderProbe(id, in);
			case GetTimeRemaining.FRAME_TYPE: return new GetTimeRemaining(id, in);
			case TimeRemaining.FRAME_TYPE: return new TimeRemaining(id, in);
			case FinishedTurn.FRAME_TYPE: return new FinishedTurn(id, in);
			case GetBoards.FRAME_TYPE: return new GetBoards(id, in);
			case Board.FRAME_TYPE: return new Board(id, in);
			case GetBoardIDs.FRAME_TYPE: return new GetBoardIDs(id, in);
			case BoardIDs.FRAME_TYPE: return new BoardIDs(id, in);
			case GetMessage.FRAME_TYPE: return new GetMessage(id, in);
			case Message.FRAME_TYPE: return new Message(id, in);
			case PostMessage.FRAME_TYPE: return new PostMessage(id, in);
			case RemoveMessage.FRAME_TYPE: return new RemoveMessage(id, in);
			case GetResource.FRAME_TYPE: return new GetResource(id, in);
			case Resource.FRAME_TYPE: return new Resource(id, in);
			case GetResourceIDs.FRAME_TYPE: return new GetResourceIDs(id, in);
			case ResourceIDs.FRAME_TYPE: return new ResourceIDs(id, in);
			case GetPlayer.FRAME_TYPE: return new GetPlayer(id, in);
			case Player.FRAME_TYPE: return new Player(id, in);
			case GetPlayerIDs.FRAME_TYPE: return new GetPlayerIDs(id, in);
			case PlayerIDs.FRAME_TYPE: return new PlayerIDs(id, in);
			case GetCategory.FRAME_TYPE: return new GetCategory(id, in);
			case RemoveCategory.FRAME_TYPE: return new RemoveCategory(id, in);
			case Category.FRAME_TYPE: return new Category(id, in);
			case AddCategory.FRAME_TYPE: return new AddCategory(id, in);
			case GetCategoryIDs.FRAME_TYPE: return new GetCategoryIDs(id, in);
			case CategoryIDs.FRAME_TYPE: return new CategoryIDs(id, in);
			case GetDesign.FRAME_TYPE: return new GetDesign(id, in);
			case RemoveDesign.FRAME_TYPE: return new RemoveDesign(id, in);
			case Design.FRAME_TYPE: return new Design(id, in);
			case AddDesign.FRAME_TYPE: return new AddDesign(id, in);
			case ModifyDesign.FRAME_TYPE: return new ModifyDesign(id, in);
			case GetDesignIDs.FRAME_TYPE: return new GetDesignIDs(id, in);
			case DesignIDs.FRAME_TYPE: return new DesignIDs(id, in);
			case GetComponent.FRAME_TYPE: return new GetComponent(id, in);
			case Component.FRAME_TYPE: return new Component(id, in);
			case GetComponentIDs.FRAME_TYPE: return new GetComponentIDs(id, in);
			case ComponentIDs.FRAME_TYPE: return new ComponentIDs(id, in);
			case GetProperty.FRAME_TYPE: return new GetProperty(id, in);
			case Property.FRAME_TYPE: return new Property(id, in);
			case GetPropertyIDs.FRAME_TYPE: return new GetPropertyIDs(id, in);
			case PropertyIDs.FRAME_TYPE: return new PropertyIDs(id, in);
			default: throw new IllegalArgumentException("Invalid Frame id: "+id);
		}
	}

	public int getCompatibility()
	{
		return 4;
	}
}
