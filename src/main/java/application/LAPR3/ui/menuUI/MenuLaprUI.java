package application.LAPR3.ui.menuUI;

import application.LAPR3.ui.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class MenuLaprUI implements Runnable {
    public MenuLaprUI() {
    }
    public void run()
    {
        //executeScheduledRegistrations();    //TODO: INICIA O SCHEDULER PARA REGISTAR A REGA EM BACKGROUND

        Scanner scanner = new Scanner(System.in);

        Map<Integer, String> optionsMenu = new HashMap<>();
        optionsMenu.put(1, "Check if the irrigation program is running now");
        optionsMenu.put(2, "Check if the irrigation program is running at a given moment");
        optionsMenu.put(3, "Generate thirty days irrigation program started now ");
        optionsMenu.put(4, "Generate thirty days irrigation program started at specific date ");
        optionsMenu.put(5, "Import data from legacy file to SQL script");
        optionsMenu.put(6, "Database Connection Test");
        optionsMenu.put(7, "US LP04 - Registar Operação de Semeadura");
        optionsMenu.put(8, "US LP05 - Registar Operação de Monda");
        optionsMenu.put(9, "US LP06 - Registar Operação de Colheita");
        optionsMenu.put(10,"US LP08 - Registar uma operação de poda");
        optionsMenu.put(11,"US BD31 - Registar uma receita de fertirrega");
        optionsMenu.put(12,"US BD32 - Registar uma operação de rega");
        optionsMenu.put(13, "Forçar Registo de Operação de Rega do Sistema de Irrigação");


        while (true) {
            System.out.println("=======================================================================");
            System.out.println("                       Irrigation Programmer                           ");
            System.out.println("=======================================================================");

            for (Map.Entry<Integer, String> entry : optionsMenu.entrySet()) {
                System.out.println(entry.getKey() + " - " + entry.getValue());
            }
            System.out.println("0 - Exit");
            System.out.println("=======================================================================");
            System.out.print("Enter Option: \n");

            String op = scanner.nextLine();

            switch (op) {
                case "1":
                    new IsIrrigationRunningNowUI().run();
                    break;
                case "2":
                    new IsIrrigationRunningAtSpecificTimeUI().run();
                    break;
                case "3":
                    new ThirtyDaysIrrigationProgramFromNowUI().run();
                    break;
                case "4":
                    new ThirtyDaysIrrigationProgramWithStartDateUI().run();
                    break;
                case "5":
                    new LegacySystemUI().run();
                    break;
                case "6":
                    new DatabaseConnectionTestUI().run();
                    break;
                case "7":
                    new RegistarOperacaoSemeaduraUI().run();
                    break;
                case "8":
                    new RegistarOperacaoMondaUI().run();
                    break;
                case "9":
                    new RegistarOperacaoColheitaUI().run();
                    break;
                case "10":
                    new RegistarOperacaoUI().run();
                    break;
                case "11":
                    new RegistarReceitaUI().run();
                    break;
                case "12":
                    new RegisterWateringUI().run();
                    break;
                case "13":
                    new RegisterWateringInDatabaseUI().run();
                    break;
                case "0":
                    System.out.println("Exiting the program.");
                    return;
                default:
                    System.out.println("\nInvalid option. Please try again.\n");

            }
        }
    }

    private void executeScheduledRegistrations() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        RegisterWateringInDatabaseUI wateringUI = new RegisterWateringInDatabaseUI();
        scheduler.scheduleAtFixedRate(wateringUI.scheduledRegistation(), 0, 60, TimeUnit.SECONDS);
    }
}