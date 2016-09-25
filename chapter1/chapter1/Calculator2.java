package chapter1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.State;

public class Calculator2 implements Runnable
{
	private int number;
	
	public Calculator2(int number)
	{
		this.number = number;
	}
	
	@Override
	public void run()
	{
		for (int i=1; i<=10; i++)
		{
			System.out.printf("%s: %d * %d = %d\n",Thread.currentThread().getName(),number,i,i*number);
		}
	}
	
	//用来写下线程的ID、名称、优先级、旧的状态和新的状态
	private static void writeThreadInfo(PrintWriter pw, Thread thread, State state) 
	{
		pw.printf("Main : Id %d - %s \n",thread.getId(),thread.getName());
		pw.println();//换行
		pw.printf("Main : Priority: %d \n",thread.getPriority());
		pw.println();
		pw.printf("Main : Old State: %s \n",state);
		pw.println();
		pw.printf("Main : New State: %s \n",thread.getState());
		pw.println();
		pw.printf("Main : ************************************\n");
		pw.println();
		
		//写入后注意要刷新一下
		pw.flush();
	}
	
	public static void main(String[] args)
	{
		System.out.printf("Minimum Priority: %s\n",Thread.MIN_PRIORITY);
		System.out.printf("Normal Priority: %s\n",Thread.NORM_PRIORITY);
		System.out.printf("Maximun Priority: %s\n",Thread.MAX_PRIORITY);
		
		Thread threads[];
		Thread.State status[];//State为Thread类的嵌套类,为线程状态类
	
		threads = new Thread[10];
		status = new Thread.State[10];
		
		for(int i = 0 ; i < 10 ; i++)
		{
			threads[i] = new Thread(new Calculator2(i));
			
			if((i % 2 == 0))
			{
				//数组中偶数下标的线程设置为最高优先级
				threads[i].setPriority(Thread.MAX_PRIORITY);
			}
			else
			{
				//数组中奇数下标的线程设置为最高优先级
				threads[i].setPriority(Thread.MIN_PRIORITY);
			}
			
			threads[i].setName("Thread " + i);
		}
		
		try
		{
			FileWriter file = new FileWriter(".\\data\\log.txt");
			PrintWriter pw = new PrintWriter(file);
			
			//把10个线程的状态写入到文件中，现在线程的状态都是NEW
			for(int i = 0 ; i < 10 ; i++)
			{
				pw.println("Main: Status of Thread " + i + " : " + threads[i].getState());
				
				//写入后注意要刷新一下，不刷新则log文件中没有数据
				pw.flush();
				
				status[i] = threads[i].getState();
			}
			
			//开始执行10个线程
			for(int i = 0 ; i < 10 ; i++)
			{
				threads[i].start();
			}
			
			//finish标识10个线程都运行完成
			boolean finish = false;
			
			while(!finish)
			{
				for(int i = 0 ; i < 10 ; i++)
				{
					//所有任何一个线程的状态发生了变化，就将它写入到文件中
					if(threads[i].getState() != status[i])
					{
						writeThreadInfo(pw , threads[i] , status[i]);
						
						status[i] = threads[i].getState();
					}
				}
				
				finish = true;
				
				//逐一检查每个线程是否已运行完毕
				for(int i = 0 ; i < 10 ; i++)
				{
					finish = finish && (threads[i].getState() == State.TERMINATED);
				}
			}
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}

















