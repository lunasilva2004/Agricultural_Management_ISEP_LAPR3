package application.LAPR3.exceptions;

public class InvalidDateFormatException extends RuntimeException {
    public InvalidDateFormatException(String inputDate) {
        super("Invalid date format: " + inputDate + "!\n" +
                "Please use the format DD-MM-YYYY.");
    }
}
