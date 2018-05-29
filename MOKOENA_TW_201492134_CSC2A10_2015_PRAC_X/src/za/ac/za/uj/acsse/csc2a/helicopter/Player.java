/**
 * @author Tsebo Mokoena
 */
package za.ac.za.uj.acsse.csc2a.helicopter;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.event.*;

public class Player extends JPanel  {

	//Player pattern 
	private static Pattern PlayerPat = Pattern.compile("\\d+\\s\\d+\\s\\d+\\s\\d+\\s\\d+");

	//Image of the player
	BufferedImage PlayerImage ; 
	
	//Helicopter health
    private int HeliHealth ; 
    public int Health;
   
	//Helicopter Position.
    public int xCoord ; 
    public int yCoord ; 
    
    //Helicopter rockets.
    public int NumRocketsStart ;
    public int NumRockets;
    
    //Helicopter bullets
    public int NumBulletsStart;
    public int NumBullets;
    
    //Moving speed of the helicopter
    public double moveXspeed=0;
    public double moveYspeed= 0;
    public double accelXspeed = 0.25;
    public double accelYspeed = 0.25;

	
   /**
    * Constructor for initialising the player
    * @param XCoord
    * @param YCoord
    * @param Health
    * @param NumRocketsStart
    * @param NumBulletsStart
    * @throws IOException
    * 
    */
	public Player(int XCoord,int YCoord,int Health,int NumRocketsStart,int NumBulletsStart) throws IOException{
		 this.xCoord = XCoord; 
		 this.yCoord = YCoord; 
    	 this.HeliHealth = Health;
         this.NumRocketsStart = NumRocketsStart;
         this.NumBulletsStart = NumBulletsStart;
        
         //loading the player image
		try {
			PlayerImage =  ImageIO.read(new File("images\\Player.gif"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
		Update();
	}
	
	/**
	* function to check which line in the file belongs to the Player
	*/
	 public static boolean Validate(String line)
	 {
		if (PlayerPat.matcher(line).matches())
		{
			return true;
		}
		return false;
	 }
	 
	 /**
	  *  Function that updates the x and y coordinate of the player 
	  */
	 public void Update()
	   {
	       // Move helicopter and its propellers.
	       xCoord += moveXspeed;
	       yCoord += moveYspeed;
	    // System.out.println(xCoord+ "-" +yCoord); 
	   }
	  
	 /**
	  *  Function that updates the number of bullets of the player 
	  */
	 public void Shoot()
	  {
		  if(NumBulletsStart > 0)
		  {
			  NumBullets = (NumBulletsStart --) - 1; 
			//  System.out.println("Shooting" + NumBullets);
		
		  }  
	  }
	  
	  /**
	   *  Function that updates the number of rockets of the player 
	   */
	 public void FireRocket()
	  {
		
		  if(NumRocketsStart > 0)
		  {
			  NumRockets = (NumRocketsStart --) - 1; 
			//  System.out.println("Launching" + NumRockets);
			 
		  }
	  }
	 
	 /**
	* Overriding toString in order to information about the player
	*/
	@Override
	 public String toString()
	  {
		return String.format("%d\t%d\t%d\t%d\t%d" , xCoord, yCoord, HeliHealth ,NumRocketsStart , NumBulletsStart);		
	  };
		
	/**
	 * paints the player image 
	 * @param g Graphics
	 * 
	 */
	public void paint (Graphics g)
	{
		g.drawImage(PlayerImage, xCoord, yCoord, null); 
	}
	
	/**
	 * sets the x coordinate
	 * @param XCoord
	 * 
	 */
	public void SetXCoord(int XCoord)
	{
		this.xCoord = XCoord; 
	}
	
	/**
	 * 
	 * @return xCoord
	 */
	public int GetXCoord()
	{
		return xCoord;
	}
	
	/**
	 *  sets the y coordinate 
	 * @param YCoord
	 *
	 */
	public void SetYCoord(int YCoord)
	{
		this.xCoord = YCoord; 
	}
	
	/**
	 * 
	 * @return yCoord
	 */
	public int  GetYCoord()
	{
		return yCoord; 
	}
	
	/**
	 * sets the health 
	 * @param Health
	 * 
	 */
	public void SetHealth(int Health)
    {
    	this.HeliHealth = Health; 
    }
    
	/**
	 * 
	 * @return HeliHealth
	 */
    public int GetHealth()
    {
    	return HeliHealth; 
    }
    
    /**
     * sets the number of initial rockets
     * @param NumRocketsStart
     * 
     */
    public void SetnumRocketsStart(int NumRocketsStart)
    {
    	this.NumRocketsStart = NumRocketsStart; 
    }
    
    /**
     * 
     * @return NumRocketsStart
     */
    public int GetnumRocketsStart()
    {
    	return NumRocketsStart; 
    }
    
    /**
     * Sets the number of initial bullets 
     * @param NumBulletsStart
     * 
     */
    public void SetNumBulletsStart(int NumBulletsStart )
    {
    	this.NumBulletsStart = NumBulletsStart; 
    }

    /**
     * 
     * @return NumBulletsStart
     */
    public int GetNumBulletsStart()
    {
    	return NumBulletsStart; 
    }

}
