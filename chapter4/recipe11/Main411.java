package recipe11;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main411
{
	public static void main(String[] args)
	{

		RejectedTaskController controller = new RejectedTaskController();
		
		//创建执行器
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors
				.newCachedThreadPool();
		
		//设置用于未执行任务的新处理程序
		executor.setRejectedExecutionHandler(controller);

		System.out.printf("Main: Starting.\n");
		
		//向执行器提交3个任务
		for (int i = 0; i < 3; i++)
		{
			Task task = new Task("Task" + i);
			executor.submit(task);
		}

		System.out.printf("Main: Shuting down the Executor.\n");
		executor.shutdown();//关闭执行器

		System.out.printf("Main: Sending another Task.\n");
		Task task = new Task("RejectedTask");
		executor.submit(task);//向执行器提交新任务

		System.out.printf("Main: End.\n");
	}
}
