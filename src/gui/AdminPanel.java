package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import share.Request;
import share.RequestHead;

/*
Author: Ziqi Tan
*/
public class AdminPanel extends JPanel implements ActionListener {
		
	private JFrame addCourseFrame;
	private JTable courseListTable;
	private JScrollPane scrollPane;
	private String[] columnNames = { 
			"ID", "Name", "Semester", "Average Grade" 
	};
	private String[][] data = { 
            { null, null, null, null }
    }; 
	
	private static final int headerHeight = 32;
	
	private int selectedRow;
	private int selectedColumn;
	private String selectedCourse;
	
	private int frameWidth;
	private int frameHeight; 
	private int tableWidth;
	private int hGap;
	private int vGap;
		
	private JLabel titleLabel;
	
	/**
	 * Constructor
	 * */
	public AdminPanel() {				

		setLayout(null);
		
		frameWidth = MainFrame.getInstance().getWidth();
		frameHeight = MainFrame.getInstance().getHeight();
		
		hGap = 50;
		vGap = 50;
		
		int x = (int)(frameWidth*0.4);
		int y = (int)(frameHeight*0.05);

		titleLabel = new JLabel("Welcome");
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
		setScrollPane();
        add(scrollPane);
        
        int buttonWidth = 130;
        int textHeight = 25;
        int buttonX = scrollPane.getX() + tableWidth + hGap;
        int buttonY = scrollPane.getY();
        System.out.println(tableWidth);
        JButton addCourseButton = new JButton("Add Course");
        addCourseButton.setBounds(buttonX, buttonY, buttonWidth, textHeight);
		add(addCourseButton);
		addCourseButton.addActionListener(this);
		
		JButton cloneCourseButton = new JButton("Clone Course");
		cloneCourseButton.setBounds(buttonX, buttonY + vGap, buttonWidth, textHeight);
		add(cloneCourseButton);
		cloneCourseButton.addActionListener(this);
		
		JButton selectCourseButton = new JButton("Select Course");
		selectCourseButton.setBounds(buttonX, buttonY + vGap * 2, buttonWidth, textHeight);
		add(selectCourseButton);
		selectCourseButton.addActionListener(this);
			
		JButton deleteCourseButton = new JButton("Delete Course");
		deleteCourseButton.setBounds(buttonX, buttonY + vGap * 3, buttonWidth, textHeight);
		add(deleteCourseButton);
		deleteCourseButton.addActionListener(this);	
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
	
	/**
	 * Method: setScrollPane
	 * */
	private void setScrollPane() {
		tableWidth = (int) courseListTable.getPreferredSize().getWidth();
        scrollPane = new JScrollPane(courseListTable); 
        scrollPane.setBounds((int) (frameWidth * 0.25), titleLabel.getY() + titleLabel.getHeight(), 
        		tableWidth, 
        		(int)(frameHeight*0.5));
	}
	
	/**
	 * Method: getCourseList
	 * Function:
	 * 		1. Sent request to backend.
	 * 		2. Dispatcher will call updateCourseList(data) to update the table.
	 * */
	public void getCourseList() {
		Request request = new Request(RequestHead.GET_COURSE_LIST);
		request.addIds(null);
		request.addIds(null);
		request.addIds(null);
		request.addIds(null);
		FrontController.getInstance().dispatchRequest(request);
	}
	/**
	 * Method: updateCourseList
	 * @param the data in the table
	 * Function: Dispatcher will call this function.
	 * */
	public void updateCourseList( String[][] _data ) {
		this.data = _data;		
		remove(scrollPane);
		createJTable();
		setScrollPane();
		add(scrollPane);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		
		if( event.getActionCommand().equals("Add Course") ) {
			if( addCourseFrame != null ) {
				addCourseFrame.dispose();
			}
			addCourseFrame = new CourseForm(RequestHead.ADD_COURSE, 0, "", "", "", 0.0, "");
		}
		
		if( event.getActionCommand().equals("Clone Course") ) {
			Request request = new Request(RequestHead.COPY_COURSE);
			request.addIds(Integer.parseInt(selectedCourse));  // course id
			request.addIds(null);
			request.addIds(null);
			request.addIds(null);
			FrontController.getInstance().dispatchRequest(request);
		}
				
		if( event.getActionCommand().equals("Select Course") ) {
			
			if( selectedCourse == null ) {
				JOptionPane.showMessageDialog(null, "Please Selected a course.");
			}
			else {
				Request request = new Request(RequestHead.SELECT_COURSE);
				request.addIds(Integer.parseInt(selectedCourse));  // course id
				request.addIds(null);
				request.addIds(null);
				request.addIds(null);
				FrontController.getInstance().dispatchRequest(request);
			}
		}
		
		if( event.getActionCommand().equals("Delete Course") ) {			
			if( selectedCourse == null ) {
				JOptionPane.showMessageDialog(null, "Please Selected a course.");
			}
			else {
				Request request = new Request(RequestHead.DELETE_COURSE);
				request.addIds(Integer.parseInt(selectedCourse));  // course id
				request.addIds(null);
				request.addIds(null);
				request.addIds(null);
				FrontController.getInstance().dispatchRequest(request);
			}
		}
		
	}

}
