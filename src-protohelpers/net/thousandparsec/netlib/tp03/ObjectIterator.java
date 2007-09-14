package net.thousandparsec.netlib.tp03;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.thousandparsec.netlib.SequentialConnection;
import net.thousandparsec.netlib.TPException;
import net.thousandparsec.netlib.tp03.GetWithID.IdsType;
import net.thousandparsec.util.AbstractIterator;

/**
 * This class is an {@link Iterator} that produces {@link Object} frames for
 * each object (in order as returned by the server).
 * 
 * @author ksobolewski
 */
public class ObjectIterator extends AbstractIterator<Object>
{
	public static final int DEFAULT_CHUNK_SIZE=100;

	private final SequentialConnection<TP03Visitor> conn;
	private final int chunkSize;
	private final SimpleObjectIterator objIds;
	private List<Object> next=new LinkedList<Object>();
	private Iterator<Object> nextIter=Collections.<Object>emptyList().iterator();

	public ObjectIterator(SequentialConnection<TP03Visitor> conn)
	{
		this(conn, DEFAULT_CHUNK_SIZE);
	}

	public ObjectIterator(SequentialConnection<TP03Visitor> conn, int chunkSize)
	{
		this.conn=conn;
		this.chunkSize=chunkSize;
		this.objIds=new SimpleObjectIterator(conn, chunkSize);
	}

	private boolean fetchObjects() throws IOException, TPException
	{
		assert next.isEmpty();

		GetObjectsByID getObj=new GetObjectsByID();
		int count;
		for (count=0; count < chunkSize && objIds.hasNext(); count++)
			getObj.getIds().add(new IdsType(objIds.next().getId()));
		if (count == 0)
			return false;

		Sequence result=conn.sendFrame(getObj, Sequence.class);
		assert result.getNumber() == count;

		for (int i=0; i < count; i++)
			next.add(conn.receiveFrame(Object.class));

		nextIter=next.iterator();
		return true;
	}

	@Override
	protected Object fetchNext()
	{
		try
		{
			if (nextIter.hasNext() || fetchObjects())
			{
				Object ret=nextIter.next();
				nextIter.remove();
				return ret;
			}

			finished();
			return null;
		}
		catch (IOException ex)
		{
			//give up on error, instead of continuously trying again and again
			finished();
			throw new RuntimeException(ex);
		}
		catch (TPException ex)
		{
			//give up on error, instead of continuously trying again and again
			finished();
			throw new RuntimeException(ex);
		}
		catch (RuntimeException ex)
		{
			//give up on error, instead of continuously trying again and again
			finished();
			throw ex;
		}
	}

	/*
	public static void main(String... args) throws UnknownHostException, IOException, TPException, URISyntaxException
	{
		SequentialConnection<TP03Visitor> conn=new SimpleSequentialConnection<TP03Visitor>(
			new TP03Decoder().makeConnection(
				new URI("tp://guest:guest@demo1.thousandparsec.net/tp"),
				true,
				new TP03Visitor(true)));
		conn.getConnection().addConnectionListener(new DefaultConnectionListener<TP03Visitor>());
		try
		{
			for (Iterator<Object> oit=new ObjectIterator(conn, DEFAULT_CHUNK_SIZE); oit.hasNext(); )
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
