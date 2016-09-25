package chapter1;

public class NumberFormatExceptionTest
{
	public static void main(String[] args)
	{
		//NumberFormatException异常，并退出程序.
		int a = Integer.parseInt("TTT");
		
		for(int i = 0 ; i < 3 ; i++)
		{
			System.out.println("-------------i-----------");
		}
	}
}
