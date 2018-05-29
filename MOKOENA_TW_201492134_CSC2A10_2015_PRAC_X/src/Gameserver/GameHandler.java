package Gameserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

import za.ac.za.uj.acsse.csc2a.gui.GameWindow;
import za.ac.za.uj.acsse.csc2a.helicopter.Game;

public class GameHandler implements Runnable {

	//creating variales 
	private Socket			connectionToClient;
	private OutputStream	os;
	private PrintWriter		txtout;
	private BufferedReader	txtin;
	private Game Game;
	/**
	 * connects to the client 
	 * @param socketConnectionToClient
	 */
	public GameHandler(Socket socketConnectionToClient)
	{
		connectionToClient = socketConnectionToClient;
		try
		{
			os = connectionToClient.getOutputStream();
			txtin = new BufferedReader(new InputStreamReader(connectionToClient.getInputStream()));
			txtout = new PrintWriter(os);
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 * closes the connection 
	 */
	private void close()
	{
		try
		{
			connectionToClient.close();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 * sends messages to the client
	 * @param message
	 */
	private void sendMessage(String message)
	{
		txtout.println(message);
		txtout.flush();
	}

	/**
	 * runs the thread
	 */
	@Override
	public void run()
	{
		System.out.println("Processing client commands");
		boolean processing = true;

		try
		{
			while (processing)
			{
				//looking fro commands from the input stream
				String message = txtin.readLine();
				StringTokenizer messagetokens = new StringTokenizer(message);
				String command = messagetokens.nextToken().toUpperCase();
				switch (command)
				{
				case "REG":
					String Username = messagetokens.nextToken();
					System.out.println(Username);
					
					String Password  = messagetokens.nextToken();
					
					Register(Username, Password); 
					sendMessage("REG"+ connectionToClient.getInetAddress());
					
					break;
					
				case "LOG":
					String LUsername = messagetokens.nextToken(); 
					System.out.println(LUsername);
					
					String LPassword  = messagetokens.nextToken();
					System.out.println(LPassword);
					
					if (ReadUser(LUsername, LPassword) == true)
					{
						sendMessage("04 Login Succesful");
					}
					else if (ReadUser(LUsername, LPassword) == false)
					{
						sendMessage("37 Incorrect user name and password combination, try again");
					}
					break;
					
				case "LOAD":
					sendMessage("PUT");
					//Other put processes
					break;
					
				case "START":
					sendMessage("START");
					//new game is created 
					try {
						Game  = new Game();
					} catch (IOException e1) {
						e1.printStackTrace();
					} 
					
					//creates a new Game Window
					try {
						GameWindow  win = new GameWindow();
						win.setSize(1360, 740);
						win.setLocationRelativeTo(null);
						win.setVisible(true);
						win.add(Game);
						
					} catch (IOException e1) {
					
						e1.printStackTrace();
					} 
					//new game is created 
					try {
						Game  = new Game();
					} catch (IOException e1) {
						e1.printStackTrace();
					} 
					 
					break; 
					
					
				case "OVER":
					sendMessage("Bye");
					processing = false;
					break;
				default:
					sendMessage("Unknown command");
				}
			}
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			close();
		}
	}
	
	/**
	 * Registers the user
	 * @param username
	 * @param password
	 * @throws FileNotFoundException
	 */
	private void Register(String username, String password) throws FileNotFoundException {
		
		File f = new File("Reg.txt"); 
		PrintWriter pw = new PrintWriter(f); 
		pw.println(username + " " + password);
		pw.close(); 
		
	}
	
	/**
	 * Reads the file and compares it to the entered user details 
	 * @param username
	 * @param password
	 * @return
	 * @throws FileNotFoundException
	 */
	private boolean ReadUser(String  username, String  password) throws FileNotFoundException {
		
		File f = new File("Reg.txt"); 
		boolean user = false; 
		boolean pass = false; 
		String word ; 
		
		Scanner sc = new Scanner(f); 
			while(sc.hasNext()){
				
				word = sc.next(); 
				
				if (username == word)
				{
					user = true;
				}
				else if (password == word)
				{
					pass = true; 
				}
			}
			sc.close(); 
			
		if(user && pass == true)
		{
			return true; 
			
		}
		else 
		{
			return false; 
		}
		
		
	}
}
	