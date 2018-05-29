/**
 * @author Tsebo Mokoena
 */
package za.ac.za.uj.acsse.csc2a.helicopter;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;


import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import SMTP.EmailGUI;

/**
 * class responsible for the execution of the game
 *
 */
public class Game extends JPanel {

	//file path
	private  String FilePath = "data\\game.txt"; 
	
	//file and scanner variables 
	private File HeliCopFile = new File(FilePath);
	private Scanner HeliCopIn; 
	
	//image variables 
	private BufferedImage BackGround; 
   	private BufferedImage Explosion; 
   	private BufferedImage GameOver; 
   	private BufferedImage GameWin; 
   	
   	//game character variables 
   	private Player pl; 
   	private Enemy eh; 
   	private Bullet bul; 
   	private Rocket rock; 
	
	//sets the font
   	private Font font ; 
	
	//boolean variable for game state 
   	private boolean GameOn = true;
   	private boolean WinsState = false; 
   	private boolean BulletFired = false; 
	private boolean RocketFired = false;
	private boolean stop = false;
	
	//explosion coordinates 
   	private int enExplosionX = - 1300; 
   	private int enExplosionY = -1000; 
   	private int plExplosionX = - 1300; 
   	private int plExplosionY = -1000; 
	
	 //enemy helicopter list
    private ArrayList<Enemy> EnemyList = new ArrayList<Enemy>();
    
    //bullet list
    private ArrayList<Bullet> BulletList = new ArrayList<Bullet>();
    
    //rocket list
    private ArrayList<Rocket> RocketList = new ArrayList<Rocket>(); 
    
    //enemy stat variables
    private int RunAwayEnemies;
    private int DestroyedEnemies;
    
    //random number 
	private int RandNum = 0 ;

	private Timer time;

	private int delay = 16;
	String Data; 
	EmailGUI Email; 
	/**
	 * Constructor for a new game 
	 * @throws IOException
	 */
	 public Game() throws IOException
	 {
		 //reading the game.txt file 
		 try
			{
			 	HeliCopIn = new Scanner(HeliCopFile);
				String line = "";
				while ( HeliCopIn.hasNext() )
				{
					line = HeliCopIn.nextLine();
					StringTokenizer GameTokenizer = new StringTokenizer(line);
					if ( !GameTokenizer.hasMoreTokens() || GameTokenizer.countTokens() != 5 )
					{
						continue;
					}
					
					int XCoord = Integer.parseInt(GameTokenizer.nextToken());
					int YCoord = Integer.parseInt(GameTokenizer.nextToken());
					int health = Integer.parseInt(GameTokenizer.nextToken());
					int NumRocketsStart = Integer.parseInt(GameTokenizer.nextToken());
					int NumBulletsStart = Integer.parseInt(GameTokenizer.nextToken());
					if (Player.Validate(line) == true)
					{
						//creating a new player and initialising the number of bullets; 
					   pl = new Player(XCoord,YCoord,health,NumRocketsStart,NumBulletsStart); 
					   pl.NumBullets = pl.GetNumBulletsStart(); 
					   pl.NumRockets = pl.GetnumRocketsStart(); 
					   
					//	System.out.println(XCoord) ;
						//System.out.println(YCoord) ;
						//System.out.println(health) ;
						//System.out.println(NumRocketsStart) ;
						//System.out.println(NumBulletsStart) ;
					}
					
				
				}
			}
			catch (FileNotFoundException ex)
			{
				ex.printStackTrace();
			}
			
			finally
			{
				if ( HeliCopIn != null )
				{
					HeliCopIn.close();
				}
			}
		 
		 
		 //loading the background image and explosion image
		 try {
				BackGround = ImageIO.read(new File("images\\finalcloud.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			} 
			
			try {
				Explosion = ImageIO.read(new File("images\\explosion.gif"));
			} catch (IOException e) {
				e.printStackTrace();
			} 
			
			try {
				GameOver = ImageIO.read(new File("images\\gameover.gif"));
			} catch (IOException e) {
				e.printStackTrace();
			} 
			
			try {
				GameWin = ImageIO.read(new File("images\\gamewin.gif"));
			} catch (IOException e) {
				e.printStackTrace();
			} 
	
									
			update();
			
			int del = 2000; 
		 // createEnemyHelicopter(); 
			time = new Timer(del , new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					createEnemyHelicopter(); 
					
				}

				
			});
			time.start();
			
			time = new Timer(delay , new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					if (GameOn==true ){
						   updateEnemies(); 
				    	   UpdateBullets(); 
				    	   UpdateRockets(); 
				    	   pl.Update(); 
				    	   repaint();
						}
					
					
			    	   try {
						WiriteToBinary();
					} catch (IOException e1) {
					
						e1.printStackTrace();
					} 
				}

			});
			time.start();
		
