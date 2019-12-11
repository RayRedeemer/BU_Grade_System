package gui;

import backend.AcademicObject;
import backend.Course;
import share.Request;
import share.RequestHead;
import share.Response;

/*
Author: Ziqi Tan
*/
public class Dispatcher {
	
	public void dispatch(Response response) {
		if( response.getHead().equals(RequestHead.LOGIN) ) {
			MainFrame.getInstance().removeCurPanel();
			MainFrame.getInstance().setAdminPanel();		
		}
		
		if( response.getHead().equals(RequestHead.LOGOUT) ) {
			MainFrame.getInstance().removeCurPanel();
			MainFrame.getInstance().setLoginPanel();
		}
		
		if( response.getHead().equals(RequestHead.GET_COURSE_LIST) ) {
			int numOfCourses = response.getBody().size();
			String[] columnNames = 
				{ "ID", "Name", "Semester", "Average Grade" };
			String[][] data = new String[numOfCourses][4]; 
			try {				
				for( int i = 0; i < numOfCourses; i++ ) {
					data[i][0] = Integer.toString(((AcademicObject) response.getBody().get(i)).getId());
					data[i][1] = ((AcademicObject) response.getBody().get(i)).getName();
					data[i][2] = ((Course) response.getBody().get(i)).getSemester();
				}
			}
			catch( Exception error ) {
				// System.out.println(error);
				error.printStackTrace();
			}
			
			MainFrame.getInstance().getAdminPanel().updateCourseList(columnNames, data);
		}
		
		if( response.getHead().equals(RequestHead.SELECT_COURSE) ) {
			MainFrame.getInstance().removeCurPanel();
			Course course = (Course) response.getBody().get(0);
			MainFrame.getInstance().setCoursePanel(course.getId(), course.getName(), course.getSemester(), course.getDescription(), course.getCurve(), course.getComment());
		}
		
		if( response.getHead().equals(RequestHead.UPDATE_CATEGORY) ) {
			MainFrame.getInstance().removeCurPanel();
			MainFrame.getInstance().setCategoryPanel();
		}
		
	}
		
}
