package bio_pool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *	客户端 
 */
public class TimeClient
{
	public static void main(String[] args)
	{
		int port = 10001;
		
		if(args != null && args.length > 0)
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
		
		Socket socket = null;
		BufferedReader in = null;
		PrintWriter out = null;
		
		try
		{
			socket = new Socket("127.0.0.1" , port);
			
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			out = new PrintWriter(socket.getOutputStream() , true);
			
		    out.println("QUERY TIME ORDER");
		    System.out.println("Send order 2 server succeed.");
		    
		    String resp = in.readLine();
		    
		    System.out.println("Now is : " + resp);
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
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
			
			if(socket != null)
			{
				try
				{
					socket.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				
				socket = null;
			}
		}
	}
}
