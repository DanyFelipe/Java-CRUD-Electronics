package org.kdcrud.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class SQLConnection {
    private static Connection connection;
    public static Connection getConnection() {
        if (connection == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/KD-productos";
                String user = "root";
                String password = "danylhall117";
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
