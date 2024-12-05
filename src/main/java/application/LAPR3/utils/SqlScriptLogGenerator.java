package application.LAPR3.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SqlScriptLogGenerator {
    private static final String log_filePath = "docs/BDDAD/US_BD04/sql_scripts_log.txt";
    public static void addLogEntry(String message)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String info = dateFormat.format(new Date()) + " - " + message;

        try (FileWriter logWriter = new FileWriter(log_filePath, true)) {
            logWriter.write(info + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
