package application.ESINF.functions;

import application.ESINF.domain.Horario;
import application.ESINF.domain.Localidades;
import application.ESINF.graph.Algorithms;
import application.ESINF.graph.map.MapGraph;

import java.time.LocalTime;
import java.util.*;

public class US_EI02_IdealVerticesForNHubs {
    private final MapGraph<Localidades, Integer> graph = US_EI01_GraphBuilder.getInstance().getDistribuicao();
    private int n;


    public Map<Localidades, Integer> calculateInfluence(MapGraph<Localidades, Integer> graph) {
        Map<Localidades, Integer> influence = new HashMap<>();
        for (Localidades vertex : graph.vertices()) {
            influence.put(vertex, graph.outDegree(vertex));
        }

        return influence;
    }

    public Map<Localidades, Integer> calculateProximity(MapGraph<Localidades, Integer> graph) {
        Map<Localidades, Integer> proximity = new HashMap<>();

        for (Localidades vertex : graph.vertices()) {
            Integer proximityValue = calculateVertexProximity(graph, vertex);
            proximity.put(vertex, proximityValue);
        }

        return proximity;
    }

    private Integer calculateVertexProximity(MapGraph<Localidades, Integer> graph, Localidades vertex) {
        ArrayList<Integer> dists = new ArrayList<>();
        Algorithms.shortestPaths(graph, vertex, Comparator.naturalOrder(), Integer::sum, 0, new ArrayList<>(), dists);

        int proximitySum = 0;
        for (Integer dist : dists) {
            if (dist != null) {
                proximitySum += dist;
            }
        }

        return proximitySum;
    }

    public Map<Localidades, Integer> calculateCentrality(MapGraph<Localidades, Integer> graph) {
        Map<Localidades, Integer> centrality = Algorithms.betweennessCentrality(graph);

        return centrality;
    }

    public Map<Localidades, Integer> getTopNHubsSeparate(Map<Localidades, Integer> map, Integer n, boolean isProximity) {
        Map<Localidades, Integer> topNHubsMap = new LinkedHashMap<>();

        // Sort the map entries based on values
        List<Map.Entry<Localidades, Integer>> sortedEntries = new ArrayList<>(map.entrySet());

        if (isProximity) {
            sortedEntries.sort(Map.Entry.comparingByValue());
        } else {
            sortedEntries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        }

        int count = 0;
        for (Map.Entry<Localidades, Integer> entry : sortedEntries) {
            if (count < n) {
                topNHubsMap.put(entry.getKey(), entry.getValue());
                count++;
            } else {
                break;
            }
        }

        return topNHubsMap;
    }

    public Map<Localidades, List<Integer>> getTopNMap(Map<Localidades, List<Integer>> map, Integer n) {
        List<Map.Entry<Localidades, List<Integer>>> entries = new ArrayList<>(map.entrySet());
        entries.sort((entry1, entry2) -> {
            List<Integer> values1 = entry1.getValue();
            List<Integer> values2 = entry2.getValue();

            int compareCentrality = Integer.compare(values2.get(0), values1.get(0));
            if (compareCentrality != 0) {
                return compareCentrality;
            }

            int compareInfluence = Integer.compare(values2.get(1), values1.get(1));
            if (compareInfluence != 0) {
                return compareInfluence;
            }

            return Integer.compare(values1.get(2), values2.get(2));
        });

        Map<Localidades, List<Integer>> sortedFinalMap = new LinkedHashMap<>();
        for (Map.Entry<Localidades, List<Integer>> entry : entries) {
            sortedFinalMap.put(entry.getKey(), entry.getValue());
        }

        return getTopNHubs(sortedFinalMap, n);
    }

