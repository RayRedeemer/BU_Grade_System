package backend;

public class Instructor extends Human {

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

    //TODO things having to do with making courses, though maybe that should not be part of this class
}
