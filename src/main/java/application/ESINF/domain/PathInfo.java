package application.ESINF.domain;

import java.util.Map;

public class PathInfo {
    private int totalDistance;
    private double totalTime;
    private Map<LocalityPair, Integer> individualDistances;  // Mapa de pares de vértices para distâncias

    public PathInfo(int totalDistance, double totalTime, Map<LocalityPair, Integer> individualDistances) {
        this.totalDistance = totalDistance;
        this.totalTime = totalTime;
        this.individualDistances = individualDistances;
    }

    public int getTotalDistance() {
        return totalDistance;
    }

    public double getTotalTime() {
        return totalTime;
    }

    public Map<LocalityPair, Integer> getIndividualDistances() {
        return individualDistances;
    }
}
