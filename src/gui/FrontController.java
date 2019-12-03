package gui;

import java.util.List;

import share.Request;

/*
Author: Ziqi Tan
*/
public class FrontController {
	
	private static FrontController frontController = new FrontController();
	
	public static FrontController getInstance() {
		return frontController;
	}
	
	private Dispatcher dispatcher;
	
	private FrontController() {
		dispatcher = new Dispatcher();
	}
	
	/**
	 * Method: isAthenticUser
	 * Return Response
	 * */
	private boolean isAuthenticUser(Request request) {
		// Send request to backend
		final String userName = "cpk";
		final String password = "123";
		
		List<Object> params = request.getParams();
		String param1 = (String) params.get(0);
		String param2 = (String) params.get(1);
		if( param1.contentEquals(userName) && param2.contentEquals(password) ) {
			System.out.println("User is authenticated successfully.");
			return true;
		}
		
		System.out.println("Username and password did not match.");
		
		
		return false;
	}
	
	/**
	 * Method: logout
	 * */
	private boolean logout(Request request) {
		// Send request to backend
		
		return true;
	}
	
	/**
	 * Method: getCourseList
	 * */
	private boolean getCourseList() {
		
		return true;
	}
	
	/**
	 * Method: trackRequest
	 * @author Ziqi Tan
	 * @param Request
	 * Function:
	 * 		Print request log.
	 * */
	private void trackRequest(Request request) {
		System.out.println("Request log: " + request); 
	}
	
	public void dispatchRequest(Request request) {
		trackRequest(request);
		
		switch(request.getHead()) {
			case LOGIN:
				if( isAuthenticUser(request) ) {
					dispatcher.dispatch(request);
				}
				break;
			case LOGOUT:
				if( logout(request) ) {
					dispatcher.dispatch(request);
				}
				break;
			case GET_COURSE_LIST:
				break;
			default:
				System.out.println("Error.");
		}
		
	}
	
}
