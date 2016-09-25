package bio_pool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *	同步阻塞式IO BIO
 *	
 *	服务器端
 */
public class TimeServer
{
	public static void main(String[] args)
	{
		int port = 10001;
		
		if (args != null && args.length > 0)
		{

			try
			{
				port = Integer.valueOf(args[0]);
			}
			catch (NumberFormatException e)
			{
				e.printStackTrace();
			}
		}
		
		ServerSocket server = null;
		
		try
		{
			server = new ServerSocket(port);
			
			System.out.println("The time server is start in port : " + port);
			
			Socket socket = null;
			
			//创建IO任务线程池
			TimeServerHandlerExecutorPool singleExecutor = new TimeServerHandlerExecutorPool(50, 10000);
			
			
			while(true)
			{
				//如果没有客户端socket连接则阻塞在这里
				//返回服务器端处理数据socket
				socket = server.accept();
				
				singleExecutor.execute(new TimeServerHandler(socket));
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(server != null)
			{
				System.out.println("The time server close");
				try
				{
					server.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				server = null;
			}
		}
	}
}
