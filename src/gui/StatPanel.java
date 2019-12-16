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
	
	private int selectedRow;
	private int selectedColumn;
	private String selectedAssignment;

	private int frameWidth;
	private int frameHeight;
	
	/**
	 * Create the panel.
	 */
	public StatPanel() {
		
		// new font
		Font btnFont = new Font("Microsoft YaHei UI", Font.PLAIN, 16);
		Font lblFont = new Font("Times New Roman", Font.PLAIN, 25);
		setLayout(null);
		
		int btnWidth = 160;
		int btnHeight = 30;
		
		int hGap = 10;
		
		frameWidth = MainFrame.getInstance().getWidth();
		frameHeight = MainFrame.getInstance().getHeight();
		
		int x = (int)(frameWidth*0.2);
		int y = (int)(frameHeight*0.2);
		
		btnLogout = new JButton("Logout");
		btnLogout.setBounds((int)(frameWidth*0.8), (int)(frameHeight*0.2), btnWidth, btnHeight);
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
		btnAll.setBounds(x, y, btnWidth, btnHeight);
		btnAll.setFont(btnFont);
		btnAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				selectedRow = 0;
				selectedColumn = 0;
				selectedAssignment = null;
			}
		});
		add(btnAll);
		
		btnUndergrad = new JButton("Undergrad");
		btnUndergrad.setBounds(btnAll.getX() + btnAll.getWidth() + hGap, y, btnWidth, btnHeight);
		btnUndergrad.setFont(btnFont);
		btnUndergrad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				selectedRow = 0;
				selectedColumn = 1;
				selectedAssignment = null;
			}
		});
		add(btnUndergrad);
		
		btnGrad = new JButton("Grad");
		btnGrad.setBounds(btnUndergrad.getX() + btnUndergrad.getWidth() + hGap, y, btnWidth, btnHeight);
		btnGrad.setFont(btnFont);
		btnGrad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				selectedRow = 0;
				selectedColumn = 2;
				selectedAssignment = null;
			}
		});
		add(btnGrad);
		
		y = y + btnHeight * 2;
		btnAllAssignments = new JButton("All Assignments");
		btnAllAssignments.setBounds(x, y, btnWidth, btnHeight);
		btnAllAssignments.setFont(btnFont);
		btnAllAssignments.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				selectedRow = 1;
				selectedColumn = 0;
				selectedAssignment = null;
			}
		});
		add(btnAllAssignments);
		
		cbHomework = new JComboBox(new String[] {"All Homeworks", "Homework1", "Homework2", "Homework3"});
		cbHomework.setBounds(btnAllAssignments.getX() + btnAllAssignments.getWidth() + hGap, y, btnWidth, btnHeight);
		cbHomework.setFont(btnFont);
		cbHomework.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				selectedRow = 1;
				selectedColumn = 1;
				selectedAssignment = cbHomework.getSelectedObjects().toString();
			}
		});
		add(cbHomework);
		
		cbProject = new JComboBox(new String[] {"All Projects", "TicTacToe", "Blackjack", "Trianta Ena", "Cave Adventure", "Fancy Bank"});
		cbProject.setBounds(cbHomework.getX() + cbHomework.getWidth() + hGap, y, btnWidth, btnHeight);
		cbProject.setFont(btnFont);
		cbProject.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				selectedRow = 1;
				selectedColumn = 2;
				selectedAssignment = cbProject.getSelectedObjects().toString();
			}
		});
		add(cbProject);
		
		cbExam = new JComboBox(new String[] {"All Exams", "Midterm1", "Midterm2", "Final exam"});
		cbExam.setBounds(cbProject.getX() + cbProject.getWidth() + hGap, y, btnWidth, btnHeight);
		cbExam.setFont(btnFont);
		cbExam.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				selectedRow = 1;
				selectedColumn = 3;
				selectedAssignment = cbExam.getSelectedObjects().toString();
			}
		});
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
		btnReturn.setBounds((int)(frameWidth*0.8), (int)(frameHeight*0.8), btnWidth, btnHeight);
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
