package recipe6;

public class Main
{
	public static void main(String[] args)
	{
		//创建文件类FileMock对象
		FileMock mock = new FileMock(101, 10);

		//创建缓冲区Buffer对象
		Buffer buffer = new Buffer(20);

		//创建生产者对象
		Producer producer = new Producer(mock, buffer);
		Thread threadProducer = new Thread(producer, "Producer");

		//创建消费者对象
		Consumer consumers[] = new Consumer[3];
		Thread threadConsumers[] = new Thread[3];

		for (int i = 0; i < 3; i++)
		{
			consumers[i] = new Consumer(buffer);
			threadConsumers[i] = new Thread(consumers[i], "Consumer " + i);
		}

		//启动生产者线程
		threadProducer.start();
		
		//启动消费者线程
		for (int i = 0; i < 3; i++)
		{
			threadConsumers[i].start();
		}
	}
}
