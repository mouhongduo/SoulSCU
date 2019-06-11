package mySCU;

import javax.swing.ImageIcon;

public class Team {
	static private Team singleton = null;
	public ImageIcon icon;
	
	private Team() {
		icon = new ImageIcon
				(Team.class.getResource("/images/µ¥Àý.jpg"));
		
	}
	
	static public Team getSingleton() {
		if(singleton == null) {
			singleton = new Team();
		}
		return singleton;
	}
	
	public ImageIcon getIcon() {
		return icon;
	}
}
