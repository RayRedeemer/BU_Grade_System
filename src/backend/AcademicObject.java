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
public abstract class AcademicObject implements Commentable {

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
        _descendants = new ArrayList<>();
    }

    // constructor with null parent reference
//    public AcademicObject(int id, String name, String description) {
//        this(id, name, description, null);
//    }

    // constructor with descendants
    public AcademicObject(int id, String name, String description, AcademicObject parent, ArrayList<AcademicObject> descendants) {
        this(id, name, description, parent);
        _descendants.addAll(descendants);
    }

    // copy constructor
    public AcademicObject(AcademicObject ao) {
        this(ao.getId(), ao.getName(), ao.getDescription(), ao.getParent(), ao.getAllDescendants());
    }


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

    public void setDescription(String desc) {
        _description = desc;
    }

    public String getComment() {
        return _comment;
    }

    public void setComment(String str) {
        _comment = str;
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
     * Remove a descendant
     *
     * @param ao
     */
    public void deleteDescendant(AcademicObject ao) {
//        if (this.hasDescendants()) {
//            ao.deleteAllDescendants();
//        }
        _descendants.remove(ao);
    }

//    /**
//     * remove all descendants
//     */
//    public void deleteAllDescendants() {
//        if (this.hasDescendants()) {
//            for (AcademicObject descendant : getAllDescendants()) {
//                deleteDescendant(descendant);
//            }
//        }
//    }

    /**
     * Check if the current object has descendants
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

    public void addAllDescendants(ArrayList<AcademicObject> arr) {
        if (!this.hasDescendants()) _descendants = new ArrayList<AcademicObject>();
        _descendants.addAll(arr);
    }

    private boolean isIndexValid(int index) {
        if (index < 0 || index >= _descendants.size()) {
            return false;
        } else {
            return true;
        }
    }
}