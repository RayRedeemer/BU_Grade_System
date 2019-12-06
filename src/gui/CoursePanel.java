package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

/*
Author: Ziqi Tan
*/
public class CoursePanel extends JPanel {
	
	private int courseID;
	
	private String courseName;
	private String semester;
	private String description;
	
	private String[] columnNames = 
		{ "Assignments", "Weight" };
	private String[][] data = { 
            { "Homeworks", "30%" }, 
            { "Projects", "35%" },
            { "Exams", "35%" }
	};
	
	private JTable assignmentTable;
	private int selectedRow;
	private int selectedColumn;
	
	/**
	 * Constructor
	 * */
	public CoursePanel( int _courseID ) {
		this.courseID = _courseID;
						
		int frameWidth = MainFrame.getInstance().getFrameWidth();
		int frameHeight = MainFrame.getInstance().getFrameHeight();
		int hgap = (int)(frameWidth*0.15);
		int vgap = (int)(frameHeight*0.03);
		
		setBackground(new Color(0,0,0,10));	
		// setLayout(new FlowLayout(FlowLayout.CENTER, hgap, vgap));
		setLayout(null);
		// Request information from backend
		courseName = "OOD";
		semester = "Fall19";
		description = "Various issues in computer science that vary semester to semester." + System.getProperty("line.separator")
				+ "Please contact the CAS Computer Science Department for detailed descriptions." + System.getProperty("line.separator")
				+ "Though courses are variable credit, registration for less than four credits requires instructor approval.";
		
		Font font = new Font("Times New Roman", Font.BOLD, 18);
		
		JLabel courseNameLabel = new JLabel(courseName);
		courseNameLabel.setFont(font);
		add(courseNameLabel);
		
		JLabel semesterLabel = new JLabel(semester);
		semesterLabel.setFont(font);
		add(semesterLabel);
		
		JTextArea descriptionArea = new JTextArea();
		descriptionArea.setBackground(new Color(0, 0, 0, 0));
		descriptionArea.setText(description);
		descriptionArea.setFont(font);
		descriptionArea.setEditable(false);
		add(descriptionArea);		
		// setLayout(new BorderLayout());
		// add(new CourseInfoPanel());
		// add(new AssignmentTablePanel());
		createAssignmentTable();
		JScrollPane sp = new JScrollPane(assignmentTable); 
        add(sp, BorderLayout.CENTER);
	}
	
	private void createAssignmentTable() {
		
		int rowHeight = 20;
		int columnWidth = 110;
		int headerHeight = 32;
		
		// Initializing the JTable
		assignmentTable = new JTable(data, columnNames);
        
        // row height
		assignmentTable.setRowHeight(rowHeight);
        
        // center alignment
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        centerRenderer.setVerticalAlignment( JLabel.CENTER );
        
        for( int columnIndex = 0; columnIndex < assignmentTable.getModel().getColumnCount(); columnIndex++ ) {
        	assignmentTable.getColumnModel().getColumn(columnIndex).setCellRenderer( centerRenderer );  
        	assignmentTable.getColumnModel().getColumn(columnIndex).setPreferredWidth(columnWidth);
        }
        
        // Font
        Font font = assignmentTable.getFont();
        assignmentTable.setFont(new Font(font.getName(), font.getStyle(), font.getSize() + 3));
        
        // table background color
        assignmentTable.setBackground(new Color(255, 255, 208));
        
        // set table size
        assignmentTable.setPreferredScrollableViewportSize(assignmentTable.getPreferredSize());
        assignmentTable.setFillsViewportHeight(true);
        
        // header height
        JTableHeader header = assignmentTable.getTableHeader();
        header.setPreferredSize(new Dimension(columnWidth, headerHeight));  
        
        // Mouse listener
        // select course
        assignmentTable.addMouseListener( new MouseAdapter() {       	
        	@Override
        	 public void mouseClicked(MouseEvent event) {
        	    selectedRow = assignmentTable.rowAtPoint(event.getPoint());
        	    selectedColumn = assignmentTable.columnAtPoint(event.getPoint());
        	    System.out.println("Click: " + "Row: " + selectedRow + " Column: " + selectedColumn);
        	 }
        } );
	}		
	
	/**
	 * inner class
	 * */
	class CourseInfoPanel extends JPanel {
		public CourseInfoPanel() {
			// gridLayout	
			setBackground(new Color(0,0,0,0));
			// setLayout(new GridLayout(3, 1, 20, 20));
			
			// Request information from backend
			courseName = "OOD";
			semester = "Fall19";
			description = "Various issues in computer science that vary semester to semester." + System.getProperty("line.separator")
					+ "Please contact the CAS Computer Science Department for detailed descriptions." + System.getProperty("line.separator")
					+ "Though courses are variable credit, registration for less than four credits requires instructor approval.";
			
			Font font = new Font("Times New Roman", Font.BOLD, 18);
			
			JLabel courseNameLabel = new JLabel(courseName);
			courseNameLabel.setFont(font);
			add(courseNameLabel);
			
			JLabel semesterLabel = new JLabel(semester);
			semesterLabel.setFont(font);
			add(semesterLabel);
			
			JTextArea descriptionArea = new JTextArea();
			descriptionArea.setBackground(new Color(0, 0, 0, 0));
			descriptionArea.setText(description);
			descriptionArea.setFont(font);
			descriptionArea.setEditable(false);
			add(descriptionArea);						
		}		
	}
	
	/**
	 * inner class
	 * */
	class AssignmentTablePanel extends JPanel {
		
		public AssignmentTablePanel() {
			setBackground(new Color(0,0,0,0));
		
			createAssignmentTable();
			JScrollPane sp = new JScrollPane(assignmentTable); 
	        add(sp);
			// add(new ControlPanelVertical());
			
		}
		
		
		
	}
	
	/**
	 * inner class
	 * */
	class ControlPanelVertical extends JPanel {
		
		public ControlPanelVertical() {
			
		}
		
		
	}
	
	/**
	 * inner class
	 * */
	class ControlPanelParallel extends JPanel {
		
		public ControlPanelParallel() {
			
		}
		
	}
	
}
