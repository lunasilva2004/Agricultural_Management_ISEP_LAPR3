package application.LAPR3.utils;

import application.LAPR3.domain.IrrigationProgram;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.time.LocalDate;
import java.util.Map;

public class ThirtyDaysIrrigationProgramFileGenerator {

    public static void generateIrrigationProgramFile(String fileName, Map<LocalDate, List<IrrigationProgram>> thirtyDaysProgram)
    {
        clearFile(fileName);
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Date,Sector,Irrigation Duration, Start Time, Stop Time, Regularity, Mix Aplied\n");
            for (Map.Entry<LocalDate, List<IrrigationProgram>> entry : thirtyDaysProgram.entrySet()) {
                List<IrrigationProgram> programs = entry.getValue();
                for (IrrigationProgram program : programs) {
                    if (!program.isHasMix()){
                        writer.write(program.getStartDate() + ",");
                        writer.write(program.getParcel() + ",");
                        writer.write(program.getIrrigationDuration() + ",");
                        writer.write(program.getStartTimes().get(0) + ",");
                        writer.write(program.getStartTimes().get(1) + ",");
                        writer.write(program.getRegularity() + "\n");
                    }else {
                        writer.write(program.getStartDate() + ",");
                        writer.write(program.getParcel() + ",");
                        writer.write(program.getIrrigationDuration() + ",");
                        writer.write(program.getStartTimes().get(0) + ",");
                        writer.write(program.getStartTimes().get(1) + ",");
                        writer.write(program.getRegularity() + ",");
                        writer.write(program.getMix() + "\n");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void clearFile(String fileName)
    {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
