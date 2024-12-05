package application.ESINF.graph.map;

import application.ESINF.graph.Edge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The type Map vertex.
 *
 * @param <V> the type parameter
 * @param <E> the type parameter
 * @author DEI -ESINF
 */
public class MapVertex<V, E> {

    final private V element;                            // Vertex information
    final private Map<V, Edge<V, E>> outVerts;    // Adjacent vertices

    /**
     * Instantiates a new Map vertex.
     *
     * @param vert the vert
     */
    public MapVertex(V vert) {
        if (vert == null) throw new RuntimeException("Vertice information cannot be null!");
        element = vert;
        outVerts = new LinkedHashMap<>();
    }

    /**
     * Gets element.
     *
     * @return the element
     */
    public V getElement() {
        return element;
    }

    /**
     * Add adj vert.
     *
     * @param vAdj the v adj
     * @param edge the edge
     */
    public void addAdjVert(V vAdj, Edge<V, E> edge) {
        outVerts.put(vAdj, edge);
    }

    /**
     * Rem adj vert.
     *
     * @param vAdj the v adj
     */
    public void remAdjVert(V vAdj) {
        outVerts.remove(vAdj);
    }

    /**
     * Gets edge.
     *
     * @param vAdj the v adj
     * @return the edge
     */
    public Edge<V, E> getEdge(V vAdj) {
        return outVerts.get(vAdj);
    }

    /**
     * Num adj verts int.
     *
     * @return the int
     */
    public int numAdjVerts() {
        return outVerts.size();
    }

    /**
     * Gets all adj verts.
     *
     * @return the all adj verts
     */
    public Collection<V> getAllAdjVerts() {
        return new ArrayList<>(outVerts.keySet());
    }

    /**
     * Gets all out edges.
     *
     * @return the all out edges
     */
    public Collection<Edge<V, E>> getAllOutEdges() {
        return new ArrayList<>(outVerts.values());
    }

    @Override
    public String toString() {
        StringBuilder st = new StringBuilder(element + ": \n");
        if (!outVerts.isEmpty())
            for (V vert : outVerts.keySet())
                st.append(outVerts.get(vert)).append("\n");

        return st.toString();
    }
}
