package SMTP;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class EmailPanel extends JPanel  {

		static JTextField txtSName = new JTextField(30); 
		static JTextField txtRName = new JTextField(29);
		private static InetAddress host; 
		public static String sender = null; 
		public static String recipient = null; 
		public static String message = null;
		private static final int PORT = 25;
		static String Response;
		private static PrintWriter os;
		private static BufferedReader is; 
		
		/**
		 * creating the gui contents
		 * @param data 
		 */
		public EmailPanel(final String data)
		{
			setLayout(new FlowLayout (FlowLayout.LEFT, 10, 20));
			JButton btnSend = new JButton("Send"); 
			 
			add(new JLabel("Recipient name: ")); 
			add(txtRName); 
			add(btnSend);
			
			btnSend.addActionListener(new ActionListener() 
			{
				@Override
				public void actionPerformed(ActionEvent e) {
				
				 sender =   "<Heli@helifrenzy.co.za>"; 
				 recipient = txtRName.getText();	
				 message = data; 				 
			 
				//if text field is empty
				 if (txtRName.getText().equals(""))
				 {
					//telling user to enter credentials 
					System.out.println("Enter recipient name");	
				 }
				 else
				 {
					 //allocating user cridentials
					 recipient = ("<" + txtRName.getText() + "@helifrenzy.co.za>");	
					 System.out.println(recipient);	
				 }
								
								
				try
				 {			 
				 host = InetAddress.getLocalHost();
				 }
				 catch(UnknownHostException e1)
				 {
				 System.out.println("Host ID not found!");
				 System.exit(1);
				 }
				 
				 	run();
				 }
			}); 
		}
		
		/**
		 * sendsthe email
		 */
		private static void run()
		{
			Socket SMTP = null;
		try
		{	
			SMTP = new Socket(host,PORT); //connect to server
			is = new BufferedReader(new InputStreamReader(SMTP.getInputStream())); 
			os = new PrintWriter(SMTP.getOutputStream(),true);
			readResponse(is); 
			
			
			if(Response.indexOf("220") == -1)
			{
			 System.out.println("\nServer is not responding\n");
			}
			
			writeMessage(os,"HELO"); //greeting
			readResponse(is); //read from server
			System.out.println("\nSERVER> " + Response);
			
			if(Response.indexOf("250") == -1)
			{
			 System.out.println("\nHELLO sent unsucessfully\n");
			}
			
			writeMessage(os,"MAIL FROM:" + sender + "\n"); //specifying sender
			readResponse(is);
			
			if(Response.indexOf("250") == -1)
			{
			 System.out.println("\nMail FROM unsucessfully\n"); 
			}
			
			writeMessage(os,"RCPT TO:" +  recipient + "\n"); //pecifying recipient
			readResponse(is); //read from server
			
			if(Response.indexOf("250") == -1)
			{
			 System.out.println("\nMail TO unsuccessful\n");
			}
			
			writeMessage(os,"DATA\n" + message +"\n"); //message
			writeMessage(os,"."); //ending message 
			readResponse(is);
			writeMessage(os,"QUIT\n"); //message
			readResponse(is);
			
		}
		
		 catch (UnknownHostException k) 
		{
			 System.out.println(k);
		}

		
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		finally
		{
			try
			{
				System.out.println("\n* Closing connection... *");
				SMTP.close();
			}
			catch(IOException e)
			{
				System.out.println("Unable to disconnect!");
				System.exit(1);
			}
		}
	  }
		
		private static void readResponse(BufferedReader is) throws IOException
		{
			Response = is.readLine();
			System.out.println(Response);
		}

		private static void writeMessage(PrintWriter out, String message)
		{
			out.println("\nSERVER> "+message);
			out.flush();
		}
}