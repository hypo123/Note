package recipe4;

public class Writer implements Runnable
{
	private PricesInfo pricesInfo;

	public Writer(PricesInfo pricesInfo)
	{
		this.pricesInfo = pricesInfo;
	}

	@Override
	public void run()
	{
		//循环修改两个价格3次
		for (int i = 0; i < 3; i++)
		{
			System.out.printf("Writer: Attempt to modify the prices.\n");
			
			//修改价格
			pricesInfo.setPrices(Math.random() * 10, Math.random() * 8);
			
			System.out.printf("Writer: Prices have been modified.\n");
			
			try
			{
				//每次修改后线程将休眠2秒钟
				Thread.sleep(2);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
