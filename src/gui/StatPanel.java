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
		
		int btnWidth = 200;
		int btnHeight = 50;
		
		btnLogout = new JButton("Logout");
		btnLogout.setBounds(1168, 54, 112, 56);
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
		btnAll.setBounds(171, 117, 200, 80);
		btnAll.setFont(btnFont);
		add(btnAll);
		
		btnUndergrad = new JButton("Undergrad");
		btnUndergrad.setBounds(371, 117, 200, 80);
		btnUndergrad.setFont(btnFont);
		add(btnUndergrad);
		
		btnGrad = new JButton("Grad");
		btnGrad.setBounds(571, 117, 200, 80);
		btnGrad.setFont(btnFont);
		add(btnGrad);
		
		btnEmpty = new JButton();
		btnEmpty.setBounds(413, 18, 33, 9);
		btnEmpty.setBackground(new Color(0, 0, 0, 0));
		//add(btnEmpty);
		
		btnAllAssignments = new JButton("All Assignments");
		btnAllAssignments.setBounds(171, 197, 200, 80);
		btnAllAssignments.setFont(btnFont);
		add(btnAllAssignments);
		
		cbHomework = new JComboBox(new String[] {"Homework"});
		cbHomework.setBounds(371, 197, 200, 80);
		cbHomework.setFont(btnFont);
		add(cbHomework);
		
		cbProject = new JComboBox(new String[] {"Project"});
		cbProject.setBounds(571, 197, 200, 80);
		cbProject.setFont(btnFont);
		add(cbProject);
		
		cbExam = new JComboBox(new String[] {"Exam"});
		cbExam.setBounds(771, 197, 200, 80);
		cbExam.setFont(btnFont);
		add(cbExam);
		
		//lable data
		int gap = 100;
		int lblWidth = 200;
		int lblHeight = 35;
		
		lbMean = new JLabel("Mean:");
		lbMean.setBounds(171, 344, lblWidth, lblHeight);
		lbMean.setFont(lblFont);
		add(lbMean);
		
		lbMedian = new JLabel("Median:");
		lbMedian.setBounds(171, 421, lblWidth, lblHeight);
		lbMedian.setFont(lblFont);
		add(lbMedian);
		
		lbDeviation = new JLabel("Deviation:");
		lbDeviation.setBounds(171, 498, lblWidth, lblHeight);
		lbDeviation.setFont(lblFont);
		add(lbDeviation);
		
		lbRange = new JLabel("Range:");
		lbRange.setBounds(171, 569, lblWidth, lblHeight);
		lbRange.setFont(lblFont);
		add(lbRange);
		
		btnReturn = new JButton("Return");
		btnReturn.setBounds(1150, 593, 130, 56);
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
