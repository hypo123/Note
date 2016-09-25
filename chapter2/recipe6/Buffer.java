package recipe6;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

//数据缓冲类
public class Buffer
{
	private LinkedList<String> buffer;//存放共享数据

	private int maxSize;//buffer长度

	private ReentrantLock lock;//显示锁

	private Condition lines;//条件
	private Condition space;//条件

	private boolean pendingLines;//缓冲区是否还有数据

	//初始化Buffer对象的属性
	public Buffer(int maxSize)
	{
		this.maxSize = maxSize;
		buffer = new LinkedList<>();
		
		lock = new ReentrantLock();
		
		//lock.newCondition返回的是Condition的一个实现
		lines = lock.newCondition();
		space = lock.newCondition();
		
		pendingLines = true;
	}

	//将字符串写入到缓冲区中
	public void insert(String line)
	{
		lock.lock();//显式加锁
		
		try
		{
			//缓冲区已满
			while (buffer.size() == maxSize)
			{
				//线程中调用了await方法后，该线程将释放锁，并将自己休眠,等待唤醒
				space.await();
			}
			
			buffer.offer(line);
			
			System.out.printf("%s: Inserted Line: %d\n", Thread.currentThread()
					.getName(), buffer.size());
			
			lines.signalAll();
		} 
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		finally
		{
			lock.unlock();
		}
	}
	
	//返回缓冲区中的第一个字符串
	public String get()
	{
		String line = null;
		
		lock.lock();//显式加锁
		
		try
		{
			//缓冲区为空
			while ((buffer.size() == 0) && (hasPendingLines()))
			{
				lines.await();
			}

			if (hasPendingLines())
			{
				line = buffer.poll();
				
				System.out.printf("%s: Line Readed: %d\n", Thread
						.currentThread().getName(), buffer.size());
				
				space.signalAll();
			}
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		} 
		finally
		{
			lock.unlock();//显式释放锁
		}
		return line;
	}

	//设置属性pendingLines的值
	public void setPendingLines(boolean pendingLines)
	{
		this.pendingLines = pendingLines;
	}
	
	//缓冲区中是否有数据行可以处理
	public boolean hasPendingLines()
	{
		return pendingLines || buffer.size() > 0;
	}

}
