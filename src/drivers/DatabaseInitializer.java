package drivers;

import java.sql.*;

public class DatabaseInitializer {

    public static void main(String[] args) {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:src/db/grades.db");
        } catch (Exception e) {
            System.out.println("Exception while connecting to db:");
            System.out.println(e.getMessage());
            return;
        }

        String instructors = "CREATE TABLE instructors (\n" +
                "instructor_id INTEGER PRIMARY KEY NOT NULL,\n" +
                "name TEXT,\n" +
                "password TEXT);";

        String courses = "CREATE TABLE courses (\n" +
                "course_id INTEGER PRIMARY KEY NOT NULL,\n" +
                "instructor_id INTEGER REFERENCES instructors(instructor_id),\n" +
                "semester TEXT,\n" +
                "name TEXT,\n" +
                "description TEXT,\n" +
                "curve DECIMAL(3,2),\n" +
                "comment TEXT);";

        String students = "CREATE TABLE students (\n" +
                "student_id INTEGER PRIMARY KEY NOT NULL,\n" +
                "course_id INTEGER REFERENCES courses(course_id),\n" +
                "name TEXT,\n" +
                "score DECIMAL(3,2),\n" +
                "bonus DECIMAL(3,2),\n" +
                "adjustment DECIMAL(3,2),\n" +
                "is_grad INTEGER,\n" +
                "withdrawn INTEGER,\n" +
                "comment TEXT);";

        String categories = "CREATE TABLE categories (\n" +
                "category_id INTEGER PRIMARY KEY NOT NULL,\n" +
                "course_id INTEGER REFERENCES courses(course_id),\n" +
                "name TEXT,\n" +
                "description TEXT,\n" +
                "weight DECIMAL(3,2),\n" +
                "comment TEXT);";

        String assignments = "CREATE TABLE assignments (\n" +
                "assignment_id INTEGER PRIMARY KEY NOT NULL,\n" +
                "category_id INTEGER REFERENCES categories(category_id),\n" +
                "name TEXT,\n" +
                "description TEXT,\n" +
                "weight DECIMAL(3,2),\n" +
                "maxscore DECIMAL(3,2),\n" +
                "assigned_date TEXT,\n" +
                "due_date TEXT,\n" +
                "comment TEXT);";

        String submissions = "CREATE TABLE submissions (\n" +
                "submission_id INTEGER PRIMARY KEY NOT NULL,\n" +
                "student_id INTEGER REFERENCES students(student_id),\n" +
                "assignment_id INTEGER REFERENCES assignments(assignment_id),\n" +
                "score DECIMAL(3,2),\n" +
                "bonus DECIMAL(3,2),\n" +
                "submitted_date TEXT,\n" +
                "comment TEXT);";

        try{
            Statement stmt = conn.createStatement();
            stmt.execute(instructors);
            stmt.execute(courses);
            stmt.execute(students);
            stmt.execute(categories);
            stmt.execute(assignments);
            stmt.execute(submissions);
        } catch (Exception e) {
            System.out.println("Exception while initializing DB:");
            System.out.println(e.getMessage());
        }
    }
}
