package gui;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

import backend.AcademicObject;
import backend.Assignment;
import backend.Category;
import backend.Course;
import backend.Student;
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
			String[][] data = new String[numOfCourses][4]; 
			try {				
				for( int i = 0; i < numOfCourses; i++ ) {
					data[i][0] = Integer.toString(((AcademicObject) response.getBody().get(i)).getId());
					data[i][1] = ((AcademicObject) response.getBody().get(i)).getName();
					data[i][2] = ((Course) response.getBody().get(i)).getSemester();
				}
			}
			catch( Exception error ) {
				System.out.println(error);
			}
			
			MainFrame.getInstance().getAdminPanel().updateCourseList(data);
		}
		
		if( response.getHead().equals(RequestHead.ADD_COURSE) ) {
			MainFrame.getInstance().getAdminPanel().getCourseList();
		}
		
		if( response.getHead().equals(RequestHead.COPY_COURSE) ) {
			MainFrame.getInstance().getAdminPanel().getCourseList();
		}
		
		if( response.getHead().equals(RequestHead.SELECT_COURSE) ) {
			MainFrame.getInstance().removeCurPanel();
			Course course = (Course) response.getBody().get(0);
			MainFrame.getInstance().setCoursePanel(course.getId(), course.getName(), course.getSemester(), course.getDescription(), course.getCurve(), course.getComment());
		
		}
		
		if( response.getHead().equals(RequestHead.DELETE_COURSE) ) {
			MainFrame.getInstance().getAdminPanel().getCourseList();
		}
		
		if( response.getHead().equals(RequestHead.GET_CATEGORY_LIST) ) {
			int numOfCates =  response.getBody().size();
			// ID, Name, Weight
			String[][] data = new String[numOfCates][3]; 
			
			DecimalFormat decimalFormat = new DecimalFormat("0.00%");
			for( int i = 0; i < numOfCates; i++ ) {
				data[i][0] = Integer.toString(((AcademicObject) response.getBody().get(i)).getId());
				data[i][1] = ((AcademicObject) response.getBody().get(i)).getName();	
				data[i][2] = decimalFormat.format(((Category) response.getBody().get(i)).getWeight()).toString();
			}			
			MainFrame.getInstance().getCoursePanel().updateCateList(data);
		}
		
		if( response.getHead().equals(RequestHead.ADD_CATEGORY) ) {
			MainFrame.getInstance().getCoursePanel().getCateList();
		}
		
		if( response.getHead().equals(RequestHead.SELECT_CATEGORY) ) {
			Category cate = (Category) response.getBody().get(0);
			MainFrame.getInstance().removeCurPanel();
			MainFrame.getInstance().setCategoryPanel(cate.getParent().getId(), cate.getId(), cate.getName(), cate.getDescription(), cate.getWeight(), cate.getComment());
		}
		
		if( response.getHead().equals(RequestHead.UPDATE_CATEGORY) ) {
			MainFrame.getInstance().getCoursePanel().getCateList();
		}
		
		if( response.getHead().equals(RequestHead.DELETE_CATEGORY) ) {
			MainFrame.getInstance().getCoursePanel().getCateList();
		}
		
		if( response.getHead().equals(RequestHead.GET_ASSIGNMENT_LIST) ) {
			int numOfAssigns = response.getBody().size();
			// "ID", "Name", "Weight", "Perfect score", "Assigned Date", "Due Date"
			String[][] data = new String[numOfAssigns][6];
			
			DecimalFormat decimalFormat = new DecimalFormat("0.00%");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			for( int i = 0; i < numOfAssigns; i++ ) {
				data[i][0] = Integer.toString(((AcademicObject) response.getBody().get(i)).getId());
				data[i][1] = ((AcademicObject) response.getBody().get(i)).getName();
				data[i][2] = decimalFormat.format(((Assignment) response.getBody().get(i)).getWeight()).toString();
				data[i][3] = Double.toString(((Assignment) response.getBody().get(i)).getMaxScore());
				try {
					data[i][4] = ((Assignment) response.getBody().get(i)).getAssignedDate().format(formatter);
				}
				catch( NullPointerException error ) {
					System.out.println(error + " No assigned date.");
					data[i][4] = "yyyy-MM-dd HH:mm";
				}
				try {
					data[i][5] = ((Assignment) response.getBody().get(i)).getDueDate().format(formatter);
				}
				catch( NullPointerException error ) {
					System.out.println(error + " No due date.");
					data[i][5] = "yyyy-MM-dd HH:mm";
				}
				
			}
			MainFrame.getInstance().getCategoryPanel().updateAssignList(data);
		}
		
		if( response.getHead().equals(RequestHead.ADD_ASSIGNMENT) ) {
			MainFrame.getInstance().getCategoryPanel().getAssignList();
		}
		
		if( response.getHead().equals(RequestHead.SELECT_ASSIGNMENT) ) {
			Assignment cate = (Assignment) response.getBody().get(0);			
			MainFrame.getInstance().getCategoryPanel().updateCurAssignInfo(cate.getDescription(), cate.getComment());
		}
		
		if( response.getHead().equals(RequestHead.UPDATE_ASSIGNMENT) ) {
			MainFrame.getInstance().getCategoryPanel().getAssignList();
		}
		
		if( response.getHead().equals(RequestHead.DELETE_ASSIGNMENT) ) {
			MainFrame.getInstance().getCategoryPanel().getAssignList();
		}
		
		if( response.getHead().equals(RequestHead.GET_STUDENT_LIST) ) {
			int numOfStus = response.getBody().size();
			// "StuID", "StuName", "BU ID", "Grade", "Undergrad/Grad", "Bonus", "Adjustment", "A/W", "Email"
			String[][] data = new String[numOfStus][9];
			for( int i = 0; i < numOfStus; i++ ) {
				Student stu = (Student)response.getBody().get(i);
				data[i][0] = Integer.toString(stu.getId());
				data[i][1] = stu.getName();
				data[i][2] = stu.getBuId();
				data[i][3] = Double.toString(stu.getGrade());
				if( stu.isGradStudent() ) {
					data[i][4] = "Grad";
				}
				else {
					data[i][4] = "Undergrad";
				}
				data[i][5] = Double.toString(stu.getBonus());
				data[i][6] = Double.toString(stu.getAdjustment());
				if( stu.getWithdrawn() ) {
					data[i][7] = "W";
				}
				else {
					data[i][7] = "A";
				}
				
				data[i][8] = stu.getEmail();
				
			}
			
			MainFrame.getInstance().getStuManagePanel().updateStuList(data);
		}
		
		if( response.getHead().equals(RequestHead.ADD_STUDENT) ) {
			MainFrame.getInstance().getStuManagePanel().getStuList();
		}
	}
		
}
