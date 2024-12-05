package application.LAPR3.controller;

import application.LAPR3.utils.ThirtyDaysIrrigationProgramFileGenerator;
import application.LAPR3.domain.IrrigationProgram;
import application.LAPR3.ui.ThirtyDaysIrrigationProgramFromNowUI;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static application.LAPR3.IrrigationProgrammer.getThirtyDaysProgramStartedAtSpecificDate;

public class ThirtyDaysIrrigationProgramFromNowController {

    public static void generateThirtyDaysProgramFromNow()
    {
        LocalDate now = LocalDate.now();
        String filePath = "docs/LAPR3/US_LP02/thirty_days_irrigation_program.csv";
        Map<LocalDate, List<IrrigationProgram>> thirtyDaysProgramWithStartDate = getThirtyDaysProgramStartedAtSpecificDate(now);
        ThirtyDaysIrrigationProgramFileGenerator.generateIrrigationProgramFile(filePath, thirtyDaysProgramWithStartDate);
        ThirtyDaysIrrigationProgramFromNowUI.displaySucessMessage(now);
    }
}

