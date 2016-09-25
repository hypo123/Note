package recipe6;

import java.util.Random;

public class Consumer implements Runnable
{
	private Buffer buffer;

	public Consumer(Buffer buffer)
	{
		this.buffer = buffer;
	}

	@Override
	public void run()
	{
		//如果缓冲区有数据行,获取一行并处理
		while (buffer.hasPendingLines())
		{
			//从缓冲区中读出
			String line = buffer.get();
			
			processLine(line);
		}
	}

	//休眠10秒钟模拟数据行处理
	private void processLine(String line)
	{
		try
		{
			Random random = new Random();
			
			//休眠任意秒
			Thread.sleep(random.nextInt(100));
		} 
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
