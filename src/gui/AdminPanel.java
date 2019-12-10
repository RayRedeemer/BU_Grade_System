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
		
		// getCourseList();
		
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
		
		JButton updateCourseButton = new JButton("Update Course");
		updateCourseButton.setBounds(buttonX, buttonY + vGap * 3, buttonWidth, textHeight);
		add(updateCourseButton);
		updateCourseButton.addActionListener(this);
		
		JButton deleteCourseButton = new JButton("Delete Course");
		deleteCourseButton.setBounds(buttonX, buttonY + vGap * 4, buttonWidth, textHeight);
		add(deleteCourseButton);
		deleteCourseButton.addActionListener(this);
		
		JButton refresh = new JButton("Refresh");
		refresh.setBounds(buttonX, buttonY + vGap * 5, buttonWidth, textHeight);
		add(refresh);
		refresh.addActionListener(this);	
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
	 * */
	private void getCourseList() {
		Request request = new Request(RequestHead.GET_COURSE_LIST);
		request.addIds(null);
		request.addIds(null);
		request.addIds(null);
		request.addIds(null);
		FrontController.getInstance().dispatchRequest(request);
	}
	
	public void updateCourseList( String[] _columnNames, String[][] _data ) {
		this.columnNames = _columnNames;
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
			addCourseFrame = new Form();
			
		}
		
		if( event.getActionCommand().equals("Clone Course") ) {
			
		}
				
		if( event.getActionCommand().equals("Select Course") ) {
			Request request = new Request(RequestHead.SELECT_COURSE);
			if( selectedCourse == null ) {
				JOptionPane.showMessageDialog(null, "Please Selected a course.");
			}
			else {
				request.addIds(Integer.parseInt(selectedCourse));  // course id
				request.addIds(null);
				request.addIds(null);
				request.addIds(null);
				FrontController.getInstance().dispatchRequest(request);
			}
		}
		
		if( event.getActionCommand().equals("Update Course") ) {
			
		}
		
		if( event.getActionCommand().equals("Delete Course") ) {
			Request request = new Request(RequestHead.DELETE_COURSE);
			if( selectedCourse == null ) {
				JOptionPane.showMessageDialog(null, "Please Selected a course.");
			}
			else {
				request.addIds(Integer.parseInt(selectedCourse));  // course id
				request.addIds(null);
				request.addIds(null);
				request.addIds(null);
				FrontController.getInstance().dispatchRequest(request);
			}
		}
		
		if( event.getActionCommand().equals("Refresh") ) {
			// update course list
			getCourseList();			
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
					if( courseName.length() == 0 || courseSemester.length() == 0 || courseDesp.length() == 0 ) {
						System.out.println("Please fill the form.");
						JOptionPane.showMessageDialog(null, "Please fill the form.");
					}
					else {
						// Request
						Request request = new Request(RequestHead.ADD_COURSE);
						request.addParams(100); // instructor id
						request.addParams(courseName);						
						request.addParams(courseDesp);
						request.addParams(courseSemester);
						request.addIds(null);
						request.addIds(null);
						request.addIds(null);
						request.addIds(null);
						FrontController.getInstance().dispatchRequest(request);
						JOptionPane.showMessageDialog(null, "Submit successfully.");
						dispose();
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
