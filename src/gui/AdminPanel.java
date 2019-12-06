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

import share.Request;
import share.RequestHead;

/*
Author: Ziqi Tan
*/
public class AdminPanel extends JPanel implements ActionListener {
		
	private JTable courseListTable;
	private String[] columnNames = 
		{ "Name", "Year", "Semester", "Enrollment Count", "Average Grade" };
	private String[][] data = { 
            { "CS 591", "19", "Fall", "66", "--"}, 
            { "CS 592", "19", "Fall", "30", "--" },
            { "CS 666", "18", "Spring", "150", "83" },
            { "CS 591", "17", "Summer", "111", "87"}
    }; 
	private String[][] tableData;
	
	private static final int headerHeight = 32;
	
	private int selectedRow;
	private int selectedColumn;
	private String selectedCourse;
	
	/**
	 * Constructor
	 * */
	public AdminPanel() {				

		setLayout(null);
		
		int frameWidth = MainFrame.getInstance().getWidth();
		int frameHeight = MainFrame.getInstance().getHeight();
		
		int hGap = 50;
		int vGap = 50;
		
		int x = (int)(frameWidth*0.4);
		int y = (int)(frameHeight*0.05);


		JLabel titleLabel = new JLabel("Welcome");
		titleLabel.setBounds(x, y, 200, 50);
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 35));
		add(titleLabel);
		
		JButton logoutButton = new JButton("Logout");
		logoutButton.setBounds(titleLabel.getX() + titleLabel.getWidth() + hGap, titleLabel.getY() + 13, 80, 25);

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

		// set up a table
		createJTable();
	
        // adding it to JScrollPane 
		int tableWidth = (int) courseListTable.getPreferredSize().getWidth();
        JScrollPane sp = new JScrollPane(courseListTable); 
        sp.setBounds((int) (frameWidth * 0.25), titleLabel.getY() + titleLabel.getHeight(), 
        		tableWidth, 
        		(int)(frameHeight*0.5));
        add(sp);
        
        int buttonWidth = 130;
        int textHeight = 25;
        int buttonX = sp.getX() + tableWidth + hGap;
        int buttonY = sp.getY();
        JButton addCourseButton = new JButton("Add Course");
        addCourseButton.setBounds(buttonX, buttonY, buttonWidth, textHeight);
		add(addCourseButton);
		
		JButton cloneCourseButton = new JButton("Clone Course");
		cloneCourseButton.setBounds(buttonX, buttonY + vGap, buttonWidth, textHeight);
		add(cloneCourseButton);
		
		JButton selectCourseButton = new JButton("Select Course");
		selectCourseButton.setBounds(buttonX, buttonY + vGap * 2, buttonWidth, textHeight);
		add(selectCourseButton);
		selectCourseButton.addActionListener(this);
		
		JButton deleteCourseButton = new JButton("Delete Course");
		deleteCourseButton.setBounds(buttonX, buttonY + vGap * 3, buttonWidth, textHeight);
		add(deleteCourseButton);
		
		
	}
	
	/**
	 * Method: createJTable
	 * Function: Create a course list table.
	 * */
	private void createJTable() {
		
		int rowHeight = 20;
		int columnWidth = 110;
		
        // Initializing the JTable
        courseListTable = new JTable(data, columnNames);
        
        // row height
        courseListTable.setRowHeight(rowHeight);
        
        // center alignment
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        centerRenderer.setVerticalAlignment( JLabel.CENTER );
        
        for( int columnIndex = 0; columnIndex < courseListTable.getModel().getColumnCount(); columnIndex++ ) {
        	courseListTable.getColumnModel().getColumn(columnIndex).setCellRenderer(centerRenderer); 
        	courseListTable.getColumnModel().getColumn(columnIndex).setPreferredWidth(columnWidth);
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
        header.setPreferredSize(new Dimension(columnWidth, headerHeight));  
        
        // Mouse listener
        // select course
        courseListTable.addMouseListener(new MouseAdapter() {       	
        	@Override
        	 public void mouseClicked(MouseEvent event) {
        		try {
        			selectedRow = courseListTable.rowAtPoint(event.getPoint());
            	    selectedColumn = courseListTable.columnAtPoint(event.getPoint());
            	    System.out.println("Click: " + "Row: " + selectedRow + " Column: " + selectedColumn);
            	    selectedCourse = data[selectedRow][0];
            	    System.out.println("Selected course: " + selectedCourse);
        		}
        		catch( Exception error ) {
        			System.out.println(error);
        		}
        	    
        	 }
        } );
	}
	

	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		if( event.getActionCommand().equals("Select Course") ) {
			Request request = new Request(RequestHead.SELECT_COURSE);
			request.addParams(selectedCourse);
			FrontController.getInstance().dispatchRequest(request);
		}
	}
	
}
