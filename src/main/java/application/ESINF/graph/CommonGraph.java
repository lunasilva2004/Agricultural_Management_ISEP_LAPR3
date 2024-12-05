package application.ESINF.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * The type Common application.ESINF.graph.
 *
 * @param <V> the type parameter
 * @param <E> the type parameter
 */
public abstract class CommonGraph<V, E> implements Graph<V, E> {
    /**
     * The Num verts.
     */
    protected int numVerts;
    /**
     * The Num edges.
     */
    protected int numEdges;
    /**
     * The Is directed.
     */
    protected final boolean isDirected;
    /**
     * The Vertices.
     */
    protected ArrayList<V> vertices;       // Used to maintain a numeric key to each vertex

    /**
     * Instantiates a new Common application.ESINF.graph.
     *
     * @param directed the directed
     */
    public CommonGraph(boolean directed) {
        numVerts = 0;
        numEdges = 0;
        isDirected = directed;
        vertices = new ArrayList<>();
    }

    @Override
    public boolean isDirected() {
        return isDirected;
    }

    @Override
    public int numVertices() {
        return numVerts;
    }

    @Override
    public ArrayList<V> vertices() {
        return new ArrayList<>(vertices);
    }

    @Override
    public boolean validVertex(V vert) {
        return vertices.contains(vert);
    }

    @Override
    public int key(V vert) {
        return vertices.indexOf(vert);
    }

    @Override
    public V vertex(int key) {
        if ((key < 0) || (key >= numVerts)) return null;
        return vertices.get(key);
    }

    @Override
    public V vertex(Predicate<V> p) {
        for (V v : vertices) {
            if (p.test(v)) return v;
        }
        return null;
    }

    @Override
    public int numEdges() {
        return numEdges;
    }

    public int numVerts(){
        return numVerts;
    }

    /**
     * Copy.
     *
     * @param from the from
     * @param to   the to
     */
    protected void copy(Graph<V, E> from, Graph<V, E> to) {
        //insert all vertices
        for (V v : from.vertices()) {
            to.addVertex(v);
        }

        //insert all edges
        for (Edge<V, E> e : from.edges()) {
            to.addEdge(e.getVOrig(), e.getVDest(), e.getWeight());
        }
    }

    @Override
    public boolean equals(Object otherObj) {

        if (this == otherObj)
            return true;

        if (!(otherObj instanceof Graph<?, ?>))
            return false;

        @SuppressWarnings("unchecked") Graph<V, E> otherGraph = (Graph<V, E>) otherObj;

        if (numVerts != otherGraph.numVertices() || numEdges != otherGraph.numEdges() || isDirected() != otherGraph.isDirected())
            return false;

        // application.ESINF.graph must have same vertices
        Collection<V> tvc = this.vertices();
        tvc.removeAll(otherGraph.vertices());
        if (tvc.size() > 0) return false;

        // application.ESINF.graph must have same edges
        Collection<Edge<V, E>> tec = this.edges();
        tec.removeAll(otherGraph.edges());
        return (tec.size() == 0);
    }

    public abstract Graph<V, E> clone();

    @Override
    public int hashCode() {
        return Objects.hash(numVerts, numEdges, isDirected, vertices);
    }
}
