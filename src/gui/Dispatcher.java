package gui;

import share.Request;
import share.RequestHead;

/*
Author: Ziqi Tan
*/
public class Dispatcher {
	
	public void dispatch(Request request) {
		if( request.getHead().equals(RequestHead.LOGIN) ) {
			MainFrame.getInstance().removeCurPanel();
			MainFrame.getInstance().setAdminPanel();			
		}
		
		if( request.getHead().equals(RequestHead.LOGOUT) ) {
			MainFrame.getInstance().removeCurPanel();
			MainFrame.getInstance().setLoginPanel();
		}
		
		if( request.getHead().equals(RequestHead.ADD_COURSE) ) {
			System.out.println("Add course");
		}
		
		if( request.getHead().equals(RequestHead.SELECT_COURSE) ) {
			MainFrame.getInstance().removeCurPanel();
			int courseID = 123; // it should be int
			MainFrame.getInstance().setCoursePanel(courseID);
		}
		
		if( request.getHead().equals(RequestHead.UPDATE_CATEGORY) ) {
			MainFrame.getInstance().removeCurPanel();
			MainFrame.getInstance().setCategoryPanel();
		}
		
	}
		
}
