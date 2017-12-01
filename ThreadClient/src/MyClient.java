import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MyClient {

	public static void startCommunication(String commands) {
		
		String hostName = "127.0.0.1";
		int portNumber = 44444;
		Socket clientSocket;
		PrintWriter out;
		BufferedReader in;
		InputStreamReader ir;

		try {
			clientSocket = new Socket(hostName, portNumber);
			// create our IO streams
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			ir = new InputStreamReader(clientSocket.getInputStream());
			in = new BufferedReader(ir);
			
			out.println(commands); // send to server three  parameters: command url cached
			
			
			String serverResponse = null;
			while ((serverResponse = in.readLine()) != null) {
				System.out.println(serverResponse);
			}
				
			
		} catch (UnknownHostException e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	
	public static void main(String[] args) {
		switch (args.length) {
  		  case 1: startCommunication( args[0].toLowerCase());
  		  break;  		  

  		  case 3: startCommunication( args[0].toLowerCase()+ " "+ args[1].toLowerCase()+ " "+ args[2].toLowerCase() );
  		  break;
  		  
  		  default :
  			System.out.println("This is command line application.");
  			System.out.println("Type from command line 'Myclient help' to see features.");
  			
  		    // On eclipse you can simulate program arguments here:
  			// Run => run configuratiion => Java application => My client argument
  			// put there for example:
  			// get http://alltic.home.pl/1.html true
  			// or
  			// list
  			// or
  			// flush
  			
  			
  			// You may also speed up testing process by uncommend one of below lines 
  			// startCommunication("help");
  			// or
  			// startCommunication("flush");
  			// or
  			
	  		// If you are really lazy you can also uncomment code below:
	  		// startCommunication("get http://alltic.home.pl/1.html true") ;
	  		// startCommunication("get http://alltic.home.pl/2.html false") ;
	  		// startCommunication("get http://alltic.home.pl/3.html true") ;
	  		// startCommunication("get http://alltic.home.pl/4.html false") ;
	  		// startCommunication("get http://alltic.home.pl/5.html true") ;
	  		// startCommunication("get http://alltic.home.pl/6.html true") ;
	  		// startCommunication("get http://alltic.home.pl/7.html true") ;
	  		// startCommunication("get http://alltic.home.pl/7.html false") ;
	  		// startCommunication("get http://alltic.home.pl/8.html false") ;
	  		// startCommunication("get http://alltic.home.pl/7.html true") ;
	  		 //startCommunication("list") ; // flush is also available (clears cache)// 

  			
  			
		}
		
	
	}

}
