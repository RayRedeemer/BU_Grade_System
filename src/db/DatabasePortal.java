package db;

import backend.Instructor;

import javax.swing.plaf.nimbus.State;
import java.sql.*;

public class DatabasePortal {

    private Connection _conn;
    private static DatabasePortal _me;

    private DatabasePortal() {
        try {
            _conn = DriverManager.getConnection("jdbc:sqlite:src/db/grades.db");
        } catch (Exception e) {
            System.out.println("Error while connecting to db:");
            System.out.println(e.getMessage());
        }
    }

    public static DatabasePortal getInstance() {
        if (_me == null) {
            _me = new DatabasePortal();
        }
        return _me;
    }

    public boolean addInstructor(String name, String password) {
        try {
            String sql = "SELECT * FROM instructors WHERE name=?;";
            PreparedStatement ps = _conn.prepareStatement(sql);
            ps.setString(1,name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                System.out.println("Instructor with name '" + name + "' already exists.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error while checking for instructor during addInstructor:");
            System.out.println(e.getMessage());
            return false;
        }
        try {
            String sql = "INSERT INTO instructors (name,password) VALUES (?,?);";
            PreparedStatement ps = _conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, password);
            ps.execute();
        } catch (Exception e) {
            System.out.println("Error while inserting new instructor in addInstructor:");
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public Instructor getInstructor(String name, String password) {
        try{
            String sql = "SELECT * FROM instructors\n" +
                    "WHERE name=?\n" +
                    "AND password=?;";
            PreparedStatement ps = _conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return new Instructor(name, rs.getInt("instructor_id"), password);
            }
        } catch (Exception e) {
            System.out.println("Error during getInstructor with params: " + name + ":" + password);
            System.out.println(e.getMessage());
        }
        return null;
    }

    public int getInstructorCount() {
        try {
            String sql = "SELECT COUNT(*) from instructors;";
            Statement stmt = _conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next(); // always true, COUNT always returns at least one row
            return rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error during getInstructorCount:");
            System.out.println(e.getMessage());
        }
        return 0;
    }
    
}
