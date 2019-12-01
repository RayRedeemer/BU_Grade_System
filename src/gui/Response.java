package gui;

import java.util.ArrayList;
import java.util.List;

/*
Author: Ziqi Tan
*/
public class Response {
	
	private RequestHead head;
	private boolean status;	    // True: request successfully. False: request failed. 
	private List<Object> body;
	
	public Response(RequestHead _head, boolean _status) {
		this.head = _head;
		this.status = _status;
		body = new ArrayList<Object>();
	}
	
	/**
	 * getter()
	 * */
	public boolean getStatus() {
		return status;
	}
	
	/**
	 * setter()
	 * */
	public void addBody(Object object) {
		body.add(object);
	}
	
	@Override
	public String toString() {
		String str = System.getProperty("line.separator") + "head: " + head + System.getProperty("line.separator");
		str += "status: " + status + System.getProperty("line.separator");
		for( int i = 0; i < body.size(); i++ ) {
			str += "param " + i + ": " + body.get(i) + System.getProperty("line.separator");
		}
		return str;
	}
	
}
