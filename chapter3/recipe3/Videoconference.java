package recipe3;

import java.util.concurrent.CountDownLatch;

/**
 *	视频会议类,等待所有参会人到达后,会议才开始.
 */
public class Videoconference implements Runnable
{
	private final CountDownLatch controller;//闭锁

	public Videoconference(int number)
	{
		//初始化闭锁,需等待的参会人数为number个
		controller = new CountDownLatch(number);
	}

	//参会人到达
	public void arrive(String name)
	{
		System.out.printf("%s has arrived.\n", name);

		//一个参会人到达,闭锁等待数减一
		controller.countDown();
		
		System.out.printf("VideoConference: Waiting for %d participants.\n",
				controller.getCount());
	}

	@Override
	public void run()
	{
		System.out.printf(
				"VideoConference: Initialization: %d participants.\n",
				controller.getCount());
		try
		{
			//使当前线程在闭锁倒计数至零之前一直等待，除非线程被中断。
			controller.await();

			System.out
					.printf("VideoConference: All the participants have come\n");
			
			System.out.printf("VideoConference: Let's start...\n");
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

}
