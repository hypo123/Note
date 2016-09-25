package recipe3;

import java.util.concurrent.PriorityBlockingQueue;

public class Main63
{
	public static void main(String[] args)
	{
		//优先级阻塞队列
		PriorityBlockingQueue<Event> queue = new PriorityBlockingQueue<>();
		
		Thread taskThreads[]=new Thread[5];

		//新建5个新线程
		for (int i = 0; i < taskThreads.length; i++)
		{
			Task task = new Task(i, queue);
			taskThreads[i] = new Thread(task);
		}

		//启动5个新线程
		for (int i = 0; i < taskThreads.length; i++)
		{
			taskThreads[i].start();
		}

		//主线程等待5个线程执行完
		for (int i = 0; i < taskThreads.length; i++)
		{
			try
			{
				taskThreads[i].join();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}

		System.out.printf("Main: Queue Size: %d\n", queue.size());
		
		for (int i = 0; i < taskThreads.length * 1000; i++)
		{
			//获取并移除此队列的头，如果此队列为空，则返回 null
			Event event = queue.poll();
			
			System.out.printf("Thread %s: Priority %d\n", event.getThread(),
					event.getPriority());
		}
		
		System.out.printf("Main: Queue Size: %d\n", queue.size());
		System.out.printf("Main: End of the program\n");
	}
}
