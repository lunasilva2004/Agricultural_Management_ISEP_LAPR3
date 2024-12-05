package application.LAPR3.ui;

import application.LAPR3.controller.RegistarOperacaoController;
import application.LAPR3.domain.Irrigation;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Scanner;


public class RegisterWateringUI implements Runnable {
    private RegistarOperacaoController controller;

    public RegisterWateringUI() {
        controller = new RegistarOperacaoController();
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date utilDate = null;
        while (utilDate == null) {
            System.out.print("Data (dd-MM-yyyy): ");
            String inputDate = scanner.next();

            try {
                utilDate = inputFormat.parse(inputDate);
            } catch (ParseException e) {
                System.out.println("Formato de data inválido. Tente novamente.");
            }
        }
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        LocalDate date = sqlDate.toLocalDate();

        String horaInicio = null;
        while (horaInicio == null) {
            System.out.print("Hora Inicio (HH:MM): ");
            horaInicio = scanner.next();
        }

        String horaFinal = null;
        while (horaFinal == null) {
            System.out.print("Hora Final (HH:MM): ");
            horaFinal = scanner.next();
        }
        LocalTime timeInicio = LocalTime.parse(horaInicio, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime timeFinal = LocalTime.parse(horaFinal, DateTimeFormatter.ofPattern("HH:mm"));

        int duracao = Math.toIntExact(ChronoUnit.MINUTES.between(timeInicio, timeFinal));

        Integer setor = null;
        while (setor == null) {
            try {
                System.out.println("Insira o ID do Setor:");
                setor = scanner.nextInt();

                if (setor <= 0) {
                    System.out.println("ID de Setor Invalido. Tente novamente.");
                    setor = null;
                }
            } catch (NumberFormatException e) {
                System.out.println("ID de Setor Invalido. Tente novamente.");
            }
        }

        System.out.println("Deseja adicionar um fertilizante à rega?");
        System.out.println("1 - Sim");
        System.out.println("2 - Não");
        System.out.println("Opção: ");
        int option = scanner.nextInt();
        String receita = "";
        switch (option) {
            case 2:
                break;
            case 1:
                receita = null;
                while (receita == null) {
                    System.out.print("Designação da Receita: ");
                    receita = scanner.next();
                }
                break;
        }


        Irrigation irrigation = new Irrigation(date, String.valueOf(setor), duracao, horaInicio, horaFinal, receita);

        int status = 0;
        try {
            status = controller.wateringRegister(irrigation);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (status == 1) {
            System.out.println("Rega registada com sucesso!");
        } else {
            System.out.println("Rega não registada!");
        }

    }
}
