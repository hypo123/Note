package recipe4;

import java.util.Random;
import java.util.concurrent.TimeUnit;

//UserValidator类实现用户验证
public class UserValidator
{
	private String name;//存储用户验证系统的名称

	public UserValidator(String name)
	{
		this.name = name;
	}

	/**
	 * @param name 用户名
	 * @param password 密码
	 */
	public boolean validate(String name, String password)
	{
		Random random = new Random();

		try
		{
			Long duration = (long) (Math.random() * 10);
			System.out.printf(
					"Validator %s: Validating a user during %d seconds\n",
					this.name, duration);
			
			//休眠一段随机时间模拟用户验证的过程
			TimeUnit.SECONDS.sleep(duration);
		}
		catch (InterruptedException e)
		{
			return false;
		}

		//返回一个随机Boolean类型结果
		return random.nextBoolean();
	}

	//返回验证系统的名称
	public String getName()
	{
		return name;
	}

}
