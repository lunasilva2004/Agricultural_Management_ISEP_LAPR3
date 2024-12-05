package application.ESINF.utils;

import java.time.format.DateTimeFormatter;

public class ExcecaoData extends Exception {

    public ExcecaoData(String message) {
        super(message);
    }

    // Static method to verify if the given date string is valid
    public static void verificarData(String data) throws ExcecaoData {
        // Implement your validation logic here
        // For example, you can check if the date string is in the correct format (dd/MM/yyyy)

        // For demonstration purposes, let's assume a simple check for the format "dd/MM/yyyy"
        if (!data.matches("\\d{2}/\\d{2}/\\d{4}")) {
            throw new ExcecaoData("Formato de data inválido. Use o formato dd/MM/yyyy.");
        }

        // You can also perform more advanced date validation if needed
        try {
            DateTimeFormatter.ofPattern("dd/MM/yyyy").parse(data);
        } catch (Exception e) {
            throw new ExcecaoData("Data inválida. Certifique-se de usar o formato dd/MM/yyyy.");
        }
    }
}

