package chapter1;

public class Calculator implements Runnable
{
	private int number;
	
	public Calculator(int number)
	{
		this.number = number;
	}
	
	@Override
	public void run()
	//run方法用来执行我们创建的线程的指令
	{
		for(int i = 1; i <= 10 ; i++)
		{
			System.out.printf("%s: %d * %d = %d\n",Thread.currentThread().getName(),number, i, i*number);
		}
	}
	
	public static void main(String[] args)
	{
		for(int i = 1 ; i <= 10 ; i++)
		{
			//Calculator是实现Runnable接口的类
			Calculator calculator = new Calculator(i);
			
			//使用带参数的Thread构造器来创建Thread对象，参数就是实现了Runnable接口的类的一个对象.
			Thread thread = new Thread(calculator);
			
			//调用Thread对象的start方法时，另一个线程将被创建.
			thread.start();
		}
	}
}






