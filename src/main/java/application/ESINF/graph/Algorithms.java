
package application.ESINF.graph;

import application.ESINF.domain.Localidades;
import application.ESINF.graph.map.MapGraph;
import application.ESINF.graph.matrix.MatrixGraph;
import org.apache.commons.math3.analysis.function.Acos;

import java.util.*;
import java.util.function.BinaryOperator;


/**
 * The type Algorithms.
 */
public class Algorithms {


    /**
     * Performs breadth-first search of a Graph starting in a vertex
     *
     * @param <V>     the type parameter
     * @param <E>     the type parameter
     * @param g       Graph instance
     * @param vert    vertex that will be the source of the search
     * @param dest    the dest
     * @param visited the visited
     * @return a LinkedList with the vertices of breadth-first search
     */
    public static <V, E> LinkedList<V> BreadthFirstSearch(Graph<V, E> g, V vert, V dest,boolean[] visited) {
        if (!g.validVertex(vert) || !g.validVertex(vert)) {
            return null;
        }

        LinkedList<V> qbfs = new LinkedList<>();
        ArrayList<V> qaux = new ArrayList<>();
        qbfs.add(vert);
        qaux.add(vert);
        visited[g.key(vert)] = true;

        while (!qaux.isEmpty()) {
            V vInf = qaux.remove(0);
            for (V vAdj : g.adjVertices(vInf)) {
                if (!visited[g.key(vAdj)]) {
                    qbfs.add(vAdj);
                    qaux.add(vAdj);
                    visited[g.key(vAdj)] = true;
                    if (vAdj.equals(dest)) {
                        // If the target vertex is reached, stop the search
                        return qbfs;
                    }
                }
            }


        }
        return qbfs;
    }


    /**
     * Performs depth-first search starting in a vertex using recursion.
     *
     * @param <V>     the type parameter
     * @param <E>     the type parameter
     * @param g       Graph instance
     * @param vOrig   vertex of application.ESINF.graph g that will be the source of the search
     * @param visited the visited
     * @param qdfs    the qdfs
     * @return LinkedList with vertices of depth-first search
     */
    public static <V, E> LinkedList<V> DepthFirstSearch(Graph<V, E> g, V vOrig, boolean[] visited, LinkedList<V> qdfs) {
        qdfs.add(vOrig);
        visited[g.key(vOrig)] = true;
        for (V vAdj : g.adjVertices(vOrig)) {
            if (!visited[g.key(vAdj)]) {
                DepthFirstSearch(g, vAdj, visited, qdfs);
            }
        }
        return qdfs;
    }


    /**
     * Performs depth-first search starting in a vertex
     *
     * @param <V>  the type parameter
     * @param <E>  the type parameter
     * @param g    Graph instance
     * @param vert vertex of application.ESINF.graph g that will be the source of the search
     * @return a LinkedList with the vertices of depth-first search
     */
    public static <V, E> LinkedList<V> DepthFirstSearch(Graph<V, E> g, V vert) {
        if (!g.validVertex(vert)) {
            return null;
        }

        LinkedList<V> qdfs = new LinkedList<>();
        boolean[] visited = new boolean[g.numVertices()];
        qdfs.add(vert);
        visited[g.key(vert)] = true;
        for (V vAdj : g.adjVertices(vert)) {
            if (!g.validVertex(vAdj)) {
                return null;
            }

            if (!visited[g.key(vAdj)]) {
                DepthFirstSearch(g, vAdj, visited, qdfs);
            }
        }
        return qdfs;
    }


