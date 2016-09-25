package recipe3;

public class Event implements Comparable<Event>
{
	private int thread;//产生该事件的线程id

	private int priority;//线程优先级

	public Event(int thread, int priority)
	{
		this.thread = thread;
		this.priority = priority;
	}

	public int getThread()
	{
		return thread;
	}

	public int getPriority()
	{
		return priority;
	}

	@Override
	public int compareTo(Event e)//比较当前Event与作为参数接受的Event的优先级
	{
		//当前Event优先级更大
		if (this.priority > e.getPriority())
		{
			return -1;
		}
		//当前Event优先级更小
		else if (this.priority < e.getPriority())
		{
			return 1;
		}
		//两个Event优先级相同
		else
		{
			return 0;
		}
	}
}
