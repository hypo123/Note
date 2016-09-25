package bio_pool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *	处理用户请求线程 
 */
public class TimeServerHandler implements Runnable
{
	private Socket socket;
	
	//传入服务器端socket
	public TimeServerHandler(Socket socket)
	{
		this.socket = socket;
	}
	
	@Override
	public void run()
	{
		BufferedReader in = null;
		PrintWriter out = null;
		
		try
		{
			in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			
			out = new PrintWriter(this.socket.getOutputStream());
			
			String currentTime = null;
			String body = null;
			
			while(true)
			{
				body = in.readLine();
				
				if(body == null)
				{
					break;
				}
				
				System.out.println("The time server receive order : " + body);
				
				currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new java.util.Date(
					System.currentTimeMillis()).toString() : "BAD ORDER";
					
				out.println(currentTime);
				out.flush();//注意要刷新
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(out != null)
			{
				out.close();
				out = null;
			}
			
			if(in != null)
			{
				try
				{
					in.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				in = null;
			}
			
			if(this.socket != null)
			{
				try
				{
					this.socket.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				
				this.socket = null;
			}
		}
	}
}
