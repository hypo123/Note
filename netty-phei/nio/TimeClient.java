package nio;

public class TimeClient
{
	public static void main(String[] args)
	{
		int port = 10002;
		
		new Thread(new TimeClientHandler("127.0.0.1", port), "TimeClient-001").start();
	}
}
