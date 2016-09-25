package chapter1;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class FileSearch implements Runnable
{
	private String initPath;//初始文件夹

	private String fileName;//要查找的文件名称

	public FileSearch(String initPath, String fileName)
	{
		this.initPath = initPath;
		this.fileName = fileName;
	}

	@Override
	public void run()
	{
		File file = new File(initPath);

		//检查file是否是目录
		if (file.isDirectory())
		{
			//在run方法中捕获并处理InterruptedException异常
			try
			{
				directoryProcess(file);
			} 
			catch (InterruptedException e)
			{
				System.out.printf("%s: The search has been interrupted", Thread
						.currentThread().getName());
				cleanResources();
			}
		}
	}

	private void cleanResources()
	{

	}

	// 获取一个文件里的所有文件和子文件夹
	private void directoryProcess(File file) throws InterruptedException 
	{
		//回一个抽象路径名数组，这些路径名表示file目录中的文件(文件和文件夹)
		File[] list = file.listFiles();
		
		if(list != null)
		{
			for(int i = 0 ; i < list.length ; i++)
			{
				if(list[i].isDirectory())
				{
					//如果是目录，则递归处理目录
					directoryProcess(list[i]);
				}
				else
				{
					//如果是文件，则判断是否为目标文件
					fileProcess(list[i]);
				}
			}
		}
		
		//处理完所有文件和文件夹后，检查现场是不是被中断了
		if(Thread.interrupted())
		{
			//抛出异常,抛出的异常在run方法中的到了处理
			throw new InterruptedException();
		}
	}

	// 比较当前文件的文件名和要查找的文件名
	private void fileProcess(File file) throws InterruptedException
	{
		if(file.getName().equals(fileName))
		{
			System.out.printf("%s : %s\n",Thread.currentThread().getName() ,file.getAbsolutePath());
		}
		
		if(Thread.interrupted())
		{
			throw new InterruptedException();
		}
	}

	public static void main(String[] args)
	{
		FileSearch searcher = new FileSearch("D:\\", "out.txt");

		Thread thread = new Thread(searcher);
		thread.start();

		try
		{
			// 主线程中断10s
			TimeUnit.SECONDS.sleep(10);
		} 
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		// 主线程中断10s后，中断thread线程
		thread.interrupt();
	}
}
