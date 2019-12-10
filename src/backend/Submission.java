package backend;

import java.time.LocalDateTime;

/**
 * Class represents a submission. It is at the lowest level so far in our system hierarchy.
 */
public class Submission extends AcademicObject{

    private double _score;
    private double _bonus;
    private LocalDateTime _submittedDate; //no setter, changing date should create new submission
    private Student _student; //who submitted it; no setter
    private boolean _earnOrLose; //whether grade is points earned (positive/true), or points missed (negative/false).
    //needed a flag for _earnOrLose to differentiate 0 being a 0 or a 100 depending on style.

    //TODO add date if time
    public Submission(int id, double score, double bonus, LocalDateTime submitted, Student s, Assignment a, boolean style) {
        super(id, s.getName() + "_" + a.getName(), "", a);
        _score = score;
        _bonus = bonus;
        _submittedDate = submitted;
        _student = s;
        _earnOrLose = style;
    }

    //copy constructor (shouldn't be needed, no reason to copy submissions)
    //TODO add date if time
//    public Submission(Submission submission){
//        super(submission);
//        _score = submission.getScore();
//        _bonus = submission.getBonus();
//        _student = submission.getStudent();
//        _earnOrLose = submission.getStyle();
//    }

    public void calculateGrade(double catweight, double assignweight, double maxscore) {
        double rawAssignment;
        if (_earnOrLose) {
            rawAssignment = (_score + _bonus) / maxscore;
        } else {
            rawAssignment = (maxscore + _score + _bonus) / maxscore;
        }
        double weightedAssignment = rawAssignment * assignweight;
        double globalScore = weightedAssignment * catweight;
        _student.setGrade(_student.getGrade() + (globalScore * 100));
    }

    public double getScore() {
        return _score;
    }

    public void setScore(double s) {
        _score = s;
    }

    public double getBonus() {
        return _bonus;
    }

    public void setBonus(double b) {
        _bonus = b;
    }

    public Student getStudent() {
        return _student;
    }
    public LocalDateTime getSubmittedDate() {
        return _submittedDate;
    }

    public boolean getStyle() {
        return _earnOrLose;
    }

    public void setStype(boolean newStyle) {
        _earnOrLose = newStyle;
    }
    public void changeStyle() {
        _earnOrLose = !_earnOrLose;
    }
}
