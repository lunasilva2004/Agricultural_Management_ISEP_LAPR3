/**
 * Classe que implementa funcionalidades relacionadas à otimização de circuito de entrega.
 * Utiliza algoritmos de grafos e cálculos logísticos para encontrar rotas eficientes.
 */
package application.ESINF.functions;

import application.ESINF.domain.Localidades;
import application.ESINF.graph.Algorithms;
import application.ESINF.graph.Edge;
import application.ESINF.graph.Graph;
import application.ESINF.graph.map.MapGraph;

import java.util.*;

public class US_EI08_FindDeliveryCircuit {

    /**
     * Encontra um circuito de entrega otimizado, considerando diversos parâmetros logísticos.
     *
     * @param distancesGraph     Grafo de distâncias entre localidades.
     * @param nHubs              Número desejado de hubs a considerar.
     * @param velMedia           Velocidade média do veículo.
     * @param autonomia          Autonomia do veículo.
     * @param localOrigem        Local de origem da entrega.
     * @param tempoDeDescarga    Tempo de descarga em cada local.
     * @param tempoDeCarregamento Tempo de carregamento em cada local.
     * @return Mapa contendo informações sobre o circuito otimizado.
     */
    public Map<String, Object> findDeliveryCircuit(MapGraph<Localidades, Integer> distancesGraph, int nHubs, int velMedia, int autonomia, Localidades localOrigem, int tempoDeDescarga, int tempoDeCarregamento) {

        // Obtenção dos hubs mais relevantes
        List<Localidades> topNHubs = getTopHubs(distancesGraph, nHubs);

        // Inicialização de estruturas para rastreamento de informações
        List<Localidades> loadingLocations = new ArrayList<>();
        List<Integer> distanceBetweenLocals = new ArrayList<>();
        List<Integer> totalDistance = new ArrayList<>();
        List<Integer> tempoTotalDescargaIda = new ArrayList<>();
        tempoTotalDescargaIda.add(tempoDeDescarga);

        // Aplicação do algoritmo do Vizinho Mais Próximo para obter um caminho inicial
        LinkedList<Localidades> path = Algorithms.nearestNeighbor(distancesGraph, localOrigem, topNHubs, Comparator.naturalOrder(), autonomia, loadingLocations, distanceBetweenLocals, totalDistance, tempoTotalDescargaIda);

        // Preparação para o retorno
        Localidades localOrigemVolta = path.getLast();
        path.removeLast();
        LinkedList<Localidades> shortestPath = calcularCaminhoMaisCurto(distancesGraph, localOrigemVolta, localOrigem);
        path.addAll(shortestPath);
        List<Integer> tempoTotalDescargaVolta = new ArrayList<>();
        tempoTotalDescargaVolta.add(tempoDeDescarga);
        loadingLocations.addAll(calculateLoadingHubs(shortestPath, distancesGraph, autonomia, distanceBetweenLocals, totalDistance, tempoDeDescarga, tempoTotalDescargaVolta));

        // Cálculos finais
        int tempoDescargaTotal = tempoTotalDescargaVolta.get(0) + tempoTotalDescargaIda.get(0);
        int tempoPercurso = totalDistance.get(0) / velMedia;
        int tempoTotalCarregamento = loadingLocations.size() * tempoDeCarregamento;
        int tempoTotal = tempoPercurso + tempoTotalCarregamento + tempoDescargaTotal;
        loadingLocations.remove(loadingLocations.size() - 1);

        // Construção do mapa de resultados
        Map<String, Object> result = new HashMap<>();
        result.put("localOrigem", localOrigem);
        result.put("locaisPassagem", path);
        result.put("distanciaEntreLocais", distanceBetweenLocals);
        result.put("distanciaTotal", totalDistance.get(0));
        result.put("numeroCarregamentos", loadingLocations.size());
        result.put("tempoPercurso", tempoPercurso);
        result.put("loadingLocations", loadingLocations);
        result.put("tempoTotalDescarga", tempoDescargaTotal);
        result.put("tempoTotalCarregamento", tempoTotalCarregamento);
        result.put("tempoTotal", tempoTotal);
        return result;
    }