    /**
     * Performs depth-first search starting in a vertex
     *
     * @param <V>  the type parameter
     * @param <E>  the type parameter
     * @param g    Graph instance
     * @param vert vertex of application.ESINF.graph g that will be the source of the search
     * @return a LinkedList with the vertices of depth-first search
     */
    public static <V, E> LinkedList<V> DepthFirstSearchIterative(Graph<V, E> g, V vert) {
        LinkedList<V> dfsResult = new LinkedList<>();
        Set<V> visited = new HashSet<>();
        Stack<V> stack = new Stack<>();

        // Check if the starting vertex is valid
        if (!g.validVertex(vert)) {
            System.out.println("Invalid starting vertex");
            return dfsResult;
        }

        // Perform DFS using iteration
        stack.push(vert);

        while (!stack.isEmpty()) {
            V currentVertex = stack.pop();

            if (!visited.contains(currentVertex)) {
                visited.add(currentVertex);
                dfsResult.add(currentVertex);

                // Push unvisited neighbors onto the stack
                for (V neighbor : g.adjVertices(currentVertex)) {
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }

        return dfsResult;
    }


    /**
     * Returns all paths from vOrig to vDest
     *
     * @param g       Graph instance
     * @param vOrig   Vertex that will be the source of the path
     * @param vDest   Vertex that will be the end of the path
     * @param visited set of discovered vertices
     * @param path    stack with vertices of the current path (the path is in reverse order)
     * @param paths   ArrayList with all the paths (in correct order)
     */

    private static <V, E> void allPaths(Graph<V, E> g, V vOrig, V vDest, boolean[] visited,
                                        LinkedList<V> path, ArrayList<LinkedList<V>> paths) {
        int vOrigKey = g.key(vOrig);
        int vDestKey = g.key(vDest);

        // Mark the current vertex as visited and add it to the path
        visited[vOrigKey] = true;
        path.add(vOrig);

        // If the current vertex is the destination, add the path to the list of paths
        if (vOrig.equals(vDest)) {
            paths.add(new LinkedList<>(path));
        } else {
            // Continue the search to adjacent vertices
            for (V neighbor : g.adjVertices(vOrig)) {
                int neighborKey = g.key(neighbor);
                if (!visited[neighborKey]) {
                    allPaths(g, neighbor, vDest, visited, path, paths);
                }
            }
        }

        // Backtrack: remove the current vertex from the path and mark it as unvisited
        visited[vOrigKey] = false;
        path.removeLast();
    }

    /**
     * Returns all paths from vOrig to vDest
     *
     * @param <V>   the type parameter
     * @param <E>   the type parameter
     * @param g     Graph instance
     * @param vOrig information of the Vertex origin
     * @param vDest information of the Vertex destination
     * @return paths ArrayList with all paths from vOrig to vDest
     */
    public static <V, E> ArrayList<LinkedList<V>> allPaths(Graph<V, E> g, V vOrig, V vDest) {
        ArrayList<LinkedList<V>> paths = new ArrayList<>();
        boolean[] visited = new boolean[g.numVertices()];
        LinkedList<V> currentPath = new LinkedList<>();

        // Check if the vertices are valid
        if (!g.validVertex(vOrig) || !g.validVertex(vDest)) {
            System.out.println("Invalid vertices");
            return paths;
        }

        // Perform DFS to find all paths
        allPaths(g, vOrig, vDest, visited, currentPath, paths);

        return paths;
    }


    /**
     * Computes shortest-path distance from a source vertex to all reachable
     * vertices of a graph g with non-negative edge weights
     * This implementation uses Dijkstra's algorithm
     *
     * @param <V>      the type parameter
     * @param <E>      the type parameter
     * @param g        Graph instance
     * @param vOrig    Vertex that will be the source of the path
     * @param ce       the ce
     * @param sum      the sum
     * @param zero     the zero
     * @param visited  set of previously visited vertices
     * @param pathKeys minimum path vertices keys
     * @param dist     minimum distances
     */
    public static <V, E> void shortestPathDijkstra(Graph<V, E> g, V vOrig,
                                                   Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                                   boolean[] visited, V[] pathKeys, E[] dist) {

        int vKey = g.key(vOrig);
        dist[vKey] = zero;
        pathKeys[vKey] = vOrig;

        while (vOrig != null) {
            vKey = g.key(vOrig);
            visited[vKey] = true;
            for (Edge<V, E> edge : g.outgoingEdges(vOrig)) {
                int keyVAdj = g.key(edge.getVDest());
                if (!visited[keyVAdj]) {
                    E s = sum.apply(dist[vKey], edge.getWeight());
                    if (dist[keyVAdj] == null || ce.compare(dist[keyVAdj], s) > 0) {
                        dist[keyVAdj] = s;
                        pathKeys[keyVAdj] = vOrig;
                    }
                }
            }

            E minDist = null;
            vOrig = null;
            for (V vertex : g.vertices()) {
                int vertexKey = g.key(vertex);
                if (!visited[vertexKey] && (dist[vertexKey] != null) && ((minDist == null) || ce.compare(dist[vertexKey], minDist) < 0)) {
                    minDist = dist[vertexKey];
                    vOrig = vertex;
                }
            }
        }
    }

    /**
     * Shortest-path between two vertices
     *
     * @param <V>       the type parameter
     * @param <E>       the type parameter
     * @param g         graph
     * @param vOrig     origin vertex
     * @param vDest     destination vertex
     * @param ce        comparator between elements of type E
     * @param sum       sum two elements of type E
     * @param zero      neutral element of the sum in elements of type E
     * @param shortPath returns the vertices which make the shortest path
     * @return if vertices exist in the graph and are connected, true, false otherwise
     */
    public static <V, E> E shortestPath(Graph<V, E> g, V vOrig, V vDest,
                                        Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                        LinkedList<V> shortPath) {
        if(!g.validVertex(vOrig) || !g.validVertex(vDest)){
            return null;
        }

        shortPath.clear();
        int numVerts = g.numVertices();
        boolean[] visited = new boolean[numVerts];
        V[] pathKeys = (V[]) new Object [numVerts];
        E[] dist = (E[]) new Object [numVerts];
        initializePathDist(numVerts, pathKeys, dist);

        shortestPathDijkstra(g, vOrig, ce, sum, zero, visited, pathKeys, dist);

        E lengthPath = dist[g.key(vDest)];

        if(lengthPath != null){
            getPath(g, vOrig, vDest, pathKeys, shortPath);
            return lengthPath;
        }

        return null;
    }

    /**
     * Initialize path dist.
     *
     * @param <V>      the type parameter
     * @param <E>      the type parameter
     * @param numVerts the num verts
     * @param pathKeys the path keys
     * @param dist     the dist
     */
    public static <V, E> void initializePathDist(int numVerts, V[] pathKeys, E[] dist){
        for (int i = 0; i < numVerts; i++) {
            pathKeys[i]=null;
            dist[i] = null;
        }
    }

    /**
     * Shortest-path between a vertex and all other vertices
     *
     * @param <V>   the type parameter
     * @param <E>   the type parameter
     * @param g     graph
     * @param vOrig start vertex
     * @param ce    comparator between elements of type E
     * @param sum   sum two elements of type E
     * @param zero  neutral element of the sum in elements of type E
     * @param paths returns all the minimum paths
     * @param dists returns the corresponding minimum distances
     * @return if vOrig exists in the graph true, false otherwise
     */
    public static <V, E> boolean shortestPaths(Graph<V, E> g, V vOrig,
                                               Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                               ArrayList<LinkedList<V>> paths, ArrayList<E> dists) {

        if (!g.validVertex(vOrig)) {
            return false;
        }
        paths.clear();
        dists.clear();
        int numVertices = g.numVertices();
        boolean[] visited = new boolean[numVertices];
        V[] pathKeys = (V[]) new Object[numVertices];
        E[] dist = (E[]) new Object[numVertices];
        initializePathDist(numVertices, pathKeys, dist);

        shortestPathDijkstra(g, vOrig, ce, sum, zero, visited, pathKeys, dist);

        dists.clear();
        paths.clear();
        for (int i = 0; i < numVertices; i++) {
            paths.add(null);
            dists.add(null);
        }
        for (V vDist:g.vertices()) {
            int i = g.key(vDist);
            if(dist[i] != null){
                LinkedList<V> shortPath = new LinkedList<>();
                getPath(g, vOrig, vDist, pathKeys, shortPath);
                paths.set(i, shortPath);
                dists.set(i, dist[i]);
            }
        }

        return true;
    }

    /**
     * Extracts from pathKeys the minimum path between voInf and vdInf
     * The path is constructed from the end to the beginning
     *
     * @param <V>      the type parameter
     * @param <E>      the type parameter
     * @param g        Graph instance
     * @param vOrig    information of the Vertex origin
     * @param vDest    information of the Vertex destination
     * @param pathKeys minimum path vertices keys
     * @param path     stack with the minimum path (correct order)
     */
    public static <V, E> void getPath(Graph<V, E> g, V vOrig, V vDest,
                                      V[] pathKeys, LinkedList<V> path) {

        if (vOrig.equals(vDest))
            path.push(vOrig);
        else {
            path.push(vDest);
            int vKey = g.key(vDest);
            vDest = pathKeys[vKey];
            getPath(g, vOrig, vDest, pathKeys, path);
        }
    }

    /**
     * Calculates the minimum distance application.ESINF.graph using Floyd-Warshall
     *
     * @param <V> the type parameter
     * @param <E> the type parameter
     * @param g   initial application.ESINF.graph
     * @param ce  comparator between elements of type E
     * @param sum sum two elements of type E
     * @return the minimum distance application.ESINF.graph
     */
    public static <V, E> MatrixGraph<V, E> minDistGraph(Graph<V, E> g, Comparator<E> ce, BinaryOperator<E> sum) {
        int n = g.numVertices();
        MatrixGraph<V, E> distGraph = new MatrixGraph<>(g);

        for (V vertex : g.vertices()) {
            distGraph.addVertex(vertex);
        }

        // Copiar arestas
        for (Edge<V, E> edge : g.edges()) {
            V orig = edge.getVOrig();
            V dest = edge.getVDest();
            E weight = edge.getWeight();
            distGraph.addEdge(orig, dest, weight);
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    E distIK = g.edge(i, k) != null ? g.edge(i, k).getWeight() : null;
                    E distKJ = g.edge(k, j) != null ? g.edge(k, j).getWeight() : null;
                    E currentDist = g.edge(i, j) != null ? g.edge(i, j).getWeight() : null;

                    if (distIK != null && distKJ != null) {
                        E newDist = sum.apply(distIK, distKJ);
                        if (currentDist == null || ce.compare(newDist, currentDist) < 0) {
                            distGraph.addEdge(g.vertex(i), g.vertex(j), newDist);
                        }
                    }
                }
            }
        }

        return distGraph;
    }


