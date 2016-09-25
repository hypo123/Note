package recipe1;

public class Cinema
{
	//使用对象controlCinema1来控制对vacanciesCinema1属性的访问,所以同一时间
	//只有一个线程能够修改这个属性
	private long vacanciesCinema1;
	
	//使用对象controlCinema2来控制对vacanciesCinema2属性的访问,所以同一时间
	//只有一个线程能够修改这个属性	
	private long vacanciesCinema2;
	
	//但是，本例中允许同时运行两个线程:一个修改vacanciesCinema1属性，
	//另一个修改vacanciesCinema2属性;
	
	private final Object controlCinema1, controlCinema2;
	
	public Cinema()
	{
		controlCinema1 = new Object();
		controlCinema2 = new Object();
		
		vacanciesCinema1 = 20;
		vacanciesCinema2 = 20;
	}
	
	//第一电影院卖票
	public boolean sellTickets1(int number)
	{
		synchronized(controlCinema1)
		{
			if(number < vacanciesCinema1)
			{
				vacanciesCinema1 -= number;
				return true;
			}
			else
			{
				return false;
			}
		}
	}
	
	//第二电影院卖票
	public boolean sellTickets2(int number)
	{
		synchronized(controlCinema2)
		{
			if(number < vacanciesCinema2)
			{
				vacanciesCinema2 -= number;
				return true;
			}
			else
			{
				return false;
			}
		}
	}
	
	//第一电影院退票
	public boolean returnTickets1(int number)
	{
		synchronized(controlCinema1)
		{
			vacanciesCinema1 += number;
			return true;
		}
	}

	//第二电影院退票
	public boolean returnTickets2(int number)
	{
		synchronized(controlCinema2)
		{
			vacanciesCinema2 += number;
			return true;
		}
	}
	
	//第一电影院剩余票数
	public long getVacanciesCinema1()
	{
		return vacanciesCinema1;
	}

	//第二电影院剩余票数
	public long getVacanciesCinema2()
	{
		return vacanciesCinema2;
	}
	
}
