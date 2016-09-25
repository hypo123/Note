异步IO AIO

Java7的java.nio.channels包中增加了4个异步通道

AsynchronousSocketChannel
AsynchronousServerSocketChannel
AsynchronousFileChannel
AsynchronousDatagramChannel

异步通道 API 提供两种对已启动异步操作的监测与控制机制:
第一种是通过返回一个 java.util.concurrent.Future 对象来实现，它将会建模一个挂起操作，并可用于查询其状态以及获取结果。
第二种是通过传递给操作一个新类的对象，java.nio.channels.CompletionHandler，来完成，它会定义在操作完毕后所执行的处理程序方法。
每个异步通道类为每个操作定义 API 副本，这样可采用任一机制


