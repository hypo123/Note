package recipe7;

import java.util.List;
import java.util.concurrent.Exchanger;

public class Consumer implements Runnable
{
	private List<String> buffer;

	private final Exchanger<List<String>> exchanger;

	public Consumer(List<String> buffer, Exchanger<List<String>> exchanger)
	{
		this.buffer = buffer;
		this.exchanger = exchanger;
	}

	@Override
	public void run()
	{
		int cycle = 1;

		for (int i = 0; i < 10; i++)
		{
			System.out.printf("Consumer: Cycle %d\n", cycle);
			
			try
			{
				//等待生产者线程到达同步点,如果生产者没有到达则消费者线程进入休眠状态
				//直到生产者线程到达或者其它某个线程中断当前线程.
				buffer = exchanger.exchange(buffer);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}

			System.out.printf("Consumer: %d\n", buffer.size());

			//消费者消费
			for (int j = 0; j < 10; j++)
			{
				String message = buffer.get(0);
				System.out.printf("Consumer: %s\n", message);
				buffer.remove(0);
			}

			cycle++;
		}

	}

}