    /**
     * Helper method to check if a value is non-null
     *
     * @param value the value to check
     * @return true if the value is non-null, false otherwise
     */

    private static <E> boolean nonNull(E value) {
        return value != null;
    }


    /**
     * Helper method to check if a value is null
     *
     * @param value the value to check
     * @return true if the value is null, false otherwise
     */

    private static <E> boolean isNull(E value) {
        return value == null;
    }

    /**
     * Initializes the distance matrix with the weights of the edges
     *
     * @param g   application.ESINF.graph
     * @param ce  comparator between elements of type E
     * @param sum sum two elements of type E
     * @return the initialized distance matrix
     */
    private static <V, E> E[][] initializeDistMatrix(Graph<V, E> g, Comparator<E> ce, BinaryOperator<E> sum) {
        int numVerts = g.numVertices();
        E[][] distMatrix = (E[][]) new Object[numVerts][numVerts];

        // Initialize the distance matrix with the weights of the edges
        for (int i = 0; i < numVerts; i++) {
            for (int j = 0; j < numVerts; j++) {
                if (i == j) {
                    // Distance to itself is zero
                    distMatrix[i][j] = nonNull(ce.compare(sum.apply(null, null), null)) ? sum.apply(null, null) : null;
                } else {
                    Edge<V, E> edge = g.edge(g.vertex(i), g.vertex(j));
                    distMatrix[i][j] = (edge != null) ? edge.getWeight() : nonNull(ce.compare(sum.apply(null, null), null)) ? sum.apply(null, null) : null;
                }
            }
        }

        return distMatrix;
    }


