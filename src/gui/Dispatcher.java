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
			MainFrame.getInstance().setHeadLine();
			MainFrame.getInstance().setAdminPanel();			
		}
		if( request.getHead().equals(RequestHead.LOGOUT) ) {
			MainFrame.getInstance().removeCurPanel();
			MainFrame.getInstance().setLoginPanel();
			MainFrame.getInstance().removeHeadLine();
		}
	}
		
}
