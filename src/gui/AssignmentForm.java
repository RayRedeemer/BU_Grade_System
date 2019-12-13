package gui;
/*
Author: Ziqi Tan
*/

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import share.Request;
import share.RequestHead;

public class AssignmentForm extends JFrame {
	
	private int courseID;
	private int cateID;
	
	public AssignmentForm(int _courseID, int _cateID) {
		this.courseID = _courseID;
		this.cateID = _cateID;
		setTitle("Assignment Form");
		System.out.println("A form is creating.");
		
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
		
		int labelWidth = 110;
		int labelHeight = 25;
		JLabel nameLabel = new JLabel("Assignment name: ");
		nameLabel.setBounds(labelX, labelY, labelWidth, labelHeight);
		formPanel.add(nameLabel);
		
		JLabel despLabel = new JLabel("Description: ");
		despLabel.setBounds(labelX, labelY + vGap * 3, labelWidth, labelHeight);
		formPanel.add(despLabel);
		
		int textFieldWidth = 130;
		JTextField nameField = new JTextField(20);
		nameField.setBounds(labelX + nameLabel.getWidth() + hGap, nameLabel.getY(), textFieldWidth, labelHeight);
		formPanel.add(nameField);	
		
		JTextArea despArea = new JTextArea();
		despArea.setLineWrap(true);
		JScrollPane textAreaScrollPane = new JScrollPane(despArea);
		textAreaScrollPane.setBounds(labelX + despLabel.getWidth() + hGap, despLabel.getY(), (int) (textFieldWidth * 2.3), 180);
		formPanel.add(textAreaScrollPane);
		
		int buttonWidth = 80;
		JButton submit = new JButton("Submit");
		submit.setBounds(labelX, despLabel.getY() + vGap, buttonWidth, labelHeight);
		formPanel.add(submit);
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				String assignName = nameField.getText();
				String assignDesp = despArea.getText();				
								
				if( assignName.length() == 0 || assignDesp.length() == 0 ) {
					System.out.println("Please fill the form.");
					JOptionPane.showMessageDialog(null, "Please fill the form.");
					return ;
				}
				
				// Request
				Request request = new Request(RequestHead.ADD_ASSIGNMENT);
				request.addIds(courseID);
				request.addIds(cateID);
				request.addIds(null);
				request.addIds(null);
				
				request.addParams(assignName);
				request.addParams(assignDesp);
				
				FrontController.getInstance().dispatchRequest(request);
				
				JOptionPane.showMessageDialog(null, "Add/Updae successfully.");
				dispose();
			}
		});
			
		JButton cancel = new JButton("Cancel");
		cancel.setBounds(labelX, despLabel.getY() + vGap * 2, buttonWidth, labelHeight);
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