    /**
     * Kruskal's algorithm for finding a minimum spanning tree in a graph.
     *
     * @param <V>  the type parameter
     * @param <E>  the type parameter
     * @param g    graph
     * @param ce   comparator between elements of type E
     * @return the minimum spanning tree
     */
    public static <V, E> MapGraph<V, E> minimumSpanningTree(MapGraph<V, E> g, Comparator<E> ce) {
        // Create a set to keep track of visited vertices
        Set<V> visitedVertices = new HashSet<>();

        // Create a priority queue to store edges based on their weights
        PriorityQueue<Edge<V, E>> edgeQueue = new PriorityQueue<>(Comparator.comparing(Edge::getWeight, ce));

        // Create a graph to represent the minimum spanning tree
        MapGraph<V, E> minimumSpanningTree = new MapGraph<>(true);

        // Add an arbitrary vertex to start the process
        V startVertex = g.vertices().iterator().next();
        visitedVertices.add(startVertex);

        // Add all edges connected to the start vertex to the priority queue
        edgeQueue.addAll(g.outgoingEdges(startVertex));

        // Continue adding edges until all vertices are visited
        while (visitedVertices.size() < g.numVertices()) {
            // Get the minimum weight edge from the priority queue
            Edge<V, E> minEdge = edgeQueue.poll();
            // Check if the priority queue is empty
            if (minEdge == null) {
                // The graph is not connected
                break;
            }

            // Get the destination vertex of the minimum weight edge
            V destVertex = minEdge.getVDest();

            // Check if adding this edge creates a cycle
            if (!visitedVertices.contains(destVertex)) {
                // Add the destination vertex to the set of visited vertices
                visitedVertices.add(destVertex);

                // Add the edge to the minimum spanning tree
                minimumSpanningTree.addEdge(minEdge.getVOrig(), destVertex, minEdge.getWeight());

                // Add all edges connected to the destination vertex to the priority queue
                edgeQueue.addAll(g.outgoingEdges(destVertex));
            }
        }
        return minimumSpanningTree;
    }


