package application.LAPR3.ui;

import application.LAPR3.controller.ThirtyDaysIrrigationProgramWithStartDateController;

import java.time.LocalDate;

import static application.LAPR3.utils.Utils.readDesiredDateFromConsole;

public class ThirtyDaysIrrigationProgramWithStartDateUI implements Runnable {
    public void run()
    {
            LocalDate desiredDate = readDesiredDateFromConsole();
            ThirtyDaysIrrigationProgramWithStartDateController.generateThirtyDaysProgramForSpecificDate(desiredDate);
    }
    public static void displaySucessMessage(LocalDate desiredDate)
    {
        System.out.println("Thirty days irrigation program with start date on " + desiredDate + " generated successfully!");
    }
}
