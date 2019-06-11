package mySCU;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class Missile {
	private int x,y;
	
	//子弹的方向
	private Direction dir;
	//持有playerClient的引用
	private PlayerClient tc=null;
	//子弹是否还存活
	private boolean live=true;
	//判断子弹的好坏
	private boolean good=true;
	
	//子弹的速度
	private static  int XSPEED=15;
	private static  int YSPEED=15;
	
	public static final int WIDTH=10;
	public static final int HEIGHT=10;
	
	public Missile(int x,int y,boolean good, Direction dir) {
		this.x=x;
		this.y=y;
		this.good=good;
		this.dir=dir;
	}
	
	public boolean isLive() {
		return live;
	}

	public static void setXSPEED(int xSPEED) {
		XSPEED = xSPEED;
	}

	public static void setYSPEED(int ySPEED) {
		YSPEED = ySPEED;
	}

	public void draw(Graphics g) {
		if(!live) return;
		
		Color c=g.getColor();
		if(good) {
			g.setColor(Color.RED);
		}else {
			g.setColor(Color.GREEN);
		}
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		
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
		if(x<0||y<0||x>PlayerClient.GAME_WIDTH||y>PlayerClient.GAME_HEIGHT) {
			live=false;
		}
	}
	
	//定义一个辅助类，Rectangel可以获得包在坦克外面的方块
	public Rectangle getRect() {
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}
	
	//判断是否击中
	//intersects可以判断两个方块是否相交
	public boolean hitEntity(Entity t) {
		//子弹活着
		if(this.live&&this.getRect().intersects(t.getRect())&&t.isLive()&&this.good!=t.isGood()) {
			if(t.isGood()) {
				t.setLife(t.getLife()-20);
				if(t.getLife()<=0) t.setLive(false);
			}else {
				t.setLive(false);
			}
			
			this.live=false;			
			//爆炸开始，加入集合
			Explode e=new Explode(x-t.getWidth()/3/2,y-t.getHeight()/3/2,t.tc);
			t.tc.explodes.add(e);
			return true;
		}
		return false;
	}
	
	//判断是否击中敌人的集合
	public boolean hitEntitys(Vector<Entity> ts) {
		for(int i=0;i<ts.size();i++) {
			if(hitEntity(ts.get(i))) {
				return true;
			}
		}
		return false;
	}
	
	//判断是否撞到墙
	public boolean collideWithwall(Wall w) {
		if(this.live&&this.getRect().intersects(w.getRect())) {
			this.live=false;
			return true;
		}
		return false;
	}
	
	public boolean collideWithwalls(Vector<Wall> ws) {
		for(int i=0;i<ws.size();i++) {
			if(collideWithwall(ws.get(i))) {
				return true;
			}
		}
		return false;
	}
}
