package recipe1;

public class Bank implements Runnable
{
	private Account account;
	
	public Bank(Account account)
	{
		this.account = account;
	}
	
	@Override
	public void run()
	{
		//取钱100次，每次取1000
		for(int i = 0 ; i < 100 ; i++)
		{
			account.subtractAmount(1000);
		}
	}
}