    /**
     * Calcula o caminho mais curto entre dois pontos em um grafo de distâncias.
     *
     * @param distancesGraph    Grafo de distâncias entre localidades.
     * @param localOrigemVolta  Local de origem para o caminho de volta.
     * @param localOrigem       Local de origem para o caminho de ida.
     * @return Lista contendo o caminho mais curto entre os locais.
     */
    public LinkedList<Localidades> calcularCaminhoMaisCurto(MapGraph<Localidades, Integer> distancesGraph, Localidades localOrigemVolta, Localidades localOrigem) {
        LinkedList<Localidades> shortestPath = new LinkedList<>();
        Algorithms.shortestPath(distancesGraph, localOrigemVolta, localOrigem, Comparator.naturalOrder(), Integer::sum, 0, shortestPath);
        return shortestPath;
    }

    /**
     * Calcula os locais de carregamento ao longo de um caminho, considerando a autonomia do veículo.
     *
     * @param shortestPath          Caminho mais curto entre locais.
     * @param distancesGraph        Grafo de distâncias entre localidades.
     * @param autonomia             Autonomia do veículo.
     * @param distanceBetweenLocals Lista para armazenar as distâncias entre locais.
     * @param totalDistance         Lista para armazenar a distância total.
     * @param tempoDescarga         Tempo de descarga em cada local.
     * @param tempoDescargaVolta    Lista para armazenar o tempo total de descarga na volta.
     * @return Lista de locais de carregamento ao longo do caminho.
     */
    public List<Localidades> calculateLoadingHubs(List<Localidades> shortestPath, MapGraph<Localidades, Integer> distancesGraph, Integer autonomia, List<Integer> distanceBetweenLocals, List<Integer> totalDistance, int tempoDescarga, List<Integer> tempoDescargaVolta) {
        List<Localidades> loadingLocations = new ArrayList<>();
        int currentAutonomy = autonomia;
        int tempoDescargaVoltaVar = 0;
        int totalDistanceVar = 0;

        for (int i = 0; i < shortestPath.size() - 1; i++) {
            Localidades vertexA = shortestPath.get(i);
            Localidades vertexB = shortestPath.get(i + 1);
            int distance = distancesGraph.edge(vertexA, vertexB).getWeight();
            distanceBetweenLocals.add(distance);
            totalDistanceVar += distance;

            if (vertexB.isHub()) {
                tempoDescargaVoltaVar += tempoDescarga;
            }

            int distanceAux = 0;
            Edge<Localidades, Integer> edge = distancesGraph.edge(vertexA, vertexB);
            if (edge != null) {
                distanceAux = edge.getWeight();
            }

            if (distanceAux > currentAutonomy) {
                loadingLocations.add(vertexA);
                currentAutonomy = autonomia;
            }

            currentAutonomy -= distanceAux;
        }

        tempoDescargaVolta.set(0, tempoDescargaVoltaVar);
        totalDistance.set(0, totalDistance.get(0) + totalDistanceVar);

        return loadingLocations;
    }

    /**
     * Obtém os principais hubs do grafo de distâncias.
     *
     * @param distancesGraph Grafo de distâncias entre localidades.
     * @param nHubs          Número desejado de hubs a considerar.
     * @return Lista contendo os hubs mais relevantes.
     */
    public List<Localidades> getTopHubs(MapGraph<Localidades, Integer> distancesGraph, int nHubs) {
        List<Localidades> hubs = new ArrayList<>();
        List<Localidades> topNHubs = new ArrayList<>();
        int qtd = 0;

        for (Localidades localidade : distancesGraph.vertices()) {
            if (localidade.isHub()) {
                hubs.add(localidade);
            }
        }

        hubs.sort(Comparator.comparingInt(o -> -extractNumber(o.getNumId())));

        for (Localidades localidades : hubs) {
            if (qtd != nHubs) {
                topNHubs.add(localidades);
                qtd++;
            }

            if (qtd == 5) {
                break;
            }
        }

        return topNHubs;
    }

    /**
     * Extrai o número do identificador da localidade.
     *
     * @param s Identificador da localidade.
     * @return Número extraído do identificador.
     */
    private int extractNumber(String s) {
        StringBuilder number = new StringBuilder();
        for (int i = s.length() - 1; i >= 0; i--) {
            char ch = s.charAt(i);
            if (Character.isDigit(ch)) {
                number.insert(0, ch);
            } else {
                break;
            }
        }
        return number.length() > 0 ? Integer.parseInt(number.toString()) : 0;
    }
}

