package backend;

import share.*;
import db.*;
import gui.MainFrame;

import java.util.ArrayList;
import java.util.List;

/**
 * Class serves as the interface between GUI and backend. All APIs must be private
 */
public class SystemPortal {

    // tracks the level of objects
    private AcademicObject _currentObj;
    
    // Singleton Pattern
    private static SystemPortal systemPortal = new SystemPortal();
    
    private SystemPortal() {}
    
    public static SystemPortal getInstance() {
    	return systemPortal;
    }
    
    /**
     * Entry method for the grade system. GUI set up and other fields should be initialized here.
     */
    public void launch() {
        // TODO: kick off GUI login Panel
    	MainFrame.getInstance().run();
    }

    /**
     * Method that the frontend interacts with to retrieve responses by passing requests with corresponding RequestHead.
     * Within Request object, there are multiple fields that we are interested in:
     * ids:ArrayList that stores four ids in the following order: courseId, categoryId, assignmentId and submissionId.
     *
     * @param req Request obj sent from GUI. It has fields such as enum head to specify the operation
     * @return Response obj with head and status. Status will be true if succeeds, false otherwise.
     */
    public Response getResponse(Request req) {
        RequestHead head = req.getHead();
        Response res = new Response(head, true);
        List<Object> params = req.getParams();
        List<Integer> ids = req.getIds();

        setCurrentObj(ids);

        switch (head) {
            case LOGIN:
                Boolean isValid = login((String) params.get(0), (String) params.get(1));
                return new Response(head, isValid);
            case LOGOUT:
                return res;

            case GET_STUDENT_LIST:
                for (Student student : getStudentListByCourse((Course) _currentObj)) {
                    res.addBody(student);
                }
                return res;
            case GET_COURSE_LIST:
                for (Course course : getCourseList()) {
                    res.addBody(course);
                }
                return res;
            case GET_CATEGORY_LIST:
                for (Category category : getCategoryList((Course) _currentObj)) {
                    res.addBody(category);
                }
                return res;
            case GET_ASSIGNMENT_LIST:
                for (Assignment assignment : getAssignmentList((Category) _currentObj)) {
                    res.addBody(assignment);
                }
                return res;
            case GET_SUBMISSION_LIST:
                for (Submission submission : getSubmissionList((Assignment) _currentObj)) {
                    res.addBody(submission);
                }
                return res;

            case ADD_INSTRUCTOR:
                addInstructor((String) params.get(0), (String) params.get(1));
                return res;
            case ADD_STUDENT:
                addStudent((String) params.get(0), (Boolean) params.get(1));
                return res;
            case DROP_STUDENT:
                dropStudent((Integer) params.get(0));
                return res;
            case WITHDRAW_STUDENT:
                withdrawStudent((Integer) params.get(0));
                return res;
            case UPDATE_STUDENT:
                updateStudent((Integer) params.get(0));
                return res;

            case ADD_COURSE:
                addCourse((int) params.get(0), (String) params.get(1), (String) params.get(2), (String) params.get(3));
                return res;
            case ADD_CATEGORY:
                addCategory((String) params.get(0), (String) params.get(1));
                return res;
            case ADD_ASSIGNMENT:
                addAssignment((String) params.get(0), (String) params.get(1));
                return res;
            case ADD_SUBMISSION:
                addSubmission((Integer) params.get(0), (Double) params.get(1), (Double) params.get(2), (Boolean) params.get(3));
                return res;

            case DELETE_COURSE:
                deleteCourse((Integer) params.get(0));
                return res;
            case DELETE_CATEGORY:
                deleteCategory((Integer) params.get(0), (Integer) params.get(1));
                return res;
            case DELETE_ASSIGNMENT:
                deleteAssignment((Integer) params.get(0), (Integer) params.get(1), (Integer) params.get(2));
                return res;
            case DELETE_SUBMISSION:
                deleteSubmission((Integer) params.get(0), (Integer) params.get(1), (Integer) params.get(2), (Integer) params.get(3));
                return res;

            case UPDATE_COURSE:
                updateCourse((Integer) params.get(0));
                return res;
            case UPDATE_CATEGORY:
                updateCategory((Integer) params.get(0));
                return res;
            case UPDATE_ASSIGNMENT:
                updateAssignment((Integer) params.get(0));
                return res;
            case UPDATE_SUBMISSION:
                updateSubmission((Integer) params.get(0));
                return res;

            case GET_COURSE_STATISTICS:
                // TODO
            case GET_CATEGORY_STATISTICS:
                // TODO
            case GET_ASSIGNMENT_STATISTICS:
                // TODO

            case CHECK_COURSE_VALID: // check weights of all its categories sum up to 1.0
                Boolean isCourseValid = isCourseValid((Integer) params.get(0));
                return new Response(head, isCourseValid);
            case CHECK_CATEGORY_VALID: // check weights of all its assignments sum up to 1.0
                Boolean isCategoryValid = isCategoryValid((Integer) params.get(0));
                return new Response(head, isCategoryValid);
        }
        return null;
    }

