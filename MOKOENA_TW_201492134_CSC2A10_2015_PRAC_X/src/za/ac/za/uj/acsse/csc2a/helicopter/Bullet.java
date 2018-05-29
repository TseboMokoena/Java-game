/**
 * @author Tsebo Mokoena
 */
package za.ac.za.uj.acsse.csc2a.helicopter;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bullet {
   
	//x and y coordinates of the bullet
	public int xCoord;
	public int yCoord;
    
	// Damage made to an enemy helicopter when it is hit with a bullet.
    public static int DamagePower = 30;
    
    // Moving speed of the bullet
    private static int bulletSpeed = 25;
    private double movingXspeed;
   
    // Image of enemy Rocket
    public BufferedImage BulletImage;
        
    /**
    * initializes the bullet using the x and y coordinate 
    * @param xCoord
    * @param yCoord
	*/
    public Bullet(int xCoord, int yCoord)
    {
    	try {
    		BulletImage =  ImageIO.read(new File("images\\bullet.gif"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
    	
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        
        setSpeed();
    }
    
    
    /**
     * Calculate new moving speed on the x axis.
     * 
     * @param mousePosition 
     */
    private void setSpeed()
    {  
       
        this.movingXspeed += bulletSpeed ; 
       
    }
    
     /**
     * Checks if the bullet is left the screen.
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
     * updates the position of the bullet.
     */
    public void Update()
    {
        xCoord += movingXspeed;
        
    }
   
    /**
     * paints the Rocket
     * @param g Graphics
     */
    public void paint(Graphics g)
    {
        g.drawImage(BulletImage, xCoord, yCoord, null);
    }

}


