package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static final String URL = "jdbc:mysql://localhost:3306/appointment_system_db";
    public static final String USER = "root";
    public static final String PASSWORD = System.getenv("DB_PASSWORD");

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD);
    }

}
