package recipe1;

import java.util.concurrent.ConcurrentLinkedDeque;

public class AddTask implements Runnable
{
	//非阻塞线程安全列表
	private ConcurrentLinkedDeque list;
	
	public AddTask(ConcurrentLinkedDeque list)
	{
		this.list = list;
	}
	
	@Override
	public void run()
	{
		String name = Thread.currentThread().getName();
		
		for(int i = 0 ; i < 10000 ; ++i)
		{
			list.add(name + ": Element " + i);//向队列尾部插入元素
		}
	}
}
