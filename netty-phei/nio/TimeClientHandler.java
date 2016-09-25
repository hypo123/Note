package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 *	客户端处理程序 
 */
public class TimeClientHandler implements Runnable
{
	private String host;
	private int port;
	
	private Selector selector;
	private SocketChannel socketChannel;
	
	private volatile boolean stop;
	
	public TimeClientHandler(String host , int port)
	{
		this.host = host == null ? "127.0.0.1" : host;
		this.port = port;
		
		try
		{
			selector = Selector.open();
			
			socketChannel = SocketChannel.open();
			
			socketChannel.configureBlocking(false);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void run()
	{
		try
		{
			doConnect();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		while(!stop)
		{
			try
			{
				selector.select(1000);
				Set<SelectionKey> selectedKeys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = selectedKeys.iterator();
				SelectionKey key = null;
				
				while (iterator.hasNext()) 
				{
					key = iterator.next();
					
					iterator.remove();
					
					try
					{
						handleInput(key);
					}
					catch (Exception e)
					{
						if(key != null)
						{
							key.cancel();
							
							if(key.channel() != null)
							{
								key.channel().close();
							}
						}
					}
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
				System.exit(1);
			}
		}
		
		if(selector != null)
		{
			try
			{
				selector.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
	
	private void handleInput(SelectionKey key) throws IOException
	{
		//告知此键是否有效
		if (key.isValid())
		{
			
			SocketChannel sc = (SocketChannel) key.channel();
			
			// 测试此键的通道是否已完成其套接字连接操作
			if (key.isConnectable())
			{
				if (sc.finishConnect())
				{
					sc.register(selector, SelectionKey.OP_READ);
					
					doWrite(sc);
				}
				else
				{
					System.exit(1);// 连接失败，进程退出
				}
			}
			
			// 测试此键的通道是否已准备好进行读取
			if (key.isReadable())
			{
				//新分配大小为1024字节的缓冲区
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				
				//将通道sc中字节序列读入到缓冲区readBuffer中
				int readBytes = sc.read(readBuffer);
				
				if (readBytes > 0)
				{
					readBuffer.flip();
					
					byte[] bytes = new byte[readBuffer.remaining()];
					
					readBuffer.get(bytes);
					
					String body = new String(bytes, "UTF-8");
					
					System.out.println("Now is : " + body);
					
					this.stop = true;
				}
				else if (readBytes < 0)
				{
					// 对端链路关闭
					key.cancel();
					sc.close();
				}
				else
				{
					; // 读到0字节，忽略
				}
			}
		}
	}
	
	private void doConnect() throws IOException
	{
		// 如果直接连接成功，则注册到多路复用器上，发送请求消息，读应答
		if (socketChannel.connect(new InetSocketAddress(host, port)))
		{
			socketChannel.register(selector, SelectionKey.OP_READ);
			
			doWrite(socketChannel);
		}
		else
		{
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
		}
	}

	private void doWrite(SocketChannel sc) throws IOException
	{
		byte[] req = "QUERY TIME ORDER".getBytes();
		
		//分配一个大小为req.length个字节的新缓冲区
		ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
		
		//将字节数组传输到通道中
		writeBuffer.put(req);
		
		//反转通道
		writeBuffer.flip();
		
		//将缓冲区writeBuffer中数据写入到通道中
		sc.write(writeBuffer);
		
		//缓冲区中数据全部写入到同道中
		if (!writeBuffer.hasRemaining())
		{
			System.out.println("Send order 2 server succeed.");
		}
	}

}
