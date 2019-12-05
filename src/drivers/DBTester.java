package drivers;

import db.DatabasePortal;

public class DBTester {

    public static void main(String[] args) {
        DatabasePortal db = DatabasePortal.getInstance();
        System.out.println(db.addInstructor("test","password"));
        System.out.println(db.addInstructor("test","password"));
        System.out.println(db.getInstructor("test","password"));
        System.out.println(db.getInstructor("test","different"));
        System.out.println(db.getInstructor("test","different"));
        System.out.println(db.getInstructorCount());
    }
}
