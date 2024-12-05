package application.LAPR3;

import application.LAPR3.exceptions.NoIrrigationProgramException;
import application.LAPR3.domain.IrrigationProgram;
import application.LAPR3.enums.RegularityType;
import application.LAPR3.repositories.Repositories;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class IrrigationProgrammer {

    public static Map<LocalDate, List<IrrigationProgram>> getThirtyDaysProgram() {
        List<IrrigationProgram> irrigationProgram = Repositories.getInstance().getIrrigationProgramRepository().getIrrigationProgramArrayList();
        Map<LocalDate, List<IrrigationProgram>> thirtyDaysProgram = new TreeMap<>();

        LocalDate startDate = irrigationProgram.get(0).getStartDate();

        return getFilteredProgramsMap(irrigationProgram, thirtyDaysProgram, startDate);
    }

    public static Map<LocalDate, List<IrrigationProgram>> getThirtyDaysProgramStartedAtSpecificDate(LocalDate date) {
        List<IrrigationProgram> irrigationProgram = Repositories.getInstance().getIrrigationProgramRepository().getIrrigationProgramArrayList();
        Map<LocalDate, List<IrrigationProgram>> thirtyDaysProgram = new TreeMap<>();

        return getFilteredProgramsMap(irrigationProgram, thirtyDaysProgram, date);
    }

    public static List<IrrigationProgram> getIrrigationProgramForDesiredDay(LocalDate date) {
        Map<LocalDate, List<IrrigationProgram>> thirtyDaysProgram = getThirtyDaysProgram();
        List<IrrigationProgram> irrigationPrograms = thirtyDaysProgram.get(date);
        if (irrigationPrograms == null || irrigationPrograms.isEmpty()) {
            throw new NoIrrigationProgramException(date);
        }
        return new ArrayList<>(irrigationPrograms);
    }

    private static Map<LocalDate, List<IrrigationProgram>> getFilteredProgramsMap(List<IrrigationProgram> irrigationProgram, Map<LocalDate, List<IrrigationProgram>> thirtyDaysProgram, LocalDate startDate) {
        int hasMixFlag = -1;
        for (int i = 0; i < irrigationProgram.size(); i++) {
            for (int dayOfMonthIndex = 0; dayOfMonthIndex < 30; dayOfMonthIndex++) {
                if (!irrigationProgram.get(i).isHasMix()) {
                    if (irrigationProgram.get(i).getRegularity().equals(RegularityType.EVERY.getCode())) {
                        handleEvery(dayOfMonthIndex, startDate, irrigationProgram, thirtyDaysProgram, i);
                    }
                    if (irrigationProgram.get(i).getRegularity().equals(RegularityType.EVEN.getCode())) {
                        handleEven(dayOfMonthIndex, startDate, irrigationProgram, thirtyDaysProgram, i);
                    }
                    if (irrigationProgram.get(i).getRegularity().equals(RegularityType.ODD.getCode())) {
                        handleOdd(dayOfMonthIndex, startDate, irrigationProgram, thirtyDaysProgram, i);
                    }
                    if (irrigationProgram.get(i).getRegularity().equals(RegularityType.EVERY_THREE.getCode())) {
                        handleEveryThree(dayOfMonthIndex, startDate, irrigationProgram, thirtyDaysProgram, i);
                    }
                }else {
                    int mixRegularity = irrigationProgram.get(i).getMixRegularity();
                    if (hasMixFlag == mixRegularity){
                        int isToAdd = 1;
                        handleHasMix(dayOfMonthIndex, startDate, irrigationProgram, thirtyDaysProgram, i, isToAdd);
                        hasMixFlag = 0;
                    }else if (hasMixFlag >= 0){
                        int isToAdd = 0;
                        handleHasMix(dayOfMonthIndex, startDate, irrigationProgram, thirtyDaysProgram, i, isToAdd);
                        hasMixFlag++;
                    }
                    if (hasMixFlag == -1){
                        int isToAdd = 1;
                        handleHasMix(dayOfMonthIndex, startDate, irrigationProgram, thirtyDaysProgram, i, isToAdd);
                        hasMixFlag = 1;
                    }
                }
            }
        }
        return thirtyDaysProgram;
    }

    public static boolean isIrrigationProgramRunningNow(IrrigationProgram irrigationProgram) {
        boolean isRunning = false;
        LocalDateTime now = LocalDateTime.now();
        List<String> startTimes = irrigationProgram.getStartTimes();
        LocalDate startDate = irrigationProgram.getStartDate();
        List<String> invalidTimes = new ArrayList<>();

        for (String time : startTimes) {
            if (time.length() == 4) {
                time = "0" + time;
            }
            LocalTime startTime;
            try {
                startTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
            } catch (DateTimeParseException e) {
                invalidTimes.add(time);
                continue;
            }
            LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
            LocalDateTime stopTime = startDateTime.plus(irrigationProgram.getIrrigationTemporalAmount());

            if (now.isAfter(startDateTime) && now.isBefore(stopTime)) {
                isRunning = true;
                break;
            }
        }
        if (!invalidTimes.isEmpty()) {
            System.out.println("Invalid time formats read from txt file: " + invalidTimes);
        }
        return isRunning;
    }

    public static boolean isIrrigationProgramRunningAtSpecificTime(IrrigationProgram irrigationProgram, LocalDateTime checkTime) {
        boolean isRunning = false;
        List<String> startTimes = irrigationProgram.getStartTimes();
        LocalDate startDate = irrigationProgram.getStartDate();
        List<String> invalidTimes = new ArrayList<>();

        for (String time : startTimes) {
            if (time.length() == 4) {
                time = "0" + time;
            }
            LocalTime startTime;
            try {
                startTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
            } catch (DateTimeParseException e) {
                invalidTimes.add(time);
                continue;
            }
            LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
            LocalDateTime stopTime = startDateTime.plus(irrigationProgram.getIrrigationTemporalAmount());

            if (checkTime.isAfter(startDateTime) && checkTime.isBefore(stopTime)) {
                isRunning = true;
                break;
            }
        }
        if (!invalidTimes.isEmpty()) {
            System.out.println("Invalid time formats read from txt file: " + invalidTimes);
        }
        return isRunning;
    }

    public static Map<String, Integer> timeToStopIrrigationProgram(IrrigationProgram irrigationProgram, LocalDateTime checkTime) {
        List<String> startTimes = Collections.singletonList(irrigationProgram.getStartTimes().get(0));
        LocalDate startDate = irrigationProgram.getStartDate();
        Map<String, Integer> remaingTimeByParcel = new HashMap<>();
        int remainingMinutes;

        for (String time : startTimes) {
            LocalTime startTime;
            try {
                startTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
            } catch (DateTimeParseException e) {
                continue;
            }
            LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
            LocalDateTime stopTime = startDateTime.plus(irrigationProgram.getIrrigationTemporalAmount());

            if (checkTime.isAfter(startDateTime) && checkTime.isBefore(stopTime)) {
                Duration remainingDuration = Duration.between(checkTime, stopTime);
                remainingMinutes = (int) remainingDuration.toMinutes();
                remaingTimeByParcel.put(irrigationProgram.getParcel(), remainingMinutes);
                break;
            }
        }
        return remaingTimeByParcel;
    }

    private static void createOrUpdateIrrigationProgram(LocalDate newDate, List<IrrigationProgram> irrigationProgram, Map<LocalDate, List<IrrigationProgram>> thirtyDaysProgram, int i) {
        List<IrrigationProgram> irrigationPrograms = thirtyDaysProgram.computeIfAbsent(newDate, k -> new ArrayList<>());
        irrigationPrograms.add(new IrrigationProgram(irrigationProgram.get(i).getStartTimes(), newDate, irrigationProgram.get(i).getParcel(), irrigationProgram.get(i).getIrrigationDuration(), irrigationProgram.get(i).getRegularity()));
    }
    private static void createOrUpdateIrrigationProgramWithMix(LocalDate newDate, List<IrrigationProgram> irrigationProgram, Map<LocalDate, List<IrrigationProgram>> thirtyDaysProgram, int i, int isToAdd) {
        List<IrrigationProgram> irrigationPrograms = thirtyDaysProgram.computeIfAbsent(newDate, k -> new ArrayList<>());
        if (isToAdd == 1){
        irrigationPrograms.add(new IrrigationProgram(irrigationProgram.get(i).getStartTimes(), newDate, irrigationProgram.get(i).getParcel(), irrigationProgram.get(i).getIrrigationDuration(), irrigationProgram.get(i).getRegularity(), irrigationProgram.get(i).getMix(), irrigationProgram.get(i).getMixRegularity(), irrigationProgram.get(i).isHasMix()));
        } else {
            irrigationPrograms.add(new IrrigationProgram(irrigationProgram.get(i).getStartTimes(), newDate, irrigationProgram.get(i).getParcel(), irrigationProgram.get(i).getIrrigationDuration(), irrigationProgram.get(i).getRegularity()));
        }
    }

    private static void handleEvery(int j, LocalDate startDate, List<IrrigationProgram> irrigationProgram, Map<LocalDate, List<IrrigationProgram>> thirtyDaysProgram, int i) {
        LocalDate newDate = startDate.plusDays(j);
        createOrUpdateIrrigationProgram(newDate, irrigationProgram, thirtyDaysProgram, i);
    }

    private static void handleEven(int j, LocalDate startDate, List<IrrigationProgram> irrigationProgram, Map<LocalDate, List<IrrigationProgram>> thirtyDaysProgram, int i) {
        if (startDate.getDayOfMonth() % 2 == 0) {
            if (j % 2 == 0) {
                LocalDate newDate = startDate.plusDays(j);
                createOrUpdateIrrigationProgram(newDate, irrigationProgram, thirtyDaysProgram, i);
            }
        } else {
            if (j % 2 != 0) {
                LocalDate newDate = startDate.plusDays(j);
                createOrUpdateIrrigationProgram(newDate, irrigationProgram, thirtyDaysProgram, i);
            }
        }
    }

    private static void handleOdd(int j, LocalDate startDate, List<IrrigationProgram> irrigationProgram, Map<LocalDate, List<IrrigationProgram>> thirtyDaysProgram, int i) {
        if (startDate.getDayOfMonth() % 2 == 0) {
            if (j % 2 != 0) {
                LocalDate newDate = startDate.plusDays(j);
                createOrUpdateIrrigationProgram(newDate, irrigationProgram, thirtyDaysProgram, i);
            }
        } else {
            if (j % 2 != 0) {
                LocalDate newDate = startDate.plusDays(j - 1);
                createOrUpdateIrrigationProgram(newDate, irrigationProgram, thirtyDaysProgram, i);
            }
        }
    }

    private static void handleEveryThree(int j, LocalDate startDate, List<IrrigationProgram> irrigationProgram, Map<LocalDate, List<IrrigationProgram>> thirtyDaysProgram, int i) {
        if (j % 3 == 0) {
            LocalDate newDate = startDate.plusDays(j);
            createOrUpdateIrrigationProgram(newDate, irrigationProgram, thirtyDaysProgram, i);
        }
    }
    private static void handleHasMix(int j, LocalDate startDate, List<IrrigationProgram> irrigationProgram, Map<LocalDate, List<IrrigationProgram>> thirtyDaysProgram, int i, int isToAdd) {
            LocalDate newDate = startDate.plusDays(j);
            createOrUpdateIrrigationProgramWithMix(newDate, irrigationProgram, thirtyDaysProgram, i, isToAdd);
        }
    }


