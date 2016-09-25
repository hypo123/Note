package recipe4;

import java.util.concurrent.CyclicBarrier;

public class Main34
{
	public static void main(String[] args)
	{
		final int ROWS = 10000;//矩阵行数
		final int NUMBERS = 1000;//矩阵列数
		final int SEARCH = 5;//查找目标数字
		final int PARTICIPANTS = 5;//查找线程数
		final int LINES_PARTICIPANT = 2000;//每个线程需查找的行数
		
		MatrixMock mock = new MatrixMock(ROWS, NUMBERS, SEARCH);

		Results results = new Results(ROWS);

		Grouper grouper = new Grouper(results);

		//屏障拦截的线程个数为PARTICIPANTS, 所有屏障等待线程到达屏障后,优先执行grouper线程.
		CyclicBarrier barrier = new CyclicBarrier(PARTICIPANTS, grouper);

		Searcher searchers[] = new Searcher[PARTICIPANTS];
		Thread[] thread = new Thread[PARTICIPANTS];
		
		//创建5个查找线程,并启动
		for (int i = 0; i < PARTICIPANTS; i++)
		{
			searchers[i] = new Searcher(i * LINES_PARTICIPANT,
					(i * LINES_PARTICIPANT) + LINES_PARTICIPANT, mock, results,
					5, barrier);
			
			thread[i] = new Thread(searchers[i]);
			
			thread[i].start();
		}
		
		//主线程等待查找线程执行完.
		for(int i = 0 ; i < PARTICIPANTS ; i++)
		{
			try
			{
				thread[i].join();
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.printf("Main: The main thread has finished.\n");
	}
}
