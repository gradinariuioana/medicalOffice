package ro.unibuc.medicalOffice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {
    private static Connection connection;
    private static DBConnection instance;
    private static final String URL = "jdbc:mysql://localhost";
    private static final String USER_PASSWORD = "root";


    private DBConnection() throws SQLException, ClassNotFoundException {
        this.connection = DriverManager.getConnection(URL, USER_PASSWORD, USER_PASSWORD);
    }

    private Connection getConnection() throws SQLException, ClassNotFoundException{
        return connection;
    }

    public static Connection getInstance() throws SQLException, ClassNotFoundException{
        if (connection == null) {
            connection = new DBConnection().getConnection();
        }
        return connection;
    }
}
