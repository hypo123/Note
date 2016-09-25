package recipe4;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

//Delayed接口:一种混合风格的接口,用来标记那些应该在给定延迟时间之后执行的对象
//Delayed接口继承了Comparable接口
public class Event implements Delayed
{
	private Date startDate;//激活事件时间

	public Event(Date startDate)
	{
		this.startDate = startDate;
	}

	@Override
	public int compareTo(Delayed o)
	{
		//getDelay方法:返回与此对象相关的剩余延迟时间
		long result = this.getDelay(TimeUnit.NANOSECONDS)
				- o.getDelay(TimeUnit.NANOSECONDS);
		
		if (result < 0)
		{
			return -1;
		}
		else if (result > 0)
		{
			return 1;
		}
		return 0;
	}

	@Override
	public long getDelay(TimeUnit unit)//该方法返回到激活日期的剩余时间
	{
		Date now = new Date();
		
		long diff = startDate.getTime() - now.getTime();
		
		return unit.convert(diff, TimeUnit.MILLISECONDS);
	}

}
