package application.ESINF.ui;

import application.ESINF.functions.US_EI01_GraphBuilder;
import application.ESINF.functions.US_EI03_VehicleRouteAutonomy;
import application.ESINF.functions.US_EI08_FindDeliveryCircuit;
import org.apache.commons.math3.util.Pair;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class VehicleRouteAutonomyUI implements Runnable {

    public void run() {

        US_EI03_VehicleRouteAutonomy us_ei03_vehicleRouteAutonomy = new US_EI03_VehicleRouteAutonomy();
        Scanner scanner = new Scanner(System.in);
        int autonomia = 0;

        // Loop até que o usuário insira um valor válido
        while (true) {
            System.out.print("Insira a autonomia do veículo (metros): ");

            // Verifica se o valor inserido é um número inteiro
            if (scanner.hasNextInt()) {
                autonomia = scanner.nextInt();
                break; // Sai do loop se um valor válido for inserido
            } else {
                System.out.println("Por favor, insira um valor válido para a autonomia.");
                scanner.next(); // Limpa o buffer do scanner
            }
        }


        Map<String, Object> result = us_ei03_vehicleRouteAutonomy.shortestPathBetweenTheTwoLocals(US_EI01_GraphBuilder.getInstance().getDistribuicao(),autonomia);

        System.out.println("Shortest Path Information:");
        System.out.println("==========================");
        System.out.println("Autonomia do veiculo: " +autonomia );
        System.out.println("Source Vertex: " + result.get("sourceVertex"));
        System.out.println("Destination Vertex: " + result.get("destinationVertex"));
        System.out.println("Number of Loadings: " + result.get("numberOfLoadings"));

        // Print loading locations
        System.out.println("Loading Locations:");
        @SuppressWarnings("unchecked")
        List<String> loadingLocations = (List<String>) result.get("loadingLocations");
        for (Object location : loadingLocations) {
            System.out.println("- " + location);
        }

        // Print path distances
        System.out.println("Path Distances:");
        @SuppressWarnings("unchecked")
        List<Pair<String, String>> pathDistances = (List<Pair<String, String>>) result.get("pathDistances");
        for (Pair<String, String> distance : pathDistances) {
            System.out.println("- From " + distance.getFirst() + " to " + distance.getSecond());
        }
    }

}
