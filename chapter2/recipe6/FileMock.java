package recipe6;

public class FileMock
{
	//文件内容
	private String content[];
	
	//内容的当前行号
	private int index;

	/**
	 * 
	 * @param size 行数
	 * @param length 每行字符串字符个数
	 */
	public FileMock(int size, int length)
	{
		content = new String[size];
		
		//初始化文件内容
		for (int i = 0; i < size; i++)
		{
			StringBuilder buffer = new StringBuilder(length);
			
			for (int j = 0; j < length; j++)
			{
				//随机字符
				int indice = (int) Math.random() * 255;
				
				buffer.append((char) indice);
			}
			
			content[i] = buffer.toString();
		}
		
		index = 0;
	}

	public boolean hasMoreLines()
	{
		return index < content.length;
	}

	public String getLine()
	{
		if (this.hasMoreLines())
		{
			//打印还剩多少行没有处理
			System.out.println("Mock: " + (content.length - index));
			
			return content[index++];
		}
		return null;
	}
}
