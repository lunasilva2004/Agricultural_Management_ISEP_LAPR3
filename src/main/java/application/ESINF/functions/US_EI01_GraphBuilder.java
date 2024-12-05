package application.ESINF.functions;

import application.ESINF.domain.Localidades;
import application.ESINF.graph.map.MapGraph;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Usei 01 graph builder.
 */
public class US_EI01_GraphBuilder {
    private static final US_EI01_GraphBuilder instance = new US_EI01_GraphBuilder();

    final private MapGraph<Localidades, Integer> distribuicao;

    private US_EI01_GraphBuilder() {
        this.distribuicao = new MapGraph<>(false);
    }

    /**
     * Gets distribuicao.
     *
     * @return the distribuicao
     */
    public MapGraph<Localidades, Integer> getDistribuicao() {
        return distribuicao;
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static US_EI01_GraphBuilder getInstance() {
        return instance;
    }

    /**
     * Add hub boolean.
     *
     * @param numId the num id
     * @param lat   the lat
     * @param lon   the lon
     * @return the boolean
     */
    public boolean addLocalidade(String numId, Double lat, Double lon) {
        Localidades vert = new Localidades(numId, lat, lon);
        return distribuicao.addVertex(vert);
    }

    /**
     * Add route boolean.
     *
     * @param orig     the orig
     * @param dest     the dest
     * @param distance the distance
     * @return the boolean
     */
    public boolean addRoute(Localidades orig, Localidades dest, Integer distance) {
        return distribuicao.addEdge(orig, dest, distance);
    }

    @Override
    public String toString() {
        return distribuicao.toString();
    }

    public Localidades getHubById(String hubId) {
        // Itera sobre todos os vértices (Hubs) no grafo
        for (Localidades localidades : distribuicao.vertices()) {
            // Compara o ID do Hub atual com o ID desejado
            if (localidades.getNumId().equals(hubId)) {
                // Retorna o Hub se encontrar correspondência
                return localidades;
            }
        }
        // Retorna null se nenhum Hub correspondente for encontrado
        return null;
    }



}