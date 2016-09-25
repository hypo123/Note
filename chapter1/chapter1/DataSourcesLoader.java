package chapter1;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DataSourcesLoader implements Runnable
{
	@Override
	public void run()
	{
		System.out.printf("Begining data sources loading: %s\n",new Date());
		
		try
		{
			//thread1休眠4s
			TimeUnit.SECONDS.sleep(4);	
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		System.out.printf("Data sources loading has finished: %s\n",new Date());
	}
	
	public static void main(String[] args)
	{
		DataSourcesLoader dsloader = new DataSourcesLoader();
		Thread thread1 = new Thread(dsloader);
		//启动线程thread1
		thread1.start();
		
		NetworkConnectionsLoader ncloader = new NetworkConnectionsLoader();
		Thread thread2 = new Thread(ncloader);
		//启动线程thread2
		thread2.start();
		
		try
		{
			thread1.join();//主线程等待thread1线程终止
			thread2.join();//主线程等待thread2线程终止
			
			//以上两行的作用是，主线程要等子线程thread1和子线程thread2都终止了才会继续执行.
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		
		System.out.printf("Main: Configuration has been loaded: %s\n",new Date());
	}
}

class NetworkConnectionsLoader implements Runnable
{
	@Override
	public void run()
	{
		System.out.printf("Begining network connections loading: %s\n",new Date());
		
		try
		{
			//thread2休眠6s
			TimeUnit.SECONDS.sleep(6);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		
		System.out.printf("Network connections loading has finished: %s\n",new Date());
	}
}