    /**
     * Track the current level of AcademicObject. If user is checking on a Category, then _currentObj will store
     * that Category object.
     * Order of ids: CourseId, CategoryId, AssignmentId, SubmissionId
     * @param ids
     */
    private void setCurrentObj(List<Integer> ids) {
    	System.out.println("ids: " + ids.toString());
    	/*
        Course course = DatabasePortal.getInstance().getCourseById(ids.get(0));
        Category category = DatabasePortal.getInstance().getCategoryById(course, ids.get(1));
        if (category == null) {
            _currentObj = course;
        }
        Assignment assignment = DatabasePortal.getInstance().getAssignmentById(category, ids.get(2));
        if (assignment == null) {
            _currentObj = category;
        }
        _currentObj = DatabasePortal.getInstance().getSubmissionById(assignment, ids.get(3));*/
    	
    	// Modified by Ziqi Tan
    	// Find the current level
    	Course course = null;
    	Category category = null;
    	Assignment assignment = null;
    	// Submission submission = null;
    	
    	if( ids.get(0) != null ) {
    		course = DatabasePortal.getInstance().getCourseById(ids.get(0));
    	}
    	else {
    		return ;
    	}
    	
    	if( ids.get(1) != null ) {
    		category = DatabasePortal.getInstance().getCategoryById(course, ids.get(1));
    	}
    	else {
    		_currentObj = course;
    		return ;
    	}
    	
    	if( ids.get(2) != null ) {
    		assignment = DatabasePortal.getInstance().getAssignmentById(category, ids.get(2));
    	}
    	else {
    		_currentObj = category;
    		return ;
    	}
    	
    	if( ids.get(3) != null ) {
    		_currentObj  = DatabasePortal.getInstance().getSubmissionById(assignment, ids.get(3));
    	}

    
    }

    /**
     * instructor login auth
     *
     * @param name
     * @param password
     * @return
     */
    private Boolean login(String name, String password) {
        Instructor instructor = DatabasePortal.getInstance().getInstructor(name, password);		
        if( instructor == null ) {
        	return false;
        }
        return true;
    }

    /**
     * add a new student
     */
    private Student addStudent(String name, Boolean isGrad) {
        return DatabasePortal.getInstance().addStudent((Course) _currentObj, name, isGrad);
    }

    /**
     * drop a student
     *
     * @param studentId
     * @return
     */
    private Boolean dropStudent(int studentId) {
        Student student = DatabasePortal.getInstance().getStudentById(studentId);
        return DatabasePortal.getInstance().dropStudent(student);
    }

    /**
     * withdraw a student
     *
     * @param studentId
     * @return
     */
    private Boolean withdrawStudent(int studentId) {
        Student student = DatabasePortal.getInstance().getStudentById(studentId);
        return DatabasePortal.getInstance().withdrawStudent(student);
    }

    /**
     * update a student with new fields
     * @param studentId
     * @return
     */
    private Boolean updateStudent(int studentId) {
        Student student = DatabasePortal.getInstance().getStudentById(studentId);
        return DatabasePortal.getInstance().updateStudent(student);
    }

    /**
     * add a new instructor
     */
    private Instructor addInstructor(String name, String password) {
        return DatabasePortal.getInstance().addInstructor(name, password);
    }

    /**
     * add a new Course obj
     * java
     *
     * @param instructorId
     * @param name
     * @param description
     * @param semester
     * @return null if fails, Course obj if succeeds.
     */
    private Course addCourse(int instructorId, String name, String description, String semester) {
        return DatabasePortal.getInstance().addCourse(instructorId, name, description, semester);
    }

    /**
     * delete a course from a bottom-to-top manner
     *
     * @param courseId
     * @return
     */
    private Boolean deleteCourse(int courseId) {
        Course course = DatabasePortal.getInstance().getCourseById(courseId);
        for (Category category : DatabasePortal.getInstance().getCategoriesByCourse(course)) {
            deleteCategory(courseId, category.getId());
        }
        return DatabasePortal.getInstance().deleteCourse(course);
    }

    /**
     * update a course
     *
     * @param courseId
     * @return
     */
    private Boolean updateCourse(int courseId) {
        Course course = DatabasePortal.getInstance().getCourseById(courseId);
        return DatabasePortal.getInstance().updateCourse(course);
    }

    /**
     * Check if the sum of weights of all categories is 1.0
     *
     * @param courseId course you want to check
     * @return
     */
    private Boolean isCourseValid(int courseId) {
        Course course = DatabasePortal.getInstance().getCourseById(courseId);
        return course.isValid();
    }

    /**
     * Check if the sum of weights of all submissions is 1.0
     *
     * @param categoryId category you want to check
     * @return
     */
    private Boolean isCategoryValid(int categoryId) {
        Category category = DatabasePortal.getInstance().getCategoryById((Course) _currentObj, categoryId);
        return category.isValid();
    }

