package backend;

/**
 * Class represents a category. It has a _weight field recording the percentage it takes
 */
public class Category extends AcademicObject {

    private double _weight; //form: percent as #.##...

    public Category(int id, String name, String description, Course c) {
        super(id, name, description, c);
    }

    /**
     * Compute grades of the current Category. It passes _weight to all assignments it records.
     */
    public void calculateGrades() {
        for (AcademicObject ao : getAllDescendants()) {
            Assignment a = (Assignment) ao;
            a.calculateGrades(_weight);
        }
    }

    public double getWeight() {
        return _weight;
    }

    public void setWeight(double w) {
        _weight = w;
    }
}
