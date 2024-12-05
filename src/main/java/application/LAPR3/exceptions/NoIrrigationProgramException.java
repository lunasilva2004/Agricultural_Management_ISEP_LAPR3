package application.LAPR3.exceptions;

import java.time.LocalDate;

public class NoIrrigationProgramException extends RuntimeException {
    public NoIrrigationProgramException(LocalDate date)
    {
        super("No irrigation program found for the date " + date + "!");
    }
}

