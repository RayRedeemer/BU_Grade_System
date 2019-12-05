package backend;

import java.time.LocalDateTime;

/**
 * Class represents an assignment. _weight is in the format of 0.##.
 */
public class Assignment extends AcademicObject {

    private double _maxScore;
    private double _weight; //form: percent as 0.##...
    private LocalDateTime _assignedDate;
    private LocalDateTime _dueDate;

    public Assignment(int id, String name, String description, Category c){
        super(id, name, description, c);
    }

    /**
     * Compute Grades given the category weight
     *
     * @param catweight double
     */
    public void calculateGrades(double catweight){
        for (AcademicObject ao : getAllDescendants()) {
            Submission sub = (Submission) ao;
            ((Submission) ao).calculateGrade(catweight, _weight, _maxScore);
        }
    }

    public double getMaxScore() {
        return _maxScore;
    }

    public void setMaxScore(double ms) {
        _maxScore = ms;
    }

    public double getWeight() {
        return _weight;
    }

    public void setWeight(double w) {
        _weight = w;
    }

    public LocalDateTime getAssignedDate() {
        return _assignedDate;
    }

    public void setAssignedDate(LocalDateTime ad) {
        _assignedDate = ad;
    }

    public LocalDateTime getDueDate() {
        return _dueDate;
    }

    public void setDueDate(LocalDateTime dd) {
        _dueDate = dd;
    }

}
