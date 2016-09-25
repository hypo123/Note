package recipe4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main44
{
	public static void main(String[] args)
	{
		String username = "test";
		String password = "test";

		//创建两个用户验证对象
		UserValidator ldapValidator = new UserValidator("LDAP");
		UserValidator dbValidator = new UserValidator("DataBase");

		//创建两个任务用来验证用户
		TaskValidator ldapTask = new TaskValidator(ldapValidator, username,
				password);
		TaskValidator dbTask = new TaskValidator(dbValidator, username,
				password);

		//将两个任务添加到任务列表
		List<TaskValidator> taskList = new ArrayList<>();
		taskList.add(ldapTask);
		taskList.add(dbTask);

		//创建新执行器
		ExecutorService executor = (ExecutorService) Executors
				.newCachedThreadPool();
		String result;
		try
		{
			 //执行给定的任务，如果某个任务已成功完成（也就是未抛出异常），则返回其结果。
			//一旦正常或异常返回后,则取消尚未完成的任务.如果此操作正在进行时修改了给定
			//的collection,则此方法的结果不确定.
			result = executor.invokeAny(taskList);

			System.out.printf("Main: Result: %s\n", result);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		//如果两个任务都抛出Exception异常,则invokeAny方法将抛出ExecutionException异常
		catch (ExecutionException e)
		{
			e.printStackTrace();
		}
		
		//关闭执行器
		executor.shutdown();
		System.out.printf("Main: End of the Execution\n");
	}
}