    /**
     * Calculates the betweenness centrality for each vertex in the graph using Brandes' algorithm.
     * Betweenness centrality measures the extent to which a vertex lies on the shortest paths
     * between other vertices in the graph.
     * <p>
     * The algorithm works by performing a breadth-first search from each vertex as a source,
     * counting the number of shortest paths and calculating the dependency of each vertex.
     * The betweenness centrality for each vertex is then updated based on the calculated dependency.
     *
     * @param <V>   The type of vertex in the graph.
     * @param <E>   The type of edge in the graph.
     * @param graph The graph for which to calculate betweenness centrality.
     * @return A map where each vertex is associated with its betweenness centrality value.
     */
    public static <V, E> Map<V, Integer> betweennessCentrality(Graph<V, E> graph) {
        Map<V, Integer> centrality = new HashMap<>();

        for (V vertex : graph.vertices()) {
            centrality.put(vertex, 0);
        }

        for (V source : graph.vertices()) {
            LinkedList<V> queue = new LinkedList<>();
            queue.add(source);

            Map<V, Integer> numShortestPaths = new HashMap<>();
            numShortestPaths.put(source, 1);

            Map<V, Integer> dependency = new HashMap<>();
            for (V vertex : graph.vertices()) {
                dependency.put(vertex, 0);
            }

            Map<V, Integer> distance = new HashMap<>();
            distance.put(source, 0);

            while (!queue.isEmpty()) {
                V currentVertex = queue.poll();

                for (V neighbor : graph.adjVertices(currentVertex)) {
                    if (!distance.containsKey(neighbor)) {
                        distance.put(neighbor, distance.get(currentVertex) + 1);
                        queue.add(neighbor);
                    }

                    if (distance.get(neighbor) == distance.get(currentVertex) + 1) {
                        numShortestPaths.put(neighbor, numShortestPaths.getOrDefault(neighbor, 0) + numShortestPaths.get(currentVertex));
                        dependency.put(neighbor, dependency.get(neighbor) + 1);
                    }
                }
            }

            for (V vertex : graph.vertices()) {
                if (!vertex.equals(source)) {
                    Integer currentCentrality = centrality.get(vertex);
                    Integer currentDependency = dependency.get(vertex);
                    Integer currentNumShortestPaths = numShortestPaths.get(vertex);

                    if (currentCentrality == null) {
                        currentCentrality = 0;
                    }
                    if (currentDependency == null) {
                        currentDependency = 0;
                    }
                    if (currentNumShortestPaths == null) {
                        currentNumShortestPaths = 1; // Avoid division by zero
                    }

                    centrality.put(vertex, currentCentrality + (currentDependency / currentNumShortestPaths));
                }
            }

        }

        return centrality;
    }

