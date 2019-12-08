package share;

import backend.AcademicObject;

import java.util.ArrayList;
import java.util.List;

/*
Author: Ziqi Tan
*/
public class Request {

    private RequestHead head;
    private List<Object> params;
    private List<Integer> ids;

    public Request(RequestHead _head) {
        this.head = _head;
        params = new ArrayList<>();
        ids = new ArrayList<>();
    }

    /**
     * getter()
     */
    public RequestHead getHead() {
        return head;
    }

    public List<Object> getParams() {
        return params;
    }

    public List<Integer> getIds() {
        return ids;
    }

    /**
     * setter()
     */
    public void addParams(Object object) {
        params.add(object);
    }

    @Override
    public String toString() {
        String str = System.getProperty("line.separator") + "head: " + head + System.getProperty("line.separator");
        for (int i = 0; i < params.size(); i++) {
            str += "param " + i + ": " + params.get(i) + System.getProperty("line.separator");
        }
        return str;
    }
}
