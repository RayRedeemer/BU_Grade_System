package gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import share.Request;
import share.RequestHead;

import javax.swing.JButton;
import javax.swing.JEditorPane;

public class OverviewPanel extends JPanel implements ActionListener {
	
	
	// Use a map
	private HashMap<Integer, List<Integer>> assignmentMap;
	// < CategoryID, List<AssignmentID> >
	
	private JTable stuViewTable;
	private JScrollPane scrollPane;
	private JLabel lblTitle, lblComment;
	private JButton btnLogout, btnAdd, btnUpdate, btnDelete, btnReturn;
	private JEditorPane epComment;
		
	// add
	// int studentId, double score, double bonus, LocalDateTime submitTime, Boolean style
	
	// update
	// int courseId, int categoryId, int assignmentId, int submissionId, 
	// List<Object> params (score, bonus, submittedDate, earnOrLose, comment)
	// Student ID		
	private String[] columnNames = {
		"ID", "Student", "Homework1", "Homework2", "TicTacToe", "Blackjack", "Trianta Ena", "Cave Adventure", "Fancy Bank", "Exam1", "Exam2"
	};
	
	private String[][] data = {
		{"2", "Charles", "-10", "-5", "A", "-1", "-10", "-1", "-2", "-15", "B", "A"},
		{"4", "Ziqi", "-20", "-30", "-40", "B", "-10", "-1", "-2", "-15", "B", "C"},
		{"8", "Someone", "-15", "-30", "A", "B", "-6", "-3", "-6", "-10", "C", "A"}, 
		{"9", "Somebody", "-10", "C", "-40", "B", "-10", "-1", "-7", "-8", "B", "A"}
	};
	
	private int courseID;
	private int selectedRow;
	private int selectedColumn;
	private int selectedStuId;
	private int selectedAssignmentId;
	private int selectedSubmissionId;
	private int selectedCateId;
	
	private int frameWidth;
	private int frameHeight;
	
