package chapter1;

import java.util.Date;
import java.util.Deque;

public class CleanerTask extends Thread
{
	private Deque<Event> deque;
	
	public CleanerTask(Deque<Event> deque)
	{
		this.deque = deque;
		
		//将该线程设置为守护线程
		setDaemon(true);
	}
	
	@Override
	public void run()
	{
		while(true)
		{
			//获取当前时间
			Date date = new Date();
			
			//调用clean方法
			clean(date);
		}
	}
	
	//clean方法读取队列的最后一个事件对象，如果这个事件对象时10秒钟前创建的，则将它删除并检查下一个
	//如果有事件被删除则打印这个被删事件的信息.
	private void clean(Date date)
	{
		long difference;
		boolean delete;
		
		if(deque.size() == 0)
		{
			return;
		}
		
		delete = false;
		
		do
		{
			Event e = deque.getLast();
			
			difference = date.getTime() - e.getDate().getTime();
			
			//队列最后一个Event对象是否是10s前创建,如果是则删除
			if(difference > 10000)
			{
				System.out.printf("Cleaner: %s\n",e.getEvent());
				
				//获取并移除此双端队列的最后一个元素
				deque.removeLast();
				
				delete = true;
			}
		}
		while(difference > 10000);
		
		if(delete)
		{
			System.out.printf("Cleaner: Size of the queue: %d\n",deque.size());
		}
	}
}
