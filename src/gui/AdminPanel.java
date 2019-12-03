package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

/*
Author: Ziqi Tan
*/
public class AdminPanel extends JPanel {
		
	private JTable courseListTable;
	private static final int HEADER_HEIGHT = 32;
	private String[] columnNames = 
		{ "Name", "Year", "Semester", "Enrollment Count", "Average Grade" };
	private String[][] data = { 
            { "CS 591", "19", "Fall", "66", "--"}, 
            { "CS 592", "19", "Fall", "30", "--" },
            { "CS 666", "18", "Spring", "150", "83" },
            { "CS 591", "17", "Summer", "111", "87"}
    }; 
	private String[][] tableData;
	
	private int selectedRow;
	private int selectedColumn;
	private String selectedCourse;
	
	/**
	 * Constructor
	 * */
	public AdminPanel() {				

		setBackground(new Color(0,0,0,30));
		
		int frameWidth = MainFrame.getInstance().getFrameWidth();
		int frameHeight = MainFrame.getInstance().getFrameHeight();
		int hgap = (int)(frameWidth*0.15);
		int vgap = (int)(frameHeight*0.03);
		// FlowLayout
		setLayout(new FlowLayout(FlowLayout.LEFT, hgap, vgap));
		
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "Course list",
                TitledBorder.LEFT,
                TitledBorder.TOP));		
		// set up a table
		createJTable();
			        	        
        // adding it to JScrollPane 
        JScrollPane sp = new JScrollPane(courseListTable); 
        add(sp);
        
        // add ControlPanel
        add(new ControlPanel());
	}
	
	/**
	 * Method: createJTable
	 * Function: Create a course list table.
	 * */
	private void createJTable() {
		      
        // Initializing the JTable
        courseListTable = new JTable(data, columnNames);
        
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
        
        // table background color
        courseListTable.setBackground(new Color(255, 255, 208));
        
        // set table size
        courseListTable.setPreferredScrollableViewportSize(courseListTable.getPreferredSize());
        courseListTable.setFillsViewportHeight(true);
        
        // header height
        JTableHeader header = courseListTable.getTableHeader();
        header.setPreferredSize(new Dimension(100, HEADER_HEIGHT));  
        
        // Mouse listener
        // select course
        courseListTable.addMouseListener( new MouseAdapter() {       	
        	@Override
        	 public void mouseClicked(MouseEvent event) {
        	    selectedRow = courseListTable.rowAtPoint(event.getPoint());
        	    selectedColumn = courseListTable.columnAtPoint(event.getPoint());
        	    System.out.println("Click: " + "Row: " + selectedRow + " Column: " + selectedColumn);
        	    selectedCourse = data[selectedRow][0];
        	    System.out.println("Selected course: " + selectedCourse);
        	 }
        } );
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
