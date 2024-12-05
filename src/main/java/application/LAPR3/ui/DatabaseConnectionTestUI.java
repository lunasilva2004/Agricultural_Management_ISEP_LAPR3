package application.LAPR3.ui;

import application.LAPR3.controller.DatabaseConnectionTestController;
import application.LAPR3.repositories.DatabaseConnection;

import java.sql.SQLException;

public class DatabaseConnectionTestUI implements Runnable {

    private DatabaseConnectionTestController controller;

    public DatabaseConnectionTestUI() {
        controller = new DatabaseConnectionTestController();
    }

    public void run() {
        try {
            int result = controller.DatabaseConnectionTest();

            if (result == DatabaseConnection.CONNECTION_SUCCESS) {
                System.out.println("Connected to the database.");
            } else {
                System.out.println("Not connected to the database!");
            }
        } catch (SQLException e) {
            System.out.println("Error: Unable to establish a database connection." +
                    "\nPlease check if connection parameters are correct and if the database server is running.");
        }
    }
}
