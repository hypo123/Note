package recipe8;

import java.util.concurrent.Callable;

public class Task implements Callable<String>
{
	@Override
	public String call() throws Exception
	{
		while (true)
		{
			System.out.printf("Task: Test\n");
			//休眠100毫秒
			Thread.sleep(100);
		}
	}
}
