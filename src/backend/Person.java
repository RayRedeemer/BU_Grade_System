package backend;

/**
 * Class represents a person with a _name and unique _id. All students and instructors should extend from this class
 * There is no id setter as id should not change
 */
public abstract class Person {

    private String _name; //more like username
    private int _id; //monotonic increasing number for instructors, student id for students

    public Person(String name, int id) {
        _name = name;
        _id = id;
    }

    public String getName(){
        return _name;
    }

    public void setName(String n) {
        _name = n;
    }

    public int getId() {
        return _id;
    }


}
