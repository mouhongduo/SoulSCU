package mySCU;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.ImageObserver;
import java.util.Vector;


public class Entity implements KeyListener{
	private static final int WIDTH=30;
	private static final int HEIGHT=30;
	
	//��ҵ��ٶȣ����ɱ�
	private static int XSPEED=10;
	private static int YSPEED=10;
	
	private boolean good=true;
	
	//�������λ��
	private int x,y;
	//�������һ��λ��
	private int oldX,oldY;
	
	//������������
	private int life=100;
	private BloodBar bb=new BloodBar();
	
	private boolean bL=false,bU=false,bR=false,bD=false;
		
	//����ķ���Ĭ��Ϊ��ֹ
	private Direction dir=Direction.STOP;
	//�������ķ���
	private Direction ptDir=Direction.D;
	
	PlayerClient tc=null;
	//�����������
	private boolean live=true;
	
	//�ø����ƶ��̶��Ĳ�������ת����3-15��
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

	//��������ĺ���
	public void draw(Graphics g) {
		
		//������������ʧ
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
		
		//�趨λ��
		
		//����Ѫ��
		if(good) {
			bb.draw(g);
		}

		
		//�жϸ��巽�򻭳�
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
	//������ƶ�
	private void move() {
		//��¼����һ��λ��
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
		
		//������ķ��򴫸���Ͳ
		if(this.dir!=Direction.STOP) {
			this.ptDir=this.dir;
		}
		
		//��ֹ�������
		if(x<0) x=0;
		if(y<65) y=65;
		if(x+Entity.WIDTH>PlayerClient.GAME_WIDTH) x=PlayerClient.GAME_WIDTH-Entity.WIDTH;
		if(y+Entity.HEIGHT>PlayerClient.GAME_HEIGHT) y=PlayerClient.GAME_HEIGHT-Entity.HEIGHT;
		
		//����ǵ���ÿ�ƶ�һ�������������
		if(!good) {
			Direction dirs[]=Direction.values();
			if(0==step) {
				step=(int) (Math.random()*12)+3;
				//����һ�����������
				int r=(int) (Math.random()*8);
				dir=dirs[r];
			}			
			step--;
			
			if(((int)(Math.random()*40))>38) this.fire();
		}
		
	}
	
	//�̵��Ƿ�ͬʱ����������ť������λ����
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
		//��
		case KeyEvent.VK_W:
			bU=true;
			break;
		//��
		case KeyEvent.VK_S:
			bD=true;
			break;
		//��
		case KeyEvent.VK_A:
			bL=true;
			break;
		//��
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
		//��
		case KeyEvent.VK_W:
			bU=false;
			break;
		//��
		case KeyEvent.VK_S:
			bD=false;
			break;
		//��
		case KeyEvent.VK_A:
			bL=false;
			break;
		//��
		case KeyEvent.VK_D:
			bR=false;
			break;
		//����
		case KeyEvent.VK_J:
			fire();
			break;
		//�����ڵ�
		case KeyEvent.VK_K:
			superFire();
			break;
		}
		locateDirection();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	//���忪��ĺ���
	public Missile fire() {
		//����������ˣ��� �����ڵ���
		if(!isLive()) return null;
		int x=this.x+Entity.WIDTH/2-Missile.WIDTH/2;
		int y=this.y+Entity.HEIGHT/2-Missile.HEIGHT/2;
		//������Ŀǰ��λ�úͷ��򽻸��ӵ�
		Missile m=new Missile(x,y,good,ptDir);
		tc.missiles.add(m);
		return m;
	}
	
	public Missile fire(Direction dir) {
		if(!isLive()) return null;
		int x=this.x+Entity.WIDTH/2-Missile.WIDTH/2;
		int y=this.y+Entity.HEIGHT/2-Missile.HEIGHT/2;
		//������Ŀǰ��λ�úͷ��򽻸��ӵ�
		Missile m=new Missile(x,y,good,dir);
		tc.missiles.add(m);
		return m;
	}
	
	// ����һ�������࣬Rectangel���Ի�ð��ڸ�������ķ���
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	//�жϸ����Ƿ�ײǽ
	public boolean collideWithwall(Wall w) {
		if(this.live&&this.getRect().intersects(w.getRect())) {
			//�ص���һ��λ��
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
	
	//�жϸ����Ƿ���ײ
	public boolean collideWithtank(Entity t) {
		if(this!=t) {
			if(t.isLive()&&this.live&&this.getRect().intersects(t.getRect())) {
				//�ص���һ��λ��
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
	
	//�����ڵ�
	private void superFire() {
		Direction dirs[]=Direction.values();
		for(int i=0;i<8;i++) {
			fire(dirs[i]);
		}
	}
	
	//�ڲ��࣬Ѫ��
	private class BloodBar{
		public void draw(Graphics g) {
			Color c=g.getColor();
			g.setColor(Color.ORANGE);
			g.drawRect(x, y-10, WIDTH, 5);
			g.fillRect(x, y-10, WIDTH*life/100, 5);
			g.setColor(c);
		}
	}
	
	//�ж��Ƿ�Ե�Ѫ��
	public boolean eatBlood(Blood b) {
		if(b.isLive()&&this.live&this.getRect().intersects(b.getRect())) {
			this.setLife(100);
			b.setLive(false);
			return true;
		}
		return false;
	}
}
