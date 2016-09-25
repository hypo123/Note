package recipe6;

public class Main36
{
	public static void main(String[] args)
	{
		MyPhaser phaser = new MyPhaser();

		Student students[] = new Student[5];
		
		for (int i = 0; i < students.length; i++)
		{
			students[i] = new Student(phaser);
			
			//注册到MyPhaser类的对象phaser中
			phaser.register();
		}

		Thread threads[] = new Thread[students.length];
		
		//新建5个学生线程并启动.
		for (int i = 0; i < students.length; i++)
		{
			threads[i] = new Thread(students[i], "Student " + i);
			threads[i].start();
		}

		for (int i = 0; i < threads.length; i++)
		{
			try
			{
				threads[i].join();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}

		System.out.printf("Main: The phaser has finished: %s.\n",
				phaser.isTerminated());
	}
}
