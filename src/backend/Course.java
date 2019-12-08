package backend;

import javafx.application.Preloader;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class represents a course. This class is currently at the top level of all derived classes.
 * It records the _semester, _curve value and all students in _roster
 *
 * Hierarchy of our design: Course, Category, Assignment and Submission, top to bottom.
 */
public class Course extends AcademicObject implements Statisticsable {

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

    //copy constructor
    public Course(Course course) {
        super(course);
        _semester = course.getSemester();
        _curve = course.getCurve();
        _roster = new ArrayList<>();
        _roster.addAll(course.getAllStudents());
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

    /**
     * Check if the sum of weights of all categories is 100%
     * @return true if satisfies, false otherwise.
     */
    public Boolean isValid() {
        double weightSum = 0.0;
        for (AcademicObject ao : getAllDescendants()) {
            Category cat = (Category) ao;
            weightSum += cat.getWeight();
        }
        return weightSum == 1.0;
    }

    /**
     * return average
     * @return
     */
    public double getAvg() {
        double sum = 0;
        double count = 0;
        for (Student student: getAllStudents()) {
            sum += student.getGrade();
            count++;
        }
        return sum / count;
    }

    /**
     * return median
     * @return
     */
    public double getMedian() {
        ArrayList<Double> studentScoreList = new ArrayList<>();
        for (Student student: getAllStudents()) {
            studentScoreList.add(student.getGrade());
        }
        Collections.sort(studentScoreList);
        int size = studentScoreList.size();
        if (size % 2 == 0)
            return (studentScoreList.get(size/2) + studentScoreList.get(size/2 - 1)) /2;
        else
            return studentScoreList.get(size/2);
    }

    /**
     * return range lower bound
     * @return
     */
    public double getRangeLowerBound() {
        ArrayList<Double> studentScoreList = new ArrayList<>();
        for (Student student: getAllStudents()) {
            studentScoreList.add(student.getGrade());
        }
        Collections.sort(studentScoreList);
        return studentScoreList.get(0);
    }

    /**
     * return range upper bound
     * @return
     */
    public double getRangeUpperBound() {
        ArrayList<Double> studentScoreList = new ArrayList<>();
        for (Student student: getAllStudents()) {
            studentScoreList.add(student.getGrade());
        }
        Collections.sort(studentScoreList);
        return studentScoreList.get(studentScoreList.size() - 1);
    }
}
