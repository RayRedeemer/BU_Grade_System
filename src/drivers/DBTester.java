package drivers;

import db.DatabasePortal;
import backend.*;

import java.util.ArrayList;

public class DBTester {

    public static void main(String[] args) {
        DatabasePortal db = DatabasePortal.getInstance();

        Instructor i = db.getInstructor("test","password");
        Course c1 = db.getCourseById(1);
        Course c2 = db.getCourseById(3);

        Category cat1 = db.getCategoryById(c1, 1);
        Category cat2 = db.getCategoryById(c2, 3);

        Assignment a1 = db.getAssignmentById(cat1, 1);
        Assignment a2 = db.getAssignmentById(cat2, 3);
        System.out.println(a1.getName() + ":" + a2.getName());

        Student s1 = db.getStudentById(2);
        Student s2 = db.getStudentById(3);
        System.out.println(s1.getName() + ":" + s2.getName());

        Submission sub1 = db.addSubmission(a1, s1, 2.0, 2.0, true);
        System.out.println(sub1.getId());
        Submission sub2 = db.addSubmission(a2, s1 ,4.0,15.0, true);
        System.out.println(sub2.getId());
        Submission sub3 = db.addSubmission(a1, s2, -5.0, 0.0, false);
        System.out.println(sub3.getId());

        ArrayList<Submission> arr = db.getSubmissionsByAssignment(a1);
        for (Submission sub : arr) System.out.println(sub.getScore());
        Submission subid = db.getSubmissionById(a2, 2);
        System.out.println(subid.getScore());

        subid.setComment("blah");
        System.out.println(db.updateSubmission(subid));
        System.out.println(db.getSubmissionById(a2, 2).getComment());
        System.out.println(db.deleteSubmission(sub3));
        arr = db.getSubmissionsByAssignment(a1);
        for (Submission sub : arr) System.out.println(sub.getScore());
    }
}
