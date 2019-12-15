package backend;

import share.*;
import db.*;
import gui.MainFrame;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

/**
 * Class serves as the interface between GUI and backend. All APIs must be private.
 * It follows singleton design pattern.
 */
public class SystemPortal {

    // tracks the level of objects
    private AcademicObject _currentObj;

    // Singleton Pattern
    private static SystemPortal systemPortal = new SystemPortal();

    public static SystemPortal getInstance() {
        return systemPortal;
    }

    /**
     * Entry method for the grade system. GUI set up and other fields should be initialized here.
     */
    public void launch() {
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
                addStudent((String) params.get(0), (String) params.get(1), (String) params.get(2), (Boolean) params.get(3));
                return res;
            case DROP_STUDENT:
                dropStudent((Integer) params.get(0));
                return res;
            case WITHDRAW_STUDENT:
                withdrawStudent((Integer) params.get(0));
                return res;
            case UPDATE_STUDENT:
                updateStudent(params);
                return res;

            case SELECT_STUDENT:
                res.addBody(getStudentById((Integer) params.get(0)));
                return res;
            case SELECT_COURSE:
                res.addBody(getCourse(ids.get(0)));
                return res;
            case SELECT_CATEGORY:
                res.addBody(getCategory(ids.get(0), ids.get(1)));
                return res;
            case SELECT_ASSIGNMENT:
                res.addBody(getAssignment(ids.get(0), ids.get(1), ids.get(2)));
                return res;
            case SELECT_SUBMISSION:
                res.addBody(getSubmission(ids.get(0), ids.get(1), ids.get(2), ids.get(3)));
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
                addSubmission((Integer) params.get(0), (Double) params.get(1), (Double) params.get(2), (LocalDateTime) params.get(3), (Boolean) params.get(4));
                return res;

            case DELETE_COURSE:
                deleteCourse(ids.get(0));
                return res;
            case DELETE_CATEGORY:
                deleteCategory(ids.get(0), ids.get(1));
                return res;
            case DELETE_ASSIGNMENT:
                deleteAssignment(ids.get(0), ids.get(1), ids.get(2));
                return res;
            case DELETE_SUBMISSION:
                deleteSubmission(ids.get(0), ids.get(1), ids.get(2), ids.get(3));
                return res;

            case UPDATE_COURSE:
                updateCourse(ids.get(0), params);
                return res;
            case UPDATE_CATEGORY:
                updateCategory(ids.get(0), ids.get(1), params);
                return res;
            case UPDATE_ASSIGNMENT:
                updateAssignment(ids.get(0), ids.get(1), ids.get(2), params);
                return res;
            case UPDATE_SUBMISSION:
                updateSubmission(ids.get(0), ids.get(1), ids.get(2), ids.get(3), params);
                return res;

            case COPY_COURSE:
                copyCourse(ids.get(0));
                return res;
            case COPY_CATEGORY:
                copyCategory(ids.get(0), ids.get(1));
                return res;
            case COPY_ASSIGNMENT:
                copyAssignment(ids.get(0), ids.get(1), ids.get(2));

            case GET_COURSE_STATISTICS:
                AcademicStatistics courseStatistics = getCourseStatistics((Integer) params.get(0), (Boolean) params.get(1));
                res.addBody(courseStatistics);
                return res;
            case GET_CATEGORY_STATISTICS:
                AcademicStatistics categoryStatistics = getCategoryStatistics((Integer) params.get(0), (Integer) params.get(1), (Boolean) params.get(2));
                res.addBody(categoryStatistics);
                return res;
            case GET_ASSIGNMENT_STATISTICS:
                AcademicStatistics assignmentStatistics = getAssignmentStatistics((Integer) params.get(0), (Integer) params.get(1), (Integer) params.get(2), (Boolean) params.get(3));
                res.addBody(assignmentStatistics);
                return res;
        }
        return null;
    }

    /**
     * Track the current level of AcademicObject. If user is checking on a Category, then _currentObj will store
     * that Category object.
     * Order of ids: CourseId, CategoryId, AssignmentId, SubmissionId
     *
     * @param ids
     */
    private void setCurrentObj(List<Integer> ids) {
        // Find the current level
        Course course = null;
        Category category = null;
        Assignment assignment = null;

        if (ids.get(0) != null) {
            course = DatabasePortal.getInstance().getCourseById(ids.get(0));
        } else {
            return;
        }

        if (ids.get(1) != null) {
            category = DatabasePortal.getInstance().getCategoryById(course, ids.get(1));
        } else {
            _currentObj = course;
            return;
        }

        if (ids.get(2) != null) {
            assignment = DatabasePortal.getInstance().getAssignmentById(category, ids.get(2));
        } else {
            _currentObj = category;
            return;
        }

        if (ids.get(3) != null) {
            _currentObj = DatabasePortal.getInstance().getSubmissionById(assignment, ids.get(3));
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
        if (instructor == null) {
            return false;
        }
        return true;
    }

    /**
     * add a new student
     */
    private Student addStudent(String name, String email, String buId, Boolean isGrad) {
        return DatabasePortal.getInstance().addStudent((Course) _currentObj, name, email, buId, isGrad);
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
     *
     * @param params
     * @return
     */
    private Boolean updateStudent(List<Object> params) {
        Integer studentId = (Integer) params.get(0);
        Student student = DatabasePortal.getInstance().getStudentById(studentId);

        student.setName((String) params.get(1)); // name
        student.setEmail((String) params.get(2)); // email
        student.setBuId((String) params.get(3)); // buId
        student.setGrade((Double) params.get(4)); // grade
        student.setAdjustment((Double) params.get(5)); // adjustment
        student.setAdjustment((Double) params.get(6)); // bonus
        student.setGrad((Boolean) params.get(7)); // isGrad
        student.setGrad((Boolean) params.get(8)); // withdrawn
        student.setComment((String) params.get(9)); // comment
        return DatabasePortal.getInstance().updateStudent(student);
    }

    /**
     * add a new instructor
     */
    private Instructor addInstructor(String name, String password) {
        return DatabasePortal.getInstance().addInstructor(name, password);
    }

    /**
     * return a course given its id
     *
     * @param courseId
     * @return
     */
    private Course getCourse(int courseId) {
        return DatabasePortal.getInstance().getCourseById(courseId);
    }

    /**
     * add a new Course obj
     *
     * @param instructorId for future use, we may add instructor fields.
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
    private Boolean updateCourse(int courseId, List<Object> params) {
        Course course = DatabasePortal.getInstance().getCourseById(courseId);
        course.setName((String) params.get(0)); // name
        course.setDescription((String) params.get(1)); // desc
        course.setSemester((String) params.get(2)); // semester
        course.setCurve((Double) params.get(3)); // curve
        course.setComment((String) params.get(4)); // comment
        return DatabasePortal.getInstance().updateCourse(course);
    }

    /**
     * copy a course
     *
     * @param courseId
     * @return new course obj with the same fields but different courseId
     */
    private Course copyCourse(int courseId) {
        Course course = DatabasePortal.getInstance().getCourseById(courseId);
        return DatabasePortal.getInstance().copyCourse(course);
    }

    /**
     * get course statistics
     *
     * @param courseId
     * @return
     */
    private AcademicStatistics getCourseStatistics(int courseId, Boolean isGrad) {
        Course course = DatabasePortal.getInstance().getCourseById(courseId);
        return AcademicStatistics.of(course, isGrad);
    }

    /**
     * return a category given courseId and categoryId
     *
     * @param courseId
     * @param categoryId
     * @return
     */
    private Category getCategory(int courseId, int categoryId) {
        Course course = getCourse(courseId);
        return DatabasePortal.getInstance().getCategoryById(course, categoryId);
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
    private Boolean updateCategory(int courseId, int categoryId, List<Object> params) {
        Course course = DatabasePortal.getInstance().getCourseById(courseId);
        Category category = DatabasePortal.getInstance().getCategoryById(course, categoryId);
        category.setName((String) params.get(0)); //name
        category.setDescription((String) params.get(1)); // desc
        category.setWeight((Double) params.get(2)); // weight
        category.setComment((String) params.get(3)); // comment

        return DatabasePortal.getInstance().updateCategory(category);
    }

    /**
     * copy a category
     *
     * @param courseId
     * @param categoryId
     * @return category obj with the same fields as input but different categoryId
     */
    private Category copyCategory(int courseId, int categoryId) {
        Course course = DatabasePortal.getInstance().getCourseById(courseId);
        Category category = DatabasePortal.getInstance().getCategoryById(course, categoryId);
        return DatabasePortal.getInstance().copyCategory(course, category);
    }

    /**
     * return statistics of a category
     *
     * @param courseId
     * @param categoryId
     * @return
     */
    private AcademicStatistics getCategoryStatistics(int courseId, int categoryId, Boolean isGrad) {
        Course course = DatabasePortal.getInstance().getCourseById(courseId);
        Category category = DatabasePortal.getInstance().getCategoryById(course, categoryId);
        return AcademicStatistics.of(category, isGrad);
    }

    /**
     * return an assignment given courseId, categoryId and assignmentId
     *
     * @param courseId
     * @param categoryId
     * @param assignmentId
     * @return
     */
    private Assignment getAssignment(int courseId, int categoryId, int assignmentId) {
        Category category = getCategory(courseId, categoryId);
        return DatabasePortal.getInstance().getAssignmentById(category, assignmentId);
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
    private Boolean updateAssignment(int courseId, int categoryId, int assignmentId, List<Object> params) {
        Course course = DatabasePortal.getInstance().getCourseById(courseId);
        Category category = DatabasePortal.getInstance().getCategoryById(course, categoryId);
        Assignment assignment = DatabasePortal.getInstance().getAssignmentById(category, assignmentId);
        assignment.setName((String) params.get(0)); // name
        assignment.setDescription((String) params.get(1)); // desc
        assignment.setWeight((Double) params.get(2)); // weight
        assignment.setAssignedDate((LocalDateTime) params.get(3)); // assignedDate
        assignment.setDueDate((LocalDateTime) params.get(4)); // dueDate
        assignment.setMaxScore((Double) params.get(5)); // maxScore
        assignment.setComment((String) params.get(6)); // comment
        return DatabasePortal.getInstance().updateAssignment(assignment);
    }

    /**
     * copy an assignment
     *
     * @param courseId
     * @param categoryId
     * @param assignmentId
     * @return assignment object with the same fields as the input but with a different assignmentId
     */
    private Assignment copyAssignment(int courseId, int categoryId, int assignmentId) {
        Course course = DatabasePortal.getInstance().getCourseById(courseId);
        Category category = DatabasePortal.getInstance().getCategoryById(course, categoryId);
        Assignment assignment = DatabasePortal.getInstance().getAssignmentById(category, assignmentId);
        return DatabasePortal.getInstance().copyAssignment(category, assignment);
    }

    /**
     * return statistics of a category
     *
     * @param courseId
     * @param categoryId
     * @return
     */
    private AcademicStatistics getAssignmentStatistics(int courseId, int categoryId, int assignmentId, Boolean isGrad) {
        Course course = DatabasePortal.getInstance().getCourseById(courseId);
        Category category = DatabasePortal.getInstance().getCategoryById(course, categoryId);
        Assignment assignment = DatabasePortal.getInstance().getAssignmentById(category, assignmentId);
        return AcademicStatistics.of(assignment, isGrad);
    }

    /**
     * return a submission object
     *
     * @param courseId
     * @param categoryId
     * @param assignmentId
     * @param submissionId
     * @return
     */
    private Submission getSubmission(int courseId, int categoryId, int assignmentId, int submissionId) {
        Assignment assignment = getAssignment(courseId, categoryId, assignmentId);
        return DatabasePortal.getInstance().getSubmissionById(assignment, submissionId);
    }

    /**
     * add a new Submission obj
     *
     * @param studentId
     * @param score
     * @param bonus
     * @param submitTime
     * @param style
     * @return null if fails, Assignment obj if succeeds.
     */
    private Submission addSubmission(int studentId, double score, double bonus, LocalDateTime submitTime, Boolean style) {
        Student student = DatabasePortal.getInstance().getStudentById(studentId);
        return DatabasePortal.getInstance().addSubmission((Assignment) _currentObj, student, score, bonus, submitTime, style);
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
    private Boolean updateSubmission(int courseId, int categoryId, int assignmentId, int submissionId, List<Object> params) {
        Course course = DatabasePortal.getInstance().getCourseById(courseId);
        Category category = DatabasePortal.getInstance().getCategoryById(course, categoryId);
        Assignment assignment = DatabasePortal.getInstance().getAssignmentById(category, assignmentId);
        Submission submission = DatabasePortal.getInstance().getSubmissionById(assignment, submissionId);
        submission.setScore((Double) params.get(0)); // score
        submission.setBonus((Double) params.get(1)); // bonus
        submission.setSubmittedDate((LocalDateTime) params.get(2)); // submittedDate
        submission.setStype((Boolean) params.get(3)); // style
        submission.setComment((String) params.get(4)); // comment
        return DatabasePortal.getInstance().updateSubmission(submission);
    }


    /**
     * return a student given studentId
     * @param studentId
     * @return
     */
    private Student getStudentById(int studentId) {
        return DatabasePortal.getInstance().getStudentById(studentId);
    }

    /**
     * return all courses as an ArrayList
     *
     * @return
     */
    private ArrayList<Course> getCourseList() {
        return DatabasePortal.getInstance().getAllCourses();
    }

    /**
     * get all students within a course
     *
     * @param course
     * @return
     */
    private ArrayList<Student> getStudentListByCourse(Course course) {
        return DatabasePortal.getInstance().getStudentsByCourse(course);
    }

    /**
     * get all categories in a course
     *
     * @param course
     * @return
     */
    private ArrayList<Category> getCategoryList(Course course) {
        return DatabasePortal.getInstance().getCategoriesByCourse(course);
    }

    /**
     * get all assignments in a category
     *
     * @param category
     * @return
     */
    private ArrayList<Assignment> getAssignmentList(Category category) {
        return DatabasePortal.getInstance().getAssignmentsByCategory(category);
    }

    /**
     * get all submissions in an assignment
     *
     * @param assignment
     * @return
     */
    private ArrayList<Submission> getSubmissionList(Assignment assignment) {
        return DatabasePortal.getInstance().getSubmissionsByAssignment(assignment);
    }

}
