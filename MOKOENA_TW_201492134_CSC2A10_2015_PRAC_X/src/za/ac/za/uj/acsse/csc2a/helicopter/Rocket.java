/**
 * @author Tsebo Mokoena
 */
package za.ac.za.uj.acsse.csc2a.helicopter;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Rocket {

	// Rocket position
    public int xCoord;
    public int yCoord; 
    
	// Damage made to an enemy helicopter when it is hit with a rocket.
	public  int damagePower = 100;
		    
	// Moving speed of the Rocket
	private static double XMovingSpeed;
	   
	// Image of enemy Rocket
	public  BufferedImage RocketImage;
	
		/**
	    * initializes the rocket using the x and y coordinate 
	    * @param xCoord
	    * @param yCoord
	    */
	    public Rocket(int xCoord, int yCoord)
	    {
	    	try {
	    		RocketImage =  ImageIO.read(new File("images\\rocket.gif"));
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
	    	
	        this.xCoord = xCoord;
	        this.yCoord = yCoord;
	        
	        Rocket.XMovingSpeed = 25;
	       
	    }
	    
	    /**
	     * Checks if the Rocket is left the screen.
	     * @return true if the enemy is left the screen, false otherwise.
	     */
	    public boolean HasLeftScreen()
	    {
	        if(xCoord > 0 && xCoord < 1360) 
	            return false;
	        else
	            return true;
	    }
	    
	    /**
	     * Updates position of Rocket.
	     */
	    public void Update()
	    {
	        xCoord += XMovingSpeed;
	    }
    
	    /**
	     * paints the Rocket
	     * @param g Graphics
	     */
	    public void paint(Graphics g)
	    {
	        g.drawImage(RocketImage, xCoord, yCoord, null);
	    }
	
}


