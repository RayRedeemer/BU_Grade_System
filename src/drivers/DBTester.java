package drivers;

import db.DatabasePortal;
import backend.*;

import java.util.ArrayList;

public class DBTester {

    public static void main(String[] args) {
        DatabasePortal db = DatabasePortal.getInstance();

        Instructor i = db.getInstructor("test","password");
        Course c1 = db.getCourseById(i, 1);
        Course c2 = db.getCourseById(i, 3);

        Category cat1 = db.getCategoryById(c1, 1);
        Category cat2 = db.getCategoryById(c2, 3);

        Assignment a1 = db.addAssignment(cat1, "hw1","hw1 desc");
        System.out.println(a1.getId());
        Assignment a2 = db.addAssignment(cat1, "hw2","hw2 haha");
        System.out.println(a2.getId());
        Assignment a3 = db.addAssignment(cat2, "part 1","ppppppp");
        System.out.println(a3.getId());

        ArrayList<Assignment> arr = db.getAssignmentsByCategory(cat1);
        for (Assignment a : arr) System.out.println(a.getName());
        Assignment aid = db.getAssignmentById(cat2, 3);
        System.out.println(aid.getName());

        aid.setComment("blah");
        System.out.println(db.updateAssignment(aid));
        System.out.println(db.getAssignmentById(cat2, 3).getComment());
        System.out.println(db.deleteAssignment(a2));
        arr = db.getAssignmentsByCategory(cat1);
        for (Assignment a : arr) System.out.println(a.getName());
    }
}
