package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

/*
Author: Ziqi Tan
*/
public class AdminPanel extends JPanel {
	
	public AdminPanel() {
		int hgap = 10;
		int vgap = 15;
		setLayout(new FlowLayout(FlowLayout.LEFT, hgap, vgap));
		setBackground(new Color(0,0,0,0));
		
		JLabel loginLabel = new JLabel(" Welcome");
		loginLabel.setFont(new Font("Times New Roman", Font.BOLD, 35));
		add(loginLabel);
	}
	
	/**
	 * inner class
	 * */
	class CoursePanel {
		
		public CoursePanel() {
			
		}
		
	}
	
}
