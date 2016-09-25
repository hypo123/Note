package recipe2;

public class Main3
{
	public static void main(String[] args)
	{
		PrintQueue printQueue = new PrintQueue();

		Thread thread[] = new Thread[12];
		
		//创建12线程对象
		for (int i = 0; i < 12; i++)
		{
			thread[i] = new Thread(new Job(printQueue), "Thread " + i);
		}

		//启动12个新线程
		for (int i = 0; i < 12; i++)
		{
			thread[i].start();
		}
	}
}
