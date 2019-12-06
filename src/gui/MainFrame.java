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
	
	private HeadLine headLine;
	private LoginPanel loginPanel;		
	private AdminPanel adminPanel;
	private BufferedImage bgImage;
	private ImageIcon bgImageIcon;
	private StatisticsPanel statPanel;
	
	private int frameWidth;
	private int frameHeight;
	
	public static MainFrame getInstance() {
		return mainFrame;
	}
		
	private MainFrame() {
		System.out.println("A main frame is creating.");
		setTitle("Grading System");	
					
		bgImage = getBgImage("./image/bg-image.jpg");		
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int)(screenSize.getWidth() * 0.75);
		int screenHeight = (int)(screenSize.getHeight() * 0.75);
		
		bgImageIcon = new ImageIcon(bgImage);
		// If the image is too big for the screen
		bgImageIcon = resizeImageIcon(bgImageIcon, screenWidth, screenHeight);
		
		frameWidth = bgImageIcon.getIconWidth();
		frameHeight = bgImageIcon.getIconHeight();
		setSize(frameWidth, frameHeight);
		
		setContentPane(new JLabel(bgImageIcon));
		setLayout(new FlowLayout(FlowLayout.CENTER));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);         		
		setLocationRelativeTo(null);   // this will center your frame
				
		//setLoginPanel();
		setStatPanel();

		setVisible(true);				
	}
	
	/**
	 * Method: getBgImage
	 * */
	private BufferedImage getBgImage(String imagePath) {
		BufferedImage bgImage = null;
		try {
			bgImage = ImageIO.read(new File(imagePath));
		}
		catch( Exception error ) {
			error.printStackTrace();
		}
		return bgImage;
	}
	
	/**
	 * Method: resizeBgImage
	 * Function: 
	 * 		If the image is too big for the screen,
	 * 		resize it.
	 * */
	private ImageIcon resizeImageIcon(ImageIcon icon, int targetWidth, int targetHeight ) {
		
		int newWidth = icon.getIconWidth();
		int newHeight = icon.getIconHeight();
		
		// keep the width-height ratio
		if( icon.getIconWidth() > targetWidth ) {
			newWidth = targetWidth;
			newHeight = newWidth * icon.getIconHeight() / icon.getIconWidth();
		}
		if( newHeight > targetHeight ) {
			newHeight = targetHeight;
			newWidth = newHeight * icon.getIconWidth() / icon.getIconHeight();
		}
		
		return new ImageIcon(icon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT));
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
		add(loginPanel);		
		curPanel = loginPanel;
		loginPanel.setEnabled(true);
		loginPanel.setVisible(true);
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
	
	public void setStatPanel() {
		if(statPanel == null) {
			statPanel = new StatisticsPanel();
		}
		add(statPanel);
		curPanel = statPanel;
		statPanel.setEnabled(true);
		statPanel.setVisible(true);
		System.out.println("setStatPanel");
	}
	
	public void setHeadLine() {
		if( headLine == null ) {
			headLine = new HeadLine();
		}       
        add(headLine);
        headLine.setEnabled(true);
        headLine.setVisible(true);
        System.out.println("setHeadLine");
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
	
	public void removeHeadLine() {
		headLine.setEnabled(false);
		headLine.setVisible(false);
		remove(headLine);
	}
		
	/**
	 * Main
	 * */
	public static void main(String[] args) {
		MainFrame.getInstance();
	}

}
