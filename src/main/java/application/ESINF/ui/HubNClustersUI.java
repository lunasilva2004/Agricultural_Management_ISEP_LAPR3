package application.ESINF.ui;

import application.ESINF.domain.Localidades;
import application.ESINF.functions.US_EI09_HubNClusters;

import java.util.*;

public class HubNClustersUI implements Runnable {

    private final US_EI09_HubNClusters usei09 = new US_EI09_HubNClusters();

    @Override
    public void run() {
        printMenu();
    }

    private void printMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Choose an algorithm:");
            System.out.println("1. Floyd-Warshall");
            System.out.println("2. Dijkstra");
            System.out.println("3. Exit");
            System.out.print("Your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    printClusters(usei09.getClustersFW());
                    break;
                case 2:
                    printClusters(usei09.getClustersD());
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void printClusters(Map<Localidades, List<Localidades>> clusters) {
        for (Map.Entry<Localidades, List<Localidades>> entry : clusters.entrySet()) {
            Localidades hub = entry.getKey();
            List<Localidades> cluster = entry.getValue();

            System.out.println("Hub: " + hub.getNumId());

            List<String> clusterCTs = new ArrayList<>();
            for (Localidades localidade : cluster) {
                // Exclude the hub from the list of localidades in the cluster
                if (!localidade.isHub()) {
                    clusterCTs.add(localidade.getNumId());
                }
            }

            System.out.println("Cluster: " + clusterCTs);
        }
    }
}