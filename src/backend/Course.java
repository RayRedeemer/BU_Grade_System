package backend;

import java.util.ArrayList;

public class Course extends AcademicObject {

    private String _semester; //form: (F|S)##
    private double _curve;
    private ArrayList<Student> _roster;

    //course is top level, so null parent
    public Course(int id, String name, String _description) {
        super(id, name, _description, null);
        _roster = new ArrayList<Student>();
    }

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
        _roster.get(index).withdraw();
    }

}
