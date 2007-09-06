package net.thousandparsec.netlib.tp03;

import java.io.IOException;

import net.thousandparsec.netlib.Connection;
import net.thousandparsec.netlib.TPException;
import net.thousandparsec.netlib.tp03.GetWithID.IdsType;
import net.thousandparsec.util.AbstractIterator;

/**
 * This class is an {@link java.util.Iterator} that produces {@link Object}
 * frames for each object (in order as returned by the server).
 * 
 * @author ksobolewski
 */
public class ObjectIterator extends AbstractIterator<Object>
{
	private final Connection<TP03Visitor> conn;
	private final SimpleObjectIterator oit;
	private Object next;

	public ObjectIterator(Connection<TP03Visitor> conn, int chunkSize)
	{
		this.conn=conn;
		this.oit=new SimpleObjectIterator(conn, chunkSize);
	}

	void setNext(Object next)
	{
		this.next=next;
	}

	@Override
	protected Object fetchNext()
	{
		try
		{
			if (oit.hasNext())
			{
				//FIXME: fetch objects in larger chunks
				int id=oit.next().getID();
				GetObjectsByID getObj=new GetObjectsByID();
				getObj.getIds().add(new IdsType(id));

				conn.sendFrame(getObj, new TP03Visitor(true)
					{
						@Override
						public void frame(Sequence frame)
						{
							assert frame.getNumber() == 1;
						}
					});
				next=null;
				conn.receiveFrame(new TP03Visitor(true)
					{
						@Override
						public void frame(Object frame)
						{
							setNext(frame);
						}
					});
				assert next != null;
				return next;
			}

			finished();
			return null;
		}
		catch (IOException ex)
		{
			throw new RuntimeException(ex);
		}
		catch (TPException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	/*
	public static void main(String... args) throws UnknownHostException, IOException, TPException, URISyntaxException
	{
		Connection<TP03Visitor> conn=new TP03Decoder().makeConnection(new URI("tp://guest:guest@demo1.thousandparsec.net/tp"), true);
		try
		{
			for (Iterator<Object> oit=new ObjectIterator(conn, 100); oit.hasNext(); )
			{
				Object object=oit.next();
				System.out.printf("id=%d, type=%d, name=%s%n", object.getId(), object.getOtype(), object.getName());
			}
		}
		finally
		{
			conn.close();
		}
	}
	*/
}
