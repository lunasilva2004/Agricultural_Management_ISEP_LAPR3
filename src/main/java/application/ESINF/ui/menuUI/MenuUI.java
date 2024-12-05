package application.ESINF.ui.menuUI;

import application.ESINF.ui.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class MenuUI implements Runnable {
    public MenuUI() {
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        Map<Integer, String> optionsMenu = new HashMap<>();
        optionsMenu.put(1, "Initialize the graph with the data from the files");
        optionsMenu.put(2, "Determine the ideal vertices for the location of N hubs in order to optimize the distribution network.");
        optionsMenu.put(3, "Vehicle Route Autonomy");
        optionsMenu.put(4, "Minimum spanning tree in weighted graph");
        optionsMenu.put(5, "Not implemented yet");
        optionsMenu.put(6, "Paths between two points");
        optionsMenu.put(7, "Delivery Rout Calculator");
        optionsMenu.put(8, "Find Delivery Circuit");
        optionsMenu.put(9, "Hub and N Clusters");
        optionsMenu.put(11, "Load operation hours from file");

        while (true) {
            System.out.println("=======================================================================");
            System.out.println("                       GFH - Logistic Operator                            ");
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
                    new InitializeGraphUI().run();
                    break;
                case "2":
                    new IdealVerticesForNHubsUI().run();
                    break;
                case "3":
                    new VehicleRouteAutonomyUI().run();
                    break;
                case "4":
                    new MinimumSpanningTreeUI().run();
                    break;
                case "5":
                    System.out.println("Not implemented yet");
                    break;
                case "6":
                    new PossiblePathsBetweenPointsUI().run();
                    break;
                case "7":
                    new DeliveryRouteCalculatorUI().run();
                    break;
                case "8":
                    new FindDeliveryCircuitUI().run();
                    break;
                case "9":
                    new HubNClustersUI().run();
                    break;
                case "11":
                    try {
                        new LoadOperatingHoursUI().run();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "0":
                    System.out.println("Exiting the program.");
                    return;
                default:
                    System.out.println("\nInvalid option. Please try again.\n");

            }
        }
    }
}