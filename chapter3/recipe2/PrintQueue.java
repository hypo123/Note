package recipe2;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintQueue
{
	private Semaphore semaphore;//信号量

	private boolean freePrinters[];//存放打印机状态

	private Lock lockPrinters;//显式锁,保护对freePrinters数组的访问

	public PrintQueue()
	{
		//创建有3个许可数的信号量对象,共有3台打印机
		semaphore = new Semaphore(3);
		
		freePrinters = new boolean[3];

		//初始化3台打印机为空闲可用状态
		for (int i = 0; i < 3; i++)
		{
			freePrinters[i] = true;
		}
		
		//显式锁
		lockPrinters = new ReentrantLock();
	}

	public void printJob(Object document)
	{
		try
		{
			//从信号量获取一个许可
			semaphore.acquire();

			//获取空闲打印机编号
			int assignedPrinter = getPrinter();

			Long duration = (long) (Math.random() * 10);
			
			System.out
					.printf("%s: PrintQueue: Printing a Job in Printer %d during %d seconds\n",
							Thread.currentThread().getName(), assignedPrinter,
							duration);
			
			//休眠,模拟正在打印
			TimeUnit.SECONDS.sleep(duration);

			//打印完成,将该打印机标记为空闲
			freePrinters[assignedPrinter] = true;
			
		} 
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		finally
		{
			//释放许可
			semaphore.release();
		}
	}

	//分配打印机
	private int getPrinter()
	{
		int ret = -1;

		try
		{
			lockPrinters.lock();//加锁

			for (int i = 0; i < freePrinters.length; i++)
			{
				if (freePrinters[i])
				{
					ret = i;
					freePrinters[i] = false;
					break;
				}
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			lockPrinters.unlock();//释放锁
		}
		return ret;
	}

}
