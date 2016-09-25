package recipe2;

import java.util.Date;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class Main62
{
	public static void main(String[] args) throws Exception
	{

		LinkedBlockingDeque<String> list = new LinkedBlockingDeque<>(3);

		//新建并启动线程
		Client client = new Client(list);
		Thread thread = new Thread(client);
		thread.start();

		for (int i = 0; i < 5; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				// 获取并移除此双端队列表示的队列的头部(即此双端队列的第一个元素),必要时将一直等待可用元素
				String request = list.take();
				
				System.out.printf("Main: Request: %s at %s. Size: %d\n",
						request, new Date(), list.size());
			}
			
			//主线程休眠300ms
			TimeUnit.MILLISECONDS.sleep(300);
		}

		System.out.printf("Main: End of the program.\n");

	}
}
