package chapter1;

public class MyThreadGroup extends ThreadGroup
{
	//必须声明带参数的构造函数，因为ThreadGroup没有默认的不带参数的构造函数.
	public MyThreadGroup(String name)
	{
		super(name);
	}
	
	@Override
	public void uncaughtException(Thread t, Throwable e)
	//覆盖uncaughtException方法，当线程组中的任何线程对象抛出异常的时候，这个方法就会被调用.
	{
		// 打印线程id
		System.out.printf("The thread %s has thrown an Exception\n",t.getId());

		//打印异常堆栈信息
		e.printStackTrace(System.out);
		
		System.out.printf("Terminating the rest of the Threads\n");
		//中断线程组中的剩余线程
		interrupt();
	}
	
	public static void main(String[] args)
	{
		MyThreadGroup threadGroup = new MyThreadGroup("MyThreadGroup");
		
		SmallTask task = new SmallTask();
		
		//创建2个线程对象.
		for(int i = 0 ; i < 2 ; i++)
		{
			//加入到线程组
			Thread t = new Thread(threadGroup,task);
			//启动新线程
			t.start();
		}
	}
}
