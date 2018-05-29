package Gameserver;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameClient extends JFrame
{

	private static final long serialVersionUID = 1L;
	
	//creating variables 
	private Socket			clientSocket;
	private InputStream		is;
	private PrintWriter		txtout;
	private BufferedReader	txtin;
	private JButton			btnReg;
	private JButton			btnLogin;
	private JButton 		btnLoad; 
	private JButton 		btnStart; 
	private JButton			btnLogout;

	/**
	 * creates the client 
	 */
	public GameClient()
	{
		setTitle("PUFF Client");
		JPanel panel = new JPanel();
		btnReg = new JButton("REGISTER");
		btnLogin = new JButton("LOGIN");
		btnLoad = new JButton("LOAD");
		btnStart = new JButton("START");
		btnLogout = new JButton("LOGOUT");
		btnLogin.setEnabled(false);
		btnLoad.setEnabled(false);
		btnLogout.setEnabled(false);

		setLayout(new FlowLayout (FlowLayout.LEFT, 10, 10));
		panel.add(btnReg); 
		panel.add(btnLogin);
		panel.add(btnLoad);
		panel.add(btnStart);
		panel.add(btnLogout);
		add(panel); 
		
		/**
		 * Register the users
		 */
		btnReg.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				//input for the host name and port 
				String host = JOptionPane.showInputDialog(GameClient.this, "Enter hostname", "localhost");
				String port = JOptionPane.showInputDialog(GameClient.this, "Enter port", "1000");
				
				//input for the username and password
				String Username = JOptionPane.showInputDialog(GameClient.this, "Enter Username");
				String Password = JOptionPane.showInputDialog(GameClient.this, "Enter Password");
				
				try
				{
					//creating the socket
					clientSocket = new Socket(host, Integer.parseInt(port));
					is = clientSocket.getInputStream();
					txtout = new PrintWriter(clientSocket.getOutputStream());
					txtin = new BufferedReader(new InputStreamReader(is));
					sendCommand("REG" + "," + Username+ "," +Password);
					readResponse();
					btnReg.setEnabled(false);
					btnLogin.setEnabled(true);
					btnLoad.setEnabled(true);
					
				}
				catch (NumberFormatException ex)
				{
					ex.printStackTrace();
				}
				catch (UnknownHostException ex)
				{
					ex.printStackTrace();
				}
				catch (IOException ex)
				{
					ex.printStackTrace();
				}

			}
		});

		/**
		 * logs in the user 
		 */
		btnLogin.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String Username = JOptionPane.showInputDialog(GameClient.this, "Enter login Username");
				String Password = JOptionPane.showInputDialog(GameClient.this, "Enter login Password");
				sendCommand("LOG" + Username + Password);
				readResponse();
				btnLogout.setEnabled(true);
				btnStart.setEnabled(true); 
			}
		});
		
		/**
		 * loads the file of the game
		 */
		btnLoad.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				sendCommand("LOAD");
				readResponse();
			}
		});;
		
		/**
		 * starts the game
		 */
		btnStart.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				sendCommand("START");
				readResponse();
			}
		});;
		
		/**
		 * Logout the user 
		 */
		btnLogout.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				sendCommand("LOGOUT");
				readResponse();
				close();
				btnReg.setEnabled(true);
				btnLogin.setEnabled(false);
				btnLoad.setEnabled(false);
				btnLogout.setEnabled(false);

			}
		});
	}
	
	/**
	 * clsoe the connection
	 */
	private void close()
	{
		if (clientSocket != null)
		{
			try
			{
				clientSocket.close();
				clientSocket = null;
			}
			catch (IOException ex)
			{
				
				ex.printStackTrace();
			}
		}

	}

	/**
	 * sends teh command to the server 
	 * @param message
	 */
	protected void sendCommand(String message)
	{
		txtout.println(message);
		txtout.flush();
	}

	/**
	 * reads the respose from the server 
	 */
	private void readResponse()
	{
		try
		{
			String response = txtin.readLine();
			System.out.println(response);
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}


}
