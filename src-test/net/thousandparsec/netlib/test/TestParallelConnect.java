package net.thousandparsec.netlib.test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.thousandparsec.netlib.Connection;
import net.thousandparsec.netlib.DefaultConnectionListener;
import net.thousandparsec.netlib.PipelinedConnection;
import net.thousandparsec.netlib.SequentialConnection;
import net.thousandparsec.netlib.TPException;
import net.thousandparsec.netlib.tp03.Object;
import net.thousandparsec.netlib.tp03.ObjectIterator;
import net.thousandparsec.netlib.tp03.TP03Decoder;
import net.thousandparsec.netlib.tp03.TP03Visitor;

public class TestParallelConnect
{
	private static final int NUM_THREADS=3;

	public static void main(String... args) throws UnknownHostException, IOException, TPException, URISyntaxException, InterruptedException, ExecutionException
	{
		TP03Decoder decoder=new TP03Decoder();
		Connection<TP03Visitor> conn=decoder.makeConnection(
			new URI(args.length > 0 ? args[0] : "tp://guest:guest@demo1.thousandparsec.net/tp"),
			true, new TP03Visitor(false));
		conn.addConnectionListener(new DefaultConnectionListener<TP03Visitor>());
		new TestParallelConnect(conn).start();
	}

	private final PipelinedConnection<TP03Visitor> conn;

	public TestParallelConnect(Connection<TP03Visitor> conn)
	{
		this.conn=new PipelinedConnection<TP03Visitor>(conn);
	}

	private void start() throws InterruptedException, IOException, ExecutionException
	{
		ExecutorService exec=Executors.newFixedThreadPool(NUM_THREADS);

		Collection<Callable<List<Object>>> tasks=new ArrayList<Callable<List<Object>>>(NUM_THREADS);
		for (int i=0; i < NUM_THREADS; i++)
			tasks.add(new Task(conn.createPipeline()));

		try
		{
			List<Future<List<Object>>> futures=exec.invokeAll(tasks);

			List<Object> objects=null;
			top: for (Future<List<Object>> f : futures)
			{
				try
				{
					List<Object> ret=f.get();
					if (objects == null)
						objects=ret;
					else
					{
						Iterator<Object> o1=objects.iterator();
						Iterator<Object> o2=ret.iterator();
						while (o1.hasNext() && o2.hasNext())
							if (!o1.next().getName().equals(o2.next().getName()))
							{
								System.out.println("Results are not equal!!");
								continue top;
							}
						if (o1.hasNext() || o2.hasNext())
						{
							System.out.println("Results are not equal length!!");
							continue top;
						}
						System.out.println("Results are equal");
						objects=ret;
					}
				}
				catch (ExecutionException ex)
				{
					System.out.println("Task failed:");
					ex.printStackTrace(System.out);
					System.out.println();
					System.out.println();
				}
			}

			System.out.println("Done!");
		}
		finally
		{
			exec.shutdown();
			conn.close();
		}
	}

	private static class Task implements Callable<List<Object>>
	{
		private final SequentialConnection<TP03Visitor> conn;

		Task(SequentialConnection<TP03Visitor> conn)
		{
			this.conn=conn;
		}

		public List<Object> call() throws Exception
		{
			List<Object> objects=new LinkedList<Object>();
			//use small chunk size to really test sequentiality
			for (java.util.Iterator<Object> oit=new ObjectIterator(conn, 10); oit.hasNext(); )
				objects.add(oit.next());
			conn.close();
			//just to make sure...
			Collections.sort(objects, new Comparator<Object>()
				{
					public int compare(Object o1, Object o2)
					{
						return o1.getName().compareTo(o2.getName());
					}
				});
			return objects;
		}
	}
}
