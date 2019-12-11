package db;

import backend.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DatabasePortal {

    private Connection _conn;
    private static DatabasePortal _me;

    private DatabasePortal() {
        try {
            _conn = DriverManager.getConnection("jdbc:sqlite:src/db/grades.db");
        } catch (Exception e) {
            System.out.println("Error while connecting to db:");
            System.out.println(e.getMessage());
        }
    }

    public static DatabasePortal getInstance() {
        if (_me == null) {
            _me = new DatabasePortal();
        }
        return _me;
    }

    public Instructor addInstructor(String name, String password) {
        try {
            String sql = "SELECT * FROM instructors WHERE name=?;";
            PreparedStatement ps = _conn.prepareStatement(sql);
            ps.setString(1,name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                System.out.println("Instructor with name '" + name + "' already exists.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error while checking for instructor during addInstructor:");
            System.out.println(e.getMessage());
            return null;
        }
        try {
            String sql = "INSERT INTO instructors (name,password) VALUES (?,?);";
            PreparedStatement ps = _conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, password);
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return new Instructor(name, rs.getInt(1), password);
            }
        } catch (Exception e) {
            System.out.println("Error while inserting new instructor in addInstructor:");
            System.out.println(e.getMessage());
            return null;
        }
        return null;
    }

    public Instructor getInstructor(String name, String password) {
        try{
            String sql = "SELECT * FROM instructors\n" +
                    "WHERE name=?\n" +
                    "AND password=?;";
            PreparedStatement ps = _conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return new Instructor(name, rs.getInt(1), password);
            }
        } catch (Exception e) {
            System.out.println("Error during getInstructor with params: " + name + ":" + password);
            System.out.println(e.getMessage());
        }
        return null;
    }

    public int getInstructorCount() {
        try {
            String sql = "SELECT COUNT(*) from instructors;";
            Statement stmt = _conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next(); // always true, COUNT always returns at least one row
            return rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error during getInstructorCount:");
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public Course addCourse(int instructor, String name, String description, String semester) {
        try {
            String sql = "INSERT INTO courses (instructor_id, semester, name, description, curve, comment)\n" +
                    "VALUES (" + instructor + ",?,?,?,0.0,\"\");";
            PreparedStatement ps = _conn.prepareStatement(sql);
            ps.setString(1, semester);
            ps.setString(2, name);
            ps.setString(3, description);
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) return new Course(rs.getInt(1), name, description, semester);
        } catch (Exception e) {
            System.out.println("Error during addCourse with params: " + instructor + ":" + name + ":" + description + ":" + semester);
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Course> getCoursesByInstructor(Instructor instructor) {
        ArrayList<Course> ret = new ArrayList<Course>();
        try {
            String sql = "SELECT * FROM courses WHERE instructor_id=" + instructor.getId() + ";";
            Statement stmt = _conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                Course c = new Course(rs.getInt(1), rs.getString(4), rs.getString(5), rs.getString(3));
                c.setCurve(rs.getDouble(6));
                c.setComment(rs.getString(7));
                ret.add(c);
            }
        } catch (Exception e) {
            System.out.println("Error while fetching courses by instructor " + instructor.getId());
            System.out.println(e.getMessage());
        }
        return ret;
    }

    public ArrayList<Course> getAllCourses() {
        ArrayList<Course> ret = new ArrayList<Course>();
        try {
            String sql = "SELECT * FROM courses;";
            Statement stmt = _conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                Course c = new Course(rs.getInt(1), rs.getString(4), rs.getString(5), rs.getString(3));
                c.setCurve(rs.getDouble(6));
                c.setComment(rs.getString(7));
                ret.add(c);
            }
        } catch (Exception e) {
            System.out.println("Error during getAllCourses:");
            System.out.println(e.getMessage());
        }
        return ret;
    }

    public Course getCourseById(int id) {
        try {
            String sql = "SELECT * FROM courses WHERE course_id=" + id + ";";
            Statement stmt = _conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()) {
                Course c = new Course(rs.getInt(1), rs.getString(4), rs.getString(5), rs.getString(3));
                c.setCurve(rs.getDouble(6));
                c.setComment(rs.getString(7));
                return c;
            }
        } catch (Exception e) {
            System.out.println("Error during getCourseById with id: " + id);
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean updateCourse(Course c) {
        try {
            String sql = "UPDATE courses\n" +
                    "SET semester=?,name=?,description=?,curve=?,comment=?\n" +
                    "WHERE course_id=" + c.getId() + ";";
            PreparedStatement ps = _conn.prepareStatement(sql);
            ps.setString(1,c.getSemester());
            ps.setString(2, c.getName());
            ps.setString(3, c.getDescription());
            ps.setDouble(4, c.getCurve());
            ps.setString(5, c.getComment());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error while updating course: " + c);
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean deleteCourse(Course c) {
        try {
            String sql = "DELETE FROM courses\n" +
                    "WHERE course_id=" + c.getId() + ";";
            Statement stmt = _conn.createStatement();
            return stmt.executeUpdate(sql) > 0;
        } catch (Exception e) {
            System.out.println("Error while updating course: " + c);
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Course copyCourse(Course c) {
        Course cNew = addCourse(1, c.getName(), c.getDescription(), c.getSemester());
        if(cNew == null) {
            System.out.println("Error during copyCourse for course " + c.getId() + ", course not copied.");
            return null;
        }
        cNew.setCurve(c.getCurve());
        cNew.setComment(c.getComment());
        if(!updateCourse(cNew)) {
            System.out.println("Error during copyCourse, course " + cNew.getId() + " was not updated.");
            return null;
        }
        for (Category cat : getCategoriesByCourse(c)) {
            Category catNew = copyCategory(cNew, cat);
            if (catNew == null || catNew.getParent().getId() != cNew.getId()){
                System.out.println("Error during copyCourse, category " + cat.getId() + " was not copied for course " + cNew.getId());
                return null;
            }
            cNew.addDescendant(catNew);
        }
        return cNew;
    }


    public Category addCategory(Course c, String name, String description) {
        try {
            String sql = "INSERT INTO categories (course_id, name, description, weight, comment)\n" +
                    "VALUES (" + c.getId() + ",?,?,0.0,\"\");";
            PreparedStatement ps = _conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, description);
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) return new Category(rs.getInt(1), name, description, c);
        } catch (Exception e) {
            System.out.println("Error during addCategory with params: " + c.getId() + ":" + name + ":" + description);
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Category> getCategoriesByCourse(Course c) {
        ArrayList<Category> ret = new ArrayList<Category>();
        try {
            String sql = "SELECT * FROM categories WHERE course_id=" + c.getId() + ";";
            Statement stmt = _conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                Category cat = new Category(rs.getInt(1), rs.getString(3), rs.getString(4), c);
                cat.setWeight(rs.getDouble(5));
                cat.setComment(rs.getString(6));

                ret.add(cat);
            }
        } catch (Exception e) {
            System.out.println("Error while fetching courses by course " + c.getId());
            System.out.println(e.getMessage());
        }
        return ret;
    }

    public Category getCategoryById(Course c, int id) {
        if (c == null) return null;
        try {
            String sql = "SELECT * FROM categories WHERE category_id=" + id + ";";
            Statement stmt = _conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                Category cat = new Category(rs.getInt(1), rs.getString(3), rs.getString(4), c);
                cat.setWeight(rs.getDouble(5));
                cat.setComment(rs.getString(6));
                return cat;
            }
        } catch (Exception e) {
            System.out.println("Exception during getCategoryById for id: " + id);
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean updateCategory(Category cat){
        try {
            String sql = "UPDATE categories\n" +
                    "SET name=?,description=?,weight=?,comment=?\n" +
                    "WHERE category_id=" + cat.getId() + ";";
            PreparedStatement ps = _conn.prepareStatement(sql);
            ps.setString(1, cat.getName());
            ps.setString(2, cat.getDescription());
            ps.setDouble(3, cat.getWeight());
            ps.setString(4, cat.getComment());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error while updating category: " + cat.getId());
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean deleteCategory(Category cat){
        try {
            String sql = "DELETE FROM categories\n" +
                    "WHERE category_id=" + cat.getId() + ";";
            Statement stmt = _conn.createStatement();
            return stmt.executeUpdate(sql) > 0;
        } catch (Exception e) {
            System.out.println("Error while deleting course: " + cat.getId());
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Category copyCategory(Course c, Category cat) {
        Category catNew = addCategory(c, cat.getName(), cat.getDescription());
        if (catNew == null) {
            System.out.println("Error during copyCategory, no new category was created.");
            return null;
        }
        catNew.setWeight(cat.getWeight());
        catNew.setComment(cat.getComment());
        for (Assignment a: getAssignmentsByCategory(cat)) {
            Assignment aNew = copyAssignment(catNew, a);
            if (aNew == null || aNew.getParent().getId() != catNew.getId()){
                System.out.println("Error during copyCategory, assignment " + a.getId() + " was not copied for category " + catNew.getId());
                return null;
            }
            catNew.addDescendant(aNew);
        }
        if (!updateCategory(catNew)) {
            System.out.println("Error during copyCategory, category " + catNew.getId() + " was not updated.");
            return null;
        }
        return catNew;
    }

    //

    public Assignment addAssignment(Category cat, String name, String description) {
        try {
            String sql = "INSERT INTO assignments (category_id, name, description, weight, maxscore, assigned_date, due_date, comment)\n" +
                    "VALUES (" + cat.getId() + ", ?, ?, 0.0, 0.0, \"\", \"\", \"\");";
            PreparedStatement ps = _conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, description);
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) return new Assignment(rs.getInt(1), name, description, cat);
        } catch (Exception e) {
            System.out.println("Error during addAssignment with params: " + cat.getId() + ":" + name + ":" + description);
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Assignment> getAssignmentsByCategory(Category cat) {
        ArrayList<Assignment> ret = new ArrayList<Assignment>();
        try {
            String sql = "SELECT * FROM assignments WHERE category_id=" + cat.getId() + ";";
            Statement stmt = _conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                LocalDateTime ad;
                try {
                    ad = LocalDateTime.parse(rs.getString(7));
                } catch (Exception e) {
                    ad = null;
                }
                LocalDateTime dd;
                try {
                    dd = LocalDateTime.parse(rs.getString(8));
                } catch (Exception e) {
                    dd = null;
                }
                Assignment a = new Assignment(rs.getInt(1), rs.getString(3), rs.getString(4), cat);
                a.setWeight(rs.getDouble(5));
                a.setMaxScore(rs.getDouble(6));
                a.setAssignedDate(ad);
                a.setAssignedDate(dd);
                a.setComment(rs.getString(9));
                ret.add(a);
            }
        } catch (Exception e) {
            System.out.println("Error during getAssignmentsByCategory with category " + cat.getId());
            System.out.println(e.getMessage());
        }
        return ret;
    }

    public Assignment getAssignmentById(Category cat, int id) {
        if (cat == null) return null;
        try {
            String sql = "SELECT * FROM assignments WHERE assignment_id=" + id + ";";
            Statement stmt = _conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                LocalDateTime ad;
                try {
                    ad = LocalDateTime.parse(rs.getString(7));
                } catch (Exception e) {
                    ad = null;
                }
                LocalDateTime dd;
                try {
                    dd = LocalDateTime.parse(rs.getString(8));
                } catch (Exception e) {
                    dd = null;
                }
                Assignment a = new Assignment(rs.getInt(1), rs.getString(3), rs.getString(4), cat);
                a.setWeight(rs.getDouble(5));
                a.setMaxScore(rs.getDouble(6));
                a.setAssignedDate(ad);
                a.setAssignedDate(dd);
                a.setComment(rs.getString(9));
                return a;
            }
        } catch (Exception e) {
            System.out.println("Exception during getAssignmentById for id: " + id);
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean updateAssignment(Assignment a){
        try {
            String sql = "UPDATE assignments\n" +
                    "SET name=?,description=?,weight=?,maxscore=?,assigned_date=?,due_date=?,comment=?\n" +
                    "WHERE assignment_id=" + a.getId() + ";";
            PreparedStatement ps = _conn.prepareStatement(sql);
            ps.setString(1, a.getName());
            ps.setString(2, a.getDescription());
            ps.setDouble(3, a.getWeight());
            ps.setDouble(4, a.getMaxScore());
            ps.setString(5, a.getComment());
            ps.setString(6, a.getAssignedDate().toString());
            ps.setString(7, a.getDueDate().toString());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error while updating assignment: " + a.getId());
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean deleteAssignment(Assignment a){
        try {
            String sql = "DELETE FROM assignments\n" +
                    "WHERE assignment_id=" + a.getId() + ";";
            Statement stmt = _conn.createStatement();
            return stmt.executeUpdate(sql) > 0;
        } catch (Exception e) {
            System.out.println("Error while deleting assignment: " + a.getId());
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Assignment copyAssignment(Category cat, Assignment a) {
        Assignment aNew = addAssignment(cat, a.getName(), a.getDescription());
        if (aNew == null){
            System.out.println("Error in copyAssignment, no assignment was created.");
            return null;
        }
        aNew.setMaxScore(a.getMaxScore());
        aNew.setWeight(a.getWeight());
        aNew.setAssignedDate(a.getAssignedDate());
        aNew.setDueDate(a.getDueDate());
        aNew.setComment((a.getComment()));
        aNew.move(cat);
        if (updateAssignment(aNew)) {
            return aNew;
        }
        System.out.println("Error in copyAssignment, assignment " + aNew.getId() + ", clone of " + a.getId() + ", was not updated.");
        return null;
    }

    //

    public Student addStudent(Course c, String name, String email, String buid, boolean isGrad) {
        try {
            String sql = "INSERT INTO students (course_id, name, email, buid, score, bonus, adjustment, is_grad, withdrawn, comment)\n" +
                    "VALUES (" + c.getId() + ", ?, ?, ?, 0.0, 0.0, 0.0, ?, 0, \"\");";
            PreparedStatement ps = _conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, buid);
            if(isGrad) {
                ps.setInt(4, 1);
            } else {
                ps.setInt(4, 0);
            }
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return new Student(name, rs.getInt(1), email, buid, isGrad);
        } catch (Exception e) {
            System.out.println("Error while adding student '" + name + "' to course " + c.getId());
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Student> getStudentsByCourse(Course c) {
        ArrayList<Student> ret = new ArrayList<Student>();
        try {
            String sql = "SELECT * FROM students WHERE course_id=" + c.getId() + ";";
            Statement stmt = _conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                Student s = new Student(rs.getString(3), rs.getInt(1), rs.getString(4), rs.getString(5), rs.getInt(9) == 1);
                s.setGrade(rs.getDouble(6));
                s.setBonus(rs.getDouble(7));
                s.setAdjustment(rs.getDouble(8));
                s.setWithdraw(rs.getInt(10) == 1);
                s.setComment(rs.getString(11));
                ret.add(s);
            }
        } catch (Exception e) {
            System.out.println("Error during getStudentsByCourse with id " + c.getId());
            System.out.println(e.getMessage());
        }
        return ret;
    }

    public Student getStudentById(int id) {
        try {
            String sql = "SELECT * FROM students WHERE student_id=" + id + ";";
            Statement stmt = _conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                Student s = new Student(rs.getString(3), rs.getInt(1), rs.getString(4), rs.getString(5), rs.getInt(9) == 1);
                s.setGrade(rs.getDouble(6));
                s.setBonus(rs.getDouble(7));
                s.setAdjustment(rs.getDouble(8));
                s.setWithdraw(rs.getInt(10) == 1);
                s.setComment(rs.getString(11));
                return s;
            }
        } catch (Exception e) {
            System.out.println("Error during getStudentById with id " + id);
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean dropStudent(Student s) {
        try {
            String sql = "DELETE FROM students WHERE student_id=" + s.getId() + ";";
            Statement stmt = _conn.createStatement();
            return stmt.executeUpdate(sql) > 0;
        } catch (Exception e) {
            System.out.println("Error during dropStudent for student: " + s.getId());
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean withdrawStudent(Student s) {
        try {
            String sql = "UPDATE students SET withdrawn=1 WHERE student_id=" + s.getId() + ";";
            Statement stmt = _conn.createStatement();
            return stmt.executeUpdate(sql) > 0;
        } catch (Exception e) {
            System.out.println("Error while withdrawStudent with student " + s.getId());
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean updateStudent(Student s) {
        try {
            String sql = "UPDATE students SET name=?, email=?, buid=?, score=?, bonus=?, adjustment=?, is_grad=?, withdrawn=?, comment=?\n" +
                    "WHERE student_id=" + s.getId() + ";";
            PreparedStatement ps = _conn.prepareStatement(sql);
            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getBuId());
            ps.setDouble(4, s.getGrade());
            ps.setDouble(5, s.getBonus());
            if (s.isGradStudent()) {
                ps.setInt(4, 1);
            } else {
                ps.setInt(4, 0);
            }
            if (s.getWithdrawn()) {
                ps.setInt(5, 1);
            } else {
                ps.setInt(5,0);
            }
            ps.setString(6, s.getComment());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error during updateStudent for id " + s.getId());
            System.out.println(e.getMessage());
        }
        return false;
    }

    //

    public Submission addSubmission(Assignment a, Student s, double score, double bonus, LocalDateTime submitted, boolean style) {
        try {
            String sql = "INSERT INTO submissions (student_id, assignment_id, score, bonus, submitted_date, style, comment)\n" +
                    "VALUES (" + s.getId() + ", " + a.getId() + ", ?, ?, ?, ?, \"\");";
            PreparedStatement ps = _conn.prepareStatement(sql);
            ps.setDouble(1, score);
            ps.setDouble(2, bonus);
            String date;
            try {
                date = submitted.toString();
            } catch (Exception e) {
                date = "";
            }
            ps.setString(3, date);
            if (style) {
                ps.setInt(4, 1);
            } else {
                ps.setInt(4, 0);
            }
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) return new Submission(rs.getInt(1), score, bonus, submitted, s, a, style);
        } catch (Exception e) {
            System.out.println("Error during addSubmission with params: " + a.getId() + ":" + s.getId() + ":" + score + ":" + bonus + ":" + style);
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Submission> getSubmissionsByAssignment(Assignment a) {
        ArrayList<Submission> ret = new ArrayList<Submission>();
        try {
            String sql = "SELECT * FROM submissions WHERE assignment_id=" + a.getId() + ";";
            Statement stmt = _conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                LocalDateTime date;
                try {
                    date = LocalDateTime.parse(rs.getString(6));
                } catch (Exception e) {
                    date = null;
                }
                Submission sub = new Submission(rs.getInt(1), rs.getDouble(4), rs.getDouble(5), date, getStudentById(rs.getInt(2)), a, rs.getInt(7) == 1);
                sub.setComment(rs.getString(8));
                ret.add(sub);
            }
        } catch (Exception e) {
            System.out.println("Error during getSubmissionByAssignment with assignment " + a.getId());
            System.out.println(e.getMessage());
        }
        return ret;
    }

    public Submission getSubmissionById(Assignment a, int id) {
        if (a == null) return null;
        try {
            String sql = "SELECT * FROM submissions WHERE submission_id=" + id + ";";
            Statement stmt = _conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                LocalDateTime date;
                try {
                    date = LocalDateTime.parse(rs.getString(6));
                } catch (Exception e) {
                    date = null;
                }
                Submission sub = new Submission(rs.getInt(1), rs.getDouble(4), rs.getDouble(5), date, getStudentById(rs.getInt(2)), a, rs.getInt(7) == 1);
                sub.setComment(rs.getString(8));
                return sub;
            }
        } catch (Exception e) {
            System.out.println("Exception during getSubmissionById for id: " + id);
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean updateSubmission(Submission sub){
        try {
            String sql = "UPDATE submissions\n" +
                    "SET score=?, bonus=?, style=?, submitted_date=?, comment=?\n" +
                    "WHERE submission_id=" + sub.getId() + ";";
            PreparedStatement ps = _conn.prepareStatement(sql);
            ps.setDouble(1, sub.getScore());
            ps.setDouble(2, sub.getBonus());
            if (sub.getStyle()) {
                ps.setInt(3, 1);
            } else {
                ps.setInt(3,0);
            }
            String date;
            try {
                date = sub.getSubmittedDate().toString();
            } catch (Exception e) {
                date = "";
            }
            ps.setString(4, date);
            ps.setString(5, sub.getComment());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error while updating submission: " + sub.getId());
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean deleteSubmission(Submission sub){
        try {
            String sql = "DELETE FROM submissions\n" +
                    "WHERE submission_id=" + sub.getId() + ";";
            Statement stmt = _conn.createStatement();
            return stmt.executeUpdate(sql) > 0;
        } catch (Exception e) {
            System.out.println("Error while deleting submission: " + sub.getId());
            System.out.println(e.getMessage());
        }
        return false;
    }

}
