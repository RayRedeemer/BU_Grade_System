package backend;

import java.util.ArrayList;

public abstract class AcademicObject {

    private int _id;
    private String _name;
    private String _description;
    private String _comment;

    private AcademicObject _parent; //null if top level (e.g. course)
    private ArrayList<AcademicObject> _descendants; //left null until something explicitly added
    //though not needed for this project, the above two fields could be improved with a few more constraints
    // 1. _parent and _descendants should not the same type as this
    // 2. _parent and _descendants should not be the same type as each other
    // 3. _descendants should be all the same type
    // 4. _parent and _descendants should not have any typings that would be cyclical (going up parent or down
    //      _descendants should eventually lead to a null reference

    public AcademicObject(int id, String name, String description, AcademicObject parent) {
        _id = id;
        _name = name;
        _description = description;
        _comment = "";
        _parent = parent;
    }

    public AcademicObject(int id, String name, String description) {
        this(id, name, description, null);
    }

    //TODO copy constructor

    public int getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String n) {
        _name = n;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String d) {
        _description = d;
    }

    public String getComment() {
        return _comment;
    }

    public void setComment(String c) {
        _comment = c;
    }

    public AcademicObject getParent() {
        return _parent;
    }

    public void move(AcademicObject destination) {
        _parent = destination;
    }

    public ArrayList<AcademicObject> getAllDescendants() {
        if (this.hasDescendants()) return _descendants;
        return null;
    }

    public AcademicObject getDescendant(int index) {
        if (this.hasDescendants()) return _descendants.get(index);
        return null;
    }

    public void removeDescendant(int index) {
        if (this.hasDescendants() && index < _descendants.size()) _descendants.remove(index);
    }

    public boolean hasDescendants() {
        return _descendants != null;
    }

    public void addDescendant(AcademicObject ao) {
        if (!this.hasDescendants()) _descendants = new ArrayList<AcademicObject>();
        _descendants.add(ao);
    }

}
