package mySCU;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
public class Start extends JFrame{
	private JPanel contentPane;
	private ActionListener listener1; 
	private ActionListener listener2; 
	private ActionListener listener3;
	private ActionListener listener4;
	private JButton start = new JButton(new ImageIcon
			(Start.class.getResource("/images/开始游戏.jpg")));
	private JButton onePlayer = new JButton(new ImageIcon
			(Start.class.getResource("/images/单人游戏.jpg")));
	private JButton twoPlayer = new JButton(new ImageIcon
			(Start.class.getResource("/images/双人游戏.jpg")));
	private JButton chooseOne = new JButton(new ImageIcon
			(Start.class.getResource("/images/选择.jpg")));
	private JButton chooseTwo = new JButton(new ImageIcon
			(Start.class.getResource("/images/选择.jpg")));
	private JButton chooseThreee = new JButton(new ImageIcon
			(Start.class.getResource("/images/选择.jpg")));
	private JLabel background1 = new JLabel(new ImageIcon
			(Start.class.getResource("/images/开始2.jpg")));
	private JLabel background2 = new JLabel(new ImageIcon
			(Start.class.getResource("/images/选择模式.jpg")));
	private JLabel background3 = new JLabel(new ImageIcon
			(Start.class.getResource("/images/选择角色.jpg")));
	private JButton ourTeam = new JButton("防伪标志");
	private JLabel team;
	
	private void Bounds() {
		start.setBounds(800, 775, 0, 0);
		onePlayer.setBounds(750, 550, 200, 66);
		twoPlayer.setBounds(750, 650, 200, 66);
		chooseOne.setBounds(485, 850, 100, 100);
		chooseTwo.setBounds(790, 850, 100, 100);
		chooseThreee.setBounds(1085, 850, 100, 100);
		ourTeam.setBounds(100,100,200,66);
		team.setBounds(1500,200,300,400);
	}
	
	private Start() {
		Team singleTon = Team.getSingleton();
		team = new JLabel(singleTon.getIcon());
		
		Bounds();
		listener1 = new ActionListener() {
			public void actionPerformed (ActionEvent event) {
				mode_page();
			}
		};
		listener2 = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				character_page();
			}
		};
		listener3 = new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				dispose();
				PlayerClient tc=new PlayerClient();
				tc.lauchFrame();
			}
		};
		listener4 = new ActionListener() {
			public void actionPerformed(ActionEvent eve) {
				if(!team.isVisible()) {
					team.setVisible(true);
				}
				else {
					team.setVisible(false);
				}
			}
		};
		start_page();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1920,1080);

	}
	
	public static void main(String[] args) {
		new Start();
	}
	
	
	
	private void start_page() {
		
		start.addActionListener(listener1);
		contentPane = new JPanel();
		contentPane.add(start);
		contentPane.setOpaque(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setVisible(true);
		
		((JPanel)this.getContentPane()).setOpaque(false); 
    	
    	this.getLayeredPane().add("Center",background1); 
    	this.getLayeredPane().add(team, new Integer(Integer.MIN_VALUE));
    	team.setVisible(false);
    	background1.setBounds(0, 0, 1920, 1080);
    	start.addKeyListener(new KeyAdapter(){//游戏开始时按回车键进入角色选择
			public void keyPressed(KeyEvent e){
				int code = e.getKeyCode();
				if(code==KeyEvent.VK_ENTER){
					start.doClick();
				}
			}
		});
	}
	
	private void mode_page() {
		contentPane.add(ourTeam);
		ourTeam.addActionListener(listener4);
		background1.setVisible(false);
		start.setVisible(false);
		
		onePlayer.addActionListener(listener2);
		twoPlayer.addActionListener(listener2);
		
		
		contentPane.add(onePlayer);
		contentPane.add(twoPlayer);
		
		((JPanel)this.getContentPane()).setOpaque(false); 
    	
    	this.getLayeredPane().add(background2, new Integer(Integer.MIN_VALUE)); 
    	background2.setBounds(0, 0, 1920, 1080);
	}
	
	private void character_page() {
		background2.setVisible(false);
		onePlayer.setVisible(false);
		twoPlayer.setVisible(false);
		
		contentPane.add(chooseOne);
		contentPane.add(chooseTwo);
		contentPane.add(chooseThreee );
		
		chooseOne.addActionListener(listener3);
		chooseTwo.addActionListener(listener3);
		chooseThreee.addActionListener(listener3);
		
		((JPanel)this.getContentPane()).setOpaque(false); 
    	
    	this.getLayeredPane().add(background3, new Integer(Integer.MIN_VALUE)); 
    	background3.setBounds(0, 0, 1920, 1080); 
	}
}
