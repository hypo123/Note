package chapter1;

import java.lang.Thread.UncaughtExceptionHandler;

class Task implements Runnable
{
	@Override
	public void run()
	{
		//NumberFormatException异常.
		int numero = Integer.parseInt("TTT");
	}
}

public class ExceptionHandler implements UncaughtExceptionHandler
{
	@Override
	public void uncaughtException(Thread t, Throwable e)
	{
		System.out.printf("An exception has been captured\n");
		System.out.printf("Thread: %s\n",t.getId());
		System.out.printf("Exception: %s: %s\n",e.getClass().getName(),e.getMessage());
		System.out.printf("Stack Trace: \n");
		e.printStackTrace(System.out);
		System.out.printf("Thread status: %s\n",t.getState());
	}
	
	
	public static void main(String[] args)
	{
		Task task = new Task();
		
		Thread thread = new Thread(task);
		
		//设置thread线程由于未捕获到异常而突然终止时调用的处理程序
		thread.setUncaughtExceptionHandler(new ExceptionHandler());
		
		thread.start();
		
		try
		{
			//主线程等待thread线程终止.
			thread.join();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		
		System.out.printf("Thread has finished\n");
	}
}
