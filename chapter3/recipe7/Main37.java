package recipe7;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

public class Main37
{
	public static void main(String[] args)
	{
		List<String> buffer1=new ArrayList<>();
		List<String> buffer2=new ArrayList<>();
		
		//创建Exchanger对象
		Exchanger<List<String>> exchanger=new Exchanger<>();
		
		//生产者对象
		Producer producer=new Producer(buffer1, exchanger);
		//消费者对象
		Consumer consumer=new Consumer(buffer2, exchanger);
		
		//新建生产者线程
		Thread threadProducer=new Thread(producer);
		//新建消费者线程
		Thread threadConsumer=new Thread(consumer);
		
		//启动生产者线程
		threadProducer.start();
		//启动消费者线程
		threadConsumer.start();

	}
}
