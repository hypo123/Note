package recipe1;

public class Account
{
	private double balance;
	
	public double getBalance()
	{
		return balance;
	}
	
	public void setBalance(double balance)
	{
		this.balance = balance;
	}
	
	//存钱
	public synchronized void addAmount(double amount)
	{
		double tmp = balance;
		
		try
		{
			Thread.sleep(10);
		} 
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		tmp += amount;
		balance = tmp;
		
	}

	//取钱
	public synchronized void subtractAmount(double amount)
	{
		double tmp = balance;
		try
		{
			Thread.sleep(10);
		} 
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		tmp -= amount;
		balance = tmp;
	}
}






