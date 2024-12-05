package application.ESINF.controller;

import application.ESINF.domain.Localidades;
import application.ESINF.domain.PathInfo;
import application.ESINF.functions.US_EI01_GraphBuilder;
import application.ESINF.functions.US_EI06_PossiblePathsBetweenPoints;
import application.ESINF.graph.CommonGraph;

import java.util.LinkedList;
import java.util.TreeMap;

/**
 * The type Possible paths between points controller.
 */
public class PossiblePathsBetweenPointsController {
    private final US_EI01_GraphBuilder graphBuilder;

    /**
     * Instantiates a new Possible paths between points controller.
     */
    public PossiblePathsBetweenPointsController(){ graphBuilder = US_EI01_GraphBuilder.getInstance();}

    /**
     * Gets hub by num id.
     *
     * @param graph the graph
     * @param numId the num id
     * @return the hub by num id
     */
    public Localidades getHubByNumId(CommonGraph<Localidades, ?> graph, String numId) {
        for (Localidades localidades : graph.vertices()) {
            if (localidades.getNumId().equals(numId)) {
                return localidades;
            }
        }
        return null;
    }

    /**
     * Calculate paths between two points tree map.
     *
     * @param initialHub       the initial hub
     * @param destinationHub   the destination hub
     * @param vehicleAutonomy  the vehicle autonomy
     * @param tripAverageSpeed the trip average speed
     * @return the tree map
     */
    public TreeMap<LinkedList<Localidades>, PathInfo> calculatePathsBetweenTwoPoints(String initialHub, String destinationHub, Integer vehicleAutonomy, double tripAverageSpeed){
        return US_EI06_PossiblePathsBetweenPoints.getPathsBetweenTwoPoints(graphBuilder.getDistribuicao(),getHubByNumId(graphBuilder.getDistribuicao(), initialHub), getHubByNumId(graphBuilder.getDistribuicao(), destinationHub), vehicleAutonomy, tripAverageSpeed);
    }
}
