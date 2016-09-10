//Name: Jacob Proffer
//Time Invested: 30 hours
//References: None

import java.applet.*;
import java.awt.*;

public class Bomber {
	private int x;
	private int y;
	private boolean onScreen; //true means draw it.  false means don't.
	
	Image holder;
	Applet mainApplet;
	
	public Bomber(int xCord, int yCord, Applet a) {
		x = xCord;
		y = yCord;
		mainApplet = a;
		holder = mainApplet.getImage(mainApplet.getCodeBase(), "images/holder.png");
		onScreen = true;
	}
	
	public void hide() {
		onScreen = false;
	}

	public void reset() {
		onScreen = true;
		x = 200;
		y = 400;
	}

	public int returnX() {
		return x;
	}

	public int returnY() {
		return y;
	}
	
	//Method to fly right
	public void flyRight() {
		x += 14;
		if(x >= 350) {
			x = 350;
		}
	}
	
	//Method to fly left
	public void flyLeft() {
		x -= 14;
		if(x <= 50) {
			x = 50;
		}
	}
	
	//Paint method
	public void paint(Graphics g) {
		if(onScreen == true) {
			g.drawImage(holder, x - 34, y - 34, mainApplet);
		}
	}
	
}