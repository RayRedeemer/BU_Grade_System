package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
Author: Ziqi Tan
*/
public class MainFrame extends JFrame {
	
	// Singleton Pattern
	private static MainFrame mainFrame = new MainFrame();
	
	private JPanel curPanel;
	
	private LoginPanel loginPanel;		
	private AdminPanel adminPanel;
	private CoursePanel coursePanel;
	private StatPanel statPanel;
	private CategoryPanel categoryPanel;
	private OverviewPanel overviewPanel;
	private StuManagePanel stuManagePanel;

	
	private int frameWidth;
	private int frameHeight;
	
	public static MainFrame getInstance() {
		return mainFrame;
	}
	/**
	 * Constructor
	 * */
	private MainFrame() {
					
	}
	
	public void run() {
		System.out.println("A main frame is creating.");
		setTitle("Grading System");	
					
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frameWidth = (int)(screenSize.getWidth() * 0.75);
		frameHeight = (int)(screenSize.getHeight() * 0.75);
		setSize(frameWidth, frameHeight);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);         		
		setLocationRelativeTo(null);   // this will center your frame
		
		setLoginPanel();

		setVisible(true);	
	}
		
	/**
	 * getter()
	 * */
	public int getFrameWidth() {
		return frameWidth;
	}
	
	public int getFrameHeight() {
		return frameHeight;
	}
	public JPanel getCurPanel() {
		return curPanel;
	}
	public AdminPanel getAdminPanel() {
		return adminPanel;
	}
	public CoursePanel getCoursePanel() {
		return coursePanel;
	}
	
	/**
	 * setter()
	 * */
	public void setLoginPanel() {
		if( loginPanel == null ) {
			loginPanel = new LoginPanel();
		}
		loginPanel.setEnabled(true);
		loginPanel.setVisible(true);
		add(loginPanel);		
		curPanel = loginPanel;	
		System.out.println("setLoginPanel");
	}
	
	public void setAdminPanel() {

		if( adminPanel == null ) {
			adminPanel = new AdminPanel();
		}
		adminPanel.getCourseList();
		add(adminPanel);
		curPanel = adminPanel;
		adminPanel.setEnabled(true);
		adminPanel.setVisible(true);
		System.out.println("setAdminPanel");		
	}
	
	// Overload
	public void setCoursePanel() {
		add(coursePanel);
		curPanel = coursePanel;
		coursePanel.setEnabled(true);
		coursePanel.setVisible(true);
		System.out.println("setCoursePanel");
	}
	
	// Overload
	public void setCoursePanel(int courseID, String courseName, String semester, String description, double curve, String comment) {
		if( coursePanel == null ) {
			coursePanel = new CoursePanel(courseID, courseName, semester, description, curve, comment);
		}
		coursePanel.setCourse(courseID, courseName, semester, description, curve, comment);
		coursePanel.getCateList();
		add(coursePanel);
		curPanel = coursePanel;
		coursePanel.setEnabled(true);
		coursePanel.setVisible(true);
		System.out.println("setCoursePanel");
	}
	
	public void setStatPanel() {
		if( statPanel == null ) {
			statPanel = new StatPanel();
		}
		add(statPanel);
		curPanel = statPanel;
		statPanel.setEnabled(true);
		statPanel.setVisible(true);
		System.out.println("setStatPanel");		
	}
	
	public void setCategoryPanel(int courseID, int cateID, String cateName, String desp, double weight, String comment) {
		if( categoryPanel == null ) {
			categoryPanel = new CategoryPanel(courseID, cateID, cateName, desp, weight, comment);
		}
		add(categoryPanel);
		curPanel = categoryPanel;
		categoryPanel.setEnabled(true);
		categoryPanel.setVisible(true);
		System.out.println("setCategoryPanel");		
	}
	
	public void setOverviewPanel() {
		if( overviewPanel == null ) {
			overviewPanel = new OverviewPanel();
		}
		add(overviewPanel);
		curPanel = overviewPanel;
		overviewPanel.setEnabled(true);
		overviewPanel.setVisible(true);
		System.out.println("setOverviewPanel");		
	}
	
	public void setStuManagePanel() {
		if( stuManagePanel == null ) {
			stuManagePanel = new StuManagePanel();
		}
		add(stuManagePanel);
		curPanel = stuManagePanel;
		stuManagePanel.setEnabled(true);
		stuManagePanel.setVisible(true);
		System.out.println("setStuManagePanel");
	}
	
	/**
	 * Method: removeCurPanel
	 * Function:
	 * 		Remove current Panel.
	 * */
	public void removeCurPanel() {
		curPanel.setEnabled(false);
		curPanel.setVisible(false);
		remove(curPanel);
	}
		
	/**
	 * Main
	 * */
	public static void main(String[] args) {
		MainFrame.getInstance().run();
	}

}
