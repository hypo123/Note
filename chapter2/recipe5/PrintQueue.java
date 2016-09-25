package recipe5;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintQueue
{
	//ReentrantLock参数为true  公平模式
	//当多个线程等待锁时，锁将选择它们中的一个来访问临界区，而且选择的是等待时间
	//最长的.
	private final Lock queueLock = new ReentrantLock(true);

	//ReentrantLock默认参数为false 非公平模式
//	private final Lock queueLock = new ReentrantLock(true);
	
	public void printJob(Object document)
	{
		queueLock.lock();//显示加锁
		
		try
		{
			Long duration = (long)(Math.random()*10000);
			
			//打印即将要休眠多少秒
			System.out.printf("%s: PrintQueue: Printing a Job during %d seconds\n",Thread.currentThread().getName(),(duration/1000));
			
			//休眠
			Thread.sleep(duration);
		} 
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		finally
		{
			queueLock.unlock();//显示释放锁
		}
		
		queueLock.lock();//显示加锁
		
		try
		{
			Long duration = (long) (Math.random() * 10000);
			System.out.printf(
					"%s: PrintQueue: Printing a Job during %d seconds\n",
					Thread.currentThread().getName(), (duration / 1000));
			
			//休眠
			Thread.sleep(duration);
		} 
		catch (InterruptedException e)
		{
			e.printStackTrace();
		} finally
		{
			queueLock.unlock();//显示释放锁
		}
		
	}
}
