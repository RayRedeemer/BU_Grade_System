package gui;

import javax.swing.JPanel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTable;

import share.Request;
import share.RequestHead;

import javax.swing.JButton;
import javax.swing.JEditorPane;

public class OverviewPanel extends JPanel implements ActionListener {
	private JTable table;
	private JLabel lblTitle, lblComment;
	private JButton btnLogout, btnCurve, btnSave, btnDelete, btnReturn;
	private JEditorPane epComment;

	/**
	 * Create the panel.
	 */
	public OverviewPanel() {
		setLayout(null);
		
		Font titleFont = new Font("Times New Roman", Font.BOLD, 25);
		Font labelFont = new Font("Times New Roman", Font.PLAIN, 20);
		Font btnFont = new Font("Microsoft YaHei UI", Font.PLAIN, 20);
		
		lblTitle = new JLabel("Student Overview");
		lblTitle.setFont(titleFont);
		lblTitle.setBounds(58, 38, 219, 57);
		add(lblTitle);
		
		table = new JTable();
		table.setBounds(58, 108, 1097, 405);
		add(table);
		
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

	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}

}
