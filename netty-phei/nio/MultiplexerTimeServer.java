package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 *	服务器端处理程序 
 */
public class MultiplexerTimeServer implements Runnable
{
	private Selector selector;//Selector
	private ServerSocketChannel servChannel;//Channel
	
	private volatile boolean stop;
	
	//初始化多路复用器,绑定端口
	public MultiplexerTimeServer(int port)
	{
		try
		{
			//打开一个选择器
			selector = Selector.open();
			
			//打开服务器套接字通道
			servChannel = ServerSocketChannel.open();
			
			//设置连接为非阻塞模式
			servChannel.configureBlocking(false);
			
			//绑定监听端口
			servChannel.socket().bind(new InetSocketAddress(port), 1024);
			
			//向Selector注册Channel
			servChannel.register(selector, SelectionKey.OP_ACCEPT);
			
			System.out.println("The time server is start in port : " + port);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void stop()
	{
		this.stop = true;
	}
	
	@Override
	public void run()
	{
		while(!stop)
		{
			try
			{
				//阻塞到至少有一个通道在你注册的事件上就绪了,最长会阻塞1000毫秒
				selector.select(1000);
				
				//一旦调用了select方法,就表明有一个或更多个通道就绪了;
				//可通过selectedKeys()方法，访问"已选择键集(selected key set)"中的就绪通道
				Set<SelectionKey> selectedKeys = selector.selectedKeys();
				
				Iterator<SelectionKey> iterator = selectedKeys.iterator();
				
				SelectionKey key = null;
				
				while(iterator.hasNext())
				{
					key = iterator.next();
					
					//该键已访问,从集合中删除
					iterator.remove();
					
					try
					{
						//处理
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
			}
		}
		
		//多路复用器关闭后,所有注册在上面的Channel和Pipe等资源都会被自动去注册并关闭.
		//所以不需要重复释放资源.
		if(selector != null)
		{
			try
			{
				selector.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	
	private void handleInput(SelectionKey key) throws IOException
	{
		if(key.isValid())
		{
			//测试此键的通道是否已准备好接受新的套接字连接
			if(key.isAcceptable())
			{
				//ServerSocketChannel 是一个可以监听新进来的TCP连接的通道
				ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
				
				//一个新连接到达ServerSocketChannel时,会创建一个SocketChannel
				SocketChannel sc = ssc.accept();
				
				//设置连接为非阻塞模式
				sc.configureBlocking(false);
				
				//向Selector注册通道sc
				sc.register(selector, SelectionKey.OP_READ);
			}
			
			//测试此键的通道是否已准备好进行读取
			if(key.isReadable())
			{
				//channel方法:返回为之创建此键的通道
				SocketChannel sc = (SocketChannel)key.channel();
				
				//Buffer用于和NIO通道进行交互。如你所知，数据是从通道读入缓冲区，从缓冲区写入到通道中的
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				
				//将字节序列从通道读入到readBuffer缓冲区中,返回值为读取的字节数
				int readBytes = sc.read(readBuffer);
				
				if(readBytes > 0)
				{
					//反转readBuffer缓冲区,将readBuffer从写模式切换到读模式
					readBuffer.flip();
					
					//remaining方法:返回当前位置与limit之间的元素数
					byte[] bytes = new byte[readBuffer.remaining()];
					
					//get方法:将readBuffer缓冲区的字节传输到给定的目标数组中
					readBuffer.get(bytes);
					
					//由指定使用UTF-8解码指定的 byte数组来构造一个新的 String
					String body = new String(bytes, "UTF-8");
					
				    System.out.println("The time server receive order : "
					    + body);
				    
				    String currentTime = "QUERY TIME ORDER"
					    .equalsIgnoreCase(body) ? new java.util.Date(
					    System.currentTimeMillis()).toString()
					    : "BAD ORDER";
					 
					//将字符串写入到channel中
					doWrite(sc , currentTime);
				}
				else if(readBytes < 0)
				{
					//请求取消此键的通道到其选择器的注册
					key.cancel();
					
					//关闭通道
					sc.close();
				}
				else
				{
					;
				}
			}
		}
	}
	
	//将字符串写入到客户端通道中
	private void doWrite(SocketChannel channel , String response) throws IOException
	{
		if(response != null && response.trim().length() > 0)
		{
			byte[] bytes = response.getBytes();
			
			//创建新的缓冲区
			ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
			
			//将数组bytes中的所有内容传输到缓冲区writeBuffer中
			writeBuffer.put(bytes);
			
			//反转缓冲区
			writeBuffer.flip();
			
			//将字节序列从writeBuffer缓冲区中写入通道channel中
			channel.write(writeBuffer);
		}
	}
}
