package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
        "jdbc:mysql://localhost:3307/Watch_it";
    private static final String USER = "Watch_it_user";
    private static final String PASSWORD = "12345678";

    private DBConnection() {} // prevent object creation

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}