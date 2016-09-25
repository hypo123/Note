package recipe2;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server
{
	private ThreadPoolExecutor executor;

	public Server()
	{
		// 创建线程执行器 固定长度线程池
		executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
	}

	public void executeTask(Task task)
	{
		System.out.printf("Server: A new task has arrived\n");

		// 调用execute方法将任务发送给Task
		executor.execute(task);

		// getPoolSize方法:返回执行器线程池中实际的线程数
		System.out.printf("Server: Pool Size: %d\n", executor.getPoolSize());

		// getActiveCount方法:返回执行器中正在执行任务的线程数
		System.out.printf("Server: Active Count: %d\n",
				executor.getActiveCount());

		//getTaskCount方法:获取已发送到执行器上的任务数
		System.out.printf("Server: Task Count: %d\n", executor.getTaskCount());

		// getCompletedTaskCount方法:返回执行器已经完成的任务数
		System.out.printf("Server: Completed Tasks: %d\n",
				executor.getCompletedTaskCount());
	}

	// 关闭执行器
	public void endServer()
	{
		// 按过去执行已提交任务的顺序发起一个有序的关闭，但是不接受新任务。如果已经关闭，则调用没有其他作用
		executor.shutdown();
	}

}
