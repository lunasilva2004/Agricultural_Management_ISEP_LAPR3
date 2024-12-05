package application.ESINF.ui;

import application.ESINF.domain.Localidades;
import application.ESINF.functions.US_EI01_GraphBuilder;
import application.ESINF.functions.US_EI02_IdealVerticesForNHubs;
import application.ESINF.graph.map.MapGraph;

import java.util.*;


public class IdealVerticesForNHubsUI implements Runnable {

    // Instance of the US_EI02_IdealVerticesForNHubs class for performing calculations
    private final US_EI02_IdealVerticesForNHubs usei02 = new US_EI02_IdealVerticesForNHubs();

    // Graph representing the distribution of hubs
    private final MapGraph<Localidades, Integer> graph = US_EI01_GraphBuilder.getInstance().getDistribuicao();

    /**
     * The run method is invoked when the IdealVerticesForNHubsUI instance is executed as a thread.
     * It presents a menu to the user and processes their choices until the user chooses to exit.
     */
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("===== Ideal Vertices for N Hubs Menu =====");
            System.out.println("1. Calculate and display top N hubs based on influence");
            System.out.println("2. Calculate and display top N hubs based on proximity");
            System.out.println("3. Calculate and display top N hubs based on centrality");
            System.out.println("4. Calculate and display combined top N hubs");
            System.out.println("5. Test setHubs method");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1 -> displayTopNHubs(usei02.calculateInfluence(graph), "Influence", false);
                case 2 -> displayTopNHubs(usei02.calculateProximity(graph), "Proximity", true);
                case 3 -> displayTopNHubs(usei02.calculateCentrality(graph), "Centrality", false);
                case 4 -> displayTopNCombined();
                case 5 -> testSetHubs();
                case 0 -> {
                    return;
                }

                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayTopNHubs(Map<Localidades, Integer> topNHubs, String criteria, boolean isAscending) {
        System.out.print("Enter the value of N: ");
        int n = new Scanner(System.in).nextInt();

        Map<Localidades, Integer> result = usei02.getTopNHubsSeparate(topNHubs, n, isAscending);

        System.out.println("Top " + n + " Hubs based on " + criteria + ":");
        for (Map.Entry<Localidades, Integer> entry : result.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println();
    }

    private void displayTopNCombined() {
        Map<Localidades, Integer> influence = usei02.calculateInfluence(graph);
        Map<Localidades, Integer> proximity = usei02.calculateProximity(graph);
        Map<Localidades, Integer> centrality = usei02.calculateCentrality(graph);

        Map<Localidades, List<Integer>> combinedMap = new HashMap<>();
        for (Localidades localidades : graph.vertices()) {
            List<Integer> values = new ArrayList<>();
            values.add(centrality.get(localidades));
            values.add(influence.get(localidades));
            values.add(proximity.get(localidades));
            combinedMap.put(localidades, values);
        }

        System.out.print("Enter the value of N: ");
        int n = new Scanner(System.in).nextInt();

        Map<Localidades, List<Integer>> result = usei02.getTopNMap(combinedMap, n);

        System.out.println("Top " + n + " Combined Hubs:");
        for (Map.Entry<Localidades, List<Integer>> entry : result.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println();
    }


    private void testSetHubs() {
        System.out.print("Enter the value of N for top hubs: ");
        int n = new Scanner(System.in).nextInt();

        Map<Localidades, Integer> influence = usei02.calculateInfluence(graph);
        Map<Localidades, Integer> proximity = usei02.calculateProximity(graph);
        Map<Localidades, Integer> centrality = usei02.calculateCentrality(graph);

        Map<Localidades, List<Integer>> combinedMap = new HashMap<>();
        for (Localidades localidades : graph.vertices()) {
            List<Integer> values = new ArrayList<>();
            values.add(centrality.get(localidades));
            values.add(influence.get(localidades));
            values.add(proximity.get(localidades));
            combinedMap.put(localidades, values);
        }

        Map<Localidades, List<Integer>> topNMap = usei02.getTopNMap(combinedMap, n);

        // Call the setHubs method
        usei02.setHubs(topNMap, n);

        // Print the MapGraph with hubs
        System.out.println("MapGraph with Hubs:");
        printMapGraphWithHubs(graph);
    }

    private void printMapGraphWithHubs(MapGraph<Localidades, Integer> graph) {
        for (Localidades vertex : graph.vertices()) {
            // Check if the vertex is a hub before printing
            if (graph.vertex(p -> p.equals(vertex)).isHub()) {
                System.out.print(vertex + " (Hub: " + graph.vertex(p -> p.equals(vertex)).isHub() + ") -> ");
                for (Localidades neighbor : graph.adjVertices(vertex)) {
                    System.out.print(neighbor + " ");
                }
                System.out.println();
            }
        }
        System.out.println();
    }


}
