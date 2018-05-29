package SMTP;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * Threaded server class
 */
public class ThreadServer 
{

	private  ServerSocket SSocket;
	private  int Port = 25;
	
	/*
	 * run
	 *connects to server and accepts connection from client
	 */
	public void run() throws IOException
	{
		//instanciating a server socket
		SSocket = new ServerSocket(Port); 
		
		while(true)
		{
			Socket ClientSocket = null;
			
			//accepting client
			ClientSocket = this.SSocket.accept();
			
			//creating a request
			Request req = new Request(ClientSocket); 
			
			//creating a thread
			Thread thread = new Thread(req); 
			
			//starting the thread
			thread.start();
		}
	  }
	
	/**
	 * stops the server
	 * @throws IOException 
	 */
	public void stop() throws IOException{
	
			SSocket.close();
		
	}
}