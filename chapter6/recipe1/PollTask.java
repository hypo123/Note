package recipe1;

import java.util.concurrent.ConcurrentLinkedDeque;

public class PollTask implements Runnable
{
	//非阻塞双端队列,链表实现
	private ConcurrentLinkedDeque<String> list;
	
	public PollTask(ConcurrentLinkedDeque<String> list)
	{
		this.list = list;
	}
	
	@Override
	public void run()
	{
		for(int i = 0 ; i < 5000 ; ++i)
		{
			list.pollFirst();//移除队列的首元素
			list.pollLast();//移除队列的末尾元素
		}
	}
}