			//WiriteToBinary(); 
			
	 }
	 
	 /**
	  * function the update the game as well as the game characters
	  */
	 public void update(){
			//Key listener for keyboard input 
			addKeyListener(new KeyAdapter(){
				

				@Override
				public void keyPressed(KeyEvent e)
				{			
					 	// Moving on the x axis.
				       if((e.getKeyCode() == KeyEvent.VK_D) || e.getKeyCode() == (KeyEvent.VK_RIGHT))
				           pl.moveXspeed += pl.accelXspeed;
				       		
				       else if(e.getKeyCode() == (KeyEvent.VK_A) || e.getKeyCode() == (KeyEvent.VK_LEFT))
				           pl.moveXspeed -= pl.accelXspeed;
				     
				       // Moving on the y axis.
				       if(e.getKeyCode() == (KeyEvent.VK_W) || e.getKeyCode() == (KeyEvent.VK_UP))
				           pl.moveYspeed -= pl.accelYspeed;
				       else if(e.getKeyCode() == (KeyEvent.VK_S) || e.getKeyCode() == (KeyEvent.VK_DOWN))
				           pl.moveYspeed += pl.accelYspeed;
				       else
				       {
				    	   
				       }
				   
				       if (GameOn == true){
				    	
				       }
				       else if (GameOn == false )
				       {
				    	   repaint(); 
				    	  
				    	   							
						Data = " Number of rockets remaining: "  + pl.NumRockets 
							+ "Number of bullets remaining: " +pl.NumBullets
							+ " Remaining health: " +pl.Health
							+" Number of runway enemies: " + RunAwayEnemies
							+ " Number of destroyed enemies: " +DestroyedEnemies;
				      
				    	   Email = new EmailGUI(Data); 
				    	  
				       }
				       
				     /*  else if (WinsState == true )
				       {
				    	   repaint(); 
				    	   //*******************stoip game here and send email with game details
				    	   Data = " Number of rockets remaining: "  + pl.NumRockets +"," 
									+ " Number of bullets remaining: " +pl.NumBullets+","
									+ " Remaining health: " +pl.Health+","
									+ " Number of destroyed enemies: " +DestroyedEnemies+","
									+" Number of runway enemies: " + RunAwayEnemies;
								
						        
						    	   Email = new EmailGUI(Data); 
						    	   
				       }*/
				
				}
				
			});
			
			//mouse listener for mouse input 
			addMouseListener(new MouseListener()
			{
				@Override
				public void mouseClicked(MouseEvent e) {
				//actions performed when mouse button 1 is pressed 	
				if (e.getButton() == MouseEvent.BUTTON1)
				{
					if (pl.NumBullets > 0)
					{
							//creates shoot and adds the to the list
							pl.Shoot(); 
							BulletFired = true; 
							bul = new Bullet(pl.GetXCoord() + 240,pl.GetYCoord() + 70); 
							BulletList.add(bul); 
														
						}
						else if (pl.NumBullets <= 0)
						{
							
							GameOn = false;
						}
						
				}
				
				//actions performed when mouse button 2 is pressed 	
					 if (e.getButton() == MouseEvent.BUTTON3)
					{

						if (pl.NumRockets > 0)
						{
							//creates rockets and adds the to the list
							pl.FireRocket(); 
							RocketFired  = true; 
							rock = new Rocket(pl.GetXCoord() + 200,pl.GetYCoord() + 60); 
							RocketList.add(rock); 
							
						}
						
					}
				}

				@Override
				public void mousePressed(MouseEvent e) {		
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}
				
			}); 
		
		}
	
	 /**
	  * creates an instance of the enemy
	  */
	 private void createEnemyHelicopter()
	    {
	       		RandNum = 0 + (int)(Math.random()*700);
		   		int xCoordinate = 1400;
	            int yCoordinate = RandNum; 
	            eh = new Enemy(xCoordinate,yCoordinate);
	            
	            // Add created enemy to the list of enemies.
	            EnemyList.add(eh);
	            
	    }
	        
	 /**
	  * Updates all enemies.
	  * Move the enemy and checks if it has left the screen.
	  * Checks whether enemy was destroyed.
	  * Checks whether any enemy collided with player.
	  */
	 private void updateEnemies()
	    
	    {
	        for(int i = 0; i < EnemyList.size(); i++)
	        {
	        	//get and update enemy for the enemy list
	            Enemy eh = EnemyList.get(i);
	            eh.Update();
	            
	            //checks for enemy and player crashes 
	            Rectangle playerRectangel = new Rectangle(pl.xCoord, pl.yCoord, pl.PlayerImage.getWidth(), pl.PlayerImage.getHeight());
	            Rectangle enemyRectangel = new Rectangle(eh.xCoord, eh.yCoord, eh.EnemyHelicopterImage.getWidth(), eh.EnemyHelicopterImage.getHeight());
	            if(playerRectangel.intersects(enemyRectangel)){
	                
	            	//set player health to zero
	            	pl.Health = 0;
	                
	                // Remove helicopter from the list.
	                EnemyList.remove(i);
	                
	                //creates new explosion coordinates 
	                enExplosionX = eh.xCoord; 
	                enExplosionY = eh.yCoord; 
	                plExplosionX = pl.xCoord+120;
	                plExplosionY = pl.yCoord;
	                GameOn = false;
	                
	            }
	            
	            // Check health.
	            if(eh.Health <= 0){
	           
	            // Increase the  number of destroyed enemies and removes a helicopter froma list.
	            DestroyedEnemies++;
	            
	           
	            if(DestroyedEnemies == 10)
	            {
	            	//WinsState = true; 
	            	
	            }
	            EnemyList.remove(i);
	               
	           }
	            
	            // If the current enemy has left the scree it is  removed from the list and the number of runAwayEnemies is increased.
	            if(eh.HasLeftScreen())
	            {
	            	EnemyList.remove(i);
	                RunAwayEnemies++;
	            }
	            
	            if(RunAwayEnemies > 5)
	            {
	            	GameOn = false; 
	            	
	            }
	        }
	        
	    } 
	
	 	/**
	     * Update bullets. 
	     * It moves bullets.
	     * Checks whether the bullet has left the screen.
	     * Checks whether the bullets hit an enemy or not.
	     */
	    private void UpdateBullets()
	    {
	        for(int i = 0; i < BulletList.size(); i++)
	        {
	            Bullet bullet = BulletList.get(i);
	            
	            // Move the bullet.
	            bullet.Update();
	            
	            // checks if the bullet has it left the screen
	            if(bullet.HasLeftScreen()){
	                BulletList.remove(i);
	                
	                continue;
	            }
	            
	            // checks if the bullet hit the enemy
	           Rectangle BulletRectangle = new Rectangle((int)bullet.xCoord, (int)bullet.yCoord, bullet.BulletImage.getWidth(), bullet.BulletImage.getHeight());
	            // Go trough all enemies.
	            for(int j = 0; j < EnemyList.size(); j++)
	            {
	                Enemy eh = EnemyList.get(j);

	                
	                Rectangle EnemyRectangel = new Rectangle(eh.xCoord, eh.yCoord, eh.EnemyHelicopterImage.getWidth(), eh.EnemyHelicopterImage.getHeight());

	                // Checks whether the the bullet has hit the enemy
	                if(BulletRectangle.intersects(EnemyRectangel))
	                {
	                    // Bullet hit the enemy so we reduce his health.
	                    eh.Health -= Bullet.DamagePower;
	                    
	                    // Bullet was also destroyed so we remove it.
	                    BulletList.remove(i);
	                    
	                    
	                }
	            }
	        }
	    }

	    /**
	     * Update rockets. 
	     * It moves rocket and add smoke behind it.
	     * Checks whether the rocket has left the screen.
	     * Checks whether any rocket is hit any enemy.
	    */
	    private void UpdateRockets()
	    {
	        for(int i = 0; i < RocketList.size(); i++)
	        {
	            Rocket rocket = RocketList.get(i);
	            
	            // Moves the rocket.
	            rocket.Update();
	            
	            // Checks if it the rocket has left the screen.
	            if(rocket.HasLeftScreen())
	            {
	                RocketList.remove(i);
	               
	            }
	            
	            // Checks if current rocket hit any enemy.
	            if( HasRocketHitEnemy(rocket) )
	                // Removes the rocket
	                RocketList.remove(i);
	        }
	    }
	    
	    /**
	     * Checks if the given rocket is hit any of enemy helicopters.
	     * @param rocket Rocket to check.
	     * @return True rocket hit any of enemy helicopters false otherwise.
	     */
	    private boolean HasRocketHitEnemy(Rocket rocket)
	    {
	        boolean HitEnemy = false;
	        
	        
	        Rectangle rocketRectangle = new Rectangle(rocket.xCoord, rocket.yCoord,rocket.RocketImage.getWidth(),  rocket.RocketImage.getHeight());
	        
	        // Go through all enemies.
	        for(int j = 0; j < EnemyList.size(); j++)
	        {
	            Enemy eh = EnemyList.get(j);

	            // Current enemy rectangle.
	            Rectangle enemyRectangel = new Rectangle(eh.xCoord, eh.yCoord, eh.EnemyHelicopterImage.getWidth(), eh.EnemyHelicopterImage.getHeight());

	            // Is current rocket over current enemy?
	            if(rocketRectangle.intersects(enemyRectangel))
	            {
	            	HitEnemy = true;
	                
	                // Rocket hit the enemy so we reduce his health.
	                eh.Health -= rocket.damagePower;
	                
	                // Rocket hit enemy so we don't need to check other enemies.
	                break;
	            }
	        }
	        
	        return HitEnemy;
	    }

	    /**
	     * Draws all game images 
	     * @param g Graphics
	     */
	    protected void paintComponent(Graphics g)
		{
			super.paintComponent(g); 
			//drawing the background
			g.drawImage(BackGround, 0, 0, null); 
		
			//if(GameOn == true)
			//{
			//calling the paint function of the game characters
			pl.paint(g);
		
			
			for(int i = 0 ; i < EnemyList.size(); i++)
			{
				EnemyList.get(i).paint(g);
			}
			
			
			//drawing the explosions
			g.drawImage(Explosion, enExplosionX, enExplosionY, null); 
			g.drawImage(Explosion, plExplosionX, plExplosionY, null); 
		
			font = new Font("LCD", Font.BOLD, 25);
			g.setFont(font);
			
			//g.drawString("Time: " + formatTime(gameTime),			0, 615);
		    g.drawString("Rockets Remaining: " + pl.NumRockets, 	0, 635);
		    g.drawString("Bullets Remaining: " + pl.NumBullets,    	0, 655);
		    g.drawString("Enemies Destroyed: " + DestroyedEnemies,  0, 675);
		    g.drawString("Runaway Enemies: "   + RunAwayEnemies,    0, 695); 
		//}
		    
			if(BulletFired = true)
		    {
		    	for(int i = 0; i < BulletList.size();i++)
		    	{
		    		BulletList.get(i).paint(g); 
		    	}
		    }
		    
		  		    
		    if(RocketFired = true)
		    {
		    	for(int i = 0; i < RocketList.size();i++)
		    	{
		    		RocketList.get(i).paint(g); 
		    	}
		    }
			if(GameOn == false){
		    	g.drawImage(GameOver, 200, 0, null);
		    	//g.drawString("Enemies Destroyed: " + DestroyedEnemies,  250, 10);
		    }
		    else if (WinsState == true )
		    {
		    	g.drawImage(GameWin, 200, 0, null);
		    
		    }
		    
		    
		    
		  
		}
	    
	    /**
	     * Writes the currents game stats to a binary file
	     * @throws IOException
	     */
	    public void WiriteToBinary() throws IOException
	    {
				DataOutputStream Output = new DataOutputStream (new FileOutputStream("GameProgress.dat", true));
				
				//*************************************information to send using smtp
				Output.write(pl.xCoord);
				Output.write(pl.yCoord);
				Output.write(pl.NumRockets);	
				Output.write(pl.NumBullets); 
				Output.write(pl.Health); 
				Output.write(DestroyedEnemies);   
				Output.write(RunAwayEnemies);     
				
				for (int i = 0 ; i < EnemyList.size(); i++ )
				{
					Output.write(EnemyList.get(i).xCoord); 
					Output.write(EnemyList.get(i).xCoord); 
					Output.write(EnemyList.get(i).Health); 
				}
				
			Output.close(); 
	    }
	    
	    /**
	     * Reads the currents game stats from a binary file
	     * @throws IOException
	     */
	    public void ReadFromBinary() throws IOException
	    {
	    	
	    	DataInputStream Input = new DataInputStream(new FileInputStream("GameProgress.dat"));
	    	
	    		pl.xCoord = Input.read(); 
	    		pl.xCoord = Input.read(); 
				pl.NumRockets = Input.read(); 
				pl.NumBullets = Input.read();
				pl.Health =Input.read();
				DestroyedEnemies =Input.read();
				RunAwayEnemies =Input.read();
				
				int x, y = 0; 
				
				for (int i = 0 ; i < EnemyList.size(); i++ )
				{
					x = Input.read();
					y = Input.read();
					eh = new Enemy(x,y);
					EnemyList.add(eh);
				
				}
				Input.close(); 
	    }
	    
	 
}