    /**
     * Dfs algorithm array list.
     *
     * @param <V>       the type parameter
     * @param <E>       the type parameter
     * @param g         the g
     * @param vOrig     the v orig
     * @param vDest     the v dest
     * @param maxWeight the max weight
     * @return the array list
     */
    /* Finds all paths between two vertices with a maximum weight.
     *
     * @param <V>          the type parameter
     * @param <E>          the type parameter
     * @param g            Graph instance
     * @param vOrig        Vertex that will be the source of the path
     * @param vDest        Vertex that will be the end of the path
     * @param maxWeight    Maximum weight of the paths
     * @return ArrayList with all paths from vOrig to vDest with a weight less than maxWeight
     */
    public static <V, E extends Comparable<E>> ArrayList<LinkedList<V>> dfsAlgorithm(Graph<V, E> g, V vOrig, V vDest, E maxWeight) {
        ArrayList<LinkedList<V>> paths = new ArrayList<>();
        Set<V> visitedSet = new HashSet<>();
        LinkedList<V> currentPath = new LinkedList<>();
        Integer totalWeight = 0;

        if (!g.validVertex(vOrig) || !g.validVertex(vDest)) {
            System.out.println("Invalid vertices");
            return paths;
        }

        dfsAlgorithm(g, vOrig, vDest, maxWeight, visitedSet, currentPath, paths, totalWeight);

        return paths;
    }

    private static <V, E extends Comparable<E>> void dfsAlgorithm(Graph<V, E> g, V vOrig, V vDest, E maxWeight, Set<V> visitedSet,
                                                                  LinkedList<V> path, ArrayList<LinkedList<V>> paths, Integer totalWeight) {

        visitedSet.add(vOrig);
        path.add(vOrig);

        if (vOrig.equals(vDest)) {
            paths.add(new LinkedList<>(path));
        } else {
            for (V neighbor : g.adjVertices(vOrig)) {
                Integer weight = (Integer) g.edge(vOrig, neighbor).getWeight();

                if (!visitedSet.contains(neighbor) && (totalWeight + weight) <= (Integer) maxWeight) {
                    totalWeight += weight;
                    dfsAlgorithm(g, neighbor, vDest, maxWeight, visitedSet, path, paths, totalWeight);
                }
            }
        }

        visitedSet.remove(vOrig);
        path.removeLast();
    }




    /**
     * Computes shortest-path distance from a source vertex to all reachable
     * vertices of a graph g with non-negative edge weights
     * This implementation uses Dijkstra's algorithm
     *
     * @param <V>       the type parameter
     * @param <E>       the type parameter
     * @param g         Graph instance
     * @param autonomia the autonomia
     * @param vOrig     Vertex that will be the source of the path
     * @param ce        the ce
     * @param sum       the sum
     * @param zero      the zero
     * @param visited   set of previously visited vertices
     * @param pathKeys  minimum path vertices keys
     * @param dist      minimum distances
     */
    public static <V, E> void shortestPathDijkstraWithAutonomy(Graph<V, E> g, E autonomia, V vOrig,
                                                               Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                                               boolean[] visited, V[] pathKeys, E[] dist) {

        // Obtém a chave do vértice de origem no grafo
        int vkey = g.key(vOrig);
        // Inicializa a distância e a chave do vértice de origem
        dist[vkey] = zero;
        pathKeys[vkey] = vOrig;
        // Enquanto houver vértices a serem explorados
        while (vOrig != null) {
            // Obtém a chave do vértice de origem no grafo
            vkey = g.key(vOrig);
            // Marca o vértice como visitado
            visited[vkey] = true;
            // Itera sobre as arestas saindo do vértice de origem
            for (Edge<V, E> edge : g.outgoingEdges(vOrig)) {
                // Obtém a chave do vértice de destino da aresta
                int vkeyAdj = g.key(edge.getVDest());
                // Verifica se o vértice de destino não foi visitado
                if (!visited[vkeyAdj]) {
                    // Calcula a autonomia restante
                    E remainingAutonomy = sum.apply(autonomia, dist[vkey]);
                    // Verifica se a autonomia restante é suficiente para percorrer a aresta
                    if (ce.compare(edge.getWeight(), remainingAutonomy) <= 0) {
                        // Calcula a nova distância acumulada
                        E s = sum.apply(dist[vkey], edge.getWeight());
                        // Atualiza a distância acumulada e a chave do vértice de destino se necessário
                        if (dist[vkeyAdj] == null || ce.compare(dist[vkeyAdj], s) < 0) {
                            dist[vkeyAdj] = s;
                            pathKeys[vkeyAdj] = vOrig;
                        }
                    }
                }
            }
            // Encontra o vértice não visitado com a menor distância acumulada
            E minDist = null;
            vOrig = null;
            for (V vert : g.vertices()) {
                int i = g.key(vert);
                if (!visited[i] && (dist[i] != null) && ((minDist == null) || ce.compare(dist[i], minDist) < 0)) {
                    minDist = dist[i];
                    vOrig = vert;
                }
            }
        }
    }


