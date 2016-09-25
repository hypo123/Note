package recipe10;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ReportProcessor implements Runnable
{
	private CompletionService<String> service;

	private boolean end;

	public ReportProcessor(CompletionService<String> service)
	{
		this.service = service;
		end = false;
	}

	@Override
	public void run()
	{
		//不断循环来轮询是否有新报告生产.
		while (!end)
		{
			try
			{
				//poll方法:获取已完成任务的Future,如果目前不存在这样的任务,则将等待指定的时间(如果有必要)
				Future<String> result = service.poll(20, TimeUnit.SECONDS);
				
				if (result != null)
				{
					//从返回已完成任务的Future对象获取新生产的报告
					String report = result.get();
					
					System.out.printf("ReportReceiver: Report Recived: %s\n",
							report);
				}
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			catch (ExecutionException e)
			{
				e.printStackTrace();
			}
		}

		System.out.printf("ReportSender: End\n");
	}

	public void setEnd(boolean end)
	{
		this.end = end;
	}

}
