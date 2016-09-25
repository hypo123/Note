package recipe5;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main45
{
	public static void main(String[] args)
	{

		//创建执行器
		ExecutorService executor = (ExecutorService) Executors
				.newCachedThreadPool();

		//创建3个任务并将它们加入到任务列表中
		List<Task> taskList = new ArrayList<>();
		for (int i = 0; i < 3; i++)
		{
			Task task = new Task("Task-" + i);
			taskList.add(task);
		}

		//任务的返回的结果 Future的列表
		List<Future<Result>> resultList = null;
		
		try
		{
			//执行给定的任务,当所有任务完成时,返回保持任务状态和结果的Future列表.
			//返回列表的所有元素的Future.isDone()为true.
			//注意,可以通过正常或通过抛出异常来终止已完成的任务.
			resultList = executor.invokeAll(taskList);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		//关闭执行器
		executor.shutdown();

		System.out.printf("Core: Printing the results\n");
		
		for (int i = 0; i < resultList.size(); i++)
		{
			Future<Result> future = resultList.get(i);
			
			try
			{
				//Future类的get()方法:如有必要,等待计算完成,然后获取结果.
				//这儿所有任务都已完成,不必等待
				Result result = future.get();
				
				System.out.printf("%s: %s\n", result.getName(),
						result.getValue());
			}
			catch (InterruptedException | ExecutionException e)
			{
				e.printStackTrace();
			}
		}
	}
}