    /**
     * Shortest path with autonomy e.
     *
     * @param <V>       the type parameter
     * @param <E>       the type parameter
     * @param g         the g
     * @param autonomy  the autonomy
     * @param vOrig     the v orig
     * @param vDest     the v dest
     * @param ce        the ce
     * @param sum       the sum
     * @param zero      the zero
     * @param shortPath the short path
     * @return the e
     */
    public static <V, E> E shortestPathWithAutonomy(Graph<V, E> g, E autonomy, V vOrig, V vDest,
                                                    Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                                    LinkedList<V> shortPath) {
        // Verifica se os vértices de origem e destino são válidos
        if (!g.validVertex(vOrig) || !g.validVertex(vDest)) {
            return null;
        }
        // Limpa o caminho mais curto
        shortPath.clear();
        // Obtém o número de vértices no grafo
        int numVerts = g.numVertices();
        // Array para marcar se um vértice foi visitado
        boolean[] visited = new boolean[numVerts];
        // Arrays para armazenar as chaves dos vértices anteriores e as distâncias acumuladas
        V[] pathKeys = (V[]) new Object[numVerts];
        E[] dist = (E[]) new Object[numVerts];
        // Inicializa as distâncias e as chaves dos vértices
        initializePathDist(numVerts, pathKeys, dist);
        // Chama o método que implementa o algoritmo de Dijkstra com autonomia
        shortestPathDijkstraWithAutonomy(g, autonomy, vOrig, ce, sum, zero, visited, pathKeys, dist);
        // Obtém o comprimento do caminho mais curto até o destino
        E lengthPath = dist[g.key(vDest)];
        // Se o comprimento for diferente de null, obtém o caminho
        if (lengthPath != null) {
            getPath(g, vOrig, vDest, pathKeys, shortPath);
            return lengthPath;
        }
        // Retorna null se não há caminho
        return null;
    }



