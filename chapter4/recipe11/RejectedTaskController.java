package recipe11;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

//实现 无法由 ThreadPoolExecutor执行的任务的处理程序(接口)
public class RejectedTaskController implements RejectedExecutionHandler
{
	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor)
	{
		System.out.printf("RejectedTaskController: The task %s has been rejected\n",
				r.toString());
		System.out.printf("RejectedTaskController: %s\n", executor.toString());
		System.out.printf("RejectedTaskController: Terminating: %s\n",
				executor.isTerminating());
		System.out.printf("RejectedTaksController: Terminated: %s\n",
				executor.isTerminated());
	}
}
