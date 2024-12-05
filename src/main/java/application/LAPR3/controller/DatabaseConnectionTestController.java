package application.LAPR3.controller;
import application.LAPR3.repositories.DatabaseConnection;

import java.sql.SQLException;

public class DatabaseConnectionTestController {

    public DatabaseConnectionTestController() {
    }

    public int DatabaseConnectionTest() throws SQLException {
        int testResult = DatabaseConnection.getInstance().testConnection();
        return testResult;
    }
}
