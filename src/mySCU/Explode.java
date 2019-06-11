package mySCU;

import java.awt.*;
import java.io.*;

import javax.imageio.ImageIO;


public class Explode {
	private int x,y;	
	private boolean live=true;	
	private PlayerClient tc=null;
	
	private int step=0;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();

	private static Image[] imgs = null;
	
	//确保第一张图片可以画出
	private static boolean init=false;
	
	static {
		imgs = new Image[] { tk.getImage(Explode.class.getClassLoader().getResource("images/blast1.gif")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/blast2.gif")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/blast3.gif")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/blast4.gif")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/blast5.gif")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/blast6.gif")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/blast7.gif")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/blast8.gif")) };
	}
	
	public Explode(int x,int y, PlayerClient tc) {
		this.x=x;
		this.y=y;
		this.tc=tc;
	}
	
	public void draw(Graphics g) {
		if(!init) {
			for(int i=0;i<imgs.length;i++) {
				g.drawImage(imgs[i], -100, -100, null);
			}
			init=true;
		}
		
		if(!live) {
			tc.explodes.remove(this);
			return;
		}
		
		if(step==imgs.length) {
			live=false;
			step=0;
			return;
		}
		//画出爆炸
		g.drawImage(imgs[step], x, y, 40, 40, null);
		step++;
	}
}
