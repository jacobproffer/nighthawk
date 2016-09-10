//Name: Jacob Proffer
//Time Invested: 30 hours
//References: 
	//[1] http://euclid.nmu.edu/~mkowalcz/cs120/labs/18/
	//[2] https://www.youtube.com/watch?v=siwpn14IE7E

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Nighthawk extends Applet implements MouseListener, KeyListener, Runnable {
	private Bomber holder;
	private Missile[] missileGroup;
	private int randomMissileX;
	private int playerScore;
	private int randomMissileY;
	private boolean isRightKeyPressed;
	private boolean isLeftKeyPressed;
	private int cloudSpeed = 8;
	private int numberOfClouds;
	private int[] xPos;
	private int[] yPos;
	private int[] xSize;
	private int[] ySize;
	private int holderX;
	private	int holderY;
	private int mode;
	private int playSong;
	private boolean paused;
	
	Image gradient;
	Image cloud;
	Image start;
	Image restart;
	AudioClip loggins;
	
	public void init() {
		paused = false;
		mode = 0;
		playSong = 0;
		playerScore = 0;
		numberOfClouds = 40;
		missileGroup = new Missile[10];
		randomMissileX = (int)(Math.random() * 400);
		randomMissileY = (int)(Math.random() * -1600);
		missileGroup[0] = new Missile(randomMissileX, randomMissileY, this);
		isRightKeyPressed = false;
		isLeftKeyPressed = false;
		xPos = new int[numberOfClouds];
		yPos = new int[numberOfClouds];
		xSize = new int[numberOfClouds];
		ySize = new int[numberOfClouds];
		for(int i = 0; i < xPos.length; i++) {
			xPos[i] = (int)(Math.random() * 400);
			yPos[i] = (int)(Math.random() * 800);
			xSize[i] = (int)(Math.random() * 400) + 200;
			ySize[i] = (int)(Math.random() * 200) + 200;
		}
		holder = new Bomber(200, 400, this);
		gradient = getImage(getCodeBase(), "images/gradient.png");
		cloud = getImage(getCodeBase(), "images/cloud.png");
		start = getImage(getCodeBase(), "images/startMenu.png");
		restart = getImage(getCodeBase(), "images/restartMenu.png");
		//Kenny Loggins signed off on this, I promise
		loggins = getAudioClip(getCodeBase(), "sound/danger.wav"); //Downloaded from [2]
		Thread t = new Thread(this);
		t.start();
		//register this applet to receive mouse events
		addMouseListener(this);
		//register this applet to receive key events
		addKeyListener(this);
	}
	
	public void paint(Graphics g) {
		Image buffer = createImage(getSize().width, getSize().height); //Copied from [1]
		Graphics h = buffer.getGraphics(); //Copied from [1]
		h.drawImage(gradient, 0, 0, this); //Background gradient
		for(int i = 0; i < xPos.length; i++) {
			h.setColor(new Color(240, 240, 240, 50));
			h.drawImage(cloud, xPos[i], yPos[i], xSize[i], ySize[i], this);
		}
		for(int m = 0; m < missileGroup.length; m++) {
			if(missileGroup[m] != null) {
				missileGroup[m].paint(h);
			}
		}
		holder.paint(h);
		h.setColor(new Color(255, 255, 255));
		h.setFont(new Font("Helvetica", Font.PLAIN, 20));
		h.drawString("" + playerScore, 325, 50);
		if(paused == true) {
			h.setColor(new Color(0, 0, 0, 70));
			h.fillRect(0, 0, 400, 800);
		}
		if(mode == 0) {
			h.drawImage(start, 0, 0, this);
		}
		if(mode == 2) {
			h.drawImage(restart, 0, 0, this);
		}
		g.drawImage(buffer, 0, 0, this); //Copied from [1]
	}
	
	public void update(Graphics g) { //Copied from [1]
    	paint(g);
	}
	
	public void run() {
		while(true) {
			if(paused == false && mode == 1) {
				int gotOne = 0;
				//Pause 60 milliseconds
				try {Thread.sleep(60); } catch(Exception e) {}
				for(int m = 0; m < missileGroup.length; m++) {
					if(missileGroup[m] != null) {
						missileGroup[m].flight();
						holderX = holder.returnX();
						holderY = holder.returnY();
						double distance = missileGroup[m].distanceTo(holderX, holderY);
						//If missile distance is less than 28, plane and missile will hide
						if(distance < 28) {
							holder.hide();
							missileGroup[m].explode();
							mode = 2;
							//Let the user know they personally hurt Kenny Loggins
							System.out.println("YOU LET KENNY LOGGINS DOWN!");
						}
						if(missileGroup[m].getYPosition() >= 800) {
							gotOne = 1;
							playerScore += 10;
							missileGroup[m].reset();
						}
					}
					if(missileGroup[m] == null && gotOne == 1) {
						gotOne = 0;
						missileGroup[m] = new Missile(randomMissileX, randomMissileY, this);
						break;
					}
					if(playerScore > 1500) {
						missileGroup[m].changeSpeed();
					}
				}
				for(int i = 0; i < xPos.length; i++) {
					yPos[i] = yPos[i] + cloudSpeed;
					if(yPos[i] > 800) {
						xPos[i] = (int)(Math.random() * 400);
						yPos[i] = -400;
					}
				}
				if(isRightKeyPressed == true) {
					holder.flyRight();
				}
				if(isLeftKeyPressed == true) {
					holder.flyLeft();
				}
			}
			//schedule the screen to be repainted (by the other thread)
			repaint();
		}
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_D) {
			isRightKeyPressed = true;
			repaint();
		}
		if(e.getKeyCode() == KeyEvent.VK_A) {
			isLeftKeyPressed = true;
			repaint();
		}
		if(e.getKeyCode() == KeyEvent.VK_K) {
			if(playSong == 0) {
				playSong = 1;
				//Activates the Danger Zone wav file
				loggins.play();
				//Print out to the console that the user pressed the "special" key
				System.out.println("SOMEONE CALL KENNY LOGGINS, CUZ YOU'RE IN THE DANGER ZONE!");
			}
			else {
				playSong = 0;
				//Deactivates the Danger Zone wav file
				loggins.stop();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			if(mode == 1) {
				//Pauses the run method
				paused = !paused;
			}
			repaint();
		}
	}
	public void keyReleased(KeyEvent e) {
		isRightKeyPressed = false;
		isLeftKeyPressed = false;
	}
	public void keyTyped(KeyEvent e) {
	}
	
	public void mousePressed(MouseEvent e) {
		if (e.getX() > 117 && e.getX() < 282 && e.getY() > 430 && e.getY() < 478) {
			//Start menu
			if (mode == 0) {
				//This activates the run method
				mode = 1;
			}
			//Reset menu
			if (mode == 2) {
				mode = 1;
				holder.reset();
				for(int i = 0; i < missileGroup.length; i++) {
					missileGroup[i] = null;
				}
				missileGroup[0] = new Missile(randomMissileX, randomMissileY, this);
				playerScore = 0;
				repaint();
			}
		}
	}
	public void mouseReleased(MouseEvent e) {
	}
	public void mouseClicked(MouseEvent e) {
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	
}