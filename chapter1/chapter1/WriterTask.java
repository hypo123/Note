package chapter1;

import java.util.Date;
import java.util.Deque;
import java.util.concurrent.TimeUnit;

public class WriterTask implements Runnable
{
	Deque<Event> deque;
	
	public WriterTask(Deque<Event> deque)
	{
		this.deque = deque;
	}
	
	@Override
	public void run()
	{
		//每次循环创建一个Event对象并加入到队列中.
		for(int i = 1 ; i < 100 ; i++)
		{
			Event event = new Event();
			event.setDate(new Date());
			event.setEvent(String.format("The thread %s has generated an event", Thread.currentThread().getId()));
			
			deque.addFirst(event);
			
			try
			{
				//休眠1s
				TimeUnit.SECONDS.sleep(1);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}


