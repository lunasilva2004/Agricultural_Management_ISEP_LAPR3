package application.LAPR3.ui;

import application.LAPR3.controller.IsIrrigationRunningNowController;
import application.LAPR3.exceptions.NoIrrigationProgramException;

import java.util.Map;

public class IsIrrigationRunningNowUI implements Runnable {
    @Override
    public void run()
    {
        try {
            System.out.println();
            IsIrrigationRunningNowController.checkIfIrrigatingAndRemainingTimeNow();
        } catch (NoIrrigationProgramException exception) {
            System.out.println(exception.getMessage());
        }
    }
    public static void displayIrrigationParcelAndRemainingTime(Map<String, Integer> remainingTimes)
    {
        if (remainingTimes.isEmpty()) {
            System.out.println("No irrigation programs are running right now.");
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