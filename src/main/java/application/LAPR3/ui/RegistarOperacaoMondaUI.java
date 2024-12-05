package application.LAPR3.ui;

import application.LAPR3.controller.RegistarOperacaoController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class RegistarOperacaoMondaUI {

    private RegistarOperacaoController controller;

    public RegistarOperacaoMondaUI() {
        controller = new RegistarOperacaoController();
    }

    public void run() {
        System.out.println("Regista uma operação de monda");

        Scanner scanner = new Scanner(System.in);

        //System.out.print("ID Operação: ");
        int operacaoId = 0;

        //ID_OperacaoAgricola, Tipo_OperaçãoDesignação, Quantidade, Data, Unidade
        System.out.print("ID Parcela: ");
        int parcelaID = scanner.nextInt();

        System.out.print("ID Cultura: ");
        int culturaID = scanner.nextInt();

        System.out.print("ID Planta: ");
        int plantaID = scanner.nextInt();


        System.out.print("Quantidade: ");
        int quantidade = scanner.nextInt();

        System.out.print("Data (dd-mm-yyyy): ");
        String date = scanner.next();
        //SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        //Date data = formatter.parse(date);

        String tipoOperacao = "monda";
        String unidade = "ha";



        int state = controller.mondaRegister(operacaoId, tipoOperacao, quantidade, date, unidade, culturaID, plantaID, parcelaID);

        if (state == 0)
            System.out.println("\nOperação não registada.");
        else if (state == 1) {
            System.out.println("\nOperação registada.");
        }else if (state == 2){
            System.out.println("\nTempo de espera excedido. Tente mais tarde.");
        }
    }
}
