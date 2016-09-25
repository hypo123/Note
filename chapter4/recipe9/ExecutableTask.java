package recipe9;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

//Callable泛型为其call方法返回值类型
public class ExecutableTask implements Callable<String>
{
	private String name;

	public ExecutableTask(String name)
	{
		this.name = name;
	}

	@Override
	public String call() throws Exception
	{
		try
		{
			Long duration = (long) (Math.random() * 10);
			System.out.printf("%s: Waiting %d seconds for results.\n",
					this.name, duration);
			
			//休眠随意一段时间
			TimeUnit.SECONDS.sleep(duration);
		}
		catch (InterruptedException e)
		{
		}
		return "Hello, world. I'm " + name;
	}

	public String getName()
	{
		return name;
	}
}
