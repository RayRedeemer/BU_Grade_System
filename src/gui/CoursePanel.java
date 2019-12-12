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
public class CoursePanel extends JPanel implements ActionListener {
	
	private int courseID;
	private CourseForm courseForm;
	private CategoryForm cateForm;
	
	private String[] columnNames = 
		{ "ID", "Categories", "Weight" };
	private String[][] data = { 
            { "", "Homeworks", "30%" }, 
            { "", "Projects", "35%" },
            { "", "Exams", "35%" }
	};
	
	private JTable cateTable;
	private JScrollPane tableScrollPane;
	private int selectedRow;
	private int selectedColumn;
	private String selectedCate;
	
	private static final int headerHeight = 32;
	private static final int hGap = 25;
	private static final int vGap = 35;
	
	private JLabel titleLabel;
	private JLabel semesterLabel;	
	private JTextArea descriptionArea;
	private JLabel curveLabel;
	private JTextArea commentArea;
	private JLabel cateTableLabel;
		
	private String courseName;
	private String semester;
	private String description;
	private double curve;
	private String comment;
		
	/**
	 * Constructor
	 * */
	public CoursePanel( int _courseID, String _courseName, String _semester, String _description, double _curve, String _comment ) {
		this.courseID = _courseID;
		this.courseName = _courseName;
		this.semester = _semester;
		this.description = _description;
		this.curve = _curve;
		this.comment = _comment;

		setLayout(null);
		
		int frameWidth = MainFrame.getInstance().getWidth();
		int frameHeight = MainFrame.getInstance().getHeight();
			
		int x = (int)(frameWidth*0.2);
		int y = (int)(frameHeight*0.05);
		
		// getRequest from backend
		
		titleLabel = new JLabel(courseName);
		titleLabel.setBounds(x, y, 130, 50);
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
        editCourseButton.addActionListener(this);

        semesterLabel = new JLabel("Semester: " + semester);
		semesterLabel.setFont(font);
		semesterLabel.setBounds(labelX, labelY + vGap, labelWidth, textHeight);
		add(semesterLabel);
        
		curveLabel = new JLabel("Curve: ");
		curveLabel.setFont(font);
		curveLabel.setBounds(labelX, labelY + vGap * 2, labelWidth, textHeight);
		add(curveLabel);
				
		JLabel descriptionLabel = new JLabel("Description: ");
		descriptionLabel.setFont(font);
		descriptionLabel.setBounds(labelX, labelY + vGap * 3, labelWidth, textHeight);
		add(descriptionLabel);
		
		descriptionArea = new JTextArea();
		descriptionArea.setLineWrap(true);
		descriptionArea.setEditable(false);
		descriptionArea.setText(description);
		descriptionArea.setFont(font);
		JScrollPane despAreaScrollPane = new JScrollPane(descriptionArea);
		despAreaScrollPane.setBounds(labelX, labelY + vGap * 4, (int)(frameWidth*0.5), 100);
		add(despAreaScrollPane);
			
		JLabel commentLabel = new JLabel("Comment:");
		commentLabel.setFont(font);
		commentLabel.setBounds(labelX, despAreaScrollPane.getY() + despAreaScrollPane.getHeight() + vGap/2, labelWidth, textHeight);
		add(commentLabel);
		
		commentArea = new JTextArea();
		commentArea.setLineWrap(true);
		commentArea.setEditable(false);
		commentArea.setText(comment);
		commentArea.setFont(font);
		JScrollPane commentAreaScrollPane = new JScrollPane(commentArea);
		commentAreaScrollPane.setBounds(labelX, commentLabel.getY() + commentLabel.getHeight() + vGap/2, (int)(frameWidth*0.5), 100);
		add(commentAreaScrollPane);
		
		cateTableLabel = new JLabel("Categories");
		cateTableLabel.setFont(font);
		cateTableLabel.setBounds(labelX, commentAreaScrollPane.getY() + commentAreaScrollPane.getHeight() + vGap, labelWidth, textHeight);
		add(cateTableLabel);
				
		createCateTable();
		setScrollPane();
        add(tableScrollPane);
        
        int hButtonX = tableScrollPane.getX();
        int hButtonY = tableScrollPane.getY() + tableScrollPane.getHeight() + vGap;
        JButton stuOverview = new JButton("Student Overview");
        stuOverview.setBounds(hButtonX, hButtonY, (int) (buttonWidth * 1.7), textHeight);
        add(stuOverview);
        stuOverview.addActionListener(this);
        
        JButton manageStu = new JButton("Manage Student");
        manageStu.setBounds(stuOverview.getX() + stuOverview.getWidth() + hGap, hButtonY, (int) (buttonWidth * 1.7), textHeight);
        add(manageStu);
        manageStu.addActionListener(this);
        
        JButton courseStatistics = new JButton("Course Statistics");
        courseStatistics.setBounds(manageStu.getX() + manageStu.getWidth() + hGap, hButtonY, (int) (buttonWidth * 1.7), textHeight);
        add(courseStatistics);
        courseStatistics.addActionListener(this);
        
        JButton returnButton = new JButton("Return");
		returnButton.setBounds(courseStatistics.getX() + courseStatistics.getWidth() + hGap, hButtonY, (int) (buttonWidth * 1.7), textHeight);
		add(returnButton);
		returnButton.addActionListener(this);
		
        int vButtonX = returnButton.getX();
        int vButtonY = hButtonY;
        
        JButton addCate = new JButton("Add Category");
        addCate.setBounds(vButtonX, vButtonY - vGap * 4, (int) (buttonWidth * 1.7), textHeight);
        add(addCate);
        addCate.addActionListener(this);
        
        JButton editCate = new JButton("Edit Category");
        editCate.setBounds(vButtonX, vButtonY - vGap * 3, (int) (buttonWidth * 1.7), textHeight);
        add(editCate);
        editCate.addActionListener(this);
        
        JButton delCate = new JButton("Delete Category");
        delCate.setBounds(vButtonX, vButtonY - vGap * 2, (int) (buttonWidth * 1.7), textHeight);
        add(delCate);
        delCate.addActionListener(this);
        
	}
	
