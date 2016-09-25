package chapter1;

import java.util.ArrayDeque;
import java.util.Deque;

public class EventMain
{
	public static void main(String[] args)
	{
		//ArrayDeque底层由数组实现
		Deque<Event> deque = new ArrayDeque<Event>();
		
		WriterTask writer = new WriterTask(deque);
		
		//创建3个WriterTask线程，并启动
		for(int i = 0 ; i < 3 ; i++)
		{
			Thread thread = new Thread(writer);
			thread.start();
		}
		
		//创建一个CleanerTask守护线程，并启动
		//在3个WriterTask线程都休眠后，CleanerTask线程才开始执行.
		CleanerTask cleaner = new CleanerTask(deque);
		cleaner.start();
	}
}
