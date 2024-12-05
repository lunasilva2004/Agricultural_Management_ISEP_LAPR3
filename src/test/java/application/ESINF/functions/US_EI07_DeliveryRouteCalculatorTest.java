package application.ESINF.functions;

import application.ESINF.domain.Localidades;
import application.ESINF.domain.StructurePath;
import application.ESINF.graph.map.MapGraph;
import application.ESINF.utils.ReadFiles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class US_EI07_DeliveryRouteCalculatorTest {

    private MapGraph<Localidades, Integer> graphMod;
    private US_EI07_DeliveryRouteCalculator calculator;
    private Localidades startPoint;
    private LocalTime startTime;
    private int autonomy;
    private double velocity;
    private int load;
    private int unload;
    private LinkedList<Localidades> caminhoPercorrido;

    @BeforeEach
    void setUp() throws IOException {
        ReadFiles.importDist("src/main/resources/Data_ESINF/distancias_small.csv");
        ReadFiles.importLocal("src/main/resources/Data_ESINF/locais_small.csv");
        US_EI02_IdealVerticesForNHubs usei02 = new US_EI02_IdealVerticesForNHubs();
        graphMod = usei02.getMapGraph();
        calculator = new US_EI07_DeliveryRouteCalculator();
        caminhoPercorrido = new LinkedList<>();


        startPoint = new Localidades("CT10", 38.39, -9.43, false);
        startTime = LocalTime.of(10, 30);
        autonomy = 10000;
        velocity = 50;
        load = 15;
        unload = 10;


    }

    @Test
    void getPontoPartida() {
        US_EI07_DeliveryRouteCalculator calculator = new US_EI07_DeliveryRouteCalculator();
        String result1 = calculator.getPontoPartida(startPoint);
        assertEquals("NumId: " + startPoint.getNumId() + " Coordenadas: " + startPoint.getCoordenadas(), result1);
    }

    @Test
    void calculateBestDeliveryRoute() {
        US_EI07_DeliveryRouteCalculator calculator = new US_EI07_DeliveryRouteCalculator();
        StructurePath result = calculator.calculaMelhorPercurso(startPoint, startTime, autonomy, velocity, load, unload);
        assertNotNull(result);

        String result1 = calculator.getPontoPartida(startPoint);
        assertTrue(result1.contains(startPoint.getNumId()));
    }

    @Test
    void getAllHubsInCourse() {
        // Example test case
        LinkedList<Localidades> path = new LinkedList<>();
        // Populate the path with relevant locations for the test

        List<Localidades> result = US_EI07_DeliveryRouteCalculator.getAllHubsInCourse(path, new ArrayList<>());

        assertNotNull(result);

    }
    @Test
    void testCalculaMelhorPercurso() {
        int autonomia = 100;
        double velocidade = 60.0;
        int carregar = 30;
        int decarregarMercadoria = 20;

        StructurePath result = calculator.calculaMelhorPercurso(startPoint, startTime, autonomia, velocidade, carregar, decarregarMercadoria);
        assertNotNull(result);
    }

    @Test
    void testGetTempoFinalCompleto() {
        int autonomia = 100;
        double averageVelocity = 60.0;
        int tempoRecarga = 30;
        int tempoDescarga = 20;
        int numeroDescargas = 2;

        LocalTime result = calculator.getTempoFinalCompleto(caminhoPercorrido, startTime, autonomia, averageVelocity, tempoRecarga, tempoDescarga, numeroDescargas);
        assertNotNull(result);
    }


    @Test
    void intToLocalTime() {
        int inputTime1 = 90;
        LocalTime result1 = US_EI07_DeliveryRouteCalculator.intToLocalTime(inputTime1);
        assertEquals(LocalTime.of(1, 30), result1);

        int inputTime2 = 120;
        LocalTime result2 = US_EI07_DeliveryRouteCalculator.intToLocalTime(inputTime2);
        assertEquals(LocalTime.of(2, 0), result2);

        int inputTime3 = 75;
        LocalTime result3 = US_EI07_DeliveryRouteCalculator.intToLocalTime(inputTime3);
        assertEquals(LocalTime.of(1, 15), result3);

    }



    @Test
    void addTime() {
        LocalTime time1 = LocalTime.of(10, 15);
        LocalTime time2 = LocalTime.of(2, 30);

        US_EI07_DeliveryRouteCalculator calculator = new US_EI07_DeliveryRouteCalculator();
        LocalTime result = calculator.addTime(time1, time2);

        // Assuming you expect the result to be 12:45
        LocalTime expected = LocalTime.of(12, 45);

        // Ensure the result is not null
        assertNotNull(result);

        // Check that the result matches the expected time
        assertEquals(expected, result);

    }

    @Test
    void minusTime() {
        LocalTime time1 = LocalTime.of(15, 30);
        LocalTime time2 = LocalTime.of(12, 45);

        US_EI07_DeliveryRouteCalculator calculator = new US_EI07_DeliveryRouteCalculator();
        LocalTime result = calculator.minusTime(time1, time2);

        // Assuming you expect the result to be 2 hours and 45 minutes
        LocalTime expected = LocalTime.of(2, 45);

        // Ensure the result is not null
        assertNotNull(result);

        // Check that the result matches the expected time difference
        assertEquals(expected, result);

    }


    @Test
    void getHubs() {
        US_EI07_DeliveryRouteCalculator calculator = new US_EI07_DeliveryRouteCalculator();
        List<Localidades> hubs = calculator.getHubs();

        // Assuming you know the expected hubs in your test data
        List<String> expectedHubNumIds = Arrays.asList("CT10");

        // Ensure the list is not null
        assertNotNull(hubs);

        // Check the size of the list
        assertEquals(expectedHubNumIds.size(), hubs.size());

        // Check that each expected hub is in the list
        for (String expectedHubNumId : expectedHubNumIds) {
            boolean found = hubs.stream().anyMatch(hub -> hub.getNumId().equals(expectedHubNumId));
            assertTrue(found, "Hub " + expectedHubNumId + " not found in the result");
        }
    }

    @Test
    void analyzeData() {
        US_EI07_DeliveryRouteCalculator calculator = new US_EI07_DeliveryRouteCalculator();
        LinkedList<Localidades> path = null;
        StructurePath result = calculator.analyzeData(autonomy, path);
        assertNotNull(result);
    }
}