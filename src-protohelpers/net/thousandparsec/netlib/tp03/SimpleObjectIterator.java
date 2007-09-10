package net.thousandparsec.netlib.tp03;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import net.thousandparsec.netlib.SequentialConnection;
import net.thousandparsec.netlib.TPException;
import net.thousandparsec.netlib.tp03.IDSequence.ModtimesType;
import net.thousandparsec.util.AbstractIterator;

/**
 * This is an {@link Iterator} which for each object produces a
 * {@link IDSequence.ModtimesType} with object id and object's last modification
 * time.
 * 
 * @author ksobolewski
 */
public class SimpleObjectIterator extends AbstractIterator<ModtimesType>
{
	public static final int DEFAULT_CHUNK_SIZE=100;

	private final SequentialConnection<TP03Visitor> conn;
	private final int chunkSize;
	private Iterator<ModtimesType> idsIter;
	private int key=-1;
	private int lastId=0;
	private int remaining;

	public SimpleObjectIterator(SequentialConnection<TP03Visitor> conn)
	{
		this(conn, DEFAULT_CHUNK_SIZE);
	}

	public SimpleObjectIterator(SequentialConnection<TP03Visitor> conn, int chunkSize)
	{
		this.conn=conn;
		this.chunkSize=chunkSize;
	}

	private void fetchIds(int chunk) throws IOException, TPException
	{
		GetObjectIDs getIds=new GetObjectIDs();
		getIds.setKey(key);
		getIds.setStart(lastId);
		getIds.setAmount(chunk);

		ObjectIDs result=conn.sendFrame(getIds, ObjectIDs.class);

		List<ModtimesType> ids=result.getModtimes();
		this.idsIter=ids.iterator();
		this.key=result.getKey();
		this.lastId+=ids.size();
		this.remaining=result.getRemaining();
	}

	@Override
	protected ModtimesType fetchNext()
	{
		try
		{
			if (key == -1)
				fetchIds(chunkSize);
			assert idsIter != null;
			if (idsIter.hasNext())
				return idsIter.next();
			else if (remaining > 0)
			{
				fetchIds(Math.min(remaining, chunkSize));
				assert idsIter.hasNext();
				return idsIter.next();
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
	}

	/*
	public static void main(String... args) throws UnknownHostException, IOException, TPException, URISyntaxException
	{
		Connection<TP03Visitor> conn=new TP03Decoder().makeConnection(new URI("tp://guest:guest@demo1.thousandparsec.net/tp"), true);
		try
		{
			for (Iterator<ModtimesType> oit=new SimpleObjectIterator(conn, DEFAULT_CHUNK_SIZE); oit.hasNext(); )
			{
				ModtimesType object=oit.next();
				System.out.printf("id=%d, time=%d%n", object.getID(), object.getModtime());
			}
		}
		finally
		{
			conn.close();
		}
	}
	*/
}
