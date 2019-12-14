package gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import backend.Assignment;
import backend.Category;
import backend.Course;
import db.DatabasePortal;
import share.Request;
import share.RequestHead;

import javax.swing.JEditorPane;

public class CategoryPanel extends JPanel implements ActionListener {
	
	private JLabel lblTitle, lblWeight, lblDescription, lblComment, lblAssign, lblAssignDesp, lblAssignComment;
	private JButton btnLogout, btnAddAssign, btnUpdateAssign, btnDelAssign, btnReturn, btnUpdate;
	private JEditorPane cateDespArea, cateCommentArea, assignDesp, assignComment;
	private JTable assignmentTable;
	private JTextField weightField;
	private JScrollPane scrollPane;
	
	private int courseID;
	private int cateID;
	private String cateName;
	private String desp;
	private double weight;
	private String comment;
	private double curWeightSum;
	
	private int frameWidth;
	private int frameHeight;
	
	private int selectedRow;
	private int selectedColumn;
	private String selectedAssignment;
	
	private static final int headerHeight = 32;
	
	private String[] columnNames = { 
		"ID", "Name", "Weight", "Perfect score", "Assigned Date", "Due Date"
	};
	private String[][] data = { 
        { "1", "TicTacToe", "5%", "138", "LocalDateTime", "LocalDateTime"}
    }; 
	
	private AssignmentForm assignmentForm;
	
