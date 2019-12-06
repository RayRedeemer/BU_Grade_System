package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import share.Request;
import share.RequestHead;

/*
Author: Ziqi Tan
*/
public class LoginPanel extends JPanel implements ActionListener {
	
	private JTextField userNameField;
	private JPasswordField passwordField;
	private JButton loginButton;
	
	public LoginPanel() {
		
		setLayout(null);
		
		int frameWidth = MainFrame.getInstance().getWidth();
		int frameHeight = MainFrame.getInstance().getHeight();
		
		int leftGap = (int)(frameWidth * 0.2);
		int hGap = (int)(30);
		int vGap = (int)(50);
		
		int labelWidth = 80;
		int textWidth = 150;
		int textHeight = 25;
		
		// new font
		Font font = new Font("Times New Roman", Font.BOLD, 22);
		
		// Components
		JLabel loginLabel = new JLabel("Login: ");
		loginLabel.setFont(font);
		loginLabel.setBounds(leftGap, vGap, labelWidth, textHeight);
		add(loginLabel);
		
		userNameField = new JTextField();
		userNameField.setBounds(loginLabel.getX() + loginLabel.getWidth() + hGap, vGap, textWidth, textHeight);
		userNameField.setText("cpk");
		add(userNameField);
		
		JLabel passwordLabel = new JLabel("Password: ");
		passwordLabel.setFont(font);
		passwordLabel.setBounds(userNameField.getX() + userNameField.getWidth() + hGap, vGap, labelWidth + 30, textHeight);
		add(passwordLabel);
		
		passwordField = new JPasswordField(20);
		passwordField.setBounds(passwordLabel.getX() + passwordLabel.getWidth() + hGap, vGap, textWidth, textHeight);
		passwordField.setText("123");
		add(passwordField);
		
		loginButton = new JButton("Login");
		loginButton.setBounds(passwordField.getX() + passwordField.getWidth() + hGap, vGap, textWidth, textHeight);
		add(loginButton);
		loginButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if( event.getActionCommand().equals("Login") ) {
			Request request = new Request(RequestHead.LOGIN);
			request.addParams(userNameField.getText());
			request.addParams(passwordField.getText());
			FrontController.getInstance().dispatchRequest(request);
		}
		
	}
		
	
}
