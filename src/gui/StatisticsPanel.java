package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class StatisticsPanel extends JPanel {
	
	private JPanel gridPanel;
	private JButton btnAll;
	private JButton btnUndergrad;
	private JButton btnGrad;
	private JButton btnEmpty;
	private JButton btnAllAssignments;
	private JButton btnHomework;
	private JButton btnProject;
	private JButton btnExam;
	
	private JPanel labelPanel;
	private JLabel lbMean;
	private JLabel lbMedian;
	private JLabel lbDeviation;
	private JLabel lbRange;
	
	public StatisticsPanel() {
		// setLayout
		int hgap = 10;
		int vgap = 50;
		setLayout(new BorderLayout(hgap, vgap));
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
		
		// a panel containing all buttons
		gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(2,4,0,0));
		Dimension preferredSize = new Dimension(160, 60);
		
		btnAll = new JButton("All");
		btnAll.setPreferredSize(preferredSize);
		btnAll.setFont(font);
		gridPanel.add(btnAll);
		
		btnUndergrad = new JButton("Undergrad");
		btnUndergrad.setFont(font);
		gridPanel.add(btnUndergrad);
		
		btnGrad = new JButton("Grad");
		btnGrad.setFont(font);
		gridPanel.add(btnGrad);
		
		btnEmpty = new JButton();
		btnEmpty.setBackground(new Color(0, 0, 0, 0));
		gridPanel.add(btnEmpty);
		
		btnAllAssignments = new JButton("All Assignments");
		btnAllAssignments.setFont(font);
		gridPanel.add(btnAllAssignments);
		
		btnHomework = new JButton("Homework");
		btnHomework.setFont(font);
		gridPanel.add(btnHomework);
		
		btnProject = new JButton("Project");
		btnProject.setFont(font);
		gridPanel.add(btnProject);
		
		btnExam = new JButton("Exam");
		btnExam.setFont(font);
		gridPanel.add(btnExam);
		
		add(gridPanel, BorderLayout.NORTH);
		
		// 
		labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
		
		lbMean = new JLabel("Mean:");
		lbMean.setFont(font);
		labelPanel.add(lbMean);
		
		lbMedian = new JLabel("Median:");
		lbMedian.setFont(font);
		labelPanel.add(lbMedian);
		
		lbDeviation = new JLabel("Deviation:");
		lbDeviation.setFont(font);
		labelPanel.add(lbDeviation);
		
		lbRange = new JLabel("Range:");
		lbRange.setFont(font);
		labelPanel.add(lbRange);
		
		add(labelPanel, BorderLayout.SOUTH);
		
	}
	
}