	/**
	 * Create the panel.
	 */
	public CategoryPanel(int _courseID, int _cateID, 
			String _cateName, String _desp, 
			double _weight, String _comment) {
		setLayout(null);
		
		this.courseID = _courseID;
		this.cateID = _cateID;
		this.cateName = _cateName;
		this.desp = _desp;
		this.weight = _weight;
		this.comment = _comment;
		
		this.frameWidth = MainFrame.getInstance().getWidth();
		this.frameHeight = MainFrame.getInstance().getHeight();		
		
		Font titleFont = new Font("Times New Roman", Font.BOLD, 25);
		Font labelFont = new Font("Times New Roman", Font.PLAIN, 20);
		Font btnFont = new Font("Microsoft YaHei UI", Font.PLAIN, 18);
		
		int textHeight = 25;
		int labelWidth = 120;
		int buttonWidth = 80;
		
		int x = (int)(frameWidth * 0.1);
		int y = (int)(frameHeight * 0.1);
		
		int hGap = 50;
		int vGap = 50;
		
		lblTitle = new JLabel(cateName);
		lblTitle.setBounds(x, y, labelWidth, textHeight);
		lblTitle.setFont(titleFont);
		add(lblTitle);
		
		btnLogout = new JButton("Logout");
		btnLogout.setBounds(x + lblTitle.getWidth(), y, buttonWidth, textHeight);
		add(btnLogout);
		btnLogout.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent event) {
						Request request = new Request(RequestHead.LOGOUT);
						FrontController.getInstance().dispatchRequest(request);
					}
				}
		);
		
		lblWeight = new JLabel("Weight: %");
		lblWeight.setBounds(x, y + vGap, labelWidth, textHeight);
		lblWeight.setFont(labelFont);
		add(lblWeight);
		
		weightField = new JTextField();
		weightField.setText(Double.toString(weight*100));
		weightField.setBounds(x + lblWeight.getWidth(), lblWeight.getY(), 80, textHeight);
		add(weightField);
		
		JLabel weightInputSample = new JLabel("Input: 10 or 10.00, which means 10% of weight.");
		weightInputSample.setBounds(weightField.getX(), weightField.getY() + weightField.getHeight(), 300, textHeight);
		add(weightInputSample);		
		
		lblDescription = new JLabel("Description:");
		lblDescription.setBounds(x, y + vGap * 2, labelWidth, textHeight);
		lblDescription.setFont(labelFont);
		add(lblDescription);
		
		cateDespArea = new JEditorPane();
		cateDespArea.setText(desp);
		cateDespArea.setBounds(x, lblDescription.getY() + lblDescription.getHeight(), (int)(frameWidth*0.35), 100);
		add(cateDespArea);
				
		lblComment = new JLabel("Comment:");
		lblComment.setBounds(x + cateDespArea.getWidth() + 10, lblDescription.getY(), labelWidth, textHeight);
		lblComment.setFont(labelFont);
		add(lblComment);
		
		cateCommentArea = new JEditorPane();
		cateCommentArea.setText(comment);
		cateCommentArea.setBounds(lblComment.getX(), lblComment.getY() + lblComment.getHeight(), (int)(frameWidth*0.35), 100);
		add(cateCommentArea);
		
		btnUpdate = new JButton("Update Category");
		btnUpdate.setBounds(x, cateDespArea.getY() + cateDespArea.getHeight() + 10, buttonWidth * 2, textHeight);
		add(btnUpdate);
		btnUpdate.addActionListener(this);
				
		lblAssign = new JLabel("Assignment:");
		lblAssign.setBounds(x, btnUpdate.getY() + vGap, labelWidth, textHeight);
		lblAssign.setFont(labelFont);
		add(lblAssign);
		
		createJTable();
		setScrollPane();
		add(scrollPane);
		
		int buttonX = scrollPane.getX() + scrollPane.getWidth() + hGap;
		int buttonY = scrollPane.getY();
		btnAddAssign = new JButton("Add Assignment");
		btnAddAssign.setBounds(buttonX, buttonY, buttonWidth * 2, textHeight);
		add(btnAddAssign);
		btnAddAssign.addActionListener(this);
		
		btnUpdateAssign = new JButton("Update Assignment");
		btnUpdateAssign.setBounds(buttonX, buttonY + vGap, buttonWidth * 2, textHeight);
		add(btnUpdateAssign);
		btnUpdateAssign.addActionListener(this);
		
		btnDelAssign = new JButton("Delete Assignment");
		btnDelAssign.setBounds(buttonX, buttonY + vGap * 2, buttonWidth * 2, textHeight);
		add(btnDelAssign);
		btnDelAssign.addActionListener(this);
		
		btnReturn = new JButton("Return");
		btnReturn.setBounds(buttonX, buttonY + vGap * 3, buttonWidth * 2, textHeight);
		add(btnReturn);
		btnReturn.addActionListener(				
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent event) {
						MainFrame.getInstance().removeCurPanel();
						MainFrame.getInstance().setCoursePanel();
					}
				}
		);
		
		lblAssignDesp = new JLabel("Description:");
		lblAssignDesp.setFont(labelFont);
		lblAssignDesp.setBounds(x, scrollPane.getY() + scrollPane.getHeight() + vGap, labelWidth, textHeight);
		add(lblAssignDesp);
		
		assignDesp = new JEditorPane();
		assignDesp.setBounds(lblAssignDesp.getX(), lblAssignDesp.getY() + lblAssignDesp.getHeight(), (int)(frameWidth*0.35), 100);
		add(assignDesp);
		
		lblAssignComment = new JLabel("Comment:");
		lblAssignComment.setFont(labelFont);
		lblAssignComment.setBounds(x + assignDesp.getWidth() + 10, lblAssignDesp.getY(), labelWidth, textHeight);
		add(lblAssignComment);
		
		assignComment = new JEditorPane();
		assignComment.setBounds(lblAssignComment.getX(), lblAssignComment.getY() + lblAssignComment.getHeight(), (int)(frameWidth*0.35), 100);
		add(assignComment);
			
	}
	
	/**
	 * Method: createJTable
	 * Function: create an assignment table.
	 * */
	private void createJTable() {
		
		int rowHeight = 20;
		int columnWidth = 140;
		
        // Initializing the JTable
        assignmentTable = new JTable(data, columnNames);
        
        // row height
        assignmentTable.setRowHeight(rowHeight);
        
        // center alignment
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        centerRenderer.setVerticalAlignment( JLabel.CENTER );
        
        for( int columnIndex = 0; columnIndex < assignmentTable.getModel().getColumnCount(); columnIndex++ ) {
        	assignmentTable.getColumnModel().getColumn(columnIndex).setCellRenderer(centerRenderer); 
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
        assignmentTable.addMouseListener(new MouseAdapter() {       	
        	@Override
        	 public void mouseClicked(MouseEvent event) {
        		try {
        			selectedRow = assignmentTable.rowAtPoint(event.getPoint());
            	    selectedColumn = assignmentTable.columnAtPoint(event.getPoint());
            	    System.out.println("Click: " + "Row: " + selectedRow + " Column: " + selectedColumn);
            	    selectedAssignment = data[selectedRow][0];
            	    System.out.println("Selected assignment: " + selectedAssignment);
            	    getAssign();
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
		int tableWidth = (int) assignmentTable.getPreferredSize().getWidth();
	    scrollPane = new JScrollPane(assignmentTable); 
	    scrollPane.setBounds(lblAssign.getX(), lblAssign.getY() +lblAssign.getHeight(), 
	    		tableWidth, 
	    		150);
	}
	
	/**
	 * Method: setCate
	 * */
	public void setCate(int _courseID, int _cateID, String _cateName, String _desp, double _weight, String _comment) {
		this.courseID = _courseID;
		this.cateID = _cateID;
		this.cateName = _cateName;
		this.desp = _desp;
		this.weight = _weight;
		this.comment = _comment;
		
		lblTitle.setText(_cateName);
		cateDespArea.setText(desp);
		weightField.setText(Double.toString(weight*100));
		cateCommentArea.setText(comment);
	}
	
	/**
	 * Method: getAssignList
	 * */
	public void getAssignList() {
		Request request = new Request(RequestHead.GET_ASSIGNMENT_LIST);
		request.addIds(courseID);
		request.addIds(cateID);
		request.addIds(null);
		request.addIds(null);
		FrontController.getInstance().dispatchRequest(request);
	}
	
	/**
	 * Method: updateAssignList
	 * */
	public void updateAssignList(String[][] _data) {
		this.data = _data;		
		remove(scrollPane);
		createJTable();
		setScrollPane();
		add(scrollPane);
	}
	
	/**
	 * Method: getAssign
	 * */
	private void getAssign() {
		Request request = new Request(RequestHead.SELECT_ASSIGNMENT);
		request.addIds(courseID);
		request.addIds(cateID);
		request.addIds(Integer.parseInt(selectedAssignment));
		request.addIds(null);
		FrontController.getInstance().dispatchRequest(request);
	}
	
	/**
	 * Method: clearEditorPane
	 * */
	public void clearEditorPane() {
		assignDesp.setText("");
		assignComment.setText("");
	}
	
	/**
	 * Method: updateCurAssignInfo
	 * */
	// TODO
	public void updateCurAssignInfo(String _desp, String _comment) {
		assignDesp.setText(_desp);
		assignComment.setText(_comment);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {

		if( event.getActionCommand().equals("Update Category") ) {
			String cateDesp = cateDespArea.getText();
			String cateComment = cateCommentArea.getText();
								
			// check the input format
			double newWeight = 0.0;
			try {
				newWeight = Double.parseDouble(weightField.getText()) / 100;
			}
			catch(Exception error) {
				System.out.println(error);
				JOptionPane.showMessageDialog(null, "Please input a number!");
				return ;
			}
			
			// check input range: bigger or equal to 0 and less or equal to 100
			if( newWeight < 0 || newWeight > 100 ) {
				JOptionPane.showMessageDialog(null, "Please input a positive number less than 100!");
				return ;
			}
			
			// TODO: check the sum of the weight
			// get category list
			/*if( weight + curWeight > 1.0 ) {
				JOptionPane.showMessageDialog(null, "Total weight exceeds 100%.");
				return ;
			}*/
			
			// send request			
			Request request = new Request(RequestHead.UPDATE_CATEGORY);
			request.addIds(courseID);
			request.addIds(cateID);
			request.addIds(null);
			request.addIds(null);
			
			request.addParams(cateName);
			request.addParams(cateDesp);
			request.addParams(newWeight);
			request.addParams(cateComment);
			
			FrontController.getInstance().dispatchRequest(request);	
			JOptionPane.showMessageDialog(null, "Update successfully!");
		}
		
		if( event.getActionCommand().equals("Add Assignment") ) {
			// CourseID, cateID
			// "Name", "Weight", "Perfect score", "Assigned Date", "Due Date"
			// Description
			if( assignmentForm != null ) {
				assignmentForm.dispose();
			}
			assignmentForm = new AssignmentForm(courseID, cateID);
		}
		
		if( event.getActionCommand().equals("Update Assignment") ) {
			// Use current panel to update
			// courseID, cateID, assignmentID
			// name, description, weight, assignDate, dueDate, maxScore, comment
			
			if( selectedAssignment == null ) {
				JOptionPane.showMessageDialog(null, "Please select an assignment!");
				return ;
			}
			
			try {
				String aName = data[selectedRow][1];
				String aDesp = assignDesp.getText();
				String aWeight = data[selectedRow][2];
				String aMaxScore = data[selectedRow][3];
				String aDate = data[selectedRow][4];
				String dDate = data[selectedRow][5];
				String aComment = assignComment.getText();
				
				if( aName.length() == 0 || 
						aWeight.length() == 0 || aMaxScore.length() == 0 || 
						aDate.length() == 0 || dDate.length() == 0 ) {
					JOptionPane.showMessageDialog(null, "Please fill enough information!");
					return ;
				}
				
				// input format
				// Cancel the % symbol
				if( aWeight.charAt(aWeight.length() - 1) == '%' ) {
					aWeight = aWeight.substring(0, aWeight.length()-1);
				}
				double newWeight = Double.parseDouble(aWeight) / 100;
				// TODO check sum of weight
				double maxScore = Double.parseDouble(aMaxScore);
				
				// LocalDateTime input format
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
				
				LocalDateTime assignDate = LocalDateTime.parse(aDate, formatter);
				LocalDateTime dueDate = LocalDateTime.parse(dDate, formatter);
				
				Request request = new Request(RequestHead.UPDATE_ASSIGNMENT);
				request.addIds(courseID);
				request.addIds(cateID);
				request.addIds(Integer.parseInt(selectedAssignment));
				request.addIds(null);
				
				request.addParams(aName);
				request.addParams(aDesp);
				request.addParams(newWeight);				
				request.addParams(assignDate);
				request.addParams(dueDate);  // TODO
				request.addParams(maxScore);
				request.addParams(aComment);
				
				FrontController.getInstance().dispatchRequest(request);
				
			}
			catch( NullPointerException error ) {
				System.out.println(error);
			}
			catch(NumberFormatException error) {
				System.out.println(error);
				JOptionPane.showMessageDialog(null, "Input: 10 or 10.00, which means 10% of weight.");
			}
			catch(DateTimeParseException error) {
				System.out.println(error);
				JOptionPane.showMessageDialog(null, "Input: yyyy-MM-dd HH:mm LocalDateTime format.");
			}
			catch(Exception error) {
				System.out.println(error);
				error.printStackTrace();
			}			
		}
		
		if( event.getActionCommand().equals("Delete Assignment") ) {
			Request request = new Request(RequestHead.DELETE_ASSIGNMENT);
			request.addIds(courseID);
			request.addIds(cateID);
			request.addIds(Integer.parseInt(selectedAssignment));
			request.addIds(null);
			
			FrontController.getInstance().dispatchRequest(request);
		}		
		
	}
}
