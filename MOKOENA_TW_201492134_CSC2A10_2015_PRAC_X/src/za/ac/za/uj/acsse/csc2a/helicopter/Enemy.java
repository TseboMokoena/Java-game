/**
 * @author TSebo Mokoena
 */
package za.ac.za.uj.acsse.csc2a.helicopter;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

public class Enemy {
	   
	// Enemy position
    public int xCoord;
    public int yCoord;
    
    // Health of the helicopter.
    public int Health;
       
    // Moving speed of the enemy
     private static double XMovingSpeed;
    
    // Images of enemy helicopter
    public  BufferedImage EnemyHelicopterImage;
      
   /**
    * initializes the enemy using the x and y coordinate 
    * @param xCoord
    * @param yCoord
    */
    public Enemy (int xCoord, int yCoord) {
		
		try {
			EnemyHelicopterImage =  ImageIO.read(new File("images\\enemy.gif"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		Health = 100;
	        
	    // Sets position of the enemy.
		this.xCoord = xCoord;
        this.yCoord = yCoord;
	        
	    // Moving speed of enemy.
	    Enemy.XMovingSpeed = -2;
		
	}
        
    /**
    * It increase enemy speed.
    */
    public void speedUp(){
      
    	XMovingSpeed -= 0.25;
    	Update(); 
    }
    
    /**
     * Checks if the enemy is left the screen.
     * @return true if the enemy is left the screen, false otherwise.
     */
    public boolean HasLeftScreen()
    {
        if(xCoord < 0 - EnemyHelicopterImage.getWidth()) 
            return true;
        else
            return false;
    }
    
    /**
     * Updates position of enemy.
     */
    public void Update()
    {
        // Move enemy on x coordinate.
        xCoord += XMovingSpeed;
        
     }
    
    /**
     * paints the enemy 
     * @param g Graphics
     */
    public void paint(Graphics g)
    { 
         g.drawImage(EnemyHelicopterImage, xCoord, yCoord, null);
    }

}

