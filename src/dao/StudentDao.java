package dao;

import database.DBConnection;
import model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {

    //add a student
    public static boolean addStudent(Student student){
        String sql = "INSERT INTO STUDENT (student_id, firstname, lastname, email, phone_number) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, student.getStudent_id());
            ps.setString(2, student.getFirst_name());
            ps.setString(3, student.getLast_name());
            ps.setString(4, student.getEmail());
            ps.setString(5, student.getPhone_number());
            return ps.executeUpdate() > 0;
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //update a student
    public static boolean updateStudent(Student student) {
        StringBuilder sql = new StringBuilder("UPDATE student SET ");
        List<Object> params = new ArrayList<>();

        if (student.getFirst_name() != null && !student.getFirst_name().isEmpty()) {
            sql.append("firstname = ?, ");
            params.add(student.getFirst_name());
        }

        if (student.getLast_name() != null && !student.getLast_name().isEmpty()) {
            sql.append("lastname = ?, ");
            params.add(student.getLast_name());
        }

        if (student.getEmail() != null && !student.getEmail().isEmpty()) {
            sql.append("email = ?, ");
            params.add(student.getEmail());
        }

        if (student.getPhone_number() != null && !student.getPhone_number().isEmpty()) {
            sql.append("phone_number = ?, ");
            params.add(student.getPhone_number());
        }

        // If no fields to update
        if (params.isEmpty()) {
            System.out.println("No data to update.");
            return false;
        }

        sql.setLength(sql.length() - 2); // remove trailing comma
        sql.append(" WHERE student_id = ?");
        params.add(student.getStudent_id());

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //delete a student
    public static boolean deleteStudent(int id_student){
        String sql = "DELETE FROM student WHERE student_id=?";
        try(Connection conn = DBConnection.connect();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, id_student);
            return ps.executeUpdate() > 0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    //list of all students
    public static List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student";
        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){

            while (rs.next()){
                Student student = new Student(
                        rs.getInt("student_id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("email"),
                        rs.getString("phone_number")
                );
                students.add(student);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return students;
    }


}