    /**
     * add a new Category obj
     *
     * @param name
     * @param description
     * @return null if fails, Category obj if succeeds.
     */
    private Category addCategory(String name, String description) {
        return DatabasePortal.getInstance().addCategory((Course) _currentObj, name, description);
    }

    /**
     * delete a category
     *
     * @param categoryId
     * @return
     */
    private Boolean deleteCategory(int courseId, int categoryId) {
        Course course = DatabasePortal.getInstance().getCourseById(courseId);
        Category category = DatabasePortal.getInstance().getCategoryById(course, categoryId);
        for (Assignment assignment : DatabasePortal.getInstance().getAssignmentsByCategory(category)) {
            deleteAssignment(courseId, categoryId, assignment.getId());
        }
        return DatabasePortal.getInstance().deleteCategory(category);
    }

    /**
     * update a category
     *
     * @param categoryId
     * @return
     */
    private Boolean updateCategory(int categoryId) {
        Category category = DatabasePortal.getInstance().getCategoryById((Course) _currentObj, categoryId);
        return DatabasePortal.getInstance().updateCategory(category);
    }

    /**
     * add a new Assignment obj
     *
     * @param name
     * @param description
     * @return null if fails, otherwise returns the assignment obj
     */
    private Assignment addAssignment(String name, String description) {
        return DatabasePortal.getInstance().addAssignment((Category) _currentObj, name, description);
    }

    /**
     * delete an assignment
     *
     * @param assignmentId
     * @return
     */
    private Boolean deleteAssignment(int courseId, int categoryId, int assignmentId) {
        Course course = DatabasePortal.getInstance().getCourseById(courseId);
        Category category = DatabasePortal.getInstance().getCategoryById(course, categoryId);
        Assignment assignment = DatabasePortal.getInstance().getAssignmentById(category, assignmentId);
        for (Submission submission : DatabasePortal.getInstance().getSubmissionsByAssignment(assignment)) {
            deleteSubmission(courseId, categoryId, assignmentId, submission.getId());
        }
        return DatabasePortal.getInstance().deleteAssignment(assignment);
    }

    /**
     * update an assignment
     *
     * @param assignmentId
     * @return
     */
    private Boolean updateAssignment(int assignmentId) {
        Assignment assignment = DatabasePortal.getInstance().getAssignmentById((Category) _currentObj, assignmentId);
        return DatabasePortal.getInstance().updateAssignment(assignment);
    }

    /**
     * add a new Submission obj
     *
     * @param studentId
     * @param score
     * @param bonus
     * @param style
     * @return null if fails, Assignment obj if succeeds.
     */
    private Submission addSubmission(int studentId, double score, double bonus, Boolean style) {
        Student student = DatabasePortal.getInstance().getStudentById(studentId);
        return DatabasePortal.getInstance().addSubmission((Assignment) _currentObj, student, score, bonus, style);
    }

    /**
     * delete a submission
     *
     * @param submissionId
     * @return
     */
    private Boolean deleteSubmission(int courseId, int categoryId, int assignmentId, int submissionId) {
        Course course = DatabasePortal.getInstance().getCourseById(courseId);
        Category category = DatabasePortal.getInstance().getCategoryById(course, categoryId);
        Assignment assignment = DatabasePortal.getInstance().getAssignmentById(category, assignmentId);
        Submission submission = DatabasePortal.getInstance().getSubmissionById(assignment, submissionId);
        return DatabasePortal.getInstance().deleteSubmission(submission);
    }

    /**
     * update a submission
     *
     * @param submissionId
     * @return
     */
    private Boolean updateSubmission(int submissionId) {
        Submission submission = DatabasePortal.getInstance().getSubmissionById((Assignment) _currentObj, submissionId);
        return DatabasePortal.getInstance().updateSubmission(submission);
    }

    //Todo: compute statistics for a submission
//    private double getSubmissionStatistics(int submissionId) {
//        Submission submission = DatabasePortal.getInstance().getSubmissionById((Assignment) _currentObj, submissionId);
//
//    }


    private ArrayList<Course> getCourseList() {
        return DatabasePortal.getInstance().getAllCourses();
    }

    private ArrayList<Student> getStudentListByCourse(Course course) {
        return DatabasePortal.getInstance().getStudentsByCourse(course);
    }

    private ArrayList<Category> getCategoryList(Course course) {
        return DatabasePortal.getInstance().getCategoriesByCourse(course);
    }

    private ArrayList<Assignment> getAssignmentList(Category category) {
        return DatabasePortal.getInstance().getAssignmentsByCategory(category);
    }

    private ArrayList<Submission> getSubmissionList(Assignment assignment) {
        return DatabasePortal.getInstance().getSubmissionsByAssignment(assignment);
    }

}
