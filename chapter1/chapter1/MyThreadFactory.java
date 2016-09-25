package chapter1;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class MyThreadFactory implements ThreadFactory
{
	private int counter;
	private String name;
	private List<String> stats;
	
	public MyThreadFactory(String name)
	{
		counter = 0;
		this.name = name;
		stats = new ArrayList<String>();
	}
	
	@Override
	public Thread newThread(Runnable r)
	//ThreadFactory接口只有一个方法，即newThread，它以Runnable接口对象作为传入参数
	//并返回一个线程对象。当实现ThreadFactory接口时，必须实现覆盖这个方法.
	{
		Thread t = new Thread(r , name + "-Thread_"+counter);
		
		counter++;
		
		stats.add(String.format("Created thread %d with name %s on %s\n",t.getId(),t.getName(),new Date()));
		
		return t;
	}
	
	public String getStats()
	{
		StringBuffer buffer = new StringBuffer();
		
		Iterator<String> it = stats.iterator();
		
		while(it.hasNext())
		{
			buffer.append(it.next());
		}
		
		return buffer.toString();
	}
	
	public static void main(String[] args)
	{
		//创建一个MyThreadFactory对象
		MyThreadFactory factory = new MyThreadFactory("MyThreadFactory");
		
		Task2 task = new Task2();
		
		Thread thread;
		
		System.out.printf("Starting the Threads\n");
		
		//使用工厂类MyThreadFactory创建10个线程对象.
		for(int i = 0 ; i < 10 ; i++)
		{
			//使用工厂类的newThread方法创建新线程对象
			thread = factory.newThread(task);
			
			//启动新线程
			thread.start();
		}
		
		System.out.printf("Factory stats:\n");
		System.out.printf("%s\n",factory.getStats());
	}
}

class Task2 implements Runnable
{
	@Override
	public void run()
	{
		try
		{
			//线程休眠1秒
			TimeUnit.SECONDS.sleep(1);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
