package application.ESINF.functions;

import application.ESINF.domain.Localidades;
import application.ESINF.graph.Edge;
import application.ESINF.graph.map.MapGraph;

import java.util.ArrayList;
import java.util.List;

import static application.ESINF.graph.Algorithms.minimumSpanningTree;

public class US_EI04_MinimumSpanningTree {


    public static MapGraph<Localidades, Integer> getMinimumSpanningTree(MapGraph<Localidades, Integer> graph) {
        if (graph == null) {
            return null;
        } else {
            return minimumSpanningTree(graph, Integer::compare, Integer::sum, 0);
        }
    }

    public static int calculateTotalHeight(MapGraph<Localidades, Integer> graph) {
        int totalHeight = 0;
        for (Edge<Localidades, Integer> edge : graph.edges()) {
            totalHeight += edge.getWeight();
        }
        return totalHeight;
    }

    public static List<Localidades> getHubsInGraph(MapGraph<Localidades, Integer> graph) {
        List<Localidades> localidades = new ArrayList<>();
        if (graph != null) {
            for (Localidades localidade : graph.vertices()) {
                localidades.add(localidade);
            }
        }
        return localidades;
    }
}
