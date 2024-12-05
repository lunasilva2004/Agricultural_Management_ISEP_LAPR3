package application;

import application.LAPR3.ui.menuUI.MenuLaprUI;

import java.util.Scanner;

public class MenuUI {
    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("WELCOME!");
        System.out.println("CHOOSE THE MENU YOU WANT TO ACCESS:");
        System.out.println("1. MENU LAPR 3");
        System.out.println("2. MENU ESINF");
        System.out.println("0. EXIT");
        System.out.println("Enter your choice:");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                MenuLaprUI lapr = new MenuLaprUI();
                lapr.run();
                break;
            case 2:
                application.ESINF.ui.menuUI.MenuUI esinf = new application.ESINF.ui.menuUI.MenuUI();
                esinf.run();
                break;
            case 0:
                return;
            default:
                System.out.println("Opção inválida. Insira uma opção válida!\n");
                run();
        }
        scanner.close();
    }
}
