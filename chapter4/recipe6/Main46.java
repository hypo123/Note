package recipe6;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main46
{
	public static void main(String[] args)
	{
		
		//创建执行器	newScheduledThreadPool创建固定长度线程池,而且以延迟或定时的方式来执行任务.
		ScheduledExecutorService executor = (ScheduledExecutorService) Executors
				.newScheduledThreadPool(1);

		System.out.printf("Main: Starting at: %s\n", new Date());

		for (int i = 0; i < 5; i++)
		{
			Task task = new Task("Task " + i);
			
			//创建并执行在给定延迟后启用的 ScheduledFuture
			executor.schedule(task, i + 1, TimeUnit.SECONDS);
		}

		//关闭执行器
		executor.shutdown();
		
		try
		{
			executor.awaitTermination(1, TimeUnit.DAYS);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		System.out.printf("Core: Ends at: %s\n", new Date());
	}
}
