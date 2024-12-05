package application.LAPR3.controller;
import application.LAPR3.repositories.DatabaseConnection;

import java.sql.SQLException;

public class ExitController {

    public ExitController(){
    }

    public void exit() throws SQLException {
        DatabaseConnection.getInstance().closeConnection();
    }
}
