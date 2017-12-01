import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MyServerRunnable implements Runnable {

	protected Socket clientSocket = null;
	protected Cacher cacher;  
	
	public MyServerRunnable(Socket clientSocket, Cacher cacher) {
		this.clientSocket = clientSocket;
		this.cacher = cacher;
	}

	
	
	public void run() {
		System.out.println("server thread started...");
		
		try {
 
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter out =  new PrintWriter (clientSocket.getOutputStream(), true); 
	
				String arg1 = in.readLine();
				System.out.println(arg1);

				switch (arg1.toLowerCase()) {
					case "help":
						out.println("Available commands: get URL [true/false(optional)], list, flush, exit");			
						out.println("examples:");			
						out.println("client get URL false - gets html markup from cache (if available)");			
						out.println("client get URL true - gets html markup from server and refresh cache");			
						out.println("client list - shows list of url of cached markups");			
						out.println("client flush - removes all data from cache");
						break;
					
					case "flush":
						out.println(cacher.flush().message);
						break;
					
					case "list":
						out.println(cacher.list().message);
						break;
	
					default: 
						if (arg1.toLowerCase().substring(0, Math.min(4, arg1.length())).equals("get ")) {
							String s = cacher.get(arg1).message;
							out.println(s);
							
						} else {
							out.println("Type 'help' to see features.\n"); // say hello to client
						}
				}
			out.close();


			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	
		
	}

 
}
