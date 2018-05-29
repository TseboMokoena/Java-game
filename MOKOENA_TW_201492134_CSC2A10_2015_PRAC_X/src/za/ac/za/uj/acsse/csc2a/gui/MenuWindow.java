/**
 * @author Tsebo Mokoena
 */
package za.ac.za.uj.acsse.csc2a.gui;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import Gameserver.GameClient;

public class MenuWindow extends JFrame{
	//variables for menu items, buttons and labels
	private JMenuBar mainBar;
	private JMenu fileMenu;
	private JMenuItem StartItem;
	private JMenuItem StartSItem;
	private JMenuItem exitItem;
	private JButton StartButton; 
	private JButton ExitButton; 
	private BufferedImage BackGround; 
	private JLabel Back; 
	private JLabel InstructionsLabel; 
	private String Instructions; 
	
	/**
	 * constructor for the main window
	 */
	public MenuWindow(){
	setTitle("Helicopter Frenzy");
	setSize(1360, 740);
	mainBar = new JMenuBar();
	fileMenu = new JMenu("File");
	StartItem = new JMenuItem("Start Game");
	StartSItem = new JMenuItem("Start Server Game");
	exitItem = new JMenuItem("Exit");
	StartButton = new JButton("Start Game"); 
	ExitButton = new JButton("Exit Game"); 
	
	Instructions = "use keys A, S, D, F - UP, DOWN,LEFT, RIGHT FOR MOVEMENT, LEFT MOUSE BUTTON to fire bullets and RIGHT MOUSE BUTTON to fire rockets"; 
	
	//loads  the Background image
	try {
		BackGround = ImageIO.read(new File("images\\HelicopterFrenzy.jpg"));
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} 
	
	//closes the window when the exit button is clicked
	ExitButton.addActionListener(new ActionListener() 
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			MenuWindow.this.dispose();
			
		}
	});
	
	//closes the window when the exit item in menu bar is clicked
	exitItem.addActionListener(new ActionListener() 
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			MenuWindow.this.dispose();
			
		}
	});
	
	//adding menu items, labels and buttons to the frame and setting the layout 
	ListenerClass listener = new ListenerClass();
	ListenerClass2 listener2 = new ListenerClass2();
	StartItem.addActionListener(listener);
	StartSItem.addActionListener(listener2);
	StartButton.addActionListener(listener); 
	fileMenu.add(StartItem);
	fileMenu.add(StartSItem);
	fileMenu.add(exitItem);
	mainBar.add(fileMenu);
	setJMenuBar(mainBar);
	setLayout( new FlowLayout(FlowLayout.LEFT,10,0 ));
	Back = new JLabel(new ImageIcon(BackGround)); 
	InstructionsLabel = new JLabel(Instructions, JLabel.CENTER); 
	//add(StartButton); 
	//add(ExitButton); 
	add(InstructionsLabel); 
	add(Back); 
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
}

	/**
	 * class that listens for any action performed
	 */
	private class ListenerClass implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//creates a new Game Window
			try {
				GameWindow  win = new GameWindow();
				win.setSize(1360, 740);
				win.setLocationRelativeTo(null);
				win.setVisible(true);
				
			} catch (IOException e1) {
			
				e1.printStackTrace();
			} 
		}		
	}
	
	private class ListenerClass2 implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			GameClient clientFrame = new GameClient();
			clientFrame.setSize(350, 300);
			clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			clientFrame.setLocationRelativeTo(null);
			clientFrame.setVisible(true); 
		}		
	}
		
}
