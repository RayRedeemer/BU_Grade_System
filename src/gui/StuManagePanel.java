package gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import backend.Student;
import db.DatabasePortal;
import share.Request;
import share.RequestHead;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JEditorPane;

public class StuManagePanel extends JPanel implements ActionListener {
	
	private int courseID;
	private String courseName;
	private StudentForm stuForm;
	
	private JTable stuTable;
	private JScrollPane scrollPane;
	private JEditorPane commentEditor;
	private JLabel lblTitle, courseNameLabel, commentLabel;
	private JButton btnLogout, btnAdd, btnUpdate, btnDrop, btnWithdraw, btnReturn;

	private int frameWidth;
	private int frameHeight;
	
	private int selectedRow;
	private int selectedColumn;
	private String selectedStu;
	
	private static final int headerHeight = 32;
	
	private String[] columnNames = { 
		"StuID", "StuName", "BU ID", "Grade", "Undergrad/Grad", "Bonus", "Adjustment", "A/W", "Email"
	};
	private String[][] data = { 
        { "666", "Jerry", "123456", "99", "Grad", "6", "6", "A", "Jerry@bu.edu"}
    };
	
	/**
	 * Create the panel.
	 */
	public StuManagePanel(int _courseID, String _courseName) {
		this.courseID = _courseID;
		this.courseName = _courseName;
		setLayout(null);
		
		frameWidth = MainFrame.getInstance().getWidth();
		frameHeight = MainFrame.getInstance().getHeight();
		
		Font titleFont = new Font("Times New Roman", Font.BOLD, 25);
		Font labelFont = new Font("Times New Roman", Font.PLAIN, 20);
		Font btnFont = new Font("Microsoft YaHei UI", Font.PLAIN, 20);
		
		int x = (int)(frameWidth*0.1);
		int y = (int)(frameHeight*0.1);
		
		int hGap = 30;
		int vGap = 30;
		
		int textWidth = 200;
		int textHeight = 25;
		
		int buttonWidth = 80;
		int buttonHeight = 25;
		lblTitle = new JLabel("Manage Students");
		lblTitle.setBounds(x, y, textWidth, textHeight);
		lblTitle.setFont(titleFont);
		add(lblTitle);

		courseNameLabel = new JLabel(courseName);
		courseNameLabel.setBounds(x, y + vGap, textWidth, textHeight);
		courseNameLabel.setFont(titleFont);
		add(courseNameLabel);
		
		btnLogout = new JButton("Logout");
		btnLogout.setBounds(x + lblTitle.getWidth() + hGap, y, buttonWidth, buttonHeight);
		add(btnLogout);
		btnLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Request request = new Request(RequestHead.LOGOUT);
				FrontController.getInstance().dispatchRequest(request);
			}
		});
		
		// Student table
		createStuTable();
		setScrollPane();
		add(scrollPane);
		
		commentLabel = new JLabel("Comment");
		commentLabel.setFont(labelFont);
		commentLabel.setBounds(x, scrollPane.getY() + scrollPane.getHeight() + vGap, textWidth, textHeight);
		add(commentLabel);
		
		// comment editorPane
		commentEditor = new JEditorPane();
		commentEditor.setBounds(x, commentLabel.getY() + commentLabel.getHeight(), scrollPane.getWidth(), 100);
		add(commentEditor);
		
		int buttonX = x;
		int buttonY = commentEditor.getY() + commentEditor.getHeight() + vGap;
		
		btnAdd = new JButton("Add Student");
		btnAdd.setBounds(buttonX, buttonY, (int) (buttonWidth * 1.7), buttonHeight);
		add(btnAdd);
		btnAdd.addActionListener(this);
		
		btnUpdate = new JButton("Update Student");
		btnUpdate.setBounds(buttonX + btnAdd.getWidth() + hGap, buttonY, (int) (buttonWidth * 1.7), buttonHeight);
		add(btnUpdate);
		btnUpdate.addActionListener(this);
		
		// TODO: withdraw
		btnWithdraw = new JButton("Withdraw Student");
		btnWithdraw.setBounds(btnUpdate.getX() + btnUpdate.getWidth() + hGap, buttonY, (int) (buttonWidth * 1.7), buttonHeight);
		add(btnWithdraw);
		btnWithdraw.addActionListener(this);
		
		btnDrop = new JButton("Drop Student");
		btnDrop.setBounds(btnWithdraw.getX() + btnWithdraw.getWidth() + hGap, buttonY, (int) (buttonWidth * 1.7), buttonHeight);
		add(btnDrop);
		btnDrop.addActionListener(this);
		
		btnReturn = new JButton("Return");
		btnReturn.setBounds(btnDrop.getX() + btnDrop.getWidth() + hGap, buttonY, (int) (buttonWidth * 1.7), buttonHeight);
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
	 * Method: createJTable
	 * Function: create an assignment table.
	 * */
	private void createStuTable() {
		
		int rowHeight = 20;
		int columnWidth = 100;
		
        // Initializing the JTable
		stuTable = new JTable(data, columnNames);
        
        // row height
		stuTable.setRowHeight(rowHeight);
        
        // center alignment
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        centerRenderer.setVerticalAlignment( JLabel.CENTER );
        
        for( int columnIndex = 0; columnIndex < stuTable.getModel().getColumnCount(); columnIndex++ ) {
        	stuTable.getColumnModel().getColumn(columnIndex).setCellRenderer(centerRenderer); 
        	stuTable.getColumnModel().getColumn(columnIndex).setPreferredWidth(columnWidth);
        }
                
        // Font
        Font font = stuTable.getFont();
        stuTable.setFont(new Font(font.getName(), font.getStyle(), font.getSize() + 3));
        
        // table background color
        stuTable.setBackground(new Color(255, 255, 208));
        
        // set table size
        stuTable.setPreferredScrollableViewportSize(stuTable.getPreferredSize());
        stuTable.setFillsViewportHeight(true);
        
        // header height
        JTableHeader header = stuTable.getTableHeader();
        header.setPreferredSize(new Dimension(columnWidth, headerHeight));  
        
        // Mouse listener
        // select course
        stuTable.addMouseListener(new MouseAdapter() {       	
        	@Override
        	 public void mouseClicked(MouseEvent event) {
        		try {
        			selectedRow = stuTable.rowAtPoint(event.getPoint());
            	    selectedColumn = stuTable.columnAtPoint(event.getPoint());
            	    System.out.println("Click: " + "Row: " + selectedRow + " Column: " + selectedColumn);
            	    selectedStu = data[selectedRow][0];
            	    System.out.println("Selected assignment: " + selectedStu);
            	    selectStudent();
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
		int tableWidth = (int) stuTable.getPreferredSize().getWidth();
	    scrollPane = new JScrollPane(stuTable); 
	    scrollPane.setBounds(courseNameLabel.getX(), courseNameLabel.getY() + courseNameLabel.getHeight(), 
	    		tableWidth, 
	    		(int)(frameHeight*0.4));
	}
	
	/**
	 * Method: setCurCourse
	 * */
	public void setCurCourse(int _courseID, String _courseName) {
		this.courseID = _courseID;
		this.courseName = _courseName;
		courseNameLabel.setText(courseName);
	}
	
	/**
	 * Method: getStuList
	 * */
	public void getStuList() {
		// TODO
		Request request = new Request(RequestHead.GET_STUDENT_LIST);
		request.addIds(courseID);
		request.addIds(null);
		request.addIds(null);
		request.addIds(null);
		FrontController.getInstance().dispatchRequest(request);		
	}
	
	/**
	 * Method: updateStuList
	 * */
	public void updateStuList(String[][] _data) {
		this.data = _data;
		remove(scrollPane);
		createStuTable();
		setScrollPane();
		add(scrollPane);
	}
	 
	/**
	 * Method: selectStudent
	 * */
	private void selectStudent() {
		// TODO
		Request request = new Request(RequestHead.SELECT_STUDENT);
		request.addParams(Integer.parseInt(selectedStu));
		request.addIds(null);
		request.addIds(null);
		request.addIds(null);
		request.addIds(null);
		FrontController.getInstance().dispatchRequest(request);
	}
	
	/**
	 * Method: setCurStuComment
	 * */
	public void setCurStuComment(String _comment) {
		commentEditor.setText(_comment);		
	}
	
	/**
	 * Method: clearEditor
	 * */
	public void clearEditor() {
		commentEditor.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		if( event.getActionCommand().equals("Add Student") ) {
			if( stuForm != null ) {
				stuForm.dispose();
			}
			stuForm = new StudentForm(courseID);
		}
		
		if( event.getActionCommand().equals("Update Student") ) {
			if( selectedStu == null ) {
				JOptionPane.showMessageDialog(null, "Please Selected a student.");
				return ;
			}
			
			// "StuID", "StuName", "BU ID", "Grade", "Undergrad/Grad", "Bonus", "Adjustment", "A/W", "Email"
			String stuID = data[selectedRow][0];
			String stuName = data[selectedRow][1];
			String buId = data[selectedRow][2];
			String grade = data[selectedRow][3];
			String uOrG = data[selectedRow][4];
			String bonus = data[selectedRow][5];
			String adjust = data[selectedRow][6];
			String aOrW = data[selectedRow][7];
			String email = data[selectedRow][8];
			String comment = commentEditor.getText();
			
			if( stuName.length() == 0 || buId.length() == 0 ||
					grade.length() == 0 || uOrG.length() == 0 ||
					bonus.length() == 0 || adjust.length() == 0
					) {
				JOptionPane.showMessageDialog(null, "Please fill enough information.");
				return;
			}
			
			try {
				Request request = new Request(RequestHead.UPDATE_STUDENT);
				request.addIds(null);
				request.addIds(null);
				request.addIds(null);
				request.addIds(null);
				request.addParams(Integer.parseInt(stuID));
				request.addParams(email);
				request.addParams(stuName);
				request.addParams(buId);
				request.addParams(Double.parseDouble(grade));
				request.addParams(Double.parseDouble(adjust));
				request.addParams(Double.parseDouble(bonus));
				if( uOrG.charAt(0) == 'G' ) {
					request.addParams(true);
				}
				else {
					request.addParams(false);
				}
				if( aOrW.equals("W") ) {
					request.addParams(true);
				}
				else {
					request.addParams(false);
				}
				request.addParams(comment);
				FrontController.getInstance().dispatchRequest(request);				
			}
			catch(NumberFormatException error) {
				System.out.println(error);
				JOptionPane.showMessageDialog(null, "Input number for grade, adjustment and bonus.");
			}
			catch( Exception error ) {
				System.out.println(error);
			}
			
			/*
	        Integer studentId = (Integer) params.get(0);
	        Student student = DatabasePortal.getInstance().getStudentById(studentId);

	        student.setName((String) params.get(1)); // name
	        student.setEmail((String) params.get(2)); // email
	        student.setBuId((String) params.get(3)); // buId
	        student.setGrade((Double) params.get(4)); // grade
	        student.setAdjustment((Double) params.get(5)); // adjustment
	        student.setAdjustment((Double) params.get(6)); // bonus
	        student.setGrad((Boolean) params.get(7)); // isGrad
	        student.setGrad((Boolean) params.get(8)); // withdrawn
	        student.setComment((String) params.get(9)); // comment*/
						
		}
		
		if( event.getActionCommand().equals("Withdraw Student") ) {
			if( selectedStu == null ) {
				JOptionPane.showMessageDialog(null, "Please Selected a student.");
				return ;
			}
			Request request = new Request(RequestHead.WITHDRAW_STUDENT);
			request.addParams(Integer.parseInt(selectedStu));
			request.addIds(null);
			request.addIds(null);
			request.addIds(null);
			request.addIds(null);
			FrontController.getInstance().dispatchRequest(request);
			JOptionPane.showMessageDialog(null, "Withdraw successfully.");
		}
		
		
		if( event.getActionCommand().equals("Drop Student") ) {
			if( selectedStu == null ) {
				JOptionPane.showMessageDialog(null, "Please Selected a student.");
				return ;
			}
			Request request = new Request(RequestHead.DROP_STUDENT);
			request.addParams(Integer.parseInt(selectedStu));
			request.addIds(null);
			request.addIds(null);
			request.addIds(null);
			request.addIds(null);
			FrontController.getInstance().dispatchRequest(request);
			JOptionPane.showMessageDialog(null, "Drop successfully.");
		}
		
	}
}
