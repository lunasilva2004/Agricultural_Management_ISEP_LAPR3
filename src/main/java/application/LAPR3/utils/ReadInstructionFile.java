package application.LAPR3.utils;

import application.LAPR3.domain.IrrigationProgram;
import application.LAPR3.repositories.Repositories;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class ReadInstructionFile {
    public static void lerInstrucoes(String instructionsFilePath) throws IOException {
        LocalDate currentDate = LocalDate.now();
        FileReader fileReader = new FileReader(instructionsFilePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String firstLine = bufferedReader.readLine();
        List<String> irrigationTimes = Arrays.asList(firstLine.split(", "));

        for (int i = 0; i < irrigationTimes.size(); i++) {
            String line;
            String previousEndTime = irrigationTimes.get(i);

            FileReader fileReaderIteration = new FileReader(instructionsFilePath);
            BufferedReader bufferedReaderIteration = new BufferedReader(fileReaderIteration);
            bufferedReaderIteration.readLine();

            while ((line = bufferedReaderIteration.readLine()) != null) {
                String[] lineData = line.split(",");
                if (lineData.length == 3) {
                    String startTime = previousEndTime;
                    String endTime = newFinalHour(startTime, Integer.parseInt(lineData[1].trim()));

                    IrrigationProgram irrigationProgram = new IrrigationProgram(Arrays.asList(startTime, endTime), currentDate, lineData[0], Integer.parseInt(lineData[1].trim()), lineData[2].trim());
                    Repositories.getInstance().getIrrigationProgramRepository().addIrrigationProgram(irrigationProgram);

                    previousEndTime = endTime;
                }
                if (lineData.length == 5) {
                    String startTime = previousEndTime;
                    String endTime = newFinalHour(startTime, Integer.parseInt(lineData[1].trim()));

                    IrrigationProgram irrigationProgram = new IrrigationProgram(Arrays.asList(startTime, endTime), currentDate, lineData[0], Integer.parseInt(lineData[1].trim()), lineData[2].trim(), lineData[3].trim(), Integer.parseInt(lineData[4].trim()), true);
                    Repositories.getInstance().getIrrigationProgramRepository().addIrrigationProgram(irrigationProgram);

                    previousEndTime = endTime;
                }

            }
            fileReaderIteration.close();
        }
        fileReader.close();
    }

    private static String newFinalHour(String startTime, int duration) {
        LocalTime start = LocalTime.parse(startTime);
        LocalTime end = start.plusMinutes(duration);
        return end.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
