package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "lib";
    private static final String PASS = "book";

    public static Connection connect(){
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("connection established successfully");
            return conn;
        }catch (ClassNotFoundException e){
            System.out.println("JDBC driver not found");
            e.printStackTrace();
        }catch (SQLException e){
            System.out.println("connection failled");
        }
        return null;
    }
}
