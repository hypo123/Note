package recipe4;

import java.util.Date;
import java.util.concurrent.DelayQueue;

public class Task implements Runnable
{
	private int id;

	private DelayQueue<Event> queue;

	public Task(int id, DelayQueue<Event> queue)
	{
		this.id = id;
		this.queue = queue;
	}

	@Override
	public void run()
	{

		Date now = new Date();
		Date delay = new Date();
		
		delay.setTime(now.getTime() + (id * 1000));

		System.out.printf("Thread %s: %s\n", id, delay);

		for (int i = 0; i < 100; i++)
		{
			//新建Event对象,已设置延迟时间
			Event event = new Event(delay);
			
			//将Event插入到延迟队列
			queue.add(event);
		}
	}
}
