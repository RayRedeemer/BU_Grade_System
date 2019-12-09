package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
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
		
	private JFrame addCourseFrame;
	private JTable courseListTable;
	private String[] columnNames = 
		{ "Name", "Semester", "Enrollment Count", "Average Grade" };
	private String[][] data = { 
            { "CS 591", "Fall19", "66", "--"}, 
            { "CS 592", "Fall19", "30", "--" },
            { "CS 666", "Spring18", "150", "83" },
            { "CS 591", "Summer17", "111", "87"}
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
		addCourseButton.addActionListener(this);
		
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
		
		if( event.getActionCommand().equals("Add Course") ) {
			Request request = new Request(RequestHead.ADD_COURSE);
			if( addCourseFrame != null ) {
				addCourseFrame.dispose();
			}
			addCourseFrame = new Form();
			
		}
		
		if( event.getActionCommand().equals("Clone Course") ) {
			
		}
				
		if( event.getActionCommand().equals("Select Course") ) {
			Request request = new Request(RequestHead.SELECT_COURSE);
			request.addParams(selectedCourse);
			FrontController.getInstance().dispatchRequest(request);
		}
		
		if( event.getActionCommand().equals("Delete Course") ) {
			
		}
		
	}
	
	/**
	 * inner class
	 * form to submit and add a course
	 * */
	class Form extends JFrame {
		
		public Form() {
			System.out.println("A form is creating.");
			setTitle("Add course");	
						
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int frameWidth = (int)(screenSize.getWidth() * 0.36);
			int frameHeight = (int)(screenSize.getHeight() * 0.6);
			setSize(frameWidth, frameHeight);
			      		
			int hGap = 20;
			int vGap = 50;
			
			setLocationRelativeTo(null);   // this will center your frame
			
			setVisible(true);
			
			JPanel formPanel = new JPanel();
			formPanel.setLayout(null);
			
			int labelX = (int)(frameWidth*0.2);
			int labelY = (int)(frameHeight*0.2);
			
			int labelWidth = 100;
			int labelHeight = 25;
			JLabel nameLabel = new JLabel("Course name: ");
			nameLabel.setBounds(labelX, labelY, labelWidth, labelHeight);
			formPanel.add(nameLabel);
			
			JLabel semesterLabel = new JLabel("Semester: ");
			semesterLabel.setBounds(labelX, labelY + vGap, labelWidth, labelHeight);
			formPanel.add(semesterLabel);
			
			JLabel despLabel = new JLabel("Description: ");
			despLabel.setBounds(labelX, labelY + vGap * 2, labelWidth, labelHeight);
			formPanel.add(despLabel);
			
			int textFieldWidth = 130;
			JTextField nameField = new JTextField(20);
			nameField.setBounds(labelX + nameLabel.getWidth() + hGap, nameLabel.getY(), textFieldWidth, labelHeight);
			formPanel.add(nameField);
			
			JTextField semesterField = new JTextField(20);
			semesterField.setBounds(labelX + semesterLabel.getWidth() + hGap, semesterLabel.getY(), textFieldWidth, labelHeight);
			formPanel.add(semesterField);
			
			JTextArea despArea = new JTextArea();
			despArea.setLineWrap(true);
			JScrollPane textAreaScrollPane = new JScrollPane(despArea);
			textAreaScrollPane.setBounds(labelX + despLabel.getWidth() + hGap, despLabel.getY(), (int) (textFieldWidth * 2.3), 180);
			formPanel.add(textAreaScrollPane);
			
			int buttonWidth = 80;
			JButton submit = new JButton("Submit");
			submit.setBounds(labelX, labelY + vGap * 3, buttonWidth, labelHeight);
			formPanel.add(submit);
			submit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					String courseName = nameField.getText();
					String courseSemester = semesterField.getText();
					String courseDesp = despArea.getText();
					if( courseName == null || courseSemester == null || courseDesp == null ) {
						System.out.println("Please fill the form.");
					}
					else {
						// Request
						Request request = new Request(RequestHead.ADD_COURSE);
						request.addParams(courseName);
						request.addParams(courseSemester);
						request.addParams(courseDesp);
						request.addIds(null);
						request.addIds(null);
						request.addIds(null);
						request.addIds(null);
						FrontController.getInstance().dispatchRequest(request);
						
					}
				}
			});
						
			JButton cancel = new JButton("Cancel");
			cancel.setBounds(labelX, labelY + vGap * 4, buttonWidth, labelHeight);
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
	
}
