package net.thousandparsec.netlib.tp03;

import net.thousandparsec.netlib.Visitor;
import net.thousandparsec.netlib.TPException;

public class TP03Visitor extends Visitor
{
	/**
	 * @see Visitor#Visitor()
	 */
	public TP03Visitor()
	{
	}

	/**
	 * @see Visitor#Visitor(boolean)
	 */
	public TP03Visitor(boolean errorOnUnhandled)
	{
		super(errorOnUnhandled);
	}

	public void frame(Okay frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(Fail frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(Sequence frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(Redirect frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(Connect frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(Login frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(CreateAccount frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(GetFeatures frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(Features frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(Ping frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(GetObjectsByID frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(GetObjectsByPos frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(Object frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(GetObjectIDs frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(GetObjectIDsByPos frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(GetObjectIDsByContainer frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(ObjectIDs frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(GetOrderDesc frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(OrderDesc frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(GetOrderDescIDs frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(OrderDescIDs frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(GetOrder frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(RemoveOrder frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(Order frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(OrderInsert frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(OrderProbe frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(GetTimeRemaining frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(TimeRemaining frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(GetBoards frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(Board frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(GetBoardIDs frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(BoardIDs frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(GetMessage frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(Message frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(PostMessage frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(RemoveMessage frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(GetResource frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(Resource frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(GetResourceIDs frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(ResourceIDs frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(GetPlayer frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(Player frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(GetCategory frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(RemoveCategory frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(Category frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(AddCategory frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(GetCategoryIDs frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(CategoryIDs frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(GetDesign frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(RemoveDesign frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(Design frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(AddDesign frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(ModifyDesign frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(GetDesignIDs frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(DesignIDs frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(GetComponent frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(Component frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(GetComponentIDs frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(ComponentIDs frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(GetProperty frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(Property frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(GetPropertyIDs frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(PropertyIDs frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void frame(FinishedTurn frame) throws TPException
	{
		unhandledFrame(frame);
	}

	public void unhandledObjectParams(ObjectParams objectParams) throws TPException
	{
		if (errorOnUnhandled)
			throw new TPException("Unexpected objectParams: type " + objectParams.getParameterType()+ "("+objectParams.toString()+")");
	}

	public void objectParams(ObjectParams.Universe objectParams) throws TPException
	{
		unhandledObjectParams(objectParams);
	}

	public void objectParams(ObjectParams.Galaxy objectParams) throws TPException
	{
		unhandledObjectParams(objectParams);
	}

	public void objectParams(ObjectParams.StarSystem objectParams) throws TPException
	{
		unhandledObjectParams(objectParams);
	}

	public void objectParams(ObjectParams.Planet objectParams) throws TPException
	{
		unhandledObjectParams(objectParams);
	}

	public void objectParams(ObjectParams.Fleet objectParams) throws TPException
	{
		unhandledObjectParams(objectParams);
	}

	public void unhandledOrderParams(OrderParams orderParams) throws TPException
	{
		if (errorOnUnhandled)
			throw new TPException("Unexpected orderParams: type "+orderParams.getParameterType()+ "("+orderParams.toString()+")");
	}

	public void orderParams(OrderParams.OrderParamAbsSpaceCoords orderParams) throws TPException
	{
		unhandledOrderParams(orderParams);
	}

	public void orderParams(OrderParams.OrderParamTime orderParams) throws TPException
	{
		unhandledOrderParams(orderParams);
	}

	public void orderParams(OrderParams.OrderParamObject orderParams) throws TPException
	{
		unhandledOrderParams(orderParams);
	}

	public void orderParams(OrderParams.OrderParamPlayer orderParams) throws TPException
	{
		unhandledOrderParams(orderParams);
	}

	public void orderParams(OrderParams.OrderParamRelSpaceCoords orderParams) throws TPException
	{
		unhandledOrderParams(orderParams);
	}

	public void orderParams(OrderParams.OrderParamRange orderParams) throws TPException
	{
		unhandledOrderParams(orderParams);
	}

	public void orderParams(OrderParams.OrderParamList orderParams) throws TPException
	{
		unhandledOrderParams(orderParams);
	}

	public void orderParams(OrderParams.OrderParamString orderParams) throws TPException
	{
		unhandledOrderParams(orderParams);
	}

	public void orderParams(OrderParams.OrderParamReference orderParams) throws TPException
	{
		unhandledOrderParams(orderParams);
	}

	public void orderParams(OrderParams.OrderParamReferenceList orderParams) throws TPException
	{
		unhandledOrderParams(orderParams);
	}

}
