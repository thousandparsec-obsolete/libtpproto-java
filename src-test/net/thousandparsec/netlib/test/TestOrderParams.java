package net.thousandparsec.netlib.test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.thousandparsec.netlib.DefaultConnectionListener;
import net.thousandparsec.netlib.Frame;
import net.thousandparsec.netlib.SequentialConnection;
import net.thousandparsec.netlib.SimpleSequentialConnection;
import net.thousandparsec.netlib.TPException;
import net.thousandparsec.netlib.tp03.GetOrder;
import net.thousandparsec.netlib.tp03.GetOrderDesc;
import net.thousandparsec.netlib.tp03.GetOrderDescIDs;
import net.thousandparsec.netlib.tp03.IDSequence;
import net.thousandparsec.netlib.tp03.Object;
import net.thousandparsec.netlib.tp03.ObjectIterator;
import net.thousandparsec.netlib.tp03.ObjectParams;
import net.thousandparsec.netlib.tp03.Order;
import net.thousandparsec.netlib.tp03.OrderDesc;
import net.thousandparsec.netlib.tp03.Ping;
import net.thousandparsec.netlib.tp03.Sequence;
import net.thousandparsec.netlib.tp03.TP03Decoder;
import net.thousandparsec.netlib.tp03.TP03Visitor;
import net.thousandparsec.netlib.tp03.GetWithID.IdsType;
import net.thousandparsec.netlib.tp03.GetWithIDSlot.SlotsType;
import net.thousandparsec.netlib.tp03.IDSequence.ModtimesType;

public class TestOrderParams
{
	public static void main(String... args) throws IOException, TPException, URISyntaxException
	{
		TP03Decoder decoder=new TP03Decoder();
		SequentialConnection<TP03Visitor> conn=new SimpleSequentialConnection<TP03Visitor>(decoder.makeConnection(
			new URI(args.length > 0 ? args[0] : "tp://guest:guest@demo1.thousandparsec.net/tp"),
			true, new TP03Visitor(false)));
		conn.getConnection().addConnectionListener(new DefaultConnectionListener<TP03Visitor>());

		try
		{
			System.out.println("Order desc:");

			GetOrderDescIDs getODIds=new GetOrderDescIDs();
			getODIds.setKey(-1);
			getODIds.setStart(0);
			getODIds.setAmount(-1);
			List<ModtimesType> orderDescIds=conn.sendFrame(getODIds, IDSequence.class).getModtimes();

			GetOrderDesc getOD=new GetOrderDesc();
			for (ModtimesType mt : orderDescIds)
				getOD.getIds().add(new IdsType(mt.getId()));

			final Map<Integer, OrderDesc> orderDescs=new TreeMap<Integer, OrderDesc>();
			for (int num=conn.sendFrame(getOD, Sequence.class).getNumber(); num > 0; num--)
			{
				OrderDesc orderDesc=conn.receiveFrame(OrderDesc.class);
				System.out.println(orderDesc);
				orderDescs.put(orderDesc.getId(), orderDesc);
			}

			System.out.println("Order:");

			for (Iterator<Object> oit=new ObjectIterator(conn); oit.hasNext(); )
			{
				final Object o=oit.next();

				if (o.getObject() instanceof ObjectParams.Planet)
				{
					GetOrder getOrder=new GetOrder();
					getOrder.setId(o.getId());
					getOrder.getSlots().add(new SlotsType(0));
					conn.sendFrame(getOrder, new TP03Visitor()
						{
							@Override
							public void frame(Order order) throws TPException
							{
								System.out.printf("For object: %s%n", o.toString());
								System.out.println(order.getOrderparams(orderDescs.get(order.getId())));
							}
							@Override
							public void unhandledFrame(Frame<?> frame)
							{
								System.out.printf("Unhandled frame: %s%n", frame.toString());
							}
						});
				}
			}

			conn.getConnection().sendFrame(new Ping());
		}
		finally
		{
			try {conn.close();} catch (IOException ignore) {}
		}
	}
}
