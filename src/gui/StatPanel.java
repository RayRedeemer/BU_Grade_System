package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import share.Request;
import share.RequestHead;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JComboBox;

public class StatPanel extends JPanel implements ActionListener {
	
	private JButton btnAll;
	private JButton btnUndergrad;
	private JButton btnGrad;
	private JButton btnEmpty;
	private JButton btnAllAssignments;
	private JButton btnReturn;
	private JButton btnLogout;
	
	private JLabel lbMean;
	private JLabel lbMedian;
	private JLabel lbDeviation;
	private JLabel lbRange;
	
	private JComboBox cbHomework, cbProject, cbExam;

	/**
	 * Create the panel.
	 */
	public StatPanel() {
		
		// new font
		Font btnFont = new Font("Microsoft YaHei UI", Font.PLAIN, 20);
		Font lblFont = new Font("Times New Roman", Font.PLAIN, 25);
		setLayout(null);
		
		btnLogout = new JButton("Logout");
		btnLogout.setBounds(696, 53, 101, 47);
		btnLogout.setFont(btnFont);
		add(btnLogout);
		btnLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Request request = new Request(RequestHead.LOGOUT);
				FrontController.getInstance().dispatchRequest(request);
			}
		});
		
		btnAll = new JButton("All");
		btnAll.setBounds(49, 60, 280, 80);
		btnAll.setFont(btnFont);
		add(btnAll);
		
		btnUndergrad = new JButton("Undergrad");
		btnUndergrad.setBounds(329, 60, 280, 80);
		btnUndergrad.setFont(btnFont);
		add(btnUndergrad);
		
		btnGrad = new JButton("Grad");
		btnGrad.setBounds(609, 60, 280, 80);
		btnGrad.setFont(btnFont);
		add(btnGrad);
		
		btnEmpty = new JButton();
		btnEmpty.setBounds(413, 18, 33, 9);
		btnEmpty.setBackground(new Color(0, 0, 0, 0));
		//add(btnEmpty);
		
		btnAllAssignments = new JButton("All Assignments");
		btnAllAssignments.setBounds(49, 140, 280, 80);
		btnAllAssignments.setFont(btnFont);
		add(btnAllAssignments);
		
		cbHomework = new JComboBox(new String[] {"Homework"});
		cbHomework.setBounds(329, 140, 280, 80);
		cbHomework.setFont(btnFont);
		add(cbHomework);
		
		cbProject = new JComboBox(new String[] {"Project"});
		cbProject.setBounds(609, 140, 280, 80);
		cbProject.setFont(btnFont);
		add(cbProject);
		
		cbExam = new JComboBox(new String[] {"Exam"});
		cbExam.setBounds(889, 140, 280, 80);
		cbExam.setFont(btnFont);
		add(cbExam);
		
		//lable data
		int gap = 100;
		int lblWidth = 200;
		int lblHeight = 35;
		
		lbMean = new JLabel("Mean:");
		lbMean.setBounds(137, 348, lblWidth, lblHeight);
		lbMean.setFont(lblFont);
		add(lbMean);
		
		lbMedian = new JLabel("Median:");
		lbMedian.setBounds(137, 425, lblWidth, lblHeight);
		lbMedian.setFont(lblFont);
		add(lbMedian);
		
		lbDeviation = new JLabel("Deviation:");
		lbDeviation.setBounds(137, 502, lblWidth, lblHeight);
		lbDeviation.setFont(lblFont);
		add(lbDeviation);
		
		lbRange = new JLabel("Range:");
		lbRange.setBounds(137, 573, lblWidth, lblHeight);
		lbRange.setFont(lblFont);
		add(lbRange);
		
		btnReturn = new JButton("Return");
		btnReturn.setBounds(706, 631, 130, 56);
		btnReturn.setFont(btnFont);
		add(btnReturn);
		btnReturn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {				
				MainFrame.getInstance().removeCurPanel();
				MainFrame.getInstance().setCoursePanel();
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
