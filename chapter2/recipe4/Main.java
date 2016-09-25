package recipe4;

public class Main
{
	public static void main(String[] args)
	{
		PricesInfo pricesInfo = new PricesInfo();

		Reader readers[] = new Reader[5];
		Thread threadsReader[] = new Thread[5];
		
		//创建5个读取类线程
		for (int i = 0; i < 5; i++)
		{
			readers[i] = new Reader(pricesInfo);
			threadsReader[i] = new Thread(readers[i]);
		}
		
		//创建1个写入类线程
		Writer writer = new Writer(pricesInfo);
		Thread threadWriter = new Thread(writer);

		//启动5个读线程
		for (int i = 0; i < 5; i++)
		{
			threadsReader[i].start();
		}

		//启动1个写线程
		threadWriter.start();
	}
}
