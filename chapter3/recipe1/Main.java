package recipe1;

public class Main
{
	public static void main(String[] args)
	{
		PrintQueue printQueue = new PrintQueue();

		Thread thread[] = new Thread[10];
		
		//创建10个线程对象
		for (int i = 0; i < 10; i++)
		{
			thread[i] = new Thread(new Job(printQueue), "Thread " + i);
		}

		//启动10个新线程
		for (int i = 0; i < 10; i++)
		{
			thread[i].start();
		}
	}
}
