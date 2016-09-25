package recipe4;

import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

public class Main64
{
	public static void main(String[] args) throws Exception
	{
		//阻塞队列
		//存放在Delay
		DelayQueue<Event> queue = new DelayQueue<>();

		Thread threads[] = new Thread[5];

		//新建5个任务和5个线程
		for (int i = 0; i < threads.length; i++)
		{
			Task task = new Task(i + 1, queue);
			threads[i] = new Thread(task);
		}

		//启动5个新线程
		for (int i = 0; i < threads.length; i++)
		{
			threads[i].start();
		}

		//主线程等待5个新线程执行完毕
		for (int i = 0; i < threads.length; i++)
		{
			threads[i].join();
		}

		do
		{
			int counter = 0;
			Event event;
			
			do
			{
				event = queue.poll();
				
				if (event != null)
				{
					counter++;
				}
			} while (event != null);
			
			System.out.printf("At %s you have read %d events\n", new Date(),
					counter);
			
			TimeUnit.MILLISECONDS.sleep(500);
			
		} while (queue.size() > 0);
	}
}
