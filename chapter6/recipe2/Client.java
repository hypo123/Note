package recipe2;

import java.util.Date;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class Client implements Runnable
{
	//阻塞式双端队列，链表实现
	private LinkedBlockingDeque<String> requestList;

	public Client(LinkedBlockingDeque<String> requestList)
	{
		this.requestList = requestList;
	}

	@Override
	public void run()
	{
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 5; j++)
			{
				StringBuilder request = new StringBuilder();
				request.append(i);
				request.append(":");
				request.append(j);
				
				try
				{
					//将指定的元素插入此双端队列表示的队列中(即此双端队列的尾部),必要时将一直等待可用空间
					requestList.put(request.toString());
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				
				System.out.printf("Client: %s at %s.\n", request, new Date());
			}
			try
			{
				//休眠2ms
				TimeUnit.SECONDS.sleep(2);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
		System.out.printf("Client: End.\n");
	}
}
