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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import share.Request;
import share.RequestHead;

/*
Author: Ziqi Tan
*/
public class CoursePanel extends JPanel {
	
	private int courseID;
	
	private String courseName = "CS 591 Object Oriented Design";
	private String semester = "Fall19";
	private String description = "Various issues in computer science that vary semester to semester." + System.getProperty("line.separator")
				+ "Please contact the CAS Computer Science Department for detailed descriptions." + System.getProperty("line.separator")
				+ "Though courses are variable credit, registration for less than four credits requires instructor approval.";;
	
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
	
	private static final int headerHeight = 32;
	
	/**
	 * Constructor
	 * */
	public CoursePanel( int _courseID ) {
		this.courseID = _courseID;
		setLayout(null);
		
		int frameWidth = MainFrame.getInstance().getWidth();
		int frameHeight = MainFrame.getInstance().getHeight();
		
		int hGap = 25;
		int vGap = 35;
		
		int x = (int)(frameWidth*0.2);
		int y = (int)(frameHeight*0.05);
		
		// getRequest from backend
		
		JLabel titleLabel = new JLabel(courseName);
		titleLabel.setBounds(x, y, 400, 50);
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
		add(titleLabel);
		
		int buttonWidth = 80;
		int textHeight = 25;
		JButton logoutButton = new JButton("Logout");
		logoutButton.setBounds(titleLabel.getX() + titleLabel.getWidth() + hGap, titleLabel.getY() + 13, buttonWidth, textHeight);
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
		
		
				       
        int labelWidth = 150;
        int labelX = titleLabel.getX();
        int labelY = titleLabel.getY() + titleLabel.getHeight();
        Font font = new Font("Times New Roman", Font.BOLD, 18);
        
        // Edit course
        JButton editCourseButton = new JButton("Edit Course");
        editCourseButton.setBounds(labelX, labelY, buttonWidth + 30, textHeight);
        add(editCourseButton);
        

        JLabel semesterLabel = new JLabel("Semester: " + semester);
		semesterLabel.setFont(font);
		semesterLabel.setBounds(labelX, labelY + vGap, labelWidth, textHeight);
		add(semesterLabel);
        
		JLabel descriptionLabel = new JLabel("Description: ");
		descriptionLabel.setFont(font);
		descriptionLabel.setBounds(labelX, labelY + vGap * 2, labelWidth, textHeight);
		add(descriptionLabel);
		
		JTextArea descriptionArea = new JTextArea();
		descriptionArea.setLineWrap(true);
		descriptionArea.setEditable(false);
		descriptionArea.setText(description);
		descriptionArea.setFont(font);
		JScrollPane textAreaScrollPane = new JScrollPane(descriptionArea);
		textAreaScrollPane.setBounds(labelX, labelY + vGap * 3, (int)(frameWidth*0.5), 100);
		add(textAreaScrollPane);
		
		JLabel assignmentTableLabel = new JLabel("Assignments");
		assignmentTableLabel.setFont(font);
		assignmentTableLabel.setBounds(labelX, textAreaScrollPane.getY() + textAreaScrollPane.getHeight() + vGap, labelWidth, textHeight);
		add(assignmentTableLabel);
		
		
		createAssignmentTable();
		int tableWidth = (int) assignmentTable.getPreferredSize().getWidth();
		JScrollPane tableScrollPane = new JScrollPane(assignmentTable);
		tableScrollPane.setBounds(labelX, assignmentTableLabel.getY() + vGap, 
        		tableWidth, 
        		(int) (assignmentTable.getPreferredSize().getHeight() + headerHeight + 3));
        add(tableScrollPane);
        

        
        int hButtonX = tableScrollPane.getX();
        int hButtonY = tableScrollPane.getY() + tableScrollPane.getHeight() + vGap;
        JButton stuOverview = new JButton("Student Overview");
        stuOverview.setBounds(hButtonX, hButtonY, (int) (buttonWidth * 1.7), textHeight);
        add(stuOverview);
        
        JButton manageStu = new JButton("Manage Student");
        manageStu.setBounds(stuOverview.getX() + stuOverview.getWidth() + hGap, hButtonY, (int) (buttonWidth * 1.7), textHeight);
        add(manageStu);
        
        JButton courseStatistics = new JButton("Course Statistics");
        courseStatistics.setBounds(manageStu.getX() + manageStu.getWidth() + hGap, hButtonY, (int) (buttonWidth * 1.7), textHeight);
        add(courseStatistics);
        
        JButton returnButton = new JButton("Return");
		returnButton.setBounds(courseStatistics.getX() + courseStatistics.getWidth() + hGap, hButtonY, (int) (buttonWidth * 1.7), textHeight);
		add(returnButton);
		
        int vButtonX = manageStu.getX() + manageStu.getWidth() + hGap;
        int vButtonY = hButtonY;
        
        JButton addCate = new JButton("Add Category");
        addCate.setBounds(vButtonX, vButtonY - vGap * 4, (int) (buttonWidth * 1.7), textHeight);
        add(addCate);
        
        JButton editCate = new JButton("Edit Category");
        editCate.setBounds(vButtonX, vButtonY - vGap * 3, (int) (buttonWidth * 1.7), textHeight);
        add(editCate);
        
        JButton delCate = new JButton("Delete Category");
        delCate.setBounds(vButtonX, vButtonY - vGap * 2, (int) (buttonWidth * 1.7), textHeight);
        add(delCate);
        
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
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setVerticalAlignment(JLabel.CENTER);
        
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
	
	
}
