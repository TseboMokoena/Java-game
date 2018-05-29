/**
 * @author Tsebo Mokoena
 */
package za.ac.za.uj.acsse.csc2a.gui;

import java.io.IOException;

import javax.swing.JFrame;

import za.ac.za.uj.acsse.csc2a.helicopter.Game;

/*
 * class for the Game window 
 */
public class GameWindow extends JFrame{
	
	private Game Game;
/*
 * constructor for the adding of a new game panel 
 */
public GameWindow() throws IOException{
	//new game is created 
	try {
		Game  = new Game();
	} catch (IOException e1) {
		e1.printStackTrace();
	} 
	
	add(Game); 
	 Game.setFocusable(true); 
}
		
}
	
	
