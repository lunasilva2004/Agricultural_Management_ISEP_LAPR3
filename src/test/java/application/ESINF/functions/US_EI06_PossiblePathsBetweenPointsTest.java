package application.ESINF.functions;

import application.ESINF.domain.Localidades;
import application.ESINF.domain.PathInfo;
import application.ESINF.graph.map.MapGraph;
import application.ESINF.utils.ReadFiles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class US_EI06_PossiblePathsBetweenPointsTest {
    @Test
    void testGetPossiblePathsBetweenTwoPoints() {
        MapGraph<Localidades, Integer> graph = new MapGraph<>(false);   // false para grafo nao orientado
        Localidades localidadesA = new Localidades("A");
        Localidades localidadesB = new Localidades("B");
        Localidades localidadesC = new Localidades("C");
        Localidades localidadesD = new Localidades("D");
        Localidades localidadesE = new Localidades("E");
        Localidades localidadesF = new Localidades("F");

        graph.addVertex(localidadesA);
        graph.addVertex(localidadesB);
        graph.addVertex(localidadesC);
        graph.addVertex(localidadesD);
        graph.addVertex(localidadesE);
        graph.addVertex(localidadesF);

        graph.addEdge(localidadesA, localidadesB, 7);
        graph.addEdge(localidadesB, localidadesC, 3);
        graph.addEdge(localidadesB, localidadesD, 5);
        graph.addEdge(localidadesC, localidadesD, 6);
        graph.addEdge(localidadesD, localidadesE, 2);



        TreeMap<LinkedList<Localidades>, PathInfo> paths = US_EI06_PossiblePathsBetweenPoints.getPathsBetweenTwoPoints(graph, localidadesA, localidadesE, 18, 10.0);

        //POSSIBLE PATHS:
        // A -> B -> C -> D -> E
        // A -> B -> D -> E

        assertNotNull(paths);
        System.out.println(paths);
        assertEquals(2, paths.size());  //numero de keys no treemap é o numero de paths encontrado
                  //2 paths possiveis no grafo criado acima
    }
}
