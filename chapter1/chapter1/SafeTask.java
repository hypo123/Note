package chapter1;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SafeTask implements Runnable
{
	//创建一个线程本地变量
	private static ThreadLocal<Date> startDate = new ThreadLocal<Date>()
		{
			protected Date initialValue()
			{
				return new Date();
			}
		};	
		
	@Override
	public void run()
	{
		//打印线程开始时间
		System.out.printf("Starting Thread: %s : %s\n",Thread.currentThread().getId(),startDate.get());
		
		try
		{
			//线程休眠
			TimeUnit.SECONDS.sleep((int)Math.rint(Math.random()*10));
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		//打印线程结束时间
		System.out.printf("Thread Finished: %s : %s\n",Thread.currentThread().getId(),startDate.get());
	}
	
	public static void main(String[] args)
	{
		SafeTask task = new SafeTask();
		
		for(int i = 0 ; i < 3; i++)
		{
			Thread thread = new Thread(task);
			
			try
			{
				TimeUnit.SECONDS.sleep(2);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
			
			//启动新线程
			thread.start();
		}
	}
}
