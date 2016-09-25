package recipe10;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main410
{
	public static void main(String[] args)
	{
		//创建执行器
		ExecutorService executor = (ExecutorService) Executors
				.newCachedThreadPool();
		
		//创建完成服务类对象
		//ExecutorCompletionService类是CompletionService接口的实现类
		//ExecutorCompletionService内部仍使用Executor对象来执行任务
		CompletionService<String> service = new ExecutorCompletionService<>(
				executor);

		//创建两个ReportRequest对象
		ReportRequest faceRequest = new ReportRequest("Face", service);
		ReportRequest onlineRequest = new ReportRequest("Online", service);
		
		//创建两个请求报告线程
		Thread faceThread = new Thread(faceRequest);
		Thread onlineThread = new Thread(onlineRequest);

		//创建一个ReportProcessor对象
		ReportProcessor processor = new ReportProcessor(service);
		//创建一个处理报告线程
		Thread senderThread = new Thread(processor);

		System.out.printf("Main: Starting the Threads\n");
		
		faceThread.start();//启动请求报告线程
		onlineThread.start();//启动请求报告线程
		senderThread.start();//启动处理报告线程

		try
		{
			System.out.printf("Main: Waiting for the report generators.\n");
			
			//主线程等待faceThread线程和onlineThread线程执行完成
			faceThread.join();
			onlineThread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		System.out.printf("Main: Shuting down the executor.\n");
		//关闭执行器
		executor.shutdown();
		
		try
		{
			//等待所有任务完成
			executor.awaitTermination(1, TimeUnit.DAYS);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		//设置end为true,结束ReportSender
		processor.setEnd(true);
		
		System.out.printf("Main: Ends\n");

	}
}
