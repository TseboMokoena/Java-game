package SMTP;

import javax.swing.JFrame;

import SMTP.EmailPanel;

public class EmailGUI extends JFrame {

	public EmailGUI(String Data)
	{
		JFrame frmEmail = new JFrame("Send Game data"); 
		EmailPanel p1 = new EmailPanel(Data);
		frmEmail.add(p1); 
		frmEmail.setSize(500, 200);
		frmEmail.setLocationRelativeTo(null);
		frmEmail.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frmEmail.setVisible(true); 
	}
}
