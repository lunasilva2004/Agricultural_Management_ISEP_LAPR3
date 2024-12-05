package application.ESINF.functions;

import application.ESINF.domain.Localidades;
import application.ESINF.domain.LocalityPair;
import application.ESINF.domain.PathInfo;
import application.ESINF.graph.Algorithms;
import application.ESINF.graph.map.MapGraph;

import java.util.*;

/**
 * The type Us ei 06 possible paths between points.
 */
public class US_EI06_PossiblePathsBetweenPoints {
    /**
     * Gets paths between two points.
     *
     * @param graph                  the graph
     * @param startingLocalidades    the starting localidades
     * @param destinationLocalidades the destination localidades
     * @param vehicleAutonomy        the vehicle autonomy
     * @param tripAverageSpeed       the trip average speed
     * @return the paths between two points
     */
    public static TreeMap<LinkedList<Localidades>, PathInfo> getPathsBetweenTwoPoints(MapGraph<Localidades, Integer> graph, Localidades startingLocalidades, Localidades destinationLocalidades, Integer vehicleAutonomy, double tripAverageSpeed) {
        TreeMap<LinkedList<Localidades>, PathInfo> pathsWithCostAndTime = new TreeMap<>(Comparator.comparingInt(o -> calculatePathCost(graph, o)));

        ArrayList<LinkedList<Localidades>> allPaths = Algorithms.dfsAlgorithm(graph, startingLocalidades, destinationLocalidades, vehicleAutonomy);

        for (LinkedList<Localidades> fullPath : allPaths) {
            int cost = calculatePathCost(graph, fullPath);

                double totalTime = calculateTotalTime(graph, fullPath, tripAverageSpeed);
                Map<LocalityPair, Integer> individualDistances = calculateIndividualDistances(graph, fullPath);
                PathInfo pathInfo = new PathInfo(cost, totalTime, individualDistances);
                pathsWithCostAndTime.put(fullPath, pathInfo);
        }
        return pathsWithCostAndTime;
    }

    private static int calculatePathCost(MapGraph<Localidades, Integer> graph, LinkedList<Localidades> path) {
        int cost = 0;
        Iterator<Localidades> iterator = path.iterator();
        Localidades currentLocalidades = iterator.next();

        while (iterator.hasNext()) {
            Localidades nextLocalidades = iterator.next();
            cost += graph.edge(currentLocalidades, nextLocalidades).getWeight();
            currentLocalidades = nextLocalidades;
        }
        return cost;
    }

    private static double calculateTotalTime(MapGraph<Localidades, Integer> graph, LinkedList<Localidades> path, double averageSpeed) {
        double totalTime = 0.0;
        Iterator<Localidades> iterator = path.iterator();
        Localidades currentLocalidades = iterator.next();

        while (iterator.hasNext()) {
            Localidades nextLocalidades = iterator.next();
            double distance = graph.edge(currentLocalidades, nextLocalidades).getWeight();
            double time = distance / averageSpeed;      // Tempo = Distância / Velocidade Média
            totalTime += time;
            currentLocalidades = nextLocalidades;
        }
        return totalTime;
    }

    private static Map<LocalityPair, Integer> calculateIndividualDistances(MapGraph<Localidades, Integer> graph, LinkedList<Localidades> path) {
        Map<LocalityPair, Integer> individualDistances = new HashMap<>();
        Iterator<Localidades> iterator = path.iterator();
        Localidades currentLocalidades = iterator.next();

        while (iterator.hasNext()) {
            Localidades nextLocalidades = iterator.next();
            int distance = graph.edge(currentLocalidades, nextLocalidades).getWeight();
            individualDistances.put(new LocalityPair(currentLocalidades, nextLocalidades), distance);
            currentLocalidades = nextLocalidades;
        }

        return individualDistances;
    }
}
