package mySCU;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.*;
import javax.imageio.ImageIO;

public class Wall  {
	int x,y;
	private static int WIDTH;
	private static int HEIGHT;
	
	BufferedImage img1=null;
	//Í¼Æ¬µÄÂ·¾¶
	public void getImage() {
		try {
			img1=ImageIO.read(new File("src/images/wall2.jpg"));
			WIDTH=img1.getWidth();
			HEIGHT=img1.getHeight();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Wall(int x,int y) {
		this.x=x;
		this.y=y;
	}
	
	public void draw(Graphics g) {
		this.getImage();
		g.drawImage(img1, x, y, null);
		
	}
	
	public Rectangle getRect() {
		return new Rectangle(x,y,WIDTH, HEIGHT);
	}
}
