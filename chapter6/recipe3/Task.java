package recipe3;

import java.util.concurrent.PriorityBlockingQueue;

public class Task implements Runnable
{
	private int id;

	//优先级阻塞队列
	private PriorityBlockingQueue<Event> queue;

	public Task(int id, PriorityBlockingQueue<Event> queue)
	{
		this.id = id;
		this.queue = queue;
	}

	@Override
	public void run()
	{
		for (int i = 0; i < 1000; i++)
		{
			Event event = new Event(id, i);
			
			//所有添加进PriorityBlockingQueue的元素必须实现Comparable接口
			queue.add(event);
		}
	}
}
