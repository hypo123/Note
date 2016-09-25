package recipe5;

import java.util.concurrent.Phaser;

public class Main35
{
	public static void main(String[] args)
	{
		//创建Phaser对象,并指定参与阶段同步的线程是3个
		Phaser phaser = new Phaser(3);

		//创建3个FielSearch对象,它们各自查找不同的目录
		FileSearch system = new FileSearch("C:\\Windows", "log", phaser);
		FileSearch apps = new FileSearch("C:\\Program Files", "log", phaser);
		FileSearch documents = new FileSearch("C:\\Documents And Settings",
				"log", phaser);

		//创建线程并启动
		Thread systemThread = new Thread(system, "System");
		systemThread.start();

		//创建线程并启动		
		Thread appsThread = new Thread(apps, "Apps");
		appsThread.start();

		//创建线程并启动		
		Thread documentsThread = new Thread(documents, "Documents");
		documentsThread.start();
		
		
		try
		{
			//主线程等待3个查找线程结束
			systemThread.join();
			appsThread.join();
			documentsThread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		//当Phaser的对象不存在参与同步的线程时,是终止状态的,isTerminated方法将返回true;
		System.out.printf("Terminated: %s\n", phaser.isTerminated());

	}
}
