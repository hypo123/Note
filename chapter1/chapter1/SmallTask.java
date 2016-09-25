package chapter1;

import java.util.Random;

public class SmallTask implements Runnable
{
	@Override
	public void run()
	{
		int result;
		
		Random random = new Random(Thread.currentThread().getId());
		
		while(true)
		{
			//触发AritmethicException异常:当出现异常的运算条件时，抛出此异常。例如，一个整数“除以零”时，抛出此类的一个实例
			//当随机数生成器生成0时，触发AritmethicException异常.
			result = ((int)(1000/(random.nextDouble()*1000)));
			
			System.out.printf("%s : %f\n",Thread.currentThread().getId(),result);
			
			if(Thread.currentThread().isInterrupted())
			{
				System.out.printf("%d : Interrupted\n",Thread.currentThread().getId());
				return;
			}
		}
	}
}
