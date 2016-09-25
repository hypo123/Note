package recipe9;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main49
{
	public static void main(String[] args)
	{
		//创建执行器
		ExecutorService executor = (ExecutorService) Executors
				.newCachedThreadPool();

		//创建5个任务
		ResultTask resultTasks[] = new ResultTask[5];
		for (int i = 0; i < 5; i++)
		{
			ExecutableTask executableTask = new ExecutableTask("Task " + i);
			
			resultTasks[i] = new ResultTask(executableTask);
			
			//将ResultTask任务发送给执行器
			executor.submit(resultTasks[i]);
		}

		try
		{
			//主线程休眠5s
			TimeUnit.SECONDS.sleep(5);
		}
		catch (InterruptedException e1)
		{
			e1.printStackTrace();
		}

		//取消已经发送给执行器的所有任务
		for (int i = 0; i < resultTasks.length; i++)
		{
			//如果任务已完成、或已取消，或者由于某些其他原因而无法取消，则此尝试将失败
			resultTasks[i].cancel(true);
		}

		for (int i = 0; i < resultTasks.length; i++)
		{
			try
			{
				//有些任务已经执行完毕,不属于被取消的任务
				if (!resultTasks[i].isCancelled())
				{
					//调用ResultTask对象的get方法,输出还没有被取消的任务结果
					System.out.printf("%s 没有被取消\n", resultTasks[i].get());
				}
			}
			catch (InterruptedException | ExecutionException e)
			{
				e.printStackTrace();
			}
		}

		//关闭执行器
		executor.shutdown();
	}
}
