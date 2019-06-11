package mySCU;

import java.awt.*;

public class Blood {
	private int x=(int) (Math.random()*800);
	private int y=(int) (Math.random()*600);
	//定义血块的速度
	private static final int XSPEED=6;
	private static final int YSPEED=6;
	
	private static  int WIDTH=0;
	private static  int HEIGHT=0;
	
	private boolean live=true;
	
	private Direction dir=Direction.STOP;
	PlayerClient tc=null;
	
	private int step=(int) (Math.random()*10+3);
	
	private static Toolkit tk=Toolkit.getDefaultToolkit();
	
	private static Image img1=tk.getImage(Blood.class.getClassLoader().getResource("images/star.gif"));
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public Blood(PlayerClient tc) {
		this.tc=tc;
	}
	
	public void draw(Graphics g) {
		if(this.isLive()) {
			g.drawImage(img1, x, y, null);
		}
		
		move();
	}

	private void move() {
		switch(dir) {
		case L:
			x-=XSPEED;
			break;
		case LU:
			x-=XSPEED;
			y-=YSPEED;
			break;
		case U:
			y-=YSPEED;
			break;
		case RU:
			x+=XSPEED;
			y-=YSPEED;
			break;
		case R:
			x+=XSPEED;
			break;
		case RD:
			x+=XSPEED;
			y+=YSPEED;
			break;
		case D:
			y+=YSPEED;
			break;
		case LD:
			x-=XSPEED;
			y+=YSPEED;
			break;
		}
		
		if(step==0) {
			step=(int) (Math.random()*10+3);
			Direction dirs[]=Direction.values();
			int r=(int) (Math.random()*8);
			dir=dirs[r];
		}else {
			step--;
		}
		
		WIDTH=img1.getWidth(null);
		HEIGHT=img1.getHeight(null);		
		//设置血块不能出墙
		if(x<0) x=0;
		if(y<65) y=65;
		if(x+WIDTH>tc.GAME_WIDTH) x=tc.GAME_WIDTH-WIDTH;
		if(y+HEIGHT>tc.GAME_HEIGHT) y=tc.GAME_HEIGHT-HEIGHT;
	}
	
	public Rectangle getRect() {
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}
}
