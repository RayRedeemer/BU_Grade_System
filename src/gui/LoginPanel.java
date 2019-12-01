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

/*
Author: Ziqi Tan
*/
public class LoginPanel extends JPanel implements ActionListener {
	
	private JTextField userNameField;
	private JPasswordField passwordField;
	private JButton loginButton;
	
	public LoginPanel() {
		
		// setLayout
		int hgap = 10;
		int vgap = 50;
		setLayout(new FlowLayout(FlowLayout.CENTER, hgap, vgap));
		/**
		 * FlowLayout constructor
		 * @params
		 * 		align - the alignment value
		 * 		hgap - the horizontal gap between components and between the components and the borders of the Container
		 * 		vgap - the vertical gap between components and between the components and the borders of the Container
		 * */
		
		// Transparent background
		setBackground(new Color(0,0,0,0));
		
		// new font
		Font font = new Font("Times New Roman", Font.BOLD, 22);
		
		// Components
		JLabel loginLabel = new JLabel("Login: ");
		loginLabel.setFont(font);
		add(loginLabel);
		
		userNameField = new JTextField(20);
		userNameField.setText("cpk");
		add(userNameField);
		
		JLabel passwordLabel = new JLabel("Password: ");
		passwordLabel.setFont(font);
		add(passwordLabel);
		
		passwordField = new JPasswordField(20);
		passwordField.setText("123");
		add(passwordField);
		
		loginButton = new JButton("Login");
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
