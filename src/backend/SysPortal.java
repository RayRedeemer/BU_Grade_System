package backend;

import share.*;
import db.*;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

/**
 * Class serves as the interface between GUI and backend. All APIs must be private
 */
public class SysPortal implements Statisticsable {
    private AcademicObject _currentObj;

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

    /**
     * Method that the frontend calls to retrieve responses by passing requests with corresponding RequestHead
     *
     * @param req
     * @return
     */
    public Response getResponse(Request req) {
        RequestHead head = req.getHead();
        _currentObj = req.setCurrentObj();
        Response res = new Response(head, true);
        List<Object> params = req.getParams();
        List<Integer> ids = req.getIds();

        setCurrentObj(ids);

        switch (head) {
            case LOGIN:
                boolean isValid = login((String) params.get(0), (String) params.get(1));
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
                for (Submission submission : getSubmissionList()) {
                    res.addBody(submission);
                }
                return res;
            case ADD_STUDENT:
                DatabasePortal.getInstance().addStudent((Course) _currentObj, (String) params.get(0), (Boolean) params.get(1));
                return res;
            case ADD_COURSE:
                DatabasePortal.getInstance().addStudent((Course) _currentObj, (String) params.get(0), (Boolean) params.get(1));
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

    /**
     * Track the current level of AcademicObject. If user is checking on a Category, then _currentObj will be updated
     * to that Category object.
     * @param ids
     */
    private void setCurrentObj(ArrayList<Integer> ids) {
        Course course = DatabasePortal.getInstance().getCourseById(ids.get(0));
        Category category = DatabasePortal.getInstance().getCategoryById(course, ids.get(1));
        if (category == null) {
            _currentObj = course;
        }
        Assignment assignment = DatabasePortal.getInstance().getAssignmentById(category, ids.get(2));
        if (assignment == null) {
            _currentObj = category;
        }
        Submission submission = DatabasePortal.getInstance().getSubmissionById(assignment, ids.get(3));
        if (submission == null) {
            _currentObj = submission;
        }
    }

    /**
     * instructor login auth
     *
     * @param name
     * @param password
     * @return
     */
    private boolean login(String name, String password) {
        DatabasePortal.getInstance().getInstructor(name, password);
        return true;
    }

    /**
     * add a new student
     */
    private Student addStudent(String name, boolean isGrad) {
        return DatabasePortal.getInstance().addStudent((Course) _currentObj, name, isGrad);
    }

    /**
     * add a new instructor
     */
    private void addInstructor(String name, String password) {
        Instructor newInstructor = new Instructor(name, id, password);
        // todo db add
    }

    /**
     * add a new course. null if fails, Course obj if succeeds.
     */
    private Course addCourse(int instructorId, String name, String description, String semester) {
        return DatabasePortal.getInstance().addCourse(instructorId, name, description, semester);
    }

    /**
     * add a new Category
     */
    private Category addCategory(int courseId, String name, String description) {
        Course course = DatabasePortal.getInstance().getCourseById(courseId);
        if (course == null) {
            return null;
        }
        return DatabasePortal.getInstance().addCategory(course, name, description);
    }

    /**
     * add a new Assignment
     */
    private Assignment addAssignment(int categoryID, String name, String description) {

        // todo get course from db
        // todo add category obj to db
    }

    /**
     * add a new Submission
     */
    private void addSubmission() {
        // todo db add
    }

    private ArrayList<Course> getCourseList() {
        return DatabasePortal.getInstance().getAllCourses();
    }

    private ArrayList<Student> getStudentListByCourse(Course course) {
        return DatabasePortal.getInstance().getStudentsByCourse(course);

//        return DatabasePortal.getInstance().getStudentsByCourse()
    }

    private ArrayList<Category> getCategoryList(Course course) {
//        Course course = DatabasePortal.getInstance().getCourseById(courseId);
        return DatabasePortal.getInstance().getCategoriesByCourse(course);
    }

    private ArrayList<Assignment> getAssignmentList(Category category) {
//        Course course = DatabasePortal.getInstance().getCourseById(courseId);
//        Category category = DatabasePortal.getInstance().getCategoryById(course, categoryId);
        return DatabasePortal.getInstance().getAssignmentsByCategory(category);
    }

    private ArrayList<Submission> getSubmissionList(Assignment assignment) {
        return DatabasePortal.getInstance().getSubmissionsByAssignment(assignment);
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
