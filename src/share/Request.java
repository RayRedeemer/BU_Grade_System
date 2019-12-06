package share;

import backend.AcademicObject;

import java.util.ArrayList;
import java.util.List;

/*
Author: Ziqi Tan
*/
public class Request {
	
	private RequestHead head;
	private List<Object> params;
	private AcademicObject currentObj;
	
	public Request(RequestHead _head) {
		this.head = _head;
		params = new ArrayList<Object>();		
	}
	
	/**
	 * getter()
	 * */
	public RequestHead getHead() {
		return head;
	}
	
	public List<Object> getParams() {
		return params;
	}

	public Object getFirstParam() {
		return params.get(0);
	}

	public AcademicObject getCurrentObj() {
		return this.currentObj;
	}
	
	/**
	 * setter()
	 * */
	public void addParams(Object object) {
		params.add(object);
	}
	
	@Override
	public String toString() {
		String str = System.getProperty("line.separator") + "head: " + head + System.getProperty("line.separator");
		for( int i = 0; i < params.size(); i++ ) {
			str += "param " + i + ": " + params.get(i) + System.getProperty("line.separator");
		}
		return str;
	}
}
