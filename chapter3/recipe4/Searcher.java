package recipe4;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 *	查找类 
 */
public class Searcher implements Runnable
{
	private int firstRow;

	private int lastRow;

	private MatrixMock mock;

	private Results results;

	private int number;

	private final CyclicBarrier barrier;

	public Searcher(int firstRow, int lastRow, MatrixMock mock,
			Results results, int number, CyclicBarrier barrier)
	{
		this.firstRow = firstRow;//起始行
		this.lastRow = lastRow;//结束行
		this.mock = mock;//矩阵
		this.results = results;
		this.number = number;//目标数字
		this.barrier = barrier;//屏障
	}

	@Override
	public void run()
	{
		int counter;//每行查找到目标的个数
		
		System.out.printf("%s: Processing lines from %d to %d.\n", Thread
				.currentThread().getName(), firstRow, lastRow);
		
		for (int i = firstRow; i < lastRow; i++)
		{
			int row[] = mock.getRow(i);
			
			counter = 0;
			
			for (int j = 0; j < row.length; j++)
			{
				if (row[j] == number)
				{
					counter++;
				}
			}
			
			//第i行一共查找到couter个目标
			results.setData(i, counter);
		}
		
		System.out.printf("%s: Lines processed.\n", Thread.currentThread()
				.getName());
		
		try
		{
			//一个线程查找完,到达屏障;如果不是最后一个线程到达,则该线程阻塞,等待所有其他线程到达.
			barrier.await();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		catch (BrokenBarrierException e)
		{
			e.printStackTrace();
		}
	}

}
