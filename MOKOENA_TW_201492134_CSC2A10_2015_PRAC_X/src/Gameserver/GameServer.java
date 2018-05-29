package Gameserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {


	private ServerSocket	server;
	private boolean			running;

	/**
	 * creates the server 
	 * @param port
	 */
	public GameServer(int port)
	{
		try
		{
			System.out.println("Creating server");
			server = new ServerSocket(port);
			running = true;
			start();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 * Starts the server 
	 */
	public void start()
	{
		System.out.println("Starting server");
		while (running)
		{
			try
			{
				Socket connectionToClient = server.accept();
				System.out.println("New client");
				GameHandler handler = new GameHandler(connectionToClient);
				Thread clientThread = new Thread(handler);
				System.out.println("Starting client thread");
				clientThread.start();
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
	}

	/**
	 * creates a new server
	 */
	public static void main(String[] args)
	{
		GameServer server = new GameServer(100);
	}
}

