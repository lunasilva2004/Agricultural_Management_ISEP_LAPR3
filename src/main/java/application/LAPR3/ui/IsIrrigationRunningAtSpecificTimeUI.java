package application.LAPR3.ui;

import application.LAPR3.controller.IsIrrigationRunningAtSpecificTimeController;
import application.LAPR3.exceptions.NoIrrigationProgramException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import static application.LAPR3.utils.Utils.readDesiredDateFromConsole;
import static application.LAPR3.utils.Utils.readDesiredTimeFromConsole;

public class IsIrrigationRunningAtSpecificTimeUI implements Runnable {
    @Override
    public void run()
    {
        try {
            LocalDate desiredDate = readDesiredDateFromConsole();
            LocalTime desiredTime = readDesiredTimeFromConsole();
            System.out.println();
            IsIrrigationRunningAtSpecificTimeController.checkIfIrrigatingAndRemainingTimeAtSpecificTime(desiredDate, desiredTime);
        } catch (NoIrrigationProgramException exception) {
            System.out.println(exception.getMessage());
        }
    }
    public static void displayIrrigationParcelAndRemainingTime(Map<String, Integer> remainingTimes)
    {
        if (remainingTimes.isEmpty()) {
            System.out.println("No irrigation programs are running at the specified time.");
        } else {
            System.out.println("     Irrigation programs currently running      ");
            System.out.println("+----------------------+-----------------------+");
            System.out.println("|      Parcel          |  Remaining Time (min) | ");
            System.out.println("+----------------------+-----------------------+");

            for (Map.Entry<String, Integer> entry : remainingTimes.entrySet()) {
                String parcel = entry.getKey();
                int remainingTime = entry.getValue();
                System.out.format("| %-20s | %-21d |\n", parcel, remainingTime);
            }
            System.out.println("+----------------------+-----------------------+");
            System.out.println();
        }
    }
}


