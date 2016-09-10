//Name: Jacob Proffer
//Time Invested: 30 hours
//References: 
	//[1] http://euclid.nmu.edu/~mkowalcz/cs120/notes/42%20-%20dinosaur%20jump%20and%20chair%20battle/2/Chair.java

import java.applet.*;
import java.awt.*;

public class Missile {
	private int x;
	private int y;
	private static int speed;
	private boolean onScreen;
	Image missile;
	//reference to the main applet.
	Applet mainApplet;
	
	public Missile(int misXPos, int misYPos, Applet a) {
		x = misXPos;
		y = misYPos;
		speed = 24;
		onScreen = true;
		mainApplet = a;
		missile = mainApplet.getImage(mainApplet.getCodeBase(), "images/missile.png");
	}
	
	public int getYPosition() {
		return y;
	}
	
	public static void changeSpeed() {
		speed = speed + 10;
		if(speed > 30) {
			speed = 30;
		}
	}
	
	public void flight() {
		y += speed;
	}

	public void explode() {
		onScreen = false;
	}
	
	public void reset() {
		x = (int)(Math.random() * 400);
		y = (int)(Math.random() * -1600);
	}

	//Copied from [1]
	public double distanceTo(int someX, int someY) {
		int dX = someX - x; 
		int dY = someY - y;
		return Math.sqrt(dX*dX + dY*dY);
	}
	
	public void paint(Graphics g) {
		if(onScreen == true) {
			g.drawImage(missile, x - 20, y - 20, mainApplet);
		}
	}
	
}