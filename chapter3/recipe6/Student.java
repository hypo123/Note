package recipe6;

import java.util.Date;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class Student implements Runnable
{
	private Phaser phaser;

	public Student(Phaser phaser)
	{
		this.phaser = phaser;
	}

	public void run()
	{
		System.out.printf("%s: Has arrived to do the exam. %s\n", Thread
				.currentThread().getName(), new Date());
		
		//自己已完成,等待其他参与者到达;进入阻塞,直到Phaser进入到下一阶段.
		phaser.arriveAndAwaitAdvance();
		
		System.out.printf("%s: Is going to do the first exercise. %s\n", Thread
				.currentThread().getName(), new Date());
		
		doExercise1();
		
		System.out.printf("%s: Has done the first exercise. %s\n", Thread
				.currentThread().getName(), new Date());
		
		//自己已完成练习1,等待其他参与者完成练习1;进入阻塞,直到Phaser进入到下一阶段.
		phaser.arriveAndAwaitAdvance();
		
		System.out.printf("%s: Is going to do the second exercise. %s\n",
				Thread.currentThread().getName(), new Date());
		
		doExercise2();
		
		System.out.printf("%s: Has done the second exercise. %s\n", Thread
				.currentThread().getName(), new Date());
		
		//自己已完成练习2,等待其他参与者完成练习2;进入阻塞,直到Phaser进入到下一阶段.		
		phaser.arriveAndAwaitAdvance();
		
		System.out.printf("%s: Is going to do the third exercise. %s\n", Thread
				.currentThread().getName(), new Date());
		
		doExercise3();
		
		System.out.printf("%s: Has finished the exam. %s\n", Thread
				.currentThread().getName(), new Date());
		
		//自己已完成练习3,等待其他参与者完成练习3;进入阻塞,直到Phaser进入到下一阶段.		
		phaser.arriveAndAwaitAdvance();
	}

	//模拟做练习1
	private void doExercise1()
	{
		try
		{
			Long duration = (long) (Math.random() * 10);
			TimeUnit.SECONDS.sleep(duration);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	//模拟做练习2	
	private void doExercise2()
	{
		try
		{
			Long duration = (long) (Math.random() * 10);
			TimeUnit.SECONDS.sleep(duration);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	//模拟做练习3
	private void doExercise3()
	{
		try
		{
			Long duration = (long) (Math.random() * 10);
			TimeUnit.SECONDS.sleep(duration);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