    /**
     * Nearest neighbor linked list.
     *
     * @param <V>                    the type parameter
     * @param <E>                    the type parameter
     * @param g                      the g
     * @param start                  the start
     * @param targetVertices         the target vertices
     * @param ce                     the ce
     * @param autonomia              the autonomia
     * @param locaisDeCarregamento   the locais de carregamento
     * @param distanceBeetweenLocals the distance beetween locals
     * @param totalDistance          the total distance
     * @param tempoTotalDescarga     the tempo total descarga
     * @return the linked list
     */
    public static <V, E> LinkedList<V> nearestNeighbor(Graph<V, E> g, V start, List<V> targetVertices,
                                                       Comparator<E> ce, int autonomia , List<V> locaisDeCarregamento,List<E> distanceBeetweenLocals,List<Integer> totalDistance,List<Integer> tempoTotalDescarga) {
        LinkedList<V> path = new LinkedList<>();
        Set<V> visited = new HashSet<>();
        path.add(start);
        visited.add(start);

        int currentAutonomy = autonomia;
        int total = 0;


        while (!targetVertices.isEmpty()) {
            V currentVertex = path.getLast();
            E minWeight = null;
            V nextVertex = null;

            for (V neighbor : g.adjVertices(currentVertex)) {
                if (!visited.contains(neighbor) && targetVertices.contains(neighbor)) {
                    Edge<V, E> edge = g.edge(currentVertex, neighbor);
                    if (edge != null) {
                        if (minWeight == null || ce.compare(edge.getWeight(), minWeight) <= 0) {
                            if (minWeight == null || ce.compare(edge.getWeight(), minWeight) < 0) {
                                minWeight = edge.getWeight();
                                nextVertex = neighbor;
                            }
                        }
                    }
                }
            }

            if (nextVertex != null) {


                int distance = (Integer) minWeight;

                // Verifica se a autonomia é suficiente para a distância entre os vértices
                if (distance > currentAutonomy) {
                    // Se não for, é necessário um carregamento
                    locaisDeCarregamento.add(currentVertex);
                    // Resetamos a autonomia para a autonomia máxima após o carregamento
                    currentAutonomy = autonomia;
                }

                // Reduzimos a autonomia com a distância percorrida
                currentAutonomy -= distance;

                path.add(nextVertex);
                visited.add(nextVertex);
                targetVertices.remove(nextVertex);// Remove o vértice da lista de destinos

            } else {
                // Escolhe o vizinho mais próximo, mesmo que não esteja na lista de destinos
                minWeight = null;
                for (V neighbor : g.adjVertices(currentVertex)) {
                    if (!visited.contains(neighbor)) {
                        Edge<V, E> edge = g.edge(currentVertex, neighbor);
                        if (edge != null) {
                            if (minWeight == null || ce.compare(edge.getWeight(), minWeight) < 0) {
                                minWeight = edge.getWeight();
                                nextVertex = neighbor;
                            }
                        }
                    }
                }

                if (nextVertex != null) {

                    int distance = (Integer) minWeight;

                    // Verifica se a autonomia é suficiente para a distância entre os vértices
                    if (distance > currentAutonomy) {
                        // Se não for, é necessário um carregamento
                        locaisDeCarregamento.add(currentVertex);
                        // Resetamos a autonomia para a autonomia máxima após o carregamento
                        currentAutonomy = autonomia;
                    }

                    // Reduzimos a autonomia com a distância percorrida
                    currentAutonomy -= distance;

                    path.add(nextVertex);
                    visited.add(nextVertex);
                } else if (!visited.isEmpty()) {
                    // Revisit the last vertex if there are no unvisited neighbors
                    V lastVertex = path.size() >= 2 ? path.get(path.size() - 2) : null;
                    if (lastVertex != null) {
                        // Revisit the last vertex
                        visited.add(lastVertex);
                        path.removeLast(); // Remove the last vertex
                        currentAutonomy = autonomia; // Reset autonomy after revisiting
                        continue; // Continue to the next iteration
                    } else {
                        // No more vertices to revisit, break the loop
                        break;
                    }
                } else {
                    // No more neighbors or unvisited vertices, break the loop
                    break;
                }
            }
        }
        int totalTempoDescargaVar = tempoTotalDescarga.get(0);
        // Calculate distances only between consecutive locations in the path
        for (int i = 0; i < path.size() - 1; i++) {
            V currentLocation = path.get(i);
            V nextLocation = path.get(i + 1);

            Edge<V, E> edge = g.edge(currentLocation, nextLocation);
            if (edge != null) {
                distanceBeetweenLocals.add((E) edge.getWeight());
                total += (Integer) edge.getWeight();
                Localidades nextLocation1 = (Localidades) nextLocation;

                if ( nextLocation1.isHub()){
                    totalTempoDescargaVar += tempoTotalDescarga.get(0);
                }

            }
        }

        totalDistance.add(total);
        tempoTotalDescarga.set(0,totalTempoDescargaVar);

        return path;

    }

}
