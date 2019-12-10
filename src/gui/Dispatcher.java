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
				{ "Name", "Semester", "Enrollment Count", "Average Grade" };
			String[][] data = /*{ 
		            { "CS 591", "Fall19", "66", "--"}, 
		            { "CS 592", "Fall19", "30", "--" },
		            {"", "", "", ""}
		    }*/ new String[numOfCourses][4]; 
			try {
				
				for( int i = 0; i < numOfCourses; i++ ) {
					data[i][0] = ((AcademicObject) response.getBody().get(i)).getName();
					data[i][1] = ((Course) response.getBody().get(i)).getSemester();
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
			int courseID = 123; // it should be int
			MainFrame.getInstance().setCoursePanel(courseID);
		}
		
		if( response.getHead().equals(RequestHead.UPDATE_CATEGORY) ) {
			MainFrame.getInstance().removeCurPanel();
			MainFrame.getInstance().setCategoryPanel();
		}
		
	}
		
}
