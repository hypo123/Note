package chapter1;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class UnsafeTask implements Runnable
{
	private Date startDate;
	
	@Override
	public void run()
	{
		startDate = new Date();
		
		//打印线程开始时间
		System.out.printf("Starting Thread: %s : %s\n",Thread.currentThread().getId(),startDate);
		
		try
		{
			//线程休眠
			TimeUnit.SECONDS.sleep((int)Math.rint(Math.random()*10));
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		
		//打印线程结束时间
		System.out.printf("Thread Finished: %s : %s\n",Thread.currentThread().getId(),startDate);
	}
	
	public static void main(String[] args)
	{
		UnsafeTask task = new UnsafeTask();
		
		for(int i = 0 ; i < 3; i++)
		{
			Thread thread = new Thread(task);
			//启动新线程
			thread.start();
			
			try
			{
				TimeUnit.SECONDS.sleep(2);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
