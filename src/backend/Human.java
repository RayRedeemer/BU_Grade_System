package backend;

public abstract class Human {

    private String _name; //more like username
    private int _id; //monotonic increasing number for instructors, student id for students

    public Human(String name, int id) {
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

    //no id setter as id should not change

}
