package recipe1;

//售票处1，只售退两个电影院的票
public class TicketOffice1 implements Runnable
{
	private Cinema cinema;
	
	public TicketOffice1(Cinema cinema)
	{
		this.cinema = cinema;
	}
	
	@Override
	public void run()
	{
		cinema.sellTickets1(3);//出售第一电影院的票
		cinema.sellTickets1(2);
		cinema.sellTickets2(2);//出售第二电影院的票
		cinema.returnTickets1(3);
		cinema.sellTickets1(5);
		cinema.sellTickets2(2);
		cinema.sellTickets2(2);
		cinema.sellTickets2(2);
	}
}
