package backend;

public class Student extends Human {

    //was initially more general, decided it made more sense to include course info here, use db to differentiate
    //students

    private double _grade;
    private String _comment;
    private boolean _withdrawn;
    private boolean _grad;
    private double _bonus;
    private double _adjustment;

    public Student(String name, int id, boolean grad) {
        super(name, id);
        _grade = 0.0;
        _comment = "";
        _withdrawn = false;
        _grad = grad;
        _bonus = 0.0;
        _adjustment = 0.0;
    }

    public double getGrade() {
        return _grade;
    }

    public void setGrade(double g) {
        _grade = g;
    }

    public String getComment() {
        return _comment;
    }

    public void setComment(String c) {
        _comment = c;
    }

    public boolean getWithdrawn() {
        return _withdrawn;
    }

    public void withdraw(){
        _withdrawn = true;
    }

    public boolean isGradStudent() {
        return _grad;
    }

    public double getBonus() {
        return _bonus;
    }

    public void setBonus(double b) {
        _bonus = b;
    }

    public double getAdjustment() {
        return _adjustment;
    }

    public void setAdjustment(double a) {
        _adjustment = a;
    }
}
