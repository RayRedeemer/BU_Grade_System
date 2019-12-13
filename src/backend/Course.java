package backend;

//import javafx.application.Preloader;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class represents a course. This class is currently at the top level of all derived classes.
 * It records the _semester, _curve value and all students in _roster
 *
 * Hierarchy of our design: Course, Category, Assignment and Submission, top to bottom.
 */
public class Course extends AcademicObject {

    private String _semester; //form: (F|S)##
    private double _curve;
    private ArrayList<Student> _roster;


    //course is top level, so null parent
    public Course(int id, String name, String _description, String semester) {
        super(id, name, _description, null);
        _semester = semester;
        _roster = new ArrayList<>();
        setCurve(0);// curve should be initialized as 0
    }

    /**
     * Compute grades for both students within this course and all Categories under this course, such as exams, hws, etc
     */
    public void calculateGrades() {
        for (Student s : _roster) {
            s.setGrade(_curve + s.getAdjustment());
        }
        for (AcademicObject ao : getAllDescendants()) {
            Category cat = (Category) ao;
            cat.calculateGrades();
        }
    }

    public String getSemester() {
        return _semester;
    }

    public void setSemester(String s) {
        _semester = s;
    }

    public double getCurve() {
        return _curve;
    }

    public void setCurve(double c) {
        _curve = c;
    }

    public ArrayList<Student> getAllStudents() {
        return _roster;
    }

    public void enrollStudent(Student s) {
        _roster.add(s);
    }

    public void importStudents(ArrayList<Student> s) {
        _roster.addAll(s);
    }

    public void dropStudent(int index) {
        _roster.remove(index);
    }

    public void withdrawStudent(int index) {
        _roster.get(index).setWithdraw(true);
    }

}
