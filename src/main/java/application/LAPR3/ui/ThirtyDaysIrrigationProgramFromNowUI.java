package application.LAPR3.ui;

import application.LAPR3.controller.ThirtyDaysIrrigationProgramFromNowController;

import java.time.LocalDate;

public class ThirtyDaysIrrigationProgramFromNowUI {

    public void run()
    {
        ThirtyDaysIrrigationProgramFromNowController.generateThirtyDaysProgramFromNow();
    }
    public static void displaySucessMessage(LocalDate now)
    {
        System.out.println("Thirty days irrigation program with start date on " + now + " generated successfully!");
    }
}
