package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleHTTPServer {
	
	public SimpleHTTPServer() {
		
	}
	
	public String getCode() throws Exception{
		String keyWord = "code=";
		final ServerSocket server = new ServerSocket(8081);
	    System.out.println("Listening for connection on port 8081 ....");
	    boolean isOpen = true;
	    while (isOpen){
	      // spin forever
	    	Socket clientSocket = server.accept();
	        InputStreamReader isr =  new InputStreamReader(clientSocket.getInputStream());
	        BufferedReader reader = new BufferedReader(isr);
	        String line;// = reader.readLine();
	        String out = "";
	        /*
	        while (!line.isEmpty()) {
	            System.out.println(line);
	            line = reader.readLine();
	            
	        }
	        */
	        
	        line = reader.readLine();
	        //System.out.println(line.indexOf("code="));
	        //System.out.println(line.indexOf("&state="));
	        
	        //System.out.println(line.substring(line.indexOf("code=")+5, line.indexOf("&state=")));
	        
	 
	        
	        server.close();
	        isOpen = false;
	        
	        return (line.substring(line.indexOf("code=")+5, line.indexOf("&state=")));
	    }
		System.out.println("While loop left");
		
		return null;
		
	}
}
