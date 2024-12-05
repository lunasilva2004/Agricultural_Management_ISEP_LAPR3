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
import java.util.InputMismatchException;
import java.util.Scanner;


public class RegistarReceitaUI implements Runnable {
    private RegistarOperacaoController controller;

    public RegistarReceitaUI() {
        controller = new RegistarOperacaoController();
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        String designacaoReceita = null;
        while (designacaoReceita == null) {
            System.out.print("Insira a Designação da Receita: ");
            designacaoReceita = scanner.nextLine();  // Usar nextLine para evitar problemas com espaços no nome
        }

        int option = 0;
        while (true) {
            try {
                System.out.println("Quantos Fatores de Produção deseja associar?");
                option = scanner.nextInt();
                break;  // Sair do loop se a entrada for válida
            } catch (InputMismatchException e) {
                System.out.println("Por favor, insira um valor numérico válido.");
                scanner.nextLine();  // Limpar o buffer do Scanner
            }
        }

        int[] fatoresProducao = new int[option];
        for (int i = 0; i < option; i++) {
            System.out.println("Insira o ID do Fator de Produção número " + (i + 1) + ":");
            while (true) {
                try {
                    fatoresProducao[i] = scanner.nextInt();
                    break;  // Sair do loop se a entrada for válida
                } catch (InputMismatchException e) {
                    System.out.println("Por favor, insira um valor numérico válido.");
                    scanner.nextLine();  // Limpar o buffer do Scanner
                }
            }
        }

        int status = controller.registarReceira(designacaoReceita, fatoresProducao);
        if (status == 1) {
            System.out.println("Receita registada com sucesso!");
        } else {
            System.out.println("Receita não registada!");
        }
    }
}

