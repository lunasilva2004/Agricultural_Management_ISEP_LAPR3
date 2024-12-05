package application.LAPR3.ui;

import application.LAPR3.controller.RegistarOperacaoController;

import java.util.Scanner;

public class RegistarOperacaoColheitaUI {

    private RegistarOperacaoController controller;

    public RegistarOperacaoColheitaUI() {
        controller = new RegistarOperacaoController();
    }

    public void run() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Regista uma operação de Colheita:\n");

        System.out.print("Insira a data da operação de colheita (dd-mm-yyyy): ");
        String date = scanner.next();

        System.out.print("Insira o ID da Parcela: ");
        int parcelaID = scanner.nextInt();

        System.out.print("Insira o ID da Cultura: ");
        int culturaID = scanner.nextInt();

        System.out.print("Insira o ID da Planta: ");
        int plantaID = scanner.nextInt();

        System.out.print("Insira o nome do Produto: ");
        String produto = scanner.next();

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

        String tipoOperacao = "Colheita";
        String unidade = "kg";

        int state = controller.colheitaRegister(date, tipoOperacao, quantidade, unidade, culturaID, plantaID, parcelaID, produto);
        if (state == 0)
            System.out.println("\nOperação não registada.");
        else if (state == 1) {
            System.out.println("\nOperação registada.");
        }else if (state == 2){
            System.out.println("\nTempo de espera excedido. Tente mais tarde.");
        }
    }
}
