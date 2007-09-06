package net.thousandparsec.netlib.tp03;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.thousandparsec.netlib.Connection;
import net.thousandparsec.netlib.TPException;
import net.thousandparsec.netlib.objects.Universe;
import net.thousandparsec.netlib.tp03.GetWithID.IdsType;
import net.thousandparsec.util.AbstractIterator;
import net.thousandparsec.util.Pair;

/**
 * This is an {@link Iterator} that starts from the root object (usually the
 * {@link Universe}) and returns subsequent objects in a depth-first search.
 * The objects produced by thie iterator are {@link Pair}s which have hierarchy
 * depth (starting from {@literal 0}) as the left component and the
 * {@link Object} frame object as the right component. From the changes of the
 * depth the structure of the hierarchy can be determined.
 * 
 * @author ksobolewski
 */
public final class ObjectHierarchyIterator extends AbstractIterator<Pair<Integer, Object>>
{
	private final Connection<TP03Visitor> conn;
	private final int depth;
	private final List<Object.ContainsType> rootIds;
	private int count=-1;
	private List<Object> objects;
	private Iterator<Object> objectsIter;
	private Iterator<Pair<Integer, Object>> childIter;

	public ObjectHierarchyIterator(Connection<TP03Visitor> conn, int rootId)
	{
		this(conn, 0, rootId);
	}

	private ObjectHierarchyIterator(Connection<TP03Visitor> conn, int depth, int rootId)
	{
		this(conn, depth, Collections.singletonList(new Object.ContainsType(rootId)));
	}

	private ObjectHierarchyIterator(Connection<TP03Visitor> conn, int depth, List<Object.ContainsType> rootIds)
	{
		this.conn=conn;
		this.depth=depth;
		this.rootIds=rootIds;
	}

	void setCount(int count)
	{
		this.count=count;
		this.objects=new ArrayList<Object>(count);
	}

	void addObject(Object object)
	{
		this.objects.add(object);
	}

	private void fetchIds()
	{
		try
		{
			GetObjectsByID getObjs=new GetObjectsByID();
			for (Object.ContainsType id : rootIds)
				getObjs.getIds().add(new IdsType(id.getID()));
			conn.sendFrame(getObjs, new TP03Visitor(true)
				{
					@Override
					public void frame(Sequence frame)
					{
						setCount(frame.getNumber());
					}
				});
			assert count >= 0;
			for (int i=0; i < count; i++)
				conn.receiveFrame(new TP03Visitor(true)
					{
						@Override
						public void frame(Object frame)
						{
							addObject(frame);
						}
					});
			objectsIter=objects.iterator();
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

	@Override
	protected Pair<Integer, Object> fetchNext()
	{
		if (count == -1)
			fetchIds();
		if (childIter != null && childIter.hasNext())
			return childIter.next();
		else if (objectsIter.hasNext())
		{
			Object object=objectsIter.next();
			childIter=new ObjectHierarchyIterator(conn, depth + 1, object.getContains());
			return Pair.make(depth, object);
		}

		finished();
		return null;
	}

	/*
	public static void main(String... args) throws UnknownHostException, IOException, TPException, URISyntaxException
	{
		Connection<TP03Visitor> conn=new TP03Decoder().makeConnection(new URI("tp://guest:guest@demo1.thousandparsec.net/tp"), true);
		try
		{
			TP03Visitor v=new TP03Visitor()
				{
					@Override
					public void frame(Object object)
					{
						System.out.printf("Object: type=%d, id=%d, name=%s%n", object.getObject().getObjectType(), object.getId(), object.getName());
					}
				};
			for (Iterator<Pair<Integer, Object>> oit=new ObjectHierarchyIterator(conn, Universe.OBJECT_ID); oit.hasNext(); )
			{
				Pair<Integer, Object> object=oit.next();
				for (int i=0; i < object.left; i++)
					System.out.print("    ");
				object.right.visit(v);
			}
		}
		finally
		{
			conn.close();
		}
	}
	*/
}