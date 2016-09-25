package recipe7;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Main47
{
	public static void main(String[] args)
	{

		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		
		System.out.printf("Main: Starting at: %s\n", new Date());

		Task task = new Task("Task");
		
		//周期性执行任务 第一个任务1秒后执行,之后的任务每2秒执行一次
		ScheduledFuture<?> result = executor.scheduleAtFixedRate(task, 1, 2,
				TimeUnit.SECONDS);

		for (int i = 0; i < 10; i++)
		{
			//getDelay方法:返回任务到下一次执行时所要等待的时间
			System.out.printf("Main: Delay: %d\n",
					result.getDelay(TimeUnit.MILLISECONDS));
			try
			{
				//休眠主线程500毫秒
				TimeUnit.MILLISECONDS.sleep(500);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}

		//关闭执行器
		executor.shutdown();
		
		System.out.printf("Main: No more tasks at: %s\n", new Date());

		try
		{
			//将主线程休眠5秒等待周期性任务全部执行完成
			TimeUnit.SECONDS.sleep(5);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		//打印任务完成时间
		System.out.printf("Main: Finished at: %s\n", new Date());
	}
}
