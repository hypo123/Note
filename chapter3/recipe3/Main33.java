package recipe3;

public class Main33
{
	public static void main(String[] args)
	{
		Videoconference conference = new Videoconference(10);

		Thread threadConference = new Thread(conference);
		
		//启动会议线程
		threadConference.start();

		//创建10个参会者线程，并启动
		for (int i = 0; i < 10; i++)
		{
			Participant p = new Participant(conference, "Participant " + i);
			Thread t = new Thread(p);
			t.start();
		}
	}
}
