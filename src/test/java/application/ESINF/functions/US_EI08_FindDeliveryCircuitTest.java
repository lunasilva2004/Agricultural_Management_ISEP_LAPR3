package application.ESINF.functions;

import application.ESINF.domain.Localidades;
import application.ESINF.graph.map.MapGraph;
import application.ESINF.utils.ReadFiles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalTime;
import java.util.*;

import static org.junit.Assert.*;

class US_EI08_FindDeliveryCircuitTest {

    private MapGraph<Localidades, Integer> graphMod;
    private Localidades startPoint;
    private int autonomy;
    private int velocity;
    private int tempoDeCarregamento;
    private int tempoDeDescarga;

    private int nHubs;

    @BeforeEach
    void setUp() throws IOException {
        ReadFiles.importLocal("src/main/resources/Data_ESINF/locais_small.csv");
        ReadFiles.importDist("src/main/resources/Data_ESINF/distancias_small.csv");
        US_EI02_IdealVerticesForNHubs usei02 = new US_EI02_IdealVerticesForNHubs();
        graphMod = US_EI01_GraphBuilder.getInstance().getDistribuicao();
        Map<Localidades, Integer> influence = usei02.calculateInfluence(graphMod);
        Map<Localidades, Integer> proximity = usei02.calculateProximity(graphMod);
        Map<Localidades, Integer> centrality = usei02.calculateCentrality(graphMod);

        Map<Localidades, List<Integer>> combinedMap = new HashMap<>();
        for (Localidades localidades : graphMod.vertices()) {
            List<Integer> values = new ArrayList<>();
            values.add(centrality.get(localidades));
            values.add(influence.get(localidades));
            values.add(proximity.get(localidades));
            combinedMap.put(localidades, values);
        }

        Map<Localidades, List<Integer>> topNMap = usei02.getTopNMap(combinedMap, 10);

        // Call the setHubs method
        usei02.setHubs(topNMap, 10);

        startPoint = graphMod.vertex(15);
        autonomy = 100000;
        velocity = 90;
        tempoDeCarregamento = 15;
        tempoDeDescarga = 10;
        nHubs = 5;


    }



    @Test
    void findDeliveryCircuit() {



        US_EI08_FindDeliveryCircuit us_ei08_findDeliveryCircuit = new US_EI08_FindDeliveryCircuit();

        Map<String, Object> result =  us_ei08_findDeliveryCircuit.findDeliveryCircuit(
                graphMod, nHubs, velocity, autonomy,startPoint,
                tempoDeDescarga, tempoDeCarregamento
        );



        assertNotNull(result);
        assertEquals(startPoint, result.get("localOrigem"));

        assertNotNull(result.get("locaisPassagem"));

        @SuppressWarnings("unchecked")
        LinkedList<Localidades> locaisPassagem = (LinkedList<Localidades>) result.get("locaisPassagem");;

        assertEquals(12,locaisPassagem.size());


        assertNotNull(result.get("distanciaEntreLocais"));

        @SuppressWarnings("unchecked")
        List<Integer> distanciaEntreLocais = (List<Integer>) result.get("distanciaEntreLocais");

        assertEquals(11,distanciaEntreLocais.size());


        assertTrue((int) result.get("numeroCarregamentos") >= 0);
        assertTrue((int)result.get("numeroCarregamentos") == 8);

        assertTrue((int) result.get("tempoPercurso") >= 0);
        assertTrue((int) result.get("tempoPercurso") == 11245);

        assertNotNull(result.get("loadingLocations"));
        @SuppressWarnings("unchecked")
        List<String> loadingLocations = (List<String>) result.get("loadingLocations");
        assertEquals(8,loadingLocations.size());


        assertTrue((int) result.get("tempoTotalDescarga") >= 0);
        assertTrue((int)result.get("tempoTotalDescarga") == 80);

        assertTrue((int) result.get("tempoTotalCarregamento") >= 0);
        assertTrue((int)result.get("tempoTotalCarregamento") == 135);


        assertTrue((int) result.get("tempoTotal") >= 0);
        assertTrue((int)result.get("tempoTotal") == 11460);

    }

    @Test
    void calculateLoadingHubs() {

        US_EI08_FindDeliveryCircuit us_ei08_findDeliveryCircuit = new US_EI08_FindDeliveryCircuit();

        LinkedList<Localidades> shortestPath = us_ei08_findDeliveryCircuit.calcularCaminhoMaisCurto(graphMod,new Localidades("CT13", 39.24, -8.69, true),startPoint);

        List<Integer> distanceBeetweenLocals = new ArrayList<>();

        List<Integer> totalDistance = new ArrayList<>();
        totalDistance.add(881026);

        List<Integer> tempoDescargaVolta = new ArrayList<>();
        tempoDescargaVolta.add(60);

        List<Localidades> loadingHubs =  us_ei08_findDeliveryCircuit.calculateLoadingHubs(shortestPath,graphMod,autonomy,distanceBeetweenLocals,totalDistance,tempoDeDescarga,tempoDescargaVolta);

        assertTrue(loadingHubs.contains(new Localidades("CT10", 39.74, -8.69, true)));

        assertEquals(Optional.of(1012058), Optional.ofNullable(totalDistance.get(0)));

        assertTrue(loadingHubs.size() == 1);


    }

    @Test
    void getTopHubs() {

        US_EI08_FindDeliveryCircuit us_ei08_findDeliveryCircuit = new US_EI08_FindDeliveryCircuit();
        List<Localidades> topNHubs =  us_ei08_findDeliveryCircuit.getTopHubs(graphMod,5);

        assertTrue(topNHubs.contains(new Localidades("CT17", 40.67, -7.92, true)));
        assertTrue(topNHubs.contains(new Localidades("CT16", 41.30, -7.74, true)));
        assertTrue(topNHubs.contains(new Localidades("CT13", 39.24, -8.69, true)));
        assertTrue(topNHubs.contains(new Localidades("CT12", 41.15, -8.61, true)));
        assertTrue(topNHubs.contains(new Localidades("CT11", 39.32, -7.42, true)));
        assertTrue(topNHubs.size() == 5);
    }

    @Test
    void calcularCaminhoMaisCurto() {

        US_EI08_FindDeliveryCircuit us_ei08_findDeliveryCircuit = new US_EI08_FindDeliveryCircuit();
        LinkedList<Localidades> topNHubs =  us_ei08_findDeliveryCircuit.calcularCaminhoMaisCurto(graphMod,new Localidades("CT13", 39.24, -8.69, true),startPoint);

        assertTrue(topNHubs.contains(new Localidades("CT10", 39.74, -8.69, true)));
        assertTrue(topNHubs.contains(new Localidades("CT6", 40.21, -8.43, true)));
        assertTrue(topNHubs.contains(new Localidades("CT13", 39.24, -8.69, true)));
        assertTrue(topNHubs.size() == 3);



    }
}