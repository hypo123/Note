package recipe1;

import java.util.concurrent.Semaphore;

public class PrintQueue
{
	private final Semaphore semaphore;//声明信号量引用

	public PrintQueue()
	{
		//创建具有1个许可数的非公平Semaphore对象
		//二进制信号量
		semaphore = new Semaphore(1);
	}

	public void printJob(Object document)
	{
		try
		{
			//从信号量获取一个许可,在获取到一个许可前线程将一直阻塞(或被中断).
			semaphore.acquire();

			Long duration = (long) (Math.random() * 10);
			
			System.out.printf(
					"%s: PrintQueue: Printing a Job during %d seconds\n",
					Thread.currentThread().getName(), duration);
			
			//休眠
			Thread.sleep(duration);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		finally
		{
			//释放一个许可,将它返还给信号量.
			semaphore.release();
		}
	}

}
