package gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

import share.Request;
import share.RequestHead;

import javax.swing.JEditorPane;

public class CategoryPanel extends JPanel implements ActionListener {
	
	private JLabel lblTitle, lblWeight, lblDescription, lblComment, lblAssign, lblAssignDesp, lblAssignComment;
	private JButton btnLogout, btnAddAssign, btnUpdateAssign, btnDelAssign, btnReturn, btnUpdate;
	private JEditorPane cateDespArea, cateComment, assignDesp, assignComment;
	private JTable assignmentTable;
	private JTextField weightField;
	private JScrollPane scrollPane;
	
	private int courseID;
	private int cateID;
	private String cateName;
	private String desp;
	private double weight;
	private String comment;
	
	private int frameWidth;
	private int frameHeight;
	
	private int selectedRow;
	private int selectedColumn;
	private String selectedAssignment;
	
	private static final int headerHeight = 32;
	
	private String[] columnNames = { 
		"ID", "Name", "Weight", "Assigned Date", "Due Date"
	};
	private String[][] data = { 
        { "1", "TicTacToe", "5%", "LocalDateTime", "LocalDateTime"}
    }; 
	
	/**
	 * Create the panel.
	 */
	public CategoryPanel(int _courseID, int _cateID, String _cateName, String _desp, double _weight, String _comment) {
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
		
		lblWeight = new JLabel("Weight: ");
		lblWeight.setBounds(x, y + vGap, labelWidth, textHeight);
		lblWeight.setFont(labelFont);
		add(lblWeight);
		
		weightField = new JTextField();
		weightField.setText(Double.toString(weight));
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
		
		cateComment = new JEditorPane();
		cateComment.setBounds(lblComment.getX(), lblComment.getY() + lblComment.getHeight(), (int)(frameWidth*0.35), 100);
		add(cateComment);
		
		btnUpdate = new JButton("Update Category");
		btnUpdate.setBounds(x, cateDespArea.getY() + cateDespArea.getHeight() + 10, buttonWidth * 2, textHeight);
		add(btnUpdate);
				
		lblAssign = new JLabel("Assignment:");
		lblAssign.setBounds(x, btnUpdate.getY() + vGap, labelWidth, textHeight);
		lblAssign.setFont(labelFont);
		add(lblAssign);
		
		createJTable();
		setScrollPane();
		add(scrollPane);
		
		// TODO: add/update/delete assignment
		int buttonX = scrollPane.getX() + scrollPane.getWidth() + hGap;
		int buttonY = scrollPane.getY();
		btnAddAssign = new JButton("Add Assignment");
		btnAddAssign.setBounds(buttonX, buttonY, buttonWidth * 2, textHeight);
		add(btnAddAssign);
		
		btnUpdateAssign = new JButton("Update Assignment");
		btnUpdateAssign.setBounds(buttonX, buttonY + vGap, buttonWidth * 2, textHeight);
		add(btnUpdateAssign);
		
		btnDelAssign = new JButton("Delete Assignment");
		btnDelAssign.setBounds(buttonX, buttonY + vGap * 2, buttonWidth * 2, textHeight);
		add(btnDelAssign);
		
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
	
	private void createJTable() {
		
		int rowHeight = 20;
		int columnWidth = 110;
		
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
            	    System.out.println("Selected course: " + selectedAssignment);
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
		
		// TODO: set text
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub

		
	}
}
