package recipe3;

import java.util.concurrent.TimeUnit;

public class Participant implements Runnable
{
	private Videoconference conference;

	private String name;

	public Participant(Videoconference conference, String name)
	{
		this.conference = conference;
		this.name = name;
	}

	@Override
	public void run()
	{
		Long duration = (long) (Math.random() * 10);
		try
		{
			//休眠
			TimeUnit.SECONDS.sleep(duration);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		//一位参会人到达,闭锁等待数减一
		conference.arrive(name);
	}
}
