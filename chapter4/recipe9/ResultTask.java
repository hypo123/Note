package recipe9;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

//FutureTask泛型为其get方法返回值类型
public class ResultTask extends FutureTask<String>
{
	private String name;

	public ResultTask(Callable<String> callable)
	{
		//调用父类构造函数
		super(callable);
		
		this.name = ((ExecutableTask) callable).getName();
	}

	@Override
	protected void done()//重写done方法
	{
		if (isCancelled())
		{
			System.out.printf("%s: Has been cancelled\n", name);
		}
		else
		{
			System.out.printf("%s: Has finished\n", name);
		}
	}
}
