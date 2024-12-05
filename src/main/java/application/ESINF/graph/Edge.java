package application.ESINF.graph;

import java.util.Objects;

/**
 * The type Edge.
 *
 * @param <V> the type parameter
 * @param <E> the type parameter
 */
public class Edge<V, E> {
    final private V vOrig;        // vertex origin
    final private V vDest;        // vertex destination
    private E weight;        // Edge weight


    /**
     * Instantiates a new Edge.
     *
     * @param vOrig  the v orig
     * @param vDest  the v dest
     * @param weight the weight
     */
    public Edge(V vOrig, V vDest, E weight) {
        if ((vOrig == null) || (vDest == null)) throw new RuntimeException("Edge vertices cannot be null!");
        this.vOrig = vOrig;
        this.vDest = vDest;
        this.weight = weight;
    }

    /**
     * Gets v orig.
     *
     * @return the v orig
     */
    public V getVOrig() {
        return vOrig;
    }

    /**
     * Gets v dest.
     *
     * @return the v dest
     */
    public V getVDest() {
        return vDest;
    }

    /**
     * Gets weight.
     *
     * @return the weight
     */
    public E getWeight() {
        return weight;
    }

    /**
     * Sets weight.
     *
     * @param weight the weight
     */
    public void setWeight(E weight) {
        this.weight = weight;
    }


    @Override
    public String toString() {
        return String.format("%s -> %s\nWeight: %s", vOrig, vDest, weight);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        @SuppressWarnings("unchecked") Edge<V, E> edge = (Edge<V, E>) o;
        return vOrig.equals(edge.vOrig) &&
                vDest.equals(edge.vDest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vOrig, vDest);
    }
}