    public Map<Localidades, List<Integer>> getTopNHubs(Map<Localidades, List<Integer>> map, Integer n) {
        Map<Localidades, List<Integer>> topNHubsMap = new LinkedHashMap<>();

        int count = 0;
        for (Map.Entry<Localidades, List<Integer>> entry : map.entrySet()) {
            if (count < n) {
                topNHubsMap.put(entry.getKey(), entry.getValue());
                count++;
            } else {
                break;
            }
        }

        return topNHubsMap;
    }

    public void setHubs(Map<Localidades, List<Integer>> topHubs, int n) {
        US_EI01_GraphBuilder rede = US_EI01_GraphBuilder.getInstance();
        MapGraph<Localidades, Integer> graph = rede.getDistribuicao();

        getTopNHubs(topHubs, n);
        for (Localidades local : graph.vertices()) {
            if (topHubs.containsKey(local)) {
                graph.vertex(p -> p.equals(local)).setHub(true);
            }
        }
    }

    public MapGraph<Localidades, Integer> getMapGraph() {
        Map<Localidades, Integer> influence = calculateInfluence(graph);
        Map<Localidades, Integer> proximity = calculateProximity(graph);
        Map<Localidades, Integer> centrality = calculateCentrality(graph);

        Map<Localidades, List<Integer>> combinedMap = new HashMap<>();
        for (Localidades localidades : graph.vertices()) {
            List<Integer> values = new ArrayList<>();
            values.add(centrality.get(localidades));
            values.add(influence.get(localidades));
            values.add(proximity.get(localidades));
            combinedMap.put(localidades, values);
        }
        int n = 1;
        Map<Localidades, List<Integer>> topNMap = getTopNMap(combinedMap, n);
        setHubs(topNMap, n);
        escolherHorario(graph);

        return graph;
    }

    public Localidades getLocalByID(String hubId) {
        // Itera sobre todos os vértices (Hubs) no grafo
        for (Localidades localidades : getMapGraph().vertices()) {
            // Compara o ID do Hub atual com o ID desejado
            if (localidades.getNumId().equals(hubId)) {
                // Retorna o Hub se encontrar correspondência
                return localidades;
            }
        }
        // Retorna null se nenhum Hub correspondente for encontrado
        return null;
    }

    public Map<Localidades, Integer> getMapHubs() {
        getMapGraph();
        int i = 0;
        Map<Localidades, Integer> hubs = new HashMap<>();

        for (Localidades localidades : graph.vertices()) {
            if (localidades.isHub()) {
                hubs.put(localidades, i);
                i++;
            }
        }
        return hubs;
    }

    private void escolherHorario(MapGraph<Localidades, Integer> graph) {
        for (Localidades localidades : graph.vertices()) {
            String numId = localidades.getNumId();

            // Extrair o número da localidade (ignorando o prefixo "CT")
            int numeroLocalidade = Integer.parseInt(numId.substring(2));

            // Lógica para definir horários apenas se for um hub
            if (localidades.isHub()) {
                LocalTime horarioAbertura;
                LocalTime horarioFecho;

                // Aplicar lógica para definir os horários com base no número da localidade
                if (numeroLocalidade >= 1 && numeroLocalidade <= 105) {
                    horarioAbertura = LocalTime.of(9, 0);
                    horarioFecho = LocalTime.of(14, 0);
                } else if (numeroLocalidade >= 106 && numeroLocalidade <= 215) {
                    horarioAbertura = LocalTime.of(11, 0);
                    horarioFecho = LocalTime.of(16, 0);
                } else if (numeroLocalidade >= 216 && numeroLocalidade <= 324) {
                    horarioAbertura = LocalTime.of(12, 0);
                    horarioFecho = LocalTime.of(17, 0);
                } else {
                    // Adicione lógica adicional conforme necessário para outros casos
                    horarioAbertura = LocalTime.of(0, 0);
                    horarioFecho = LocalTime.of(0, 0);
                }

                // Criar Horario com os parâmetros fornecidos
                Horario horario = new Horario(horarioAbertura, horarioFecho);
                localidades.setHorario(horario);
            }
        }
    }
}

