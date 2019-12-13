package gui;

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

/*
Author: Ziqi Tan
*/

/**
 * Form for add course or update course
 * */
public class CourseForm extends JFrame {
	
	private int courseID;
	private JTextField nameField;
	private JTextField semesterField;
	private JTextField curveField;
	private JTextArea despArea;
	private JTextArea commentArea;
	
	
	public CourseForm( RequestHead head, int _courseID, String _courseName, String _semester, String _description, double _curve, String _comment ) {
		System.out.println("A form is creating.");
		setTitle("Course form");	
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
		JLabel nameLabel = new JLabel("Course name: ");
		nameLabel.setBounds(labelX, labelY, labelWidth, labelHeight);
		formPanel.add(nameLabel);
		
		JLabel semesterLabel = new JLabel("Semester: ");
		semesterLabel.setBounds(labelX, labelY + vGap, labelWidth, labelHeight);
		formPanel.add(semesterLabel);
		
		JLabel curveLabel = new JLabel("Curve: ");
		curveLabel.setBounds(labelX, labelY + vGap * 2, labelWidth, labelHeight);		
		if( head == RequestHead.UPDATE_COURSE ) {
			formPanel.add(curveLabel);
		}
		
		JLabel despLabel = new JLabel("Description: ");
		despLabel.setBounds(labelX, labelY + vGap * 3, labelWidth, labelHeight);
		formPanel.add(despLabel);
		
		int textFieldWidth = 130;
		nameField = new JTextField(20);
		nameField.setText(_courseName);
		nameField.setBounds(labelX + nameLabel.getWidth() + hGap, nameLabel.getY(), textFieldWidth, labelHeight);
		formPanel.add(nameField);	
		
		semesterField = new JTextField(20);
		semesterField.setText(_semester);
		semesterField.setBounds(labelX + semesterLabel.getWidth() + hGap, semesterLabel.getY(), textFieldWidth, labelHeight);
		formPanel.add(semesterField);
		
		curveField = new JTextField(20);
		curveField.setText(Double.toString(_curve));
		curveField.setBounds(labelX + curveLabel.getWidth() + hGap, curveLabel.getY(), textFieldWidth, labelHeight);
		if( head == RequestHead.UPDATE_COURSE ) {
			formPanel.add(curveField);
		}
			
		despArea = new JTextArea();
		despArea.setLineWrap(true);
		despArea.setText(_description);
		JScrollPane textAreaScrollPane = new JScrollPane(despArea);
		textAreaScrollPane.setBounds(labelX + despLabel.getWidth() + hGap, despLabel.getY(), (int) (textFieldWidth * 2.3), 180);
		formPanel.add(textAreaScrollPane);
		
		JLabel commentLabel = new JLabel("Comment: ");
		commentLabel.setBounds(labelX, textAreaScrollPane.getY() + textAreaScrollPane.getHeight() + vGap/2, labelWidth, labelHeight);		
		if( head == RequestHead.UPDATE_COURSE ) {
			formPanel.add(commentLabel);
		}
		
		commentArea = new JTextArea();
		commentArea.setLineWrap(true);
		commentArea.setText(_comment);
		JScrollPane commentAreaScrollPane = new JScrollPane(commentArea);
		commentAreaScrollPane.setBounds(labelX + commentLabel.getWidth() + hGap, commentLabel.getY(), (int) (textFieldWidth * 2.3), 180);		
		if( head == RequestHead.UPDATE_COURSE ) {
			formPanel.add(commentAreaScrollPane);
		}
		
		int buttonWidth = 80;
		JButton submit;
		if( head.equals(RequestHead.ADD_COURSE) ) {
			submit = new JButton("Submit");
		}
		else {
			submit = new JButton("Update");
		}
		submit.setBounds(labelX, commentLabel.getY() + vGap, buttonWidth, labelHeight);
		formPanel.add(submit);
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				String courseName = nameField.getText();
				String courseSemester = semesterField.getText();
				String courseDesp = despArea.getText();
				double courseCurve = 0.0;
				try {
					courseCurve = Double.parseDouble(curveField.getText());
				}
				catch(Exception error) {
					System.out.println(error);
				}
				
				String courseComments = commentArea.getText();
				
				if( courseName.length() == 0 || courseSemester.length() == 0 || courseDesp.length() == 0 ) {
					System.out.println("Please fill the form.");
					JOptionPane.showMessageDialog(null, "Please fill the form.");
				}
				else {
					// Request
					Request request;
					if( head.equals(RequestHead.ADD_COURSE) ) {
						request = new Request(RequestHead.ADD_COURSE);
						request.addIds(null);
						request.addParams(1); // instructor id
					}
					else {
						request = new Request(RequestHead.UPDATE_COURSE);
						request.addIds(courseID);
					}
					
					request.addParams(courseName);						
					request.addParams(courseDesp);
					request.addParams(courseSemester);
					request.addParams(courseCurve);  // curve
					request.addParams(courseComments);  // comment
					
					request.addIds(null);
					request.addIds(null);
					request.addIds(null);
					FrontController.getInstance().dispatchRequest(request);
					JOptionPane.showMessageDialog(null, "Add/Updae successfully.");
					dispose();
				}
			}
		});
					
		JButton cancel = new JButton("Cancel");
		cancel.setBounds(labelX, commentLabel.getY() + vGap * 2, buttonWidth, labelHeight);
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
