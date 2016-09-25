package recipe3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintQueue
{
	//显示锁 ReentrantLock
	private final Lock queueLock = new ReentrantLock();
	
	public void printJob(Object document)
	{
		//显示加锁
		queueLock.lock();
		
		try
		{
			Long duration = (long)(Math.random()*10000);
			
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
			//显示释放锁
			queueLock.unlock();
		}
	}
}
