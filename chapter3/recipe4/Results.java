package recipe4;

/**
 *	该类保存矩阵中每行找到指定数字的次数. 
 */
public class Results
{
	private int data[];

	public Results(int size)
	{
		data = new int[size];
	}

	public void setData(int position, int value)
	{
		data[position] = value;
	}

	public int[] getData()
	{
		return data;
	}
}
