package application.LAPR3.ui;

import application.LAPR3.controller.RegistarOperacaoController;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class RegistarOperacaoUI {

    private RegistarOperacaoController controller;

    public RegistarOperacaoUI() {
        controller = new RegistarOperacaoController();
    }

    public void run() {
        try {
            System.out.println("Regista uma operação de poda");

            Scanner scanner = new Scanner(System.in);

            System.out.print("Designação da parcela: ");
            String parcelaDesignacao = scanner.nextLine();

            System.out.print("Plantação: ");
            String plantacao = scanner.nextLine();

            System.out.print("Unidade: ");
            String unidade = scanner.nextLine();

            System.out.print("Quantidade: ");
            int quantidade = scanner.nextInt();

            System.out.print("Data (dd-mm-yyyy): ");
            String date = scanner.next();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date data = formatter.parse(date);


            String[] partes = plantacao.split(" ");

            controller.podaRegister(quantidade, data,unidade,parcelaDesignacao,plantacao,partes[0],partes[1]);


        } catch (ParseException e) {
            System.out.println("\nOperação não registada!\n" + e.getMessage());
        }
    }
}
