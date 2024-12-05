package application.ESINF.functions;

import application.ESINF.domain.Localidades;
import application.ESINF.graph.Algorithms;
import application.ESINF.graph.Edge;
import application.ESINF.graph.map.MapGraph;
import org.apache.commons.math3.util.Pair;

import java.util.*;
import java.util.function.BinaryOperator;

public class US_EI03_VehicleRouteAutonomy {

    public Map<String, Object> shortestPathBetweenTheTwoLocals(MapGraph<Localidades, Integer> distancesGraph, Integer autonomia) {

        // Run Dijkstra's algorithm to find the shortest path
        LinkedList<Localidades> shortestPath = new LinkedList<>();
        Pair<Localidades, Localidades> pair = findTwoFarthestHubs(distancesGraph,shortestPath);


        String sourceVertex =pair.getFirst().getNumId();
        String destinationVertex = pair.getSecond().getNumId();
        // Calculate the distances between locations in the path
        List<Pair<String, String>> pathDistances = calculatePathDistances(shortestPath, distancesGraph);

        // Calculate the number of loadings (assuming it's the number of vertices in the path - 2)
        List<Localidades> numberOfLoadings = calculateLoadingHubs(shortestPath, distancesGraph, autonomia);


        // Construct the result map
        Map<String, Object> result = new HashMap<>();
        result.put("sourceVertex", sourceVertex);
        result.put("loadingLocations",numberOfLoadings); // Locais de passagem excluindo origem e destino
        result.put("pathDistances", pathDistances);
        result.put("destinationVertex", destinationVertex);
        result.put("numberOfLoadings", numberOfLoadings.size());

        return result;
    }

    public List<Localidades> calculateLoadingHubs(List<Localidades> path, MapGraph<Localidades, Integer> distancesGraph, Integer autonomia) {
        List<Localidades> loadingLocalidades = new ArrayList<>();
        int currentAutonomy = autonomia;

        for (int i = 0; i < path.size() - 1; i++) {
            Localidades vertexA = path.get(i);
            Localidades vertexB = path.get(i + 1);

            int distance = 0;
            Edge<Localidades, Integer> edge = distancesGraph.edge(vertexA, vertexB);
            if (edge != null) {
                distance = edge.getWeight();
            }

            // Verifica se a autonomia é suficiente para a distância entre os vértices
            if (distance > currentAutonomy) {
                // Se não for, é necessário um carregamento
                loadingLocalidades.add(vertexA);
                // Resetamos a autonomia para a autonomia máxima após o carregamento
                currentAutonomy = autonomia;
            }

            // Reduzimos a autonomia com a distância percorrida
            currentAutonomy -= distance;
        }

        return loadingLocalidades;
    }

    private List<Pair<String, String>> calculatePathDistances(List<Localidades> path, MapGraph<Localidades, Integer> distancesGraph) {
        List<Pair<String, String>> pathDistances = new LinkedList<>();
        int totalPathDistance = 0;

        for (int i = 0; i < path.size() - 1; i++) {
            Localidades vertexA = path.get(i);
            Localidades vertexB = path.get(i + 1);

            int distance = 0;
            Edge<Localidades, Integer> edge = distancesGraph.edge(vertexA, vertexB);
            if (edge != null) {
                distance = edge.getWeight();
            }

            pathDistances.add(new Pair<>(vertexA.getNumId(), vertexB.getNumId()));
            totalPathDistance += distance;
        }

        // Return the total path distance directly
        pathDistances.add(new Pair<>("totalDistance", String.valueOf(totalPathDistance)));
        return pathDistances;
    }


    public static Pair<Localidades, Localidades> findTwoFarthestHubs(MapGraph<Localidades, Integer> g, LinkedList<Localidades> shortestPath) {

        // Inicializa a variável para armazenar a maior distância encontrada
        Integer maxDistance = 0;

        // Inicializa as variáveis para armazenar os dois hubs mais afastados
        Localidades farthestLocalidades1 = null;
        Localidades farthestLocalidades2 = null;

        // Percorre todos os pares de hubs e calcula as distâncias
        for (Localidades localidades1 : g.vertices()) {
            for (Localidades localidades2 : g.vertices()) {
                if (!localidades1.equals(localidades2)) {
                    LinkedList<Localidades> currentShortestPath = new LinkedList<>();
                    Integer distance = Algorithms.shortestPath(g, localidades1, localidades2, Comparator.naturalOrder(), Integer::sum, 0, currentShortestPath);

                    // Atualiza os hubs mais afastados se a distância for maior que a atual
                    if (distance > maxDistance) {
                        maxDistance = distance;
                        farthestLocalidades1 = localidades1;
                        farthestLocalidades2 = localidades2;
                        shortestPath.clear();
                        shortestPath.addAll(currentShortestPath);
                    }
                }
            }
        }

        return new Pair<>(farthestLocalidades1, farthestLocalidades2);
    }




    public static Integer shortestPath(MapGraph<Localidades, Integer> g, String vOrig, String vDest,
                                       Comparator<Integer> ce, BinaryOperator<Integer> sum, Integer zero,
                                       LinkedList<Localidades> shortPath) {

        Localidades localidadesOrig = findHubByNumId(g,vOrig) /* Obtain the Hub object for vOrig */;
        Localidades localidadesDest = findHubByNumId(g,vDest)/* Obtain the Hub object for vDest */;

        if (localidadesOrig == null || localidadesDest == null) {
            return null;
        }

        LinkedList<Localidades> localidadesShortPath = new LinkedList<>();
        Integer totalDistance = Algorithms.shortestPath(g, localidadesOrig, localidadesDest, ce, sum, zero, localidadesShortPath);

        // Convert Hub objects to String representation for the result
        for (Localidades localidades : localidadesShortPath) {
            shortPath.add(localidades);
        }

        return totalDistance;
    }


    // Helper method to find a Hub by its numId
    private static Localidades findHubByNumId(MapGraph<Localidades, Integer> g, String numId) {
        for (Localidades localidades : g.vertices()) {
            if (localidades.getNumId().equals(numId)) {
                return localidades;
            }
        }
        return null;
    }

}
