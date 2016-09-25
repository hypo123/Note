package chapter1;

import java.util.concurrent.TimeUnit;

public class PrimGenerator extends Thread
{
	//判断是否为素数
	private boolean isPrime(long number)
	{
		if(number <= 2)
		{
			return true;
		}
		
		for(long i = 2 ; i < number ; i++)
		{
			if(number % 2 == 0)
			{
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void run()
	{
		long number = 1L;
		
		while(true)
		{
			if(isPrime(number))
			{
				System.out.printf("Number %d is Prime\n",number);
			}
			
			//Thread类的静态方法interrupted测试当前线程是否已经中断
			if(isInterrupted())
			{
				System.out.printf("The Prime Generator has been Interrupted\n");
				
				return;
			}
			number++;
		}
	}
	
	public static void main(String[] args)
	{
		Thread task = new PrimGenerator();
		
		//使该线程开始执行；Java 虚拟机调用该线程的 run 方法
		task.start();
		
		try
		{
			//主线程中断5s
			TimeUnit.SECONDS.sleep(5);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		
		//主线程中断5s后，中断PrimeGenerator线程
		task.interrupt();
	}
}
