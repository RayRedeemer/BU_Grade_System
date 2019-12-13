package backend;

/**
 * Class represents an instructor. It has an extra field: password used for login
 */
public class Instructor extends Person{

    //maybe not needed, as login is done via db select
    private String _password;

    public Instructor(String name, int id, String password) {
        super(name, id);
        _password = password;
    }

    public void setPassword(String p) {
        _password = p;
    }

    //no password getter as password should not be displayed anywhere
}
