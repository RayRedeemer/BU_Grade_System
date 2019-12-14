package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import share.Request;
import share.RequestHead;

/*
Author: Ziqi Tan
*/
public class StudentForm extends JFrame {
	
	private int courseID;
	
	private JTextField nameField;
	private JTextField buIdField;
	private JComboBox<String> isGradBox;
	private JTextField emailField;
	
	// String name, String email, String buId, Boolean isGrad
	public StudentForm(int _courseID) {
		System.out.println("A form is creating.");
		setTitle("Student form");	
		this.courseID = _courseID;
					
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int frameWidth = (int)(screenSize.getWidth() * 0.36);
		int frameHeight = (int)(screenSize.getHeight() * 0.7);
		setSize(frameWidth, frameHeight);
		      		
		int hGap = 20;
		int vGap = 50;
		
		setLocationRelativeTo(null);   // this will center your frame
		
		setVisible(true);
		
		JPanel formPanel = new JPanel();
		formPanel.setLayout(null);
		
		int labelX = (int)(frameWidth*0.2);
		int labelY = (int)(frameHeight*0.1);
		
		int labelWidth = 100;
		int labelHeight = 25;
		JLabel nameLabel = new JLabel("Student name: ");
		nameLabel.setBounds(labelX, labelY, labelWidth, labelHeight);
		formPanel.add(nameLabel);
		
		JLabel buIdLabel = new JLabel("BU ID: ");
		buIdLabel.setBounds(labelX, labelY + vGap, labelWidth, labelHeight);
		formPanel.add(buIdLabel);
		
		JLabel curveLabel = new JLabel("Undergrad/Grad: ");
		curveLabel.setBounds(labelX, labelY + vGap * 2, labelWidth, labelHeight);				
		formPanel.add(curveLabel);
				
		JLabel emailLabel = new JLabel("Email: ");
		emailLabel.setBounds(labelX, labelY + vGap * 3, labelWidth, labelHeight);
		formPanel.add(emailLabel);
		
		int textFieldWidth = 130;
		nameField = new JTextField(20);
		nameField.setBounds(labelX + nameLabel.getWidth() + hGap, nameLabel.getY(), textFieldWidth, labelHeight);
		formPanel.add(nameField);
		
		buIdField = new JTextField(20);
		buIdField.setBounds(labelX + buIdLabel.getWidth() + hGap, buIdLabel.getY(), textFieldWidth, labelHeight);
		formPanel.add(buIdField);
		
		isGradBox = new JComboBox<String>();
		isGradBox.addItem("Undergraduate");
		isGradBox.addItem("Graduate");
		isGradBox.setBounds(labelX + curveLabel.getWidth() + hGap, curveLabel.getY(), textFieldWidth, labelHeight);
		formPanel.add(isGradBox);
					
		emailField = new JTextField();
		emailField.setBounds(labelX + emailLabel.getWidth() + hGap, emailLabel.getY(), textFieldWidth, labelHeight);
		formPanel.add(emailField);
		
		int buttonWidth = 80;
		JButton submit = new JButton("Submit");

		submit.setBounds(labelX, emailLabel.getY() + vGap, buttonWidth, labelHeight);
		formPanel.add(submit);
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				String stuName = nameField.getText();
				String buId = buIdField.getText();
				String isGrad = (String)isGradBox.getSelectedItem();
				String email = emailField.getText();
								
				if( stuName.length() == 0 || buId.length() == 0 || email.length() == 0 ) {
					System.out.println("Please fill the form.");
					JOptionPane.showMessageDialog(null, "Please fill the form.");
					return ;
				}
				
				// Request
				Request request = new Request(RequestHead.ADD_STUDENT);
				
				// String name, String email, String buId, Boolean isGrad
				request.addParams(stuName);						
				request.addParams(email);
				request.addParams(buId);
				if( isGrad.equals("Graduate") ) {
					request.addParams(true);
				}
				else {
					request.addParams(false);
				}			
				request.addIds(courseID);
				request.addIds(null);
				request.addIds(null);
				request.addIds(null);
				
				FrontController.getInstance().dispatchRequest(request);
				JOptionPane.showMessageDialog(null, "Add/Updae successfully.");
				dispose();
				
			}
		});
					
		JButton cancel = new JButton("Cancel");
		cancel.setBounds(labelX, emailLabel.getY() + vGap * 2, buttonWidth, labelHeight);
		formPanel.add(cancel);
		cancel.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent event) {
						dispose();
					}
				}					
		);
					
		add(formPanel);
	}
	
	
}
