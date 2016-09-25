package recipe10;

import java.util.concurrent.CompletionService;

//模拟请求获取报告类
public class ReportRequest implements Runnable
{
	private String name;

	private CompletionService<String> service;//完成服务接口引用

	public ReportRequest(String name, CompletionService<String> service)
	{
		this.name = name;
		this.service = service;
	}

	@Override
	public void run()
	{
		//创建生产报告类对象来生成报告
		ReportGenerator reportGenerator = new ReportGenerator(name, "Report");
		
		//提交要执行的 Runnable任务,并返回一个表示任务完成的Future
		service.submit(reportGenerator);
	}
}
