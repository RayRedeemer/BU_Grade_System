package backend;

import java.time.LocalDateTime;

public class Submission extends AcademicObject{

    private double _score;
    private double _bonus;
    private LocalDateTime _submittedDate; //no setter, changing date should create new submission
    private Student _student; //who submitted it; no setter
    private boolean _earnOrLose; //whether grade is points earned (positive/true), or points missed (negative/false).
    //needed a flag for _earnOrLose to differentiate 0 being a 0 or a 100 depending on style.

    public Submission(int id, double score, double bonus, LocalDateTime submitted, Student s, Assignment a, boolean style) {
        super(id, s.getName() + "_" + a.getName(), "", a);
        _score = score;
        _bonus = bonus;
        _submittedDate = submitted;
        _student = s;
        _earnOrLose = style;
    }

    public void calculateGrade(double catweight, double assignweight, double maxscore) {
        double rawassignment;
        if (_earnOrLose) {
            rawassignment = (_score + _bonus) / maxscore;
        } else {
            rawassignment = (maxscore + _score + _bonus) / maxscore;
        }
        double weightedassignment = rawassignment * assignweight;
        double globalscore = weightedassignment * catweight;
        _student.setGrade(_student.getGrade() + globalscore);
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

    public LocalDateTime getSubmittedDate() {
        return _submittedDate;
    }

    public boolean getStyle() {
        return _earnOrLose;
    }

    public void changeStyle() {
        _earnOrLose = !_earnOrLose;
    }
}
