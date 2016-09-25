package recipe7;

import java.util.List;
import java.util.concurrent.Exchanger;

public class Producer implements Runnable
{
	private List<String> buffer;

	private final Exchanger<List<String>> exchanger;

	public Producer(List<String> buffer, Exchanger<List<String>> exchanger)
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
			System.out.printf("Producer: Cycle %d\n", cycle);

			//生产者生产
			for (int j = 0; j < 10; j++)
			{
				String message = "Event " + ((i * 10) + j);
				System.out.printf("Producer: %s\n", message);
				buffer.add(message);
			}

			try
			{
				//等待另一个线程到达此交换点(除非当前线程被中断)
				//然后将给定的对象传送给该线程,并接受该线程的对象.
				//exchange中的参数是本线程要交换的对象,exchange的返回值是另一个线程提供的对象.
				buffer = exchanger.exchange(buffer);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}

			System.out.printf("Producer: %d\n", buffer.size());

			cycle++;
		}

	}

}
