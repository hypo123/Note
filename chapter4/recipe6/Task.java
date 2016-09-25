package recipe6;

import java.util.Date;
import java.util.concurrent.Callable;

public class Task implements Callable<String>
{
	private String name;

	public Task(String name)
	{
		this.name = name;
	}

	@Override
	public String call() throws Exception
	{
		//打印任务启动时间
		System.out.printf("%s: Starting at : %s\n", name, new Date());
		return "Hello, world";
	}
}
