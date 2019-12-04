package backend;

import java.util.ArrayList;

/**
 * Abstract class serves as the base class of all objects that share the same properties for our grade system. It has
 * its own unique _id for db, name, description and comment. We also use a tree-like structure to serve as the data
 * structure for our system, so _parent and _descendants are similar to pointers to its upper-level and lower-level
 * objects.
 *
 * Note that _parent should be null if it's at the top level, which in this case, if it is a Course, _descendants also
 * follow this rule if the object is at the lowest level, such as Submission.
 */
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
    // 4. _parent and _descendants should not generating cyclical traversal(going up parent or down
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

    /**
     * Get all descendants of the current object if it has.
     *
     * @return
     */
    public ArrayList<AcademicObject> getAllDescendants() {
        if (this.hasDescendants()) return _descendants;
        return null;
    }

    /**
     * Get a single descendant if if has, or the index is valid.
     *
     * @param index
     * @return AcademicObject instance
     */
    public AcademicObject getDescendant(int index) {
        if (isIndexValid(index) && this.hasDescendants())
            return _descendants.get(index);
        else
            return null;
    }

    /**
     * Remove a descendant given its index
     *
     * @param index
     */
    public void removeDescendant(int index) {
        if (isIndexValid(index) && this.hasDescendants())
            _descendants.remove(index);
    }

    /**
     * Check if the current object has decendants
     *
     * @return boolean.
     */
    public boolean hasDescendants() {
        return _descendants != null;
    }

    /**
     * Add a single descendant to the current object
     *
     * @param ao descendant object to be added
     */
    public void addDescendant(AcademicObject ao) {
        if (!this.hasDescendants()) _descendants = new ArrayList<AcademicObject>();
        _descendants.add(ao);
    }

    private boolean isIndexValid(int index) {
        if (index < 0 || index >= _descendants.size()) {
            return false;
        } else {
            return true;
        }
    }

}
