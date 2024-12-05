package application.ESINF.ui;

import application.ESINF.utils.ReadFiles;

import java.io.IOException;
import java.util.Scanner;

public class LoadOperatingHoursUI {
    public void run() throws IOException {

        String filePath = "src/main/resources/Data_ESINF/horario_hubs.csv";

        ReadFiles.importOperatingHours(filePath);
        System.out.println("Operating hours loaded successfully.");

    }
}
