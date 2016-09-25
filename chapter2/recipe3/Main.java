package recipe3;

public class Main
{
	public static void main(String[] args)
	{
		PrintQueue printQueue = new PrintQueue();
		
		Thread[] thread = new Thread[10];
		
		//创建10个打印Job线程
		for(int i = 0 ; i < 10 ; i++)
		{
			thread[i] = new Thread(new Job(printQueue) , "Thread " + i);
		}
		
		//启动10个线程
		for(int i = 0 ; i < 10 ; i++)
		{
			thread[i].start();
		}
	}
}
