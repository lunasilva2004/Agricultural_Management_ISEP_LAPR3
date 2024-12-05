package application.ESINF.ui;

import application.ESINF.controller.MinimumSpanningTreeController;
import application.ESINF.domain.Localidades;
import application.ESINF.functions.US_EI04_MinimumSpanningTree;
import application.ESINF.graph.Edge;
import application.ESINF.graph.map.MapGraph;

public class MinimumSpanningTreeUI implements Runnable {
    private MinimumSpanningTreeController controller;
    public MinimumSpanningTreeUI(){controller = new MinimumSpanningTreeController();}
    @Override
    public void run() {
        MapGraph<Localidades, Integer> minimumSpanning = controller.calculateMinimumSpanningTree();
        printMinimumSpanningTree(minimumSpanning);
        printHeight(minimumSpanning);
    }

    private void printHeight(MapGraph<Localidades, Integer> minimumSpanning) {
        System.out.println("Total distance: " + US_EI04_MinimumSpanningTree.calculateTotalHeight(minimumSpanning) + "m");
    }

    public void printMinimumSpanningTree(MapGraph<Localidades, Integer> minimumSpanning) {
        if (minimumSpanning == null) {
            System.out.println("The minimum spanning tree is null. Make sure you initialize it correctly.");
        } else {
            System.out.println("Minimum Spanning Tree:");
            System.out.printf("%-10s%-20s%s%n", "Origin", "--(Distance(m))--", "Destination");

            for (Edge<Localidades, Integer> edge : minimumSpanning.edges()) {
                Localidades vOrig = edge.getVOrig();
                Localidades vDest = edge.getVDest();
                String hubOrigin = vOrig.getNumId();
                String hubDestination = vDest.getNumId();
                Integer weight = edge.getWeight();

                System.out.printf("%-10s%-20s%s%n", hubOrigin, "--(" + weight + ")--", hubDestination);
            }

        }
    }
}
