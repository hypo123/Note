package recipe2;

public class Main42
{
	public static void main(String[] args)
	{
		Server server = new Server();

		for (int i = 0; i < 100; i++)
		{
			Task task = new Task("Task " + i);
			
			server.executeTask(task);
		}

		server.endServer();
	}
}
