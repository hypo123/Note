package recipe1;

public class Company implements Runnable
{
	private Account account;
	
	public Company(Account account)
	{
		this.account = account;
	}
	
	@Override
	public void run()
	{
		//存钱100次，每次1000
		for(int i = 0 ; i < 100 ; i++)
		{
			account.addAmount(1000);
		}
	}
}
