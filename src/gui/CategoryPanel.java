package gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;

import share.Request;
import share.RequestHead;

import javax.swing.JEditorPane;

public class CategoryPanel extends JPanel implements ActionListener {
	private JLabel lblTitle, lblWeight, lblDescription, lblComment;
	private JButton btnLogout, btnSave, btnStatistics, btnReturn, btnAdd;
	private JEditorPane epComment;
	private JTable table;

	/**
	 * Create the panel.
	 */
	public CategoryPanel() {
		setLayout(null);
		
		Font titleFont = new Font("Times New Roman", Font.BOLD, 25);
		Font labelFont = new Font("Times New Roman", Font.PLAIN, 20);
		Font btnFont = new Font("Microsoft YaHei UI", Font.PLAIN, 18);
		
		lblTitle = new JLabel("Title");
		lblTitle.setBounds(43, 48, 64, 27);
		lblTitle.setFont(titleFont);
		add(lblTitle);
		
		lblWeight = new JLabel("Weight:");
		lblWeight.setBounds(43, 119, 71, 18);
		lblWeight.setFont(labelFont);
		add(lblWeight);
		
		lblDescription = new JLabel("Description:");
		lblDescription.setBounds(43, 165, 119, 39);
		lblDescription.setFont(labelFont);
		add(lblDescription);
		
		btnLogout = new JButton("Logout");
		btnLogout.setBounds(1048, 56, 113, 47);
		btnLogout.setFont(btnFont);
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
		
		btnSave = new JButton("Save");
		btnSave.setBounds(1030, 350, 131, 58);
		btnSave.setFont(btnFont);
		add(btnSave);
		
		btnStatistics = new JButton("Statistics");
		btnStatistics.setBounds(1030, 477, 131, 57);
		btnStatistics.setFont(btnFont);
		add(btnStatistics);
		
		btnReturn = new JButton("Return");
		btnReturn.setBounds(1048, 683, 113, 47);
		btnReturn.setFont(btnFont);
		add(btnReturn);
		btnReturn.addActionListener(this);
		
		lblComment = new JLabel("Comment:");
		lblComment.setBounds(43, 572, 117, 27);
		lblComment.setFont(labelFont);
		add(lblComment);
		
		epComment = new JEditorPane();
		epComment.setBounds(143, 572, 670, 66);
		add(epComment);
		
		btnAdd = new JButton("Add Assignment");
		btnAdd.setFont(btnFont);
		btnAdd.setBounds(43, 672, 177, 58);
		add(btnAdd);
		
		table = new JTable();
		table.setBounds(43, 239, 770, 295);
		add(table);

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		if( event.getActionCommand().equals("Return") ) {
			// Return to Admin Panel
			MainFrame.getInstance().removeCurPanel();
			MainFrame.getInstance().setCategoryPanel();
		}
		
	}
}
