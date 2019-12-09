package gui;

import java.util.List;

import backend.SystemPortal;
import share.Request;
import share.Response;

/*
Author: Ziqi Tan
*/
public class FrontController {
	
	private static FrontController frontController = new FrontController();
	private SystemPortal systemPortal;
	
	public static FrontController getInstance() {
		return frontController;
	}
	
	private Dispatcher dispatcher;
	
	private FrontController() {
		dispatcher = new Dispatcher();
		systemPortal = SystemPortal.getInstance();
	}
	
	/**
	 * Method: isAthenticUser
	 * Return Response
	 * */
	private boolean isAuthenticUser(Request request) {
		// Send request to back end
		
		/*Response response = systemPortal.getResponse(request);
		
		if( response.getStatus() ) {
			System.out.println("User is authenticated from backend successfully.");
			return true;
		}*/
		
		// Front end test
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
	private boolean getCourseList(Request request) {
		
		return true;
	}
	
	/**
	 * Method: addCourse
	 * */
	private boolean addCourse(Request request) {
		
		
		
		return true;
	}
	
	/**
	 * Method: selectCourse
	 * */
	private boolean selectCourse(Request request) {
		System.out.println(request.getParams());

		if( request.getParams().get(0) == null ) {
			System.out.println("Please select a course.");
			
			return false;
		}

		return true;
	}
	
	/**
	 * Method: editCategory
	 * */
	private boolean editCategory(Request request) {
		System.out.println();
		if( request.getParams().get(0) == null ) {
			System.out.println("Please select a course.");
			return false;
		}
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
			case ADD_COURSE:
				if( addCourse(request) ) {
					dispatcher.dispatch(request);
				}
				break;
			case SELECT_COURSE:
				if( selectCourse(request) ) {
					dispatcher.dispatch(request);
				}
				break;
			case UPDATE_CATEGORY:
				if( editCategory(request) ) {
					dispatcher.dispatch(request);
				}
				break;
			default:
				System.out.println("Error.");
		}
		
	}
	
}
