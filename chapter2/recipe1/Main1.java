package recipe1;

public class Main1
{
	public static void main(String[] args)
	{
		Account account = new Account();
		account.setBalance(1000);
		
		Company company = new Company(account);
		Thread companyThread = new Thread(company);
		
		Bank bank = new Bank(account);
		Thread bankThread = new Thread(bank);
		
		System.out.printf("Account : Initial Balance: %f\n",account.getBalance());
		
		companyThread.start();
		bankThread.start();
		
		try
		{
			//主线程等待以下两个线程完成
			companyThread.join();
			bankThread.join();
			
			System.out.printf("Account : Final Balance: %f\n",account.getBalance());
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
