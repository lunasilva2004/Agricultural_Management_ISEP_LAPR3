package application.LAPR3.controller;

import application.LAPR3.utils.ThirtyDaysIrrigationProgramFileGenerator;
import application.LAPR3.domain.IrrigationProgram;
import application.LAPR3.ui.ThirtyDaysIrrigationProgramWithStartDateUI;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static application.LAPR3.IrrigationProgrammer.getThirtyDaysProgramStartedAtSpecificDate;

public class ThirtyDaysIrrigationProgramWithStartDateController {

    public static void generateThirtyDaysProgramForSpecificDate(LocalDate desiredDate)
    {
        String filePath = "docs/LAPR3/US_LP02/thirty_days_irrigation_program.csv";
        Map<LocalDate, List<IrrigationProgram>> thirtyDaysProgramWithStartDate = getThirtyDaysProgramStartedAtSpecificDate(desiredDate);
        ThirtyDaysIrrigationProgramFileGenerator.generateIrrigationProgramFile(filePath, thirtyDaysProgramWithStartDate);
        ThirtyDaysIrrigationProgramWithStartDateUI.displaySucessMessage(desiredDate);
    }
}

