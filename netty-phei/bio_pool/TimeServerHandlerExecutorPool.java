package bio_pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *	线程池执行器
 */
public class TimeServerHandlerExecutorPool
{
	private ExecutorService executor;
	
	/**
	 * @param maxPoolSize 池中允许的最大线程数
	 * @param queueSize 执行前用于保持任务的队列.此队列仅保持由 execute 方法提交的 Runnable 任务
	 */
	public TimeServerHandlerExecutorPool(int maxPoolSize , int queueSize)
	{
		executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), maxPoolSize,
				120L, TimeUnit.SECONDS, new ArrayBlockingQueue<java.lang.Runnable>(queueSize));
	}
	
	public void execute(java.lang.Runnable task)
	{
		executor.execute(task);
	}
}
