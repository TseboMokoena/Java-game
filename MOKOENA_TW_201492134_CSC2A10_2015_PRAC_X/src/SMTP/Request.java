
/**
 * @author BoBo_ObesT
 */

package SMTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Send message comtaining game data 
 */
public class Request implements Runnable {

	int port = 25;
	String Response; 
	InetAddress host = null;
	Socket SSocket = null;
	PrintWriter os = null;
	BufferedReader is = null;
	
	
	/**
	 * provides service
	 * @param connection the socket number of the client
	 */
	public Request(Socket connection)
	{
		this.SSocket = connection;
	}
	@Override
	public void run() {
		try
		{
			os = new PrintWriter(SSocket.getOutputStream(), true);
			is = new BufferedReader(new InputStreamReader(SSocket.getInputStream()));
			readResponse(is);
			writeMessage(os, "HELO");
			readResponse(is);
			writeMessage(os, "MAIL From:<" + EmailPanel.sender + "@helifrenzy.co.za>");
			readResponse(is);
			writeMessage(os, "RCPT To:<" + EmailPanel.recipient + "@helifrenzy.co.za>");
			readResponse(is);
			writeMessage(os, "DATA");
			readResponse(is);
			writeMessage(os, "X-Mailer: Java");
			writeMessage(os, "From:" + EmailPanel.sender + "@helifrenzy.co.za>");
			writeMessage(os, "To:" + EmailPanel.recipient + "@helifrenzy.co.za>");
			writeMessage(os, "Subject: Game Data ");
			writeMessage(os, "");
			writeMessage(os, EmailPanel.message);
			writeMessage(os, ".");
			readResponse(is);
			writeMessage(os, "QUIT\r\n");
			readResponse(is);
		}
		catch (UnknownHostException ex)
		{
			ex.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * creates connectio to server 
	 * @param fromAddress
	 * @param toAddress
	 * @param message
	 */
	/*public void sendMessage()
	{
		try
		{
			host = InetAddress.getLocalHost();
		}
		catch (UnknownHostException ex)
		{
			ex.printStackTrace();
			return;
		}

		try
		{
			SSocket = new Socket(host, port);
			os = new PrintWriter(SSocket.getOutputStream(), true);
			is = new BufferedReader(new InputStreamReader(SSocket.getInputStream()));
			readResponse(is);
			writeMessage(os, "HELO");
			readResponse(is);
			writeMessage(os, "MAIL From:<" + fromAddress + "@@helifrenzy.co.za>");
			readResponse(is);
			writeMessage(os, "RCPT To:<" + toAddress + "@@helifrenzy.co.za>");
			readResponse(is);
			writeMessage(os, "DATA");
			readResponse(is);
			writeMessage(os, "X-Mailer: Java");
			writeMessage(os, "From:" + fromAddress + "");
			writeMessage(os, "To:" + toAddress + "");
			writeMessage(os, "Subject: Game Data ");
			writeMessage(os, "");
			writeMessage(os, message);
			writeMessage(os, ".");
			readResponse(is);
			writeMessage(os, "QUIT\r\n");
			readResponse(is);
		}
		catch (UnknownHostException ex)
		{
			ex.printStackTrace();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			if (SSocket != null)
			{
				try
				{
					SSocket.close();
				}
				catch (IOException ex)
				{
					
					ex.printStackTrace();
				}
			}
		}
	}*/
	
	private void readResponse(BufferedReader is) throws IOException
	{
		Response = is.readLine();
		System.out.println(Response);
	}

	private void writeMessage(PrintWriter out, String message)
	{
		out.println(message);
		out.flush();
	}

	
}