	/**
	 * Create the panel.
	 */
	public OverviewPanel() {
		setLayout(null);
		
		frameWidth = MainFrame.getInstance().getWidth();
		frameHeight = MainFrame.getInstance().getHeight();
		
		Font titleFont = new Font("Times New Roman", Font.BOLD, 25);
		Font labelFont = new Font("Times New Roman", Font.PLAIN, 20);
		Font btnFont = new Font("Microsoft YaHei UI", Font.PLAIN, 20);
		
		int textHeight = 25;
		int labelWidth = 80;
		int buttonWidth = 120;
				
		int x = (int)(frameWidth*0.1);
		int y = (int)(frameHeight*0.1);
		
		int hGap = 50;
		int vGap = 50;
		
		lblTitle = new JLabel("Student Overview");
		lblTitle.setFont(titleFont);
		lblTitle.setBounds(x, y, 200, 25);
		add(lblTitle);
		
		createStuViewTable();
		setScrollPane();
		add(scrollPane);
		
		btnLogout = new JButton("Logout");
		btnLogout.setBounds(x + lblTitle.getWidth() + hGap, lblTitle.getY(), buttonWidth, textHeight);
		add(btnLogout);
		btnLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Request request = new Request(RequestHead.LOGOUT);
				FrontController.getInstance().dispatchRequest(request);
			}
		});
		
		JLabel cateNameLabel = new JLabel("Category: Homework/Project");
		cateNameLabel.setBounds(x, scrollPane.getY() + scrollPane.getHeight() + 10, 200, textHeight);
		cateNameLabel.setFont(labelFont);
		add(cateNameLabel);
		
		lblComment = new JLabel("Comment:");
		lblComment.setBounds(x, scrollPane.getY() + scrollPane.getHeight() + vGap, 87, 18);
		lblComment.setFont(labelFont);
		add(lblComment);
		
		epComment = new JEditorPane();
		epComment.setBounds(x, lblComment.getY() + lblComment.getHeight(), scrollPane.getWidth(), 100);
		add(epComment);
		
		int buttonX = scrollPane.getX() + scrollPane.getWidth() + hGap;
		int buttonY = scrollPane.getY() + vGap;
		
		btnAdd = new JButton("Add Submission");
		btnAdd.setBounds(buttonX, buttonY, (int) (buttonWidth*1.4), textHeight);
		add(btnAdd);
		btnAdd.addActionListener(this);
		
		btnUpdate = new JButton("Update Submission");
		btnUpdate.setBounds(buttonX, btnAdd.getY() + vGap, (int) (buttonWidth*1.4), textHeight);
		add(btnUpdate);
		btnUpdate.addActionListener(this);
		
		btnDelete = new JButton("Delete Submission");
		btnDelete.setBounds(buttonX, btnUpdate.getY() + vGap, (int) (buttonWidth*1.4), textHeight);
		add(btnDelete);
		btnDelete.addActionListener(this);
		
		btnReturn = new JButton("Return");
		btnReturn.setBounds(buttonX, btnDelete.getY() + vGap, (int) (buttonWidth*1.4), textHeight);
		add(btnReturn);
		btnReturn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {				
				MainFrame.getInstance().removeCurPanel();
				MainFrame.getInstance().setCoursePanel();
			}
		});
	}
	
	/**
	 * Method: createStuTable
	 * */
	private void createStuViewTable() {
		int rowHeight = 20;
		int columnWidth = 75;
		int headerHeight = 32;
		
		// Initializing the JTable
		stuViewTable = new JTable(data, columnNames);
        
        // row height
		stuViewTable.setRowHeight(rowHeight);
        
        // center alignment
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setVerticalAlignment(JLabel.CENTER);
        
        for( int columnIndex = 0; columnIndex < stuViewTable.getModel().getColumnCount(); columnIndex++ ) {
        	stuViewTable.getColumnModel().getColumn(columnIndex).setCellRenderer( centerRenderer );  
        	stuViewTable.getColumnModel().getColumn(columnIndex).setPreferredWidth(columnWidth);
        }
        
        // Font
        Font font = stuViewTable.getFont();
        stuViewTable.setFont(new Font(font.getName(), font.getStyle(), font.getSize() + 3));
        
        // table background color
        stuViewTable.setBackground(new Color(255, 255, 208));
        
        // set table size
        stuViewTable.setPreferredScrollableViewportSize(stuViewTable.getPreferredSize());
        stuViewTable.setFillsViewportHeight(true);
        
        // header height
        JTableHeader header = stuViewTable.getTableHeader();
        header.setPreferredSize(new Dimension(columnWidth, headerHeight));  
        
        // Mouse listener
        // select course
        stuViewTable.addMouseListener( new MouseAdapter() {       	
        	@Override
        	 public void mouseClicked(MouseEvent event) {
        		try {
        			selectedRow = stuViewTable.rowAtPoint(event.getPoint());
            	    selectedColumn = stuViewTable.columnAtPoint(event.getPoint());
            	    System.out.println("Click: " + "Row: " + selectedRow + " Column: " + selectedColumn);
            	    /*selectedAssignmentId = assignmentIds.get(selectedColumn-1);
            	    System.out.println("Selected assignment: " + selectedAssignmentId);
            	    selectedStuId = stuIds.get(selectedRow-1);
            	    System.out.println("Selected stu: " + selectedStuId);*/
            	    
            	    // TODO: update comment
        		}
        		catch( Exception error ) {
        			System.out.println(error);
        		}        	    
        	 }
        } );
	}
	
	/**
	 * Method: set
	 * */
	private void setScrollPane() {
		int tableWidth = (int) stuViewTable.getPreferredSize().getWidth();
        scrollPane = new JScrollPane(stuViewTable); 
        scrollPane.setBounds((int) (frameWidth * 0.1), lblTitle.getY() + lblTitle.getHeight() + 10, 
        		tableWidth, 
        		(int)(frameHeight*0.5));
	}
	
	/**
	 * Method: getSubmissionList
	 * */
	public void getSubmissionList() {
		
	}
	/**
	 * Method: updateSubmissionList
	 * */
	public void updateSubmissionList( String[][] _data ) {
	
	}
	
	/**
	 * Method: selectSubmission
	 * */
	public void selectSubmission() {
		
	}
	
	/**
	 * Method: setCurSubmission
	 * */
	public void setCurSubmission() {
		
	}
	
	/**
	 * Method: getAllCate
	 * */
	
	/**
	 * Method: getAllAssignmentFromAllCate
	 * */
	
	@Override
	public void actionPerformed(ActionEvent event) {

		if( event.getActionCommand().equals("Curve") ) {
			
		}
		
		if( event.getActionCommand().equals("Save") ) {
			// TODO: save comment
		}
		
		if( event.getActionCommand().equals("Add Submission") ) {
			
		}
		
		if( event.getActionCommand().equals("Update Submission") ) {
			
		}
		
		if( event.getActionCommand().equals("Delete Submission") ) {
			
		}
		
	}

}
