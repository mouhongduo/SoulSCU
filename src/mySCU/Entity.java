package mySCU;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.ImageObserver;
import java.util.Vector;


public class Entity implements KeyListener{
	private static final int WIDTH=30;
	private static final int HEIGHT=30;
	
	//玩家的速度，不可变
	private static int XSPEED=10;
	private static int YSPEED=10;
	
	private boolean good=true;
	
	//定义个体位置
	private int x,y;
	//个体的上一步位置
	private int oldX,oldY;
	
	//定义个体的生命
	private int life=100;
	private BloodBar bb=new BloodBar();
	
	private boolean bL=false,bU=false,bR=false,bD=false;
		
	//个体的方向，默认为静止
	private Direction dir=Direction.STOP;
	//定义个体的方向
	private Direction ptDir=Direction.D;
	
	PlayerClient tc=null;
	//定义个体生死
	private boolean live=true;
	
	//让个体移动固定的步数后再转方向3-15步
	private int step=(int) (Math.random()*12)+3;

	public Entity(int x, int y,boolean good,Direction dir,PlayerClient tc) {
		this.x = x;
		this.y = y;
		this.oldX=x;
		this.oldY=y;
		this.good=good;
		this.dir=dir;
		this.tc=tc;
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}	

	public static int getWidth() {
		return WIDTH;
	}

	public static int getHeight() {
		return HEIGHT;
	}

	public boolean isGood() {
		return good;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
	
	public static void setXSPEED(int xSPEED) {
		XSPEED = xSPEED;
	}

	
	public static int getXSPEED() {
		return XSPEED;
	}

	public static void setYSPEED(int ySPEED) {
		YSPEED = ySPEED;
	}

	//画出个体的函数
	public void draw(Graphics g) {
		
		//个体死亡就消失
		if(!live) {
			if(!good) {
				tc.enemies.remove(this);
			}
			return;
		}
		
		
		if(good) {
			Image image2=Toolkit.getDefaultToolkit().getImage(Start.class.getResource("/images/p1.gif"));
			g.drawImage(image2, x, y, WIDTH*3/2,HEIGHT*3/2,null);
		}else {
			Image image2=Toolkit.getDefaultToolkit().getImage(Start.class.getResource("/images/enemy.gif"));
			g.drawImage(image2, x, y, WIDTH*2,HEIGHT*2,null);
		}	
		
		//设定位置
		
		//画出血条
		if(good) {
			bb.draw(g);
		}

		
		//判断个体方向画出
		switch(ptDir) {
		case L:
			g.drawLine(x+Entity.WIDTH/2, y+Entity.HEIGHT/2, x, y+Entity.HEIGHT/2);
			break;
		case LU:
			g.drawLine(x+Entity.WIDTH/2, y+Entity.HEIGHT/2, x, y);
			break;
		case U:
			g.drawLine(x+Entity.WIDTH/2, y+Entity.HEIGHT/2, x+Entity.WIDTH/2, y);
			break;
		case RU:
			g.drawLine(x+Entity.WIDTH/2, y+Entity.HEIGHT/2, x+Entity.WIDTH, y);
			break;
		case R:
			g.drawLine(x+Entity.WIDTH/2, y+Entity.HEIGHT/2, x+Entity.WIDTH, y+Entity.HEIGHT/2);
			break;
		case RD:
			g.drawLine(x+Entity.WIDTH/2, y+Entity.HEIGHT/2, x+Entity.WIDTH, y+Entity.HEIGHT);
			break;
		case D:
			g.drawLine(x+Entity.WIDTH/2, y+Entity.HEIGHT/2, x+Entity.WIDTH/2, y+Entity.HEIGHT);
			break;
		case LD:
			g.drawLine(x+Entity.WIDTH/2, y+Entity.HEIGHT/2, x, y+Entity.HEIGHT);
			break;
		}
		
		move();
	}
	
	public void stay() {
		x=oldX;
		y=oldY;
	}
	//个体的移动
	private void move() {
		//记录下上一步位置
		this.oldX=x;
		this.oldY=y;
		
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
		case STOP:
			break;
		}
		
		//将个体的方向传给炮筒
		if(this.dir!=Direction.STOP) {
			this.ptDir=this.dir;
		}
		
		//防止个体出界
		if(x<0) x=0;
		if(y<65) y=65;
		if(x+Entity.WIDTH>PlayerClient.GAME_WIDTH) x=PlayerClient.GAME_WIDTH-Entity.WIDTH;
		if(y+Entity.HEIGHT>PlayerClient.GAME_HEIGHT) y=PlayerClient.GAME_HEIGHT-Entity.HEIGHT;
		
		//如果是敌人每移动一步产生随机方向
		if(!good) {
			Direction dirs[]=Direction.values();
			if(0==step) {
				step=(int) (Math.random()*12)+3;
				//生成一个随机方向数
				int r=(int) (Math.random()*8);
				dir=dirs[r];
			}			
			step--;
			
			if(((int)(Math.random()*40))>38) this.fire();
		}
		
	}
	
