package gui;
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
	}
	
	
}
