package recipe5;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class FileSearch implements Runnable
{
	private String initPath;//查找的起始路径(文件夹)

	private String end;//要查找文件的扩展名

	private List<String> results;//存储查找到的文件的完整路径

	//控制任务同步阶段的同步.
	private Phaser phaser;

	public FileSearch(String initPath, String end, Phaser phaser)
	{
		this.initPath = initPath;
		this.end = end;
		this.phaser = phaser;
		results = new ArrayList<>();
	}

	@Override
	public void run()
	{
		//自己已完成,等待其他参与者完成;进入阻塞,直到Phaser进入到下一阶段.
		phaser.arriveAndAwaitAdvance();

		System.out.printf("%s: Starting.\n", Thread.currentThread().getName());

		//阶段1:查找文件,扩展名要和目标扩展名一致
		File file = new File(initPath);
		
		if (file.isDirectory())//是目录
		{
			directoryProcess(file);
		}

		if (!checkResults())//Phaser等待
		{
			return;
		}

		//阶段2:对结果进行过滤,在过去24小时内修改过
		filterResults();

		if (!checkResults())//Phaser等待
		{
			return;
		}

		//阶段3:打印过滤后结果信息
		showInfo();//Phaser等待
		
		//任务完成,取消在phaser中的注册
		phaser.arriveAndDeregister();
		
		System.out.printf("%s: Work completed.\n", Thread.currentThread()
				.getName());

	}

	//打印所查找到的所有文件的完整路径
	private void showInfo()
	{
		for (int i = 0; i < results.size(); i++)
		{
			File file = new File(results.get(i));
			System.out.printf("%s: %s\n", Thread.currentThread().getName(),
					file.getAbsolutePath());
		}
		
		phaser.arriveAndAwaitAdvance();
	}

	//第一个阶段和第二阶段结束时被调用,用来检查结果集是否为空
	private boolean checkResults()
	{
		//如果第一或第二阶段查找结果为空,则不用参与到下一个阶段了.
		if (results.isEmpty())
		{
			System.out.printf("%s: Phase %d: 0 results.\n", Thread
					.currentThread().getName(), phaser.getPhase());
			
			System.out.printf("%s: Phase %d: End.\n", Thread.currentThread()
					.getName(), phaser.getPhase());

			//任务已完成,取消在phaser中的注册
			phaser.arriveAndDeregister();
			
			return false;
		}
		else
		{
			System.out.printf("%s: Phase %d: %d results.\n", Thread
					.currentThread().getName(), phaser.getPhase(), results
					.size());
			
			//结果集不为空,则等待其它参与者完成这一阶段,一起进入下一阶段.
			phaser.arriveAndAwaitAdvance();
			
			return true;
		}
	}

	//过滤查找到的所有文件
	private void filterResults()
	{
		List<String> newResults = new ArrayList<>();
		long actualDate = new Date().getTime();
		
		for (int i = 0; i < results.size(); i++)
		{
			File file = new File(results.get(i));
			
			long fileDate = file.lastModified();

			if (actualDate - fileDate < TimeUnit.MILLISECONDS.convert(1,
					TimeUnit.DAYS))
			{
				newResults.add(results.get(i));
			}
		}
		results = newResults;
	}

	//处理目录
	private void directoryProcess(File file)
	{
		//file目录下的所有文件(文件或目录)
		File list[] = file.listFiles();
		
		if (list != null)
		{
			for (int i = 0; i < list.length; i++)
			{
				if (list[i].isDirectory())
				{
					//是目录,递归处理
					directoryProcess(list[i]);
				}
				else
				{
					//是文件,检查扩展名
					fileProcess(list[i]);
				}
			}
		}
	}

	//检查传入的文件的扩展名是否为我们所指定的扩展名
	private void fileProcess(File file)
	{
		if (file.getName().endsWith(end))
		{
			results.add(file.getAbsolutePath());
		}
	}

}
