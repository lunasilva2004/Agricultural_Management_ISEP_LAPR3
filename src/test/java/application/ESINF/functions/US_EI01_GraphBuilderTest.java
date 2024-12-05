package application.ESINF.functions;

import application.ESINF.domain.Coordenadas;
import application.ESINF.domain.Localidades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class US_EI01_GraphBuilderTest {
    private US_EI01_GraphBuilder graphBuilder;

    @BeforeEach
    public void setUp() {
        // Initialize a new instance of US_EI01_GraphBuilder before each test

        if (US_EI01_GraphBuilder.getInstance()!= null){
            graphBuilder = US_EI01_GraphBuilder.getInstance();
        }
    }

    @Test
    public void testAddHub() {
        // Add hubs to the graph
        boolean hub1Added = graphBuilder.addLocalidade("H12", 40.7128, -74.0060);
        boolean hub2Added = graphBuilder.addLocalidade("H22", 34.0522, -118.2437);
        boolean hub3Added = graphBuilder.addLocalidade("H32", 41.8781, -87.6298);

        // Check if hubs were added successfully
        assertNotNull(graphBuilder);
        assertTrue(hub1Added);
        assertTrue(hub2Added);
        assertTrue(hub3Added);

        // Check if the graph contains the added hubs
        assertTrue(graphBuilder.getDistribuicao().vertices().contains(new Localidades("H12", new Coordenadas(40.7128, -74.0060), true, null)));
        assertTrue(graphBuilder.getDistribuicao().vertices().contains(new Localidades("H22", new Coordenadas(34.0522, -118.2437), true, null)));
        assertTrue(graphBuilder.getDistribuicao().vertices().contains(new Localidades("H32", new Coordenadas(41.8781, -87.6298), true, null)));

    }

    @Test
    public void testAddRoute() {
        // Add hubs to the graph
        graphBuilder.addLocalidade("H40", 40.7128, -74.0060);
        graphBuilder.addLocalidade("H89", 34.0522, -118.2437);

        // Add a route between hubs
        boolean routeAdded = graphBuilder.addRoute(
                new Localidades("H40", new Coordenadas(40.7128, -74.0060), true, null),
                new Localidades("H89", new Coordenadas(34.0522, -118.2437), true, null),
                100 // Distance of the route
        );

        // Check if the route was added successfully
        assertTrue(routeAdded);

        // Check if the graph contains the added route
        assertNotNull(graphBuilder.getDistribuicao().edge(
                new Localidades("H1", new Coordenadas(40.7128, -74.0060), true, null),
                new Localidades("H2", new Coordenadas(34.0522, -118.2437), true, null)
        ));

    }

    @Test
    public void testToString() {
        // Add hubs to the graph
        graphBuilder.addLocalidade("H1", 40.7128, -74.0060);
        graphBuilder.addLocalidade("H2", 34.0522, -118.2437);

        // Add a route between hubs
        graphBuilder.addRoute(
                new Localidades("H1", new Coordenadas(40.7128, -74.0060), true, null),
                new Localidades("H2", new Coordenadas(34.0522, -118.2437), true, null),
                100 // Distance of the route
        );

        // Check the string representation of the graph
        assertNotNull(graphBuilder.toString());
    }

    @Test
    public void testAddDuplicateHub() {
        assertTrue(graphBuilder.addLocalidade("H1", 40.7128, -74.0060));
        assertFalse(graphBuilder.addLocalidade("H1", 35.6895, 139.6917));  // Adding hub with same identifier
    }

    @Test
    public void testAddDuplicateRoute() {
        graphBuilder.addLocalidade("H35", 40.7128, -74.0060);
        graphBuilder.addLocalidade("H25", 34.0522, -118.2437);

        assertTrue(graphBuilder.addRoute(new Localidades("H35"), new Localidades("H25"), 100));
        assertFalse(graphBuilder.addRoute(new Localidades("H35"), new Localidades("H25"), 150));  // Adding duplicate route

    }

}
