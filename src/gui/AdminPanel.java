package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

/*
Author: Ziqi Tan
*/
public class AdminPanel extends JPanel {
	
	/**
	 * Constructor
	 * */
	public AdminPanel() {				
		setLayout(new BorderLayout());
		setBackground(new Color(0,0,0,0));
		add(new HeadLine(), BorderLayout.NORTH);
		add(new CourseListPanel(), BorderLayout.CENTER);
	}
	
	/**
	 * inner class
	 * */
	class HeadLine extends JPanel {
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
	
	/**
	 * inner class
	 * */
	class CourseListPanel extends JPanel {
		
		private JTable courseListTable;
		
		public CourseListPanel() {
			setBackground(new Color(0,0,0,50));
			
			int frameWidth = MainFrame.getInstance().getFrameWidth();
			int frameHeight = MainFrame.getInstance().getFrameHeight();
			int hgap = (int)(frameWidth*0.05);
			int vgap = (int)(frameHeight*0.03);
			// FlowLayout
			setLayout(new FlowLayout(FlowLayout.LEFT, hgap, vgap));
			
			setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
                    "Course list",
                    TitledBorder.CENTER,
                    TitledBorder.TOP));
			
			// Data to be displayed in the JTable 
	        String[][] data = { 
	            { "CS 591", "19", "Fall", "66", "--"}, 
	            { "CS 592", "19", "Fall", "30", "--" },
	            { "CS 666", "18", "Spring", "150", "83" },
	            { "CS 591", "17", "Summer", "111", "87"}
	        }; 

	        // Column Names 
	        String[] columnNames = { "Name", "Year", "Semester", "Enrollment Count", "Average Grade" }; 
	  
	        // Initializing the JTable 
	        courseListTable = new JTable(data, columnNames); 
	        
	        // header render wrap

	        
	        // row height
	        courseListTable.setRowHeight(20);
	        
	        // center alignment
	        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
	        centerRenderer.setVerticalAlignment( JLabel.CENTER );
	        
	        for( int columnIndex = 0; columnIndex < courseListTable.getModel().getColumnCount(); columnIndex++ ) {
	        	courseListTable.getColumnModel().getColumn(columnIndex).setCellRenderer( centerRenderer );
	        }
	                
	        // Font
	        Font font = courseListTable.getFont();
	        courseListTable.setFont(new Font(font.getName(), font.getStyle(), font.getSize() + 3));
	        
	        
	        courseListTable.setBackground(new Color(255, 255, 208));
	        // adding it to JScrollPane 
	        JScrollPane sp = new JScrollPane(courseListTable); 
	        add(sp);
	        // System.out.println(courseListTable.set);
	        
	        // add ControlPanel
	        add(new ControlPanel());
		}
	}
	
	/**
	 * inner class
	 * */
	class ControlPanel extends JPanel implements ActionListener {
		private JButton addCourseButton;
		private JButton cloneCourseButton;
		private JButton selectCourseButton;
		private JButton deleteCourseButton;
		
		public ControlPanel() {
			setBackground(new Color(0,0,0,0));
			setLayout(new GridLayout(4, 1, 20, 20));
			
			addCourseButton = new JButton("Add Course");
			add(addCourseButton);
			
			cloneCourseButton = new JButton("Clone Course");
			add(cloneCourseButton);
			
			selectCourseButton = new JButton("Select Course");
			add(selectCourseButton);
			
			deleteCourseButton = new JButton("Delete Course");
			add(deleteCourseButton);
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
