package chapter1;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class FileClock implements Runnable
{
	@Override
	public void run()
	{
		for(int i = 0; i < 10 ; i++)
		{
			//打印当前时间
			System.out.printf("%s\n", new Date());
			
			//在run方法中捕获并处理InterruptedException异常
			try
			{
				//thread线程休眠1s
				TimeUnit.SECONDS.sleep(1);
			}
			catch(InterruptedException e)
			{
				System.out.printf("The FileClock has been interrupted");
				return;
			}
		}
	}
	
	
	public static void main(String[] args)
	{
		FileClock clock = new FileClock();
		
		Thread thread = new Thread(clock);
		//启动新线程
		thread.start();
		
		try
		{
			//主线程休眠5s
			TimeUnit.SECONDS.sleep(5);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		
		//主线程休眠5s后,中断thread线程
		thread.interrupt();
	}
}
