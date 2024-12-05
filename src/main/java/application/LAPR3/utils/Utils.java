package application.LAPR3.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Utils {
    public static LocalTime readDesiredTimeFromConsole()
    {
        Scanner scanner = new Scanner(System.in);
        LocalTime desiredTime = null;

        while (desiredTime == null) {
            System.out.print("Enter the Time ( HH:mm format ): ");
            String inputTime = scanner.nextLine();

            try {
                desiredTime = LocalTime.parse(inputTime);
            } catch (DateTimeParseException e) {
                System.err.println("Invalid Time! Use HH:mm format");
            }
        }
        return desiredTime;
    }
    public static LocalDate readDesiredDateFromConsole()
    {
        Scanner scanner = new Scanner(System.in);
        LocalDate desiredDate = null;
        while (desiredDate == null) {
            System.out.print("Enter the date ( yyyy-MM-dd format ): ");
            String inputDate = scanner.nextLine();
            try {
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                desiredDate = LocalDate.parse(inputDate, dateFormatter);
            } catch (DateTimeParseException e) {
                System.err.println("Invalid Date Format. Insert on yyyy-MM-dd format.");
            }
        }
        return desiredDate;
    }
    public static String readStringFromConsole()
    {
        Scanner scanner = new Scanner(System.in);
        String inputString = null;
        while (inputString == null){
            System.out.print("Enter the path of the file: ");
            inputString = scanner.nextLine();
        }
        return inputString;
    }
}
