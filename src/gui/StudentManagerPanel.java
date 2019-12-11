package gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTable;

import java.awt.Font;

import javax.swing.JButton;

public class StudentManagerPanel extends JPanel {
	private JTable table;
	private JLabel lblTitle;
	private JButton btnLogout, btnAdd, btnDelete, btnReturn;

	/**
	 * Create the panel.
	 */
	public StudentManagerPanel() {
		setLayout(null);
		
		Font titleFont = new Font("Times New Roman", Font.BOLD, 25);
		Font labelFont = new Font("Times New Roman", Font.PLAIN, 20);
		Font btnFont = new Font("Microsoft YaHei UI", Font.PLAIN, 20);
		
		lblTitle = new JLabel("Manage Students");
		lblTitle.setBounds(56, 42, 197, 47);
		lblTitle.setFont(titleFont);
		add(lblTitle);
		
		table = new JTable();
		table.setBounds(87, 150, 749, 390);
		add(table);
		
		btnLogout = new JButton("Logout");
		btnLogout.setBounds(696, 53, 101, 47);
		btnLogout.setFont(btnFont);
		add(btnLogout);
		
		btnAdd = new JButton("Add Student");
		btnAdd.setBounds(87, 631, 180, 56);
		btnAdd.setFont(btnFont);
		add(btnAdd);
		
		btnDelete = new JButton("Delete Student");
		btnDelete.setBounds(330, 631, 180, 56);
		btnDelete.setFont(btnFont);
		add(btnDelete);
		
		btnReturn = new JButton("Return");
		btnReturn.setBounds(706, 631, 130, 56);
		btnReturn.setFont(btnFont);
		add(btnReturn);

	}
}
