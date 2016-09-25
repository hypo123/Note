package recipe2;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class EventStorage
{
	private int maxSize;
	
	private List<Date> storage;
	
	public EventStorage()
	{
		maxSize = 10;
		storage = new LinkedList<>();
	}
	
	public synchronized void set()
	{
		//队列已满,不可再生产.
		while(storage.size() == maxSize)
		{
			try
			{
				//当一个线程调用wait方法，JVM将这个线程置入休眠，并且释放控制这个同步代码块的对象，
				//同时允许其他线程执行这个对象控制的其他同步代码块.
				wait();//休眠Producer的线程,不可再生产.
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
		storage.add(new Date());//从队尾插入
		System.out.printf("Set: %d\n",storage.size());
		
		//唤醒Consumer的线程,来消费
		notifyAll();
	}
	
	public synchronized void get()
	{
		//队列已空,不可再消费
		while(storage.size() == 0)
		{
			try
			{
				wait();//休眠Consumer的线程.不可再消费
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
		//poll()获取并移除此列表的头(第一个元素)
		System.out.printf("Get: %d: %s\n",storage.size(),((LinkedList<?>)storage).poll());
		
		//唤醒Producer的线程，可生产.
		notifyAll();
	}
}


