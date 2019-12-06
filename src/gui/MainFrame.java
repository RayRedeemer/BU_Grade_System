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
		add(adminPanel);
		curPanel = adminPanel;
		adminPanel.setEnabled(true);
		adminPanel.setVisible(true);
		System.out.println("setAdminPanel");		
	}
	
	public void setCoursePanel(int courseID) {
		if( coursePanel == null ) {
			coursePanel = new CoursePanel(courseID);
		}
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
	
	public void setCategoryPanel() {

		if( categoryPanel == null ) {
			categoryPanel = new CategoryPanel();
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
