package application.ESINF.functions;

import application.ESINF.domain.Localidades;
import application.ESINF.graph.Algorithms;
import application.ESINF.graph.Edge;
import application.ESINF.graph.map.MapGraph;

import java.util.*;

/**
 * This class is responsible for organizing the vertices (localidades) of a graph into clusters based on their proximity to hubs.
 * It contains methods to get a list of hubs and to create clusters using either Floyd-Warshall's or Dijkstra's algorithm.
 */
public class US_EI09_HubNClusters {

    // An instance of the US_EI02_IdealVerticesForNHubs class
    private static final US_EI02_IdealVerticesForNHubs usei02 = new US_EI02_IdealVerticesForNHubs();
    // A graph of localidades and their distances
    private static final MapGraph<Localidades, Integer> graphMod = usei02.getMapGraph();

    /**
     * This method returns a list of hubs in the graph.
     * @return a list of hubs
     */
    public List<Localidades> getHubs() {
        List<Localidades> result = new ArrayList<>();
        for (Localidades local : graphMod.vertices()) {
            if (local.isHub()) {
                result.add(local);
            }
        }
        return result;
    }

    /**
     * Creates clusters of vertices based on their proximity to the hubs.
     * It iteratively removes the edge with the highest number of shortest paths until each cluster is isolated.
     *
     * @return a map where the keys are hubs and the values are lists of vertices that belong to the cluster of that hub
     */
    public Map<Localidades, List<Localidades>> getClustersFW() {
        Map<Localidades, List<Localidades>> clusters = new HashMap<>();
        List<Localidades> hubs = getHubs();
        MapGraph<Localidades, Integer> graphCopy = new MapGraph<>(graphMod);

        // Initialize clusters with empty lists for each hub
        for (Localidades hub : hubs) {
            clusters.put(hub, new ArrayList<>());
        }

        // Calculate the number of shortest paths for each edge
        Map<Edge<Localidades, Integer>, Integer> edgePathCounts = calculateEdgePathCounts(graphCopy);

        // Sort the edges in descending order based on the number of shortest paths
        List<Edge<Localidades, Integer>> sortedEdges = new ArrayList<>(edgePathCounts.keySet());
        sortedEdges.sort(Comparator.comparing(edgePathCounts::get).reversed());

        // Iteratively remove the edge with the highest number of shortest paths
        for (Edge<Localidades, Integer> edge : sortedEdges) {
            graphCopy.removeEdge(edge.getVOrig(), edge.getVDest());

            // Check if the clusters are isolated
            if (areClustersIsolated(graphCopy, clusters)) {
                break;
            }
        }

        // Assign each localidade to the hub it's currently connected to
        for (Localidades localidade : graphCopy.vertices()) {
            if (!localidade.isHub()) {
                for (Localidades hub : hubs) {
                    if (graphCopy.getEdge(hub, localidade) != null) {
                        clusters.get(hub).add(localidade);
                        break;
                    }
                }
            }
        }

        return clusters;
    }

    /**
     * Calculates the number of shortest paths that pass through each edge in the graph.
     *
     * @param graph the graph to calculate the number of shortest paths for each edge
     * @return a map where the keys are edges and the values are the number of shortest paths that pass through each edge
     */
    private Map<Edge<Localidades, Integer>, Integer> calculateEdgePathCounts(MapGraph<Localidades, Integer> graph) {
        int n = graph.numVertices();
        int[][] pathCount = new int[n][n];

        // Initialize pathCount
        for (int i = 0; i < n; i++) {
            pathCount[i][i] = 1;
            for (Edge<Localidades, Integer> edge : graph.outgoingEdges(graph.vertices().get(i))) {
                int j = graph.vertices().indexOf(edge.getVDest());
                pathCount[i][j] = 1;
            }
        }

        // Calculate the number of shortest paths
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    pathCount[i][j] += pathCount[i][k] * pathCount[k][j];
                }
            }
        }

        // Count the number of shortest paths for each edge
        Map<Edge<Localidades, Integer>, Integer> edgePathCounts = new HashMap<>();
        for (Edge<Localidades, Integer> edge : graph.edges()) {
            int u = graph.vertices().indexOf(edge.getVOrig());
            int v = graph.vertices().indexOf(edge.getVDest());
            edgePathCounts.put(edge, pathCount[u][v]);
        }

        return edgePathCounts;
    }

    /**
     * Checks if the clusters are isolated.
     *
     * @param graph the graph to check if the clusters are isolated
     * @param clusters the clusters to check if they are isolated
     * @return true if the clusters are isolated, false otherwise
     */
    private boolean areClustersIsolated(MapGraph<Localidades, Integer> graph, Map<Localidades, List<Localidades>> clusters) {
        for (Edge<Localidades, Integer> edge : graph.edges()) {
            Localidades u = edge.getVOrig();
            Localidades v = edge.getVDest();

            // Find the clusters of u and v
            List<Localidades> clusterU = null;
            List<Localidades> clusterV = null;
            for (Map.Entry<Localidades, List<Localidades>> entry : clusters.entrySet()) {
                if (entry.getValue().contains(u)) {
                    clusterU = entry.getValue();
                }
                if (entry.getValue().contains(v)) {
                    clusterV = entry.getValue();
                }
            }

            // If u and v belong to different clusters and there is an edge between them, return false
            if (clusterU != clusterV && graph.getEdge(u, v) != null) {
                return false;
            }
        }

        // If no such pair of vertices is found, return true
        return true;
    }

    /**
     * This method organizes the vertices (localidades) of a graph into clusters based on their proximity to hubs.
     * It uses Dijkstra's algorithm to find the shortest path from each localidade to all hubs and assigns each localidade to the hub that provides the shortest path.
     * @return a map where each key is a hub and each value is a list of localidades that belong to the cluster of that hub
     */
    public Map<Localidades, List<Localidades>> getClustersD() {
        Map<Localidades, List<Localidades>> clusters = new HashMap<>();
        List<Localidades> hubs = getHubs();
        MapGraph<Localidades, Integer> graphCopy = new MapGraph<>(graphMod);

        // Initialize clusters with empty lists
        for (Localidades hub : hubs) {
            clusters.put(hub, new ArrayList<>());
        }

        // Assign each localidade to the closest hub
        for (Localidades localidade : graphCopy.vertices()) {
            if (!localidade.isHub()) {
                Localidades closestHub = null;
                int shortestDistance = Integer.MAX_VALUE;

                for (Localidades hub : hubs) {
                    LinkedList<Localidades> path = new LinkedList<>();
                    int distance = Algorithms.shortestPath(graphCopy, hub, localidade, Comparator.naturalOrder(), Integer::sum, 0, path);
                    if (distance < shortestDistance) {
                        closestHub = hub;
                        shortestDistance = distance;
                    }
                }

                clusters.get(closestHub).add(localidade);
            }
        }

        return clusters;
    }


}
