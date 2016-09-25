package recipe8;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main48
{
	public static void main(String[] args)
	{
		//创建执行器
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors
				.newCachedThreadPool();

		//创建任务
		Task task = new Task();

		System.out.printf("Main: Executing the Task\n");

		//提交任务
		Future<String> result = executor.submit(task);

		try
		{
			//主线程休眠2秒钟
			TimeUnit.SECONDS.sleep(2);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		System.out.printf("Main: Cancelling the Task\n");
		
		//试图取消对此任务的执行
		//参数为true,中断执行此任务的线程
		result.cancel(true);

		System.out.printf("Main: Cancelled: %s\n", result.isCancelled());
		System.out.printf("Main: Done: %s\n", result.isDone());

		//关闭执行器
		executor.shutdown();
		
		System.out.printf("Main: The executor has finished\n");
	}

}
