import application.LAPR3.repositories.DatabaseConnection;
import application.LAPR3.ui.RegisterWateringInDatabaseUI;
import application.LAPR3.utils.ReadInstructionFile;
import application.MenuUI;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Main {
    public static void main(String[] args) throws IOException {
        //NÂO APAGAR
        String intructionsFilePath = "src/main/resources/DataLapr3/instructions.txt";
        ReadInstructionFile.lerInstrucoes(intructionsFilePath);

        try {
            loadSqlConnectionProperties();

            //TODO: MENU
            MenuUI menu = new MenuUI();
            menu.run();

            DatabaseConnection.getInstance().closeConnection();

        } catch (UnknownHostException e) {
            System.out.println("\nDatabase Server not reachable!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadSqlConnectionProperties() throws IOException {
        Properties properties = new Properties(System.getProperties());

        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("Data_BDDAD/sql_connection/application.properties");

        if (inputStream != null) {
            properties.load(inputStream);
            inputStream.close();
            System.setProperties(properties);
        } else {
            System.out.println("Unable to find application.properties");
        }
    }
}

