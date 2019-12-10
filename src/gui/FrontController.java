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
	private Response isAuthenticUser(Request request) {
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
			return new Response(request.getHead(), true);
		}
		
		System.out.println("Username and password did not match.");
		
		
		return new Response(request.getHead(), false);
	}
	
	/**
	 * Method: logout
	 * */
	private Response logout(Request request) {
		// Send request to backend
		
		return new Response(request.getHead(), true);
	}
	
	/**
	 * Method: getCourseList
	 * */
	private Response getCourseList(Request request) {
		Response response = systemPortal.getResponse(request);
		
		if( response.getStatus() ) {
			System.out.println(response.getBody().toString());	
		}	
		return response;
	}
	
	/**
	 * Method: addCourse
	 * */
	private Response addCourse(Request request) {
		Response response = systemPortal.getResponse(request);
		if( response.getStatus() ) {
			System.out.println(response);		
		}
		return response;
	}
	
	/**
	 * Method: selectCourse
	 * */
	private Response selectCourse(Request request) {
		System.out.println(request.getParams());
		Response response = systemPortal.getResponse(request);
		
		return new Response(request.getHead(), true);
	}
	
	/**
	 * Method: editCategory
	 * */
	private Response editCategory(Request request) {
		System.out.println();
		if( request.getParams().get(0) == null ) {
			System.out.println("Please select a course.");
			return new Response(request.getHead(), false);
		}
		return new Response(request.getHead(), true);
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
		Response response = null;
		switch(request.getHead()) {
			case LOGIN:
				 response = isAuthenticUser(request);
				if( response.getStatus() ) {
					dispatcher.dispatch(response);
				}
				break;
			case LOGOUT:
				response = logout(request);
				if( response.getStatus() ) {
					dispatcher.dispatch(response);
				}
				break;
			case GET_COURSE_LIST:
				response = getCourseList(request);
				if( response.getStatus() ) {
					dispatcher.dispatch(response);
				}
				break;
			case ADD_COURSE:
				response = addCourse(request);
				if( response.getStatus() ) {
					dispatcher.dispatch(response);
				}
				break;
			case SELECT_COURSE:
				response = selectCourse(request);
				if( response.getStatus() ) {
					dispatcher.dispatch(response);
				}
				break;
			case UPDATE_CATEGORY:
				response = editCategory(request);
				if( response.getStatus() ) {
					dispatcher.dispatch(response);
				}
				break;
			default:
				System.out.println("Error.");
		}
		
	}
	
}
