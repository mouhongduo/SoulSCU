package mySCU;

import java.awt.*;
import javax.swing.*;


import java.awt.event.*;
import java.util.Vector;

public class PlayerClient extends Frame implements ActionListener{
	
	public static final int GAME_WIDTH=800;
	public static final int GAME_HEIGHT=600;
	private static final int enemySize=3;
	private static final int reEnemySize=5;
	//定义一个墙的标志量，来控制显示墙的类型
	private static int wallType=1;
	//定义个bool值控制线程
	private boolean printable=true;
	//定义需要的组件
	Image offScreenImage=null;
	//菜单条
	MenuBar mb=null;
	Menu m1,m2,m3,m4;
	MenuItem mi1,mi2,mi3,mi4,mi5,mi6,mi7,mi8,mi9;
		
	//定义玩家
	Entity me=null;
    //定义一个敌人的集合
	Vector<Entity> enemies=new Vector<Entity>();	
	//定义一个子弹的集合，以做到发射多发子弹
	Vector<Missile> missiles=new Vector<Missile>();
	//定义一个爆炸的集合
	Vector<Explode> explodes=new Vector<Explode>();
	//定义一个墙的集合
	Vector<Wall> walls=new Vector<Wall>();
	//创建一个血块
	Blood b=null;
	//控制血块出现的时间间隔
	private int step=500;
	
	public void lauchFrame() {
		
		//初始化玩家
		
		//初始化墙
		initWalls();
		me= new Entity(400,560,true,Direction.STOP,this);
		//初始化敌人
		for(int i=0;i<enemySize;i++) {
			for(int j=0;j<enemySize;j++) {
				Entity enemys =new Entity(100+((i+1)*150),0+((j+1)*150),false,Direction.STOP, this);
			enemies.add(enemys);
			}
		}	
		
		this.setBackground(Color.BLACK);
		this.addKeyListener(me);
		//定义一个匿名类，实现窗口的关闭
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		//初始化菜单
		this.initMenu();
		
		this.setMenuBar(mb);
		this.setTitle("Soul SCU by wangchen and mouhongduo");
		this.setSize(800, 600);
		this.setResizable(false);
		this.setVisible(true);
		this.setLocation(400, 300);
		
		//启动线程
		Thread t=new Thread(new PaintThread());
		t.start();
	}
	
	private void initWalls() {
		if(wallType==1) {
			for(int i=0;i<3;i++) {
				Wall wall1=new Wall(220,540+20*i);
				Wall wall2=new Wall(220,420+i*20);
				Wall wall3=new Wall(220,360+i*20);
				Wall wall4=new Wall(220,240+i*20);
				Wall wall5=new Wall(220,180+i*20);
				Wall wall6=new Wall(220,60+i*20);
				Wall wall7=new Wall(i*20,405);
				Wall wall8=new Wall(60+i*20,405);
				Wall wall9=new Wall(120+i*20,405);
				Wall wall10=new Wall(180+i*20,405);
				Wall wall11=new Wall(i*20,225);
				Wall wall12=new Wall(60+i*20,225);
				Wall wall13=new Wall(120+i*20,225);
				Wall wall14=new Wall(180+i*20,225);
				walls.add(wall1);
				walls.add(wall2);
				walls.add(wall3);
				walls.add(wall4);
				walls.add(wall5);
				walls.add(wall6);
				walls.add(wall7);
				walls.add(wall8);
				walls.add(wall9);
				walls.add(wall10);
				walls.add(wall11);
				walls.add(wall12);
				walls.add(wall13);
				walls.add(wall14);
			}
			for(int i=0;i<3;i++) {
				Wall wall1=new Wall(580,540+20*i);
				Wall wall2=new Wall(580,420+i*20);
				Wall wall3=new Wall(580,360+i*20);
				Wall wall4=new Wall(580,240+i*20);
				Wall wall5=new Wall(580,180+i*20);
				Wall wall6=new Wall(580,60+i*20);
				Wall wall7=new Wall(800-i*20,405);
				Wall wall8=new Wall(740-i*20,405);
				Wall wall9=new Wall(680-i*20,405);
				Wall wall10=new Wall(620-i*20,405);
				Wall wall11=new Wall(800-i*20,225);
				Wall wall12=new Wall(740-i*20,225);
				Wall wall13=new Wall(680-i*20,225);
				Wall wall14=new Wall(620-i*20,225);
				walls.add(wall1);
				walls.add(wall2);
				walls.add(wall3);
				walls.add(wall4);
				walls.add(wall5);
				walls.add(wall6);
				walls.add(wall7);
				walls.add(wall8);
				walls.add(wall9);
				walls.add(wall10);
				walls.add(wall11);
				walls.add(wall12);
				walls.add(wall13);
				walls.add(wall14);
			}
		}else if(wallType==2) {
			for(int i=0;i<4;i++) {
				for(int j=0;j<3;j++) {
					Wall wall =new Wall((i+1)*200,150+j*150);
					walls.add(wall);
				}
				
			}
		}else if(wallType==3) {
			for(int i=0;i<6;i++) {
				for(int j=0;j<2;j++) {
					Wall wall =new Wall(i*60+200,200+j*250);
					walls.add(wall);
				}
			}
		}	
	}

