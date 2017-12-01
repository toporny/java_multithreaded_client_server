import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MySocketServer {
	int portNumber = 44444;
	ServerSocket serverSocket = null;

	public void runServer() {
		
		System.out.println("running server...");
		
		try {
			serverSocket = new ServerSocket(portNumber);
			
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
			
		}

		// Casher provides few commands:
		// 
		// help - show help screen
		// flush - removes everything from cache
		// get [true/false]- get page. False from cache. True from realive website and update cache 
		// list - show list of cached urls
		
		Cacher cacher = new Cacher ();
		
		while (true) {
			try { 
				Socket clientSocket = serverSocket.accept();
				MyServerRunnable m = new MyServerRunnable(clientSocket, cacher);
				new Thread(m).start();
			}
			catch (IOException e) {
				System.out.println(e.getMessage());
			}				
		}
		
		
	}

}
