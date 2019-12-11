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
	
	private JTable stuViewTable;
	private JLabel lblTitle, lblComment;
	private JButton btnLogout, btnCurve, btnSave, btnDelete, btnReturn;
	private JEditorPane epComment;
	
	private List<Integer> assignmentIds;
	private List<Integer> stuIds;
	
	private String[] columnNames = {
		"Student", "A1 Weight", "A2 Weight", "A3 Weight"
	};
	
	private String[][] data = {
		{"Charles", "-10", "-5", "-15"},
		{"Ziqi", "-20", "-30", "-40"}
	};
	
	private int selectedRow;
	private int selectedColumn;
	private int selectedStuId;
	private int selectedAssignmentId;
	private JScrollPane scrollPane;

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
		
		lblTitle = new JLabel("Student Overview");
		lblTitle.setFont(titleFont);
		lblTitle.setBounds(58, 38, 219, 57);
		add(lblTitle);
		
		createStuViewTable();
		setScrollPane();
		add(scrollPane);
		
		btnLogout = new JButton("Logout");
		btnLogout.setBounds(1042, 43, 113, 50);
		btnLogout.setFont(btnFont);
		add(btnLogout);
		btnLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Request request = new Request(RequestHead.LOGOUT);
				FrontController.getInstance().dispatchRequest(request);
			}
		});
				
		lblComment = new JLabel("Comment:");
		lblComment.setBounds(58, 541, 87, 18);
		lblComment.setFont(labelFont);
		add(lblComment);
		
		epComment = new JEditorPane();
		epComment.setBounds(53, 579, 586, 123);
		add(epComment);
		
		btnCurve = new JButton("Curve");
		btnCurve.setBounds(778, 575, 100, 50);
		btnCurve.setFont(btnFont);
		add(btnCurve);
		btnCurve.addActionListener(this);
		
		btnSave = new JButton("Save");
		btnSave.setBounds(1055, 579, 100, 50);
		btnSave.setFont(btnFont);
		add(btnSave);
		btnSave.addActionListener(this);
		
		btnDelete = new JButton("Delete");
		btnDelete.setBounds(778, 651, 100, 50);
		btnDelete.setFont(btnFont);
		add(btnDelete);
		btnDelete.addActionListener(this);
		
		btnReturn = new JButton("Return");
		btnReturn.setBounds(1055, 652, 100, 50);
		btnReturn.setFont(btnFont);
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
		int columnWidth = 110;
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
        scrollPane.setBounds((int) (frameWidth * 0.25), lblTitle.getY() + lblTitle.getHeight(), 
        		tableWidth, 
        		(int)(frameHeight*0.5));
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		if( event.getActionCommand().equals("Curve") ) {
			
		}
		
		if( event.getActionCommand().equals("Save") ) {
			// TODO: save comment
		}
	}

}
