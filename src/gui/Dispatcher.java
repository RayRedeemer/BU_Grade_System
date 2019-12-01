package gui;
/*
Author: Ziqi Tan
*/
public class Dispatcher {
	
	public void dispatch(Request request) {
		if( request.getHead().equals(RequestHead.LOGIN) ) {
			MainFrame.getInstance().getLoginPanel().setEnabled(false);
			MainFrame.getInstance().getLoginPanel().setVisible(false);
			MainFrame.getInstance().remove(MainFrame.getInstance().getLoginPanel());
			MainFrame.getInstance().setAdminPanel();
		}
	}
	
}
