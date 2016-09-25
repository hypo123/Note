package recipe4;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PricesInfo
{
	private double price1;
	private double price2;

	//读写锁:一个资源可以被多个读操作访问,或者被一个写操作访问,但两者不能同时进行.
	
	//声明读写锁引用lock
	private ReadWriteLock lock;

	public PricesInfo()
	{
		price1 = 1.0;
		price2 = 2.0;
		
		//创建读写锁对象.
		//ReentrantReadWriteLock类是ReadWriteLock接口的唯一实现类.
		lock = new ReentrantReadWriteLock();
	}

	public double getPrice1()
	{
		lock.readLock().lock();//加读锁
		double value = price1;
		lock.readLock().unlock();//释放读锁
		return value;
	}

	public double getPrice2()
	{
		lock.readLock().lock();//加读锁
		double value = price2;
		lock.readLock().unlock();//释放读锁
		return value;
	}

	public void setPrices(double price1, double price2)
	{
		lock.writeLock().lock();//加写锁
		this.price1 = price1;
		this.price2 = price2;
		lock.writeLock().unlock();//释放写锁
	}
}
