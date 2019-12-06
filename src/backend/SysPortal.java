package backend;

import share.*;
import db.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

/**
 * Class serves as the interface between GUI and backend. All APIs must be private
 */
public class SysPortal implements Statisticsable {
    private AcademicObject curObject;

    // enum for different request types
    private RequestHead _head;

    public SysPortal(RequestHead head) {
        _head = head;
    }

    // tried to add a generic method for creation of all responses.
//    private <T> Response createResponse(Collection<T> collection, ) {
//        Response res = new Response(head, true);
//        for (Student student : getStudentList()) {
//            res.addBody(student);
//        }
//        return res;
//    }

    public Response getResponse(Request req) {

        RequestHead head = req.getHead();
        Response res = new Response(head, true);

        switch (head) {
            case LOGIN:
                boolean isValid = login(req.getParams());
                return new Response(head, isValid);
            case LOGOUT:
                return res;
            case GET_STUDENT_LIST:
                for (Student student : getStudentList()) {
                    res.addBody(student);
                }
                return res;
            case GET_COURSE_LIST:
                for (Course course : getCourseList()) {
                    res.addBody(course);
                }
                return res;
            case GET_CATEGORY_LIST:
                for (Category category : getCategoryList()) {
                    res.addBody(category);
                }
                return res;
            case GET_ASSIGNMENT_LIST:
                for (Assignment assignment : getAssignmentList()) {
                    res.addBody(assignment);
                }
                return res;
            case GET_SUBMISSION_LIST:
                for (Submission submission : getSubmissionList()) {
                    res.addBody(submission);
                }
                return res;
            case ADD_STUDENT:
                Student student = (Student) req.getFirstParam();
                // todo store to db
                return res;
            case ADD_COURSE:
                Course course = (Course) req.getFirstParam();
                // todo store to db
                return res;
            case ADD_CATEGORY:
                Category category = (Category) req.getFirstParam();
                // todo store to db
                return res;
            case ADD_ASSIGNMENT:
                Assignment assignment = (Assignment) req.getFirstParam();
                // todo store to db
                return res;
            case ADD_SUBMISSION:
                Submission submission = (Submission) req.getFirstParam();
                // todo store to db
                return res;
            case REMOVE_STUDENT:
                int studentKey = (int) req.getFirstParam();
                // todo remove from db
                return res;
            case REMOVE_COURSE:
                int courseKey = (int) req.getFirstParam();
                // todo remove from db
                return res;
            case REMOVE_CATEGORY:
                int categoryKey = (int) req.getFirstParam();
                // todo remove from db
                return res;
            case REMOVE_ASSIGNMENT:
                int assignmentKey = (int) req.getFirstParam();
                // todo remove from db
                return res;
            case REMOVE_SUBMISSION:
                int submissionKey = (int) req.getFirstParam();
                // todo remove from db
                return res;
            case UPDATE_STUDENT:
                return res;
            case UPDATE_COURSE:
                return res;
            case UPDATE_CATEGORY:
                return res;
            case UPDATE_ASSIGNMENT:
                return res;
            case UPDATE_SUBMISSION:
                return res;
        }
        return null;
    }

    private boolean login(List<Object> params) {
        //db auth params[0] = username, params[1] = password
        return true;
    }

    /**
     * add a new student
     */
    private void addStudent(String name, boolean grad) {
        int id = -1;// todo  get from db
        Student newStudent = new Student(name, id, grad);
        // todo db add
    }

    /**
     * add a new instructor
     */
    private void addInstructor(String name, String password) {
        int id = -1;// todo get from db
        Instructor newInstructor = new Instructor(name, id, password);
        // todo db add
    }

    /**
     * add a new course
     */
    private void addCourse(String name, String description, String semester) {
        int id = -1; // todo get from db
        Course newCourse = new Course(id, name, description, semester);
        // todo db add
    }

    /**
     * add a new Category
     */
    private void addCategory(int courseID, String name, String description) {
        // todo get course from db
        // todo add category obj to db
    }

    /**
     * add a new Assignment
     */
    private void addAssignment(int categoryID, ) {
        // todo get course from db
        // todo add category obj to db
    }

    /**
     * add a new Submission
     */
    private void addSubmission() {
        // todo db add
    }

    private List<Course> getCourseList() {
        // todo db
        // return db.getAllCourses();
        return null;
    }

    private List<Student> getStudentList() {
        // todo db
        // return db.getStudentsById(
        return null;
    }

    private List<Category> getCategoryList() {
        // todo db
        return null;
    }

    private List<Assignment> getAssignmentList() {
        // todo db
        return null;
    }

    private List<Submission> getSubmissionList() {
        // todo db
        return null;
    }


    /**
     * return average
     *
     * @return
     */
    public double getAvg() {
        return 0;
    }

    /**
     * return median
     *
     * @return
     */
    public double getMedian() {
        return 0;
    }

    /**
     * return range lower bound
     *
     * @return
     */
    public double getRangeLowerBound() {
        return 0;
    }

    /**
     * return range upper bound
     *
     * @return
     */
    public double getRangeUpperBound() {
        return 0;
    }
}
