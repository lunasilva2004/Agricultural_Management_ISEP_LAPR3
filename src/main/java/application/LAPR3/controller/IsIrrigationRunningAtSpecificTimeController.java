package application.LAPR3.controller;

import application.LAPR3.IrrigationProgrammer;
import application.LAPR3.domain.IrrigationProgram;
import application.LAPR3.ui.IsIrrigationRunningAtSpecificTimeUI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static application.LAPR3.IrrigationProgrammer.getIrrigationProgramForDesiredDay;

public class IsIrrigationRunningAtSpecificTimeController
{
    public static void checkIfIrrigatingAndRemainingTimeAtSpecificTime(LocalDate desiredDate, LocalTime desiredTime) {
        List<IrrigationProgram> irrigationProgramForDesiredDay = getIrrigationProgramForDesiredDay(desiredDate);
        Map<String, Integer> remainingTimes = new HashMap<>();

        for (IrrigationProgram irrigationProgram : irrigationProgramForDesiredDay) {
            LocalDateTime checkDateTime = LocalDateTime.of(desiredDate, desiredTime);

            Map<String, Integer> timeMap = IrrigationProgrammer.timeToStopIrrigationProgram(irrigationProgram, checkDateTime);
            if (timeMap.containsKey(irrigationProgram.getParcel())) {
                int remainingTime = timeMap.get(irrigationProgram.getParcel());
                if (IrrigationProgrammer.isIrrigationProgramRunningAtSpecificTime(irrigationProgram, checkDateTime)) {
                    remainingTimes.put(irrigationProgram.getParcel(), remainingTime);
                }
            }
        }
        IsIrrigationRunningAtSpecificTimeUI.displayIrrigationParcelAndRemainingTime(remainingTimes);
    }
}

