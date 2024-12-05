package application.LAPR3.controller;

import application.LAPR3.IrrigationProgrammer;
import application.LAPR3.domain.IrrigationProgram;
import application.LAPR3.ui.IsIrrigationRunningNowUI;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static application.LAPR3.IrrigationProgrammer.getIrrigationProgramForDesiredDay;

public class IsIrrigationRunningNowController {

    public static void checkIfIrrigatingAndRemainingTimeNow()
    {
        List<IrrigationProgram> irrigationProgramForDesiredDay = getIrrigationProgramForDesiredDay(LocalDate.now());
        Map<String, Integer> remainingTimes = new HashMap<>();

        for (IrrigationProgram irrigationProgram : irrigationProgramForDesiredDay) {
            LocalDateTime checkDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.now());

            Map<String, Integer> timeMap = IrrigationProgrammer.timeToStopIrrigationProgram(irrigationProgram, checkDateTime);
            if (timeMap.containsKey(irrigationProgram.getParcel())) {
                int remainingTime = timeMap.get(irrigationProgram.getParcel());
                if (IrrigationProgrammer.isIrrigationProgramRunningNow(irrigationProgram)) {
                    remainingTimes.put(irrigationProgram.getParcel(), remainingTime);
                }
            }
        }
        IsIrrigationRunningNowUI.displayIrrigationParcelAndRemainingTime(remainingTimes);
    }
}

