package recipe3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class Main43
{
	public static void main(String[] args)
	{
		// 新建线程执行器 可缓存线程池
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(2);

		List<Future<Integer>> resultList = new ArrayList<>();

		// 创建随机数字生成器
		Random random = new Random();

		//提交10个计算任务
		for (int i = 0; i < 10; i++)
		{
			Integer number = new Integer(random.nextInt(10));

			FactorialCalculator calculator = new FactorialCalculator(number);

			// 调用执行器submit方法发送FactorialCalculator任务给执行器
			// submit方法:提交一个返回值的任务用于执行，返回一个表示任务的未决结果的 Future
			Future<Integer> result = executor.submit(calculator);

			resultList.add(result);
		}

		do
		{
			System.out.printf("Main: Number of Completed Tasks: %d\n",
					executor.getCompletedTaskCount());

			for (int i = 0; i < resultList.size(); i++)
			{
				Future<Integer> result = resultList.get(i);

				System.out.printf("Main: Task %d: %s\n", i, result.isDone());
			}

			try
			{
				//休眠
				Thread.sleep(50);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		} while (executor.getCompletedTaskCount() < resultList.size());

		System.out.printf("Main: Results\n");

		for (int i = 0; i < resultList.size(); i++)
		{
			Future<Integer> result = resultList.get(i);

			Integer number = null;

			try
			{
				// 如果Future对象所控制的任务并未完成,那么这个方法将一直阻塞到任务完成.
				number = result.get();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			catch (ExecutionException e)
			{
				e.printStackTrace();
			}

			System.out.printf("Core: Task %d: %d\n", i, number);
		}

		// 关闭执行器
		executor.shutdown();

	}
}
