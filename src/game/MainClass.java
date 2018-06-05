package game;

import java.awt.Dimension;

import javax.swing.JFrame;

public class MainClass {
	public static void main(String[] args)

	{
		JFrame frame=new JFrame("sanke");
		frame.setContentPane(new GmaePanel());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
	 	frame.pack();
		 frame.setPreferredSize(new Dimension(GmaePanel.WIDTH 	, GmaePanel.HEIGHT));
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
	    
	}
	
	
	
	

}