	//菜单
	public void initMenu() {
		m1=new Menu("游戏");
		mi1=new MenuItem("重新开始");
		mi1.addActionListener(this);
		mi2=new MenuItem("退出");
		mi2.addActionListener(this);
		m1.add(mi1);
		m1.add(mi2);
		
		m2=new Menu("暂停/继续");
		mi3=new MenuItem("暂停");
		mi3.addActionListener(this);
		mi4=new MenuItem("继续");
		mi4.addActionListener(this);
		m2.add(mi3);
		m2.add(mi4);
		
		m3=new Menu("帮助");
		mi5=new MenuItem("游戏说明");
		mi5.addActionListener(this);
		mi6=new MenuItem("操作介绍");
		mi6.addActionListener(this);
		m3.add(mi5);
		m3.add(mi6);
		
		m4=new Menu("游戏关卡");
		mi7=new MenuItem("关卡1");
		mi7.addActionListener(this);
		
		m4.add(mi7);
		
		mb=new MenuBar();	
		mb.add(m1);
		mb.add(m2);
		mb.add(m3);
		mb.add(m4);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		if(enemies.size()<=0) {
			for(int i=0;i<reEnemySize;i++) {
				Entity enemy=new Entity((int) (Math.random()*GAME_WIDTH),(int) (Math.random()*GAME_HEIGHT),false,Direction.STOP, this);
				if(!enemy.collideWithwalls(walls)) {
					this.enemies.add(enemy);
				}		
			}
		}
		
		if(me.isLive()) {
			me.collideWithwalls(walls);
			if(b!=null) {
				me.eatBlood(b);
			}		
			me.draw(g);			
		}else {
			printable=false;
			String[] options= {"确定","取消"};
			int res=JOptionPane.showOptionDialog(this, "游戏结束,重新开始或退出?", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.CLOSED_OPTION
					, null, options, options[0]);
			if(res==0) {
				printable=true;
				this.dispose();
				new PlayerClient().lauchFrame();
			}else {
				System.exit(0);
			}
		}
		
		for(int i=0;i<walls.size();i++) {
			Wall wall=walls.get(i);
			wall.draw(g);
		}
		//画出子弹
		for(int i=0;i<missiles.size();i++) {
			Missile m=missiles.get(i);
			if(m!=null) {
				if(!m.isLive()) {
					missiles.remove(m);
				}else {
					m.collideWithwalls(walls);
					m.hitEntitys(enemies);
					m.hitEntity(me);
					m.draw(g);
				}
			}
		}
		//画出爆炸
		for(int i=0;i<explodes.size();i++) {
			Explode e=explodes.get(i);
			e.draw(g);
		}
		
		//画出敌人
		for(int i=0;i<enemies.size();i++) {
			Entity enemy=enemies.get(i);
			enemy.collideWithwalls(walls);
			enemy.collideWithtanks(enemies);
			enemy.draw(g);
		}	
		
		//画出血块
		if(b!=null) {
			b.draw(g);	
		}
	}
	
	// 双缓冲，消除闪烁,在repaint之前调用update
	public void update(Graphics g) {
		if (offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		// 取得图片的画笔
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.BLACK);
		gOffScreen.fillRect(0, 0, getWidth(), GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	//内部类，重绘面板线程
	private class PaintThread implements Runnable {
		@Override
		public void run() {
			while(printable) {
				//每隔50毫秒重绘一次面板
				repaint();
				if(step>0) {
					step--;
					//初始化血块
				}else {
					if(step==0) {
						b=new Blood(new PlayerClient());
					}
					step=500;
				}

				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//重新开始
		if(e.getSource()==mi1) {
			printable=false;
			String[] options= {"确定","取消"};
			int res=JOptionPane.showOptionDialog(this, "确定重新开始?", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if(res==0) {
				printable=true;
				this.dispose();
				new PlayerClient().lauchFrame();
			}else {
				printable=true;
				new Thread(new PaintThread()).start();
			}
		}else if(e.getSource()==mi2){  //退出
			System.exit(0);
		}else if(e.getSource()==mi3) { //暂停
			printable=false;
		}else if(e.getSource()==mi4) { //继续
			printable=true;
			new Thread(new PaintThread()).start();
		}else if(e.getSource()==mi5) { //游戏说明
			printable = false;
			//Team team = Team.getSingleton();
			
			JOptionPane.showMessageDialog(this, "本游戏开发团队：汪晨，牟泓多。",
					"提示！", JOptionPane.INFORMATION_MESSAGE);
			//JOptionPane.showMessageDialog(null, "哈哈", "测试", JOptionPane.CLOSED_OPTION, team.getIcon());
			this.setVisible(true);
			printable = true;
			new Thread(new PaintThread()).start(); // 线程启动
		}else if(e.getSource()==mi6) { //操作说明
			printable = false;
			String tip="用w,s,a,d,控制玩家上下左右运动,按j发射子弹，按k可以使用技能，吃到血块会恢复满血";
			JOptionPane.showMessageDialog(this,tip,"提示！", JOptionPane.INFORMATION_MESSAGE);
			this.setVisible(true);
			printable = true;
			new Thread(new PaintThread()).start(); // 线程启动
		}else if(e.getSource()==mi7) {
			Entity.setXSPEED(5);
			Entity.setYSPEED(5);
			Missile.setXSPEED(10);
			Missile.setYSPEED(10);
			
			this.dispose();
			new PlayerClient().lauchFrame();
		}else if(e.getSource()==mi8) {
			Entity.setXSPEED(7);
			Entity.setYSPEED(7);
			Missile.setXSPEED(12);
			Missile.setYSPEED(12);
			System.out.println("speed:"+Entity.getXSPEED());
			
			this.dispose();
			new PlayerClient().lauchFrame();
		}else if(e.getSource()==mi9) {
			Entity.setXSPEED(9);
			Entity.setYSPEED(9);
			Missile.setXSPEED(14);
			Missile.setYSPEED(14);
			
			this.dispose();
			new PlayerClient().lauchFrame();
		}
	}
	
}
