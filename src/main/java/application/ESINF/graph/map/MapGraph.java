package application.ESINF.graph.map;

import application.ESINF.graph.CommonGraph;
import application.ESINF.graph.Edge;
import application.ESINF.graph.Graph;

import java.util.*;


/**
 * The type Map graph.
 *
 * @param <V> Vertex value type
 * @param <E> Edge value type
 * @author DEI -ESINF
 */
public class MapGraph<V, E> extends CommonGraph<V, E> {


    final private Map<V, MapVertex<V, E>> mapVertices;  // all the Vertices of the application.ESINF.graph

    /**
     * Instantiates a new Map graph.
     *
     * @param directed the directed
     */
// Constructs an empty application.ESINF.graph (either undirected or directed)
    public MapGraph(boolean directed) {
        super(directed);
        mapVertices = new LinkedHashMap<>();
    }

    /**
     * Instantiates a new Map graph.
     *
     * @param g the g
     */
    public MapGraph(Graph<V, E> g) {
        this(g.isDirected());
        copy(g, this);
    }


    @Override
    public boolean validVertex(V vert) {
        return (mapVertices.get(vert) != null);
    }

    @Override
    public Collection<V> adjVertices(V vert) {

        return mapVertices.get(vert).getAllAdjVerts();
    }

    @Override
    public Collection<Edge<V, E>> edges() {

        ArrayList<Edge<V, E>> le = new ArrayList<>(numEdges);

        for (MapVertex<V, E> mv : mapVertices.values())
            le.addAll(mv.getAllOutEdges());

        return le;
    }

    @Override
    public Edge<V, E> edge(V vOrig, V vDest) {

        if (!validVertex(vOrig) || !validVertex(vDest))
            return null;

        MapVertex<V, E> mv = mapVertices.get(vOrig);

        return mv.getEdge(vDest);
    }

    @Override
    public Edge<V, E> edge(int vOrigKey, int vDestKey) {
        V vOrig = vertex(vOrigKey);
        V vDest = vertex(vDestKey);

        return edge(vOrig, vDest);
    }

    @Override
    public int outDegree(V vert) {

        if (!validVertex(vert))
            return -1;

        MapVertex<V, E> mv = mapVertices.get(vert);

        return mv.numAdjVerts();
    }

    @Override
    public int inDegree(V vert) {

        if (!validVertex(vert))
            return -1;

        int degree = 0;
        for (V otherVert : mapVertices.keySet())
            if (edge(otherVert, vert) != null)
                degree++;

        return degree;
    }

    @Override
    public Collection<Edge<V, E>> outgoingEdges(V vert) {

        if (!validVertex(vert))
            return null;

        MapVertex<V, E> mv = mapVertices.get(vert);

        return mv.getAllOutEdges();
    }

    @Override
    public Collection<Edge<V, E>> incomingEdges(V vert) {

        Collection<Edge<V, E>> ce = new ArrayList<>();
        Set<V> values = mapVertices.keySet();
        for (V val : values) {
            for (Edge<V, E> edge : mapVertices.get(val).getAllOutEdges()) {
                if (edge.getVDest().equals(vert)) {
                    ce.add(edge);
                }
            }
        }
        return ce;
    }

    @Override
    public boolean addVertex(V vert) {

        if (vert == null) throw new RuntimeException("Vertices cannot be null!");
        if (validVertex(vert))
            return false;

        MapVertex<V, E> mv = new MapVertex<>(vert);
        vertices.add(vert);
        mapVertices.put(vert, mv);
        numVerts++;

        return true;
    }

    @Override
    public boolean addEdge(V vOrig, V vDest, E weight) {

        if (vOrig == null || vDest == null) throw new RuntimeException("Vertices cannot be null!");
        if (edge(vOrig, vDest) != null)
            return false;

        if (!validVertex(vOrig))
            addVertex(vOrig);

        if (!validVertex(vDest))
            addVertex(vDest);

        MapVertex<V, E> mvo = mapVertices.get(vOrig);
        MapVertex<V, E> mvd = mapVertices.get(vDest);

        Edge<V, E> newEdge = new Edge<>(mvo.getElement(), mvd.getElement(), weight);
        mvo.addAdjVert(mvd.getElement(), newEdge);
        numEdges++;

        if (!isDirected)
            // if vDest different vOrig
            if (edge(vDest, vOrig) == null) {
                Edge<V, E> otherEdge = new Edge<>(mvd.getElement(), mvo.getElement(), weight);
                mvd.addAdjVert(mvo.getElement(), otherEdge);
                numEdges++;
            }

        return true;
    }

    @Override
    public boolean removeVertex(V vert) {

        if (vert == null) throw new RuntimeException("Vertices cannot be null!");
        if (!validVertex(vert))
            return false;

        //remove all edges that point to vert
        for (Edge<V, E> edge : incomingEdges(vert)) {
            removeEdge(edge.getVOrig(), vert);
        }

        MapVertex<V, E> mv = mapVertices.get(vert);

        //The edges that live from vert are removed with the vertex
        numEdges -= mv.numAdjVerts();
        mapVertices.remove(vert);
        vertices.remove(vert);

        numVerts--;

        return true;
    }

    @Override
    public boolean removeEdge(V vOrig, V vDest) {

        if (vOrig == null || vDest == null) throw new RuntimeException("Vertices cannot be null!");
        if (!validVertex(vOrig) || !validVertex(vDest))
            return false;

        Edge<V, E> edge = edge(vOrig, vDest);

        if (edge == null)
            return false;

        MapVertex<V, E> mvo = mapVertices.get(vOrig);

        mvo.remAdjVert(vDest);
        numEdges--;

        //if application.ESINF.graph is not directed
        if (!isDirected) {
            edge = edge(vDest, vOrig);
            if (edge != null) {
                MapVertex<V, E> mvd = mapVertices.get(vDest);
                mvd.remAdjVert(vOrig);
                numEdges--;
            }
        }
        return true;
    }

    public Edge<V, E> getEdge(V vOrig, V vDest) {
        if (!validVertex(vOrig) || !validVertex(vDest)) {
            return null;
        }

        for (Edge<V, E> edge : outgoingEdges(vOrig)) {
            if (edge.getVDest().equals(vDest)) {
                return edge;
            }
        }

        return null;
    }

    //Returns a clone of the application.ESINF.graph
    @Override
    public MapGraph<V, E> clone() {

        MapGraph<V, E> g = new MapGraph<>(this.isDirected);

        copy(this, g);

        return g;
    }

    //string representation
    @Override
    public String toString() {
        String s;
        if (numVerts == 0) {
            s = "\nGraph not defined!!";
        } else {
            s = "Graph: " + numVerts + " vertices, " + numEdges + " edges\n";
            for (MapVertex<V, E> mv : mapVertices.values())
                s += mv + "\n";
        }
        return s;
    }
}