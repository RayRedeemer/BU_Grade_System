package gui;

import java.util.List;

import backend.Course;
import backend.SystemPortal;
import share.Request;
import share.RequestHead;
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
		System.out.println(response);	
		return response;
	}
	
	/**
	 * Method: addCourse
	 * */
	private Response addCourse(Request request) {
		Response response = systemPortal.getResponse(request);
		System.out.println(response);	
		return response;
	}
	
	/**
	 * Method: selectCourse
	 * */
	private Response selectCourse(Request request) {
		Response response = systemPortal.getResponse(request);
		System.out.println(response);	
		return response;
	}
	
	/**
	 * Method: updateCourse
	 * */
	private Response updateCourse(Request request) {
		Response response = systemPortal.getResponse(request);
		System.out.println(response);	
		return response;
	}
	
	/**
	 * Method: deleteCourse
	 * */
	private Response deleteCourse(Request request) {
		Response response = systemPortal.getResponse(request);
		System.out.println(response);		
		return response;
	}
	
	/**
	 * Method: getCateList
	 * */
	private Response getCateList(Request request) {
		Response response = systemPortal.getResponse(request);
		System.out.println(response);		
		return response;		
	}
	
	/**
	 * Method: addCate
	 * */
	private Response addCate(Request request) {
		Response response = systemPortal.getResponse(request);
		System.out.println(response);		
		return response;		
	}
	
	/**
	 * Method: selectCate
	 * */
	private Response selectCate(Request request) {
		Response response = systemPortal.getResponse(request);
		System.out.println(response);		
		return response;		
	}
	
	/**
	 * Method: updateCate
	 * */
	private Response updateCate(Request request) {
		Response response = systemPortal.getResponse(request);
		System.out.println(response);		
		return response;	
	}
	
	/**
	 * Method: deleteCate
	 * */
	private Response deleteCate(Request request) {
		Response response = systemPortal.getResponse(request);
		System.out.println(response);		
		return response;
	}
	
	/**
	 * Method: getAssignList
	 * */
	private Response getAssignList(Request request) {
		Response response = systemPortal.getResponse(request);
		System.out.println(response);		
		return response;
	}
	
	/**
	 * Method: addAssign
	 * */
	private Response addAssign(Request request) {
		Response response = systemPortal.getResponse(request);
		System.out.println(response);		
		return response;
	}
	
	/**
	 * Method: selectAssign
	 * */
	private Response selectAssign(Request request) {
		Response response = systemPortal.getResponse(request);
		System.out.println(response);		
		return response;
	}
	
	/**
	 * Method: updateAssign
	 * */
	private Response updateAssign(Request request) {
		Response response = systemPortal.getResponse(request);
		System.out.println(response);		
		return response;
	}
	
	/**
	 * Method: deleteAssign
	 * */
	private Response deleteAssign(Request request) {
		Response response = systemPortal.getResponse(request);
		System.out.println(response);		
		return response;
	}
	
	/**
	 * Method: copyCourse
	 * */
	private Response copyCourse(Request request) {
		Response response = systemPortal.getResponse(request);
		System.out.println(response);		
		return response;
	}
	
	/**
	 * Method: getStuList
	 * */
	private Response getStuList(Request request) {
		Response response = systemPortal.getResponse(request);
		System.out.println(response);		
		return response;
	}
	
	/**
	 * Method: addStudent
	 * */
	private Response addStudent(Request request) {
		Response response = systemPortal.getResponse(request);
		System.out.println(response);		
		return response;
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
			case UPDATE_COURSE:
				response = updateCourse(request);
				Request request2 = new Request(RequestHead.SELECT_COURSE);
				request2.addIds(request.getIds().get(0));
				request2.addIds(null);
				request2.addIds(null);
				request2.addIds(null);
				response = selectCourse(request2);
				if( response.getStatus() ) {
					dispatcher.dispatch(response);
				}
				break;
			case DELETE_COURSE:
				response = deleteCourse(request);
				if( response.getStatus() ) {
					dispatcher.dispatch(response);
				}
				break;
			case GET_CATEGORY_LIST:
				response = getCateList(request);
				if( response.getStatus() ) {
					dispatcher.dispatch(response);
				}
				break;
			case ADD_CATEGORY:
				response = addCate(request);
				if( response.getStatus() ) {
					dispatcher.dispatch(response);
				}
				break;
			case SELECT_CATEGORY:
				response = selectCate(request);
				if( response.getStatus() ) {
					dispatcher.dispatch(response);
				}
				break;
			case UPDATE_CATEGORY:
				response = updateCate(request);
				if( response.getStatus() ) {
					dispatcher.dispatch(response);
				}
				break;
			case DELETE_CATEGORY:
				response = deleteCate(request);
				if( response.getStatus() ) {
					dispatcher.dispatch(response);
				}
				break;
			case GET_ASSIGNMENT_LIST:
				response = getAssignList(request);
				if( response.getStatus() ) {
					dispatcher.dispatch(response);
				}
				break;
			case ADD_ASSIGNMENT:
				response = addAssign(request);
				if( response.getStatus() ) {
					dispatcher.dispatch(response);
				}
				break;
			case SELECT_ASSIGNMENT:
				response = selectAssign(request);
				if( response.getStatus() ) {
					dispatcher.dispatch(response);
				}
				break;
			case UPDATE_ASSIGNMENT:
				response = updateAssign(request);
				if( response.getStatus() ) {
					dispatcher.dispatch(response);
				}
				break;
			case DELETE_ASSIGNMENT:
				response = deleteAssign(request);
				if( response.getStatus() ) {
					dispatcher.dispatch(response);
				}
				break;
			case COPY_COURSE:
				response = copyCourse(request);
				if( response.getStatus() ) {
					dispatcher.dispatch(response);
				}
				break;
			case GET_STUDENT_LIST:
				response = getStuList(request);
				if( response.getStatus() ) {
					dispatcher.dispatch(response);
				}
				break;
			case ADD_STUDENT:
				response = addStudent(request);
				if( response.getStatus() ) {
					dispatcher.dispatch(response);
				}
				break;
			default:
				System.out.println("Error.");
		}
		
	}
	
}