	//盘但是否同时按下两个按钮，来定位方向
	void locateDirection() {
		if(!bU&&!bD&&!bL&&!bR) {
			dir=Direction.STOP;
		}else if(!bU&&!bD&&bL&&!bR) {
			dir=Direction.L;
		}else if(bU&&!bD&&bL&&!bR) {
			dir=Direction.LU;
		}else if(bU&&!bD&&!bL&&!bR) {
			dir=Direction.U;
		}else if(bU&&!bD&&!bL&&bR) {
			dir=Direction.RU;
		}else if(!bU&&!bD&&!bL&&bR) {
			dir=Direction.R;
		}else if(!bU&&bD&&!bL&&bR) {
			dir=Direction.RD;
		}else if(!bU&&bD&&!bL&&!bR) {
			dir=Direction.D;
		}else if(!bU&&bD&&bL&&!bR) {
			dir=Direction.LD;
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key=e.getKeyCode();
		switch(key) {
		//上
		case KeyEvent.VK_W:
			bU=true;
			break;
		//下
		case KeyEvent.VK_S:
			bD=true;
			break;
		//左
		case KeyEvent.VK_A:
			bL=true;
			break;
		//右
		case KeyEvent.VK_D:
			bR=true;
			break;
		}
		locateDirection();
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int key=e.getKeyCode();
		switch(key) {
		//上
		case KeyEvent.VK_W:
			bU=false;
			break;
		//下
		case KeyEvent.VK_S:
			bD=false;
			break;
		//左
		case KeyEvent.VK_A:
			bL=false;
			break;
		//右
		case KeyEvent.VK_D:
			bR=false;
			break;
		//开火
		case KeyEvent.VK_J:
			fire();
			break;
		//超级炮弹
		case KeyEvent.VK_K:
			superFire();
			break;
		}
		locateDirection();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	//个体开火的函数
	public Missile fire() {
		//如果个体死了，就 不发炮弹了
		if(!isLive()) return null;
		int x=this.x+Entity.WIDTH/2-Missile.WIDTH/2;
		int y=this.y+Entity.HEIGHT/2-Missile.HEIGHT/2;
		//将个体目前的位置和方向交给子弹
		Missile m=new Missile(x,y,good,ptDir);
		tc.missiles.add(m);
		return m;
	}
	
	public Missile fire(Direction dir) {
		if(!isLive()) return null;
		int x=this.x+Entity.WIDTH/2-Missile.WIDTH/2;
		int y=this.y+Entity.HEIGHT/2-Missile.HEIGHT/2;
		//将个体目前的位置和方向交给子弹
		Missile m=new Missile(x,y,good,dir);
		tc.missiles.add(m);
		return m;
	}
	
	// 定义一个辅助类，Rectangel可以获得包在个体外面的方块
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	//判断个体是否撞墙
	public boolean collideWithwall(Wall w) {
		if(this.live&&this.getRect().intersects(w.getRect())) {
			//回到上一个位置
			this.stay();
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
	
	//判断个体是否相撞
	public boolean collideWithtank(Entity t) {
		if(this!=t) {
			if(t.isLive()&&this.live&&this.getRect().intersects(t.getRect())) {
				//回到上一个位置
				this.stay();
				t.stay();
				return true;
			}
		}	
		return false;
	}	
	public boolean collideWithtanks(Vector<Entity> ts) {
		for(int i=0;i<ts.size();i++) {
			if(collideWithtank(ts.get(i))) {
				return true;
			}
		}
		return false;
	}
	
	//超级炮弹
	private void superFire() {
		Direction dirs[]=Direction.values();
		for(int i=0;i<8;i++) {
			fire(dirs[i]);
		}
	}
	
	//内部类，血条
	private class BloodBar{
		public void draw(Graphics g) {
			Color c=g.getColor();
			g.setColor(Color.ORANGE);
			g.drawRect(x, y-10, WIDTH, 5);
			g.fillRect(x, y-10, WIDTH*life/100, 5);
			g.setColor(c);
		}
	}
	
	//判断是否吃到血块
	public boolean eatBlood(Blood b) {
		if(b.isLive()&&this.live&this.getRect().intersects(b.getRect())) {
			this.setLife(100);
			b.setLive(false);
			return true;
		}
		return false;
	}
}
