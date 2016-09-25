package recipe6;

public class Producer implements Runnable
{
	private FileMock mock;//文件类FileMock对象引用

	private Buffer buffer;//缓冲区类Buffer对象引用

	public Producer(FileMock mock, Buffer buffer)
	{
		this.mock = mock;
		this.buffer = buffer;
	}

	@Override
	public void run()
	{
		buffer.setPendingLines(true);
		
		//读取文件中所有行,并将其插入到缓冲区中.
		while (mock.hasMoreLines())
		{
			String line = mock.getLine();
			
			//向缓冲区中写入
			buffer.insert(line);
		}
		
		buffer.setPendingLines(false);
	}
}
