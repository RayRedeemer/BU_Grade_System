package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import share.Request;
import share.RequestHead;

/*
Author: Ziqi Tan
*/
public class HeadLine extends JPanel {
	/**
	 * Constructor
	 * */
	public HeadLine() {
		
		int frameWidth = MainFrame.getInstance().getFrameWidth();
		int frameHeight = MainFrame.getInstance().getFrameHeight();
		
		setBackground(new Color(0,0,0,0));
		int hgap = (int)(frameWidth*0.05);
		int vgap = (int)(frameHeight*0.03);
		// FlowLayout
		setLayout(new FlowLayout(FlowLayout.LEFT, hgap, vgap));
		
		JLabel loginLabel = new JLabel("Welcome");
		
		loginLabel.setFont(new Font("Times New Roman", Font.BOLD, 35));
		add(loginLabel);
		
		JButton logoutButton = new JButton("Logout");
		add(logoutButton);
		logoutButton.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent event) {
						Request request = new Request(RequestHead.LOGOUT);
						FrontController.getInstance().dispatchRequest(request);
					}
				}
		);
		
	}
}
