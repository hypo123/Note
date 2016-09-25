package recipe4;

public class Reader implements Runnable
{
	private PricesInfo pricesInfo;

	public Reader(PricesInfo pricesInfo)
	{
		this.pricesInfo = pricesInfo;
	}

	@Override
	public void run()
	{
		//循环读取2个价格5次
		for (int i = 0; i < 10; i++)
		{
			System.out.printf("%s: Price 1: %f\n", Thread.currentThread()
					.getName(), pricesInfo.getPrice1());
			
			System.out.printf("%s: Price 2: %f\n", Thread.currentThread()
					.getName(), pricesInfo.getPrice2());
		}
	}
}
