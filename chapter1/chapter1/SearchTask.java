package chapter1;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

class Result
{
	private String name;
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
}

public class SearchTask implements Runnable
{
	private Result result;
	
	public SearchTask(Result result)
	{
		this.result = result;
	}
	
	@Override
	public void run()
	{
		String name = Thread.currentThread().getName();
		
		System.out.printf("Thread %s: Start\n",name);
		
		try
		{
			doTask();
			result.setName(name);
		}
		catch(InterruptedException e)
		//捕获中断并处理
		{
			System.out.printf("Thread %s: Interrupted\n",name);
			return;
		}
	}
	
	private void doTask() throws InterruptedException
	{
		Random random = new Random((new Date()).getTime());
		
		//产生随机数
		int value = (int)(random.nextDouble()*100);
		
		System.out.printf("Thread %s: %d\n",Thread.currentThread().getName(),value);
		//休眠
		TimeUnit.SECONDS.sleep(value);
	}
	
	private static void waitFinish(ThreadGroup threadGroup)
	{
		
		while(threadGroup.activeCount() > 9)
		{
			try
			{
				TimeUnit.SECONDS.sleep(1);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args)
	{
		//新建一个线程组，线程组名为searcher
		ThreadGroup threadGroup = new ThreadGroup("searcher");
		
		Result result = new Result();
		
		SearchTask searchTask = new SearchTask(result);
		
		//启动10个新线程，并将其加入到线程组中.
		for(int i = 0 ; i < 10; i++)
		{
			//分配新的Thread对象，并将其加入到线程组threadGroup中.
			Thread thread = new Thread(threadGroup , searchTask);
			
			//启动线程
			thread.start();
			
			try
			{
				//休眠1秒钟
				TimeUnit.SECONDS.sleep(1);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
		//打印线程组中活动线程的估计数
		System.out.printf("Number of Threads: %d\n",threadGroup.activeCount());
		System.out.printf("Information about the Thread Group\n");
		//将有关此线程组的信息打印到标准输出。此方法只对调试有用
		threadGroup.list();
		
		Thread[] threads = new Thread[threadGroup.activeCount()];
		
		//enumerate方法：把此线程组及其子组中的所有活动线程复制到指定数组中
		threadGroup.enumerate(threads);
		
		for(int i = 0 ; i < threadGroup.activeCount(); i++)
		{
			//打印线程数组中线程的状态
			System.out.printf("Thread %s: %s\n",threads[i].getName(),threads[i].getState());
		}
		
		waitFinish(threadGroup);
		
		//中断此线程组中的所有线程
		threadGroup.interrupt();
		
	}
}
