package recipe4;

import java.util.Random;

public class MatrixMock
{
	private int data[][];

	public MatrixMock(int size, int length, int number)
	{
		//随机矩阵中目标数字的个数.
		int counter = 0;
		
		//行数为size,列数为length的矩阵
		data = new int[size][length];
		
		Random random = new Random();
		
		//随机数字为矩阵赋值
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < length; j++)
			{
				data[i][j] = random.nextInt(10);
				if (data[i][j] == number)
				{
					counter++;
				}
			}
		}
		
		System.out.printf(
				"Mock: There are %d ocurrences of number in generated data.\n",
				counter, number);
	}

	//返回矩阵一行数据
	public int[] getRow(int row)
	{
		if ((row >= 0) && (row < data.length))
		{
			return data[row];
		}
		return null;
	}

}