	/**
	 * Method: 
	 * Function: create assignment table
	 * */
	private void createCateTable() {
		int rowHeight = 20;
		int columnWidth = 110;
		int headerHeight = 32;
		
		// Initializing the JTable
		cateTable = new JTable(data, columnNames);
        
        // row height
		cateTable.setRowHeight(rowHeight);
        
        // center alignment
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setVerticalAlignment(JLabel.CENTER);
        
        for( int columnIndex = 0; columnIndex < cateTable.getModel().getColumnCount(); columnIndex++ ) {
        	cateTable.getColumnModel().getColumn(columnIndex).setCellRenderer( centerRenderer );  
        	cateTable.getColumnModel().getColumn(columnIndex).setPreferredWidth(columnWidth);
        }
        
        // Font
        Font font = cateTable.getFont();
        cateTable.setFont(new Font(font.getName(), font.getStyle(), font.getSize() + 3));
        
        // table background color
        cateTable.setBackground(new Color(255, 255, 208));
        
        // set table size
        cateTable.setPreferredScrollableViewportSize(cateTable.getPreferredSize());
        cateTable.setFillsViewportHeight(true);
        
        // header height
        JTableHeader header = cateTable.getTableHeader();
        header.setPreferredSize(new Dimension(columnWidth, headerHeight));  
        
        // Mouse listener
        // select course
        cateTable.addMouseListener( new MouseAdapter() {       	
        	@Override
        	 public void mouseClicked(MouseEvent event) {
        		try {
        			selectedRow = cateTable.rowAtPoint(event.getPoint());
            	    selectedColumn = cateTable.columnAtPoint(event.getPoint());
            	    System.out.println("Click: " + "Row: " + selectedRow + " Column: " + selectedColumn);
            	    selectedCate = data[selectedRow][0];
            	    System.out.println("Selected Cate: " + selectedCate);
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
		int tableWidth = (int) cateTable.getPreferredSize().getWidth();
		tableScrollPane = new JScrollPane(cateTable);
		tableScrollPane.setBounds(cateTableLabel.getX(), cateTableLabel.getY() + vGap, 
        		tableWidth, 
        		100);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if( event.getActionCommand().equals("Return") ) {
			// Return to Admin Panel
			MainFrame.getInstance().removeCurPanel();
			MainFrame.getInstance().setAdminPanel();
		}
		
		if( event.getActionCommand().equals("Edit Course") ) {
			if( courseForm != null ) {
				courseForm.dispose();
			}
			courseForm = new CourseForm(RequestHead.UPDATE_COURSE, courseID, courseName, semester, description, curve, comment);
		}
		
		if( event.getActionCommand().equals("Add Category") ) {
			if( cateForm != null ) {
				cateForm.dispose();
			}
			cateForm = new CategoryForm(courseID, 0);
		}
		
		if( event.getActionCommand().equals("Edit Category") ) {
			
			if( selectedCate == null ) {
				JOptionPane.showMessageDialog(null, "Please Selected a category.");
			}
			else {
				Request request = new Request(RequestHead.SELECT_CATEGORY);
				request.addIds(courseID);  // course id
				request.addIds(Integer.parseInt(selectedCate));
				request.addIds(null);
				request.addIds(null);
				FrontController.getInstance().dispatchRequest(request);
				
			}
		}
		
		if( event.getActionCommand().equals("Delete Category") ) {
			if( selectedCate == null ) {
				JOptionPane.showMessageDialog(null, "Please Selected a category.");
			}
			else {
				Request request = new Request(RequestHead.DELETE_CATEGORY);
				request.addIds(courseID);  // course id
				request.addIds(Integer.parseInt(selectedCate));
				request.addIds(null);
				request.addIds(null);
				FrontController.getInstance().dispatchRequest(request);				
			}
		}
		
		if( event.getActionCommand().equals("Student Overview") ) {
			MainFrame.getInstance().removeCurPanel();
			MainFrame.getInstance().setOverviewPanel();
		}
		
		if( event.getActionCommand().equals("Manage Student") ) {
			MainFrame.getInstance().removeCurPanel();
			MainFrame.getInstance().setStuManagePanel();
		}
		
		if( event.getActionCommand().equals("Course Statistics") ) {
			MainFrame.getInstance().removeCurPanel();
			MainFrame.getInstance().setStatPanel();
		}
	}
	
	/**
	 * Method: setCourse
	 * */
	public void setCourse(int _courseID, String _courseName, String _semester, String _description, double _curve, String _comment) {
		this.courseID = _courseID;
		this.courseName = _courseName;
		this.semester = _semester;
		this.description = _description;
		this.curve = _curve;
		this.comment = _comment;
		titleLabel.setText(courseName);
		semesterLabel.setText("Semester: " + semester);
		descriptionArea.setText(description);
		curveLabel.setText("Curve: " + Double.toString(curve));
		commentArea.setText(comment);
	}
	
	/**
	 * Method: getCateList
	 * Function: 
	 * 		1. send request to back end.
	 * 		2. dispatcher will call updateCateList.
	 * */
	public void getCateList() {
		Request request = new Request(RequestHead.GET_CATEGORY_LIST);
		request.addIds(courseID);
		request.addIds(null);
		request.addIds(null);
		request.addIds(null);
		FrontController.getInstance().dispatchRequest(request);		
	}
	
	/**
	 * Method: updateCateList
	 * Function:
	 * 		Dispatcher will call this function
	 * */
	public void updateCateList(String[][] _data) {
		this.data = _data;
		remove(tableScrollPane);
		createCateTable();
		setScrollPane();
		add(tableScrollPane);
	}
	
}
