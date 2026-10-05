package task1;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    static String url = "jdbc:mysql://localhost:3306/bankdb";
    static String username = "root";
    static String password = "vj2023";

    public static Connection getConnection() {

        Connection con = null;

        try {

            con = DriverManager.getConnection(url, username, password);

            System.out.println("Database connected successfully");

        } catch (Exception e) {

            e.printStackTrace();
        }

        return con;
    }
}