package application.LAPR3.ui;

import application.LAPR3.controller.RegistarOperacaoController;
import application.LAPR3.exceptions.InvalidDateFormatException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class RegistarOperacaoSemeaduraUI {

    private RegistarOperacaoController controller;

    public RegistarOperacaoSemeaduraUI() {
        controller = new RegistarOperacaoController();
    }

    public void run() {
        System.out.println("Registar uma operação de semeadura");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Insira o ID da Parcela: ");
        int parcelaID = scanner.nextInt();

        System.out.print("Insira o ID da Cultura: ");
        int culturaID = scanner.nextInt();

        System.out.print("Insira o ID da Planta: ");
        int plantaID = scanner.nextInt();

        Integer area = null;
        while (area == null) {
            try {
                System.out.println("Insira a Área da Semeadura:");
                String input = scanner.next();
                 area = Integer.parseInt(input);

                if (area <= 0) {
                    System.out.println("Area Invalida. Tente novamente.");
                    area = null; // Reset para null se a opção for inválida
                }
            } catch (NumberFormatException e) {
                System.out.println("Area Invalida. Tente novamente.");
            }
        }


        Integer quantidade = null;
        while (quantidade == null) {
            try {
                System.out.println("Insira a Quantidade da Semeadura:");
                String input = scanner.next();
                quantidade = Integer.parseInt(input);

                if (quantidade <= 0) {
                    System.out.println("Quantidade Invalida. Tente novamente.");
                    quantidade = null; // Reset para null se a opção for inválida
                }
            } catch (NumberFormatException e) {
                System.out.println("Quantidade Invalida. Tente novamente.");
            }
        }


        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date utilDate = null;
        while (utilDate == null) {
            System.out.print("Data (dd-MM-yyyy): ");
            String inputDate = scanner.next();

            try {
                // Tente fazer o parse da data de entrada
                utilDate = inputFormat.parse(inputDate);
            } catch (ParseException e) {
                // Se ocorrer uma exceção, imprima uma mensagem de erro e continue o loop
                System.out.println("Formato de data inválido. Tente novamente.");
            }
        }

        // Converta para java.sql.Date
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        String tipoOperacao = "Semeadura";
        String unidade = "ha";


        int state = controller.semeaduraRegister(tipoOperacao, quantidade, area, sqlDate, unidade, culturaID, plantaID, parcelaID);

        if (state == 0)
            System.out.println("\nOperação não registada.");
        else if (state == 1) {
            System.out.println("\nOperação registada.");
        }else if (state == 2){
            System.out.println("\nTempo de espera excedido. Tente mais tarde.");
        }
    }
}
