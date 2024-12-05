package application.ESINF.functions;

import application.ESINF.utils.ReadFiles;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is a test class for US_EI09_HubNClusters.
 * It contains tests for both the FW and D versions of the class.
 */
class US_EI09_HubNClustersTest {

    // Instance of the FW version of the class
    private US_EI09_HubNClusters hubNClustersFW;
    // Instance of the D version of the class
    private US_EI09_HubNClusters hubNClustersD;

    /**
     * Sets up the test environment before each test.
     * It imports necessary data and initializes the instances of the classes to be tested.
     * @throws IOException if an I/O error occurs
     */
    @BeforeEach
    void setUp() throws IOException {
        ReadFiles.importDist("src/main/resources/Data_ESINF/distancias_big.csv");
        ReadFiles.importLocal("src/main/resources/Data_ESINF/locais_big.csv");
        hubNClustersFW = new US_EI09_HubNClusters();
        hubNClustersD = new US_EI09_HubNClusters();
    }

    /**
     * Tests if the getHubs method returns a non-empty list of hubs.
     * It checks if the list is not null, not empty, and all elements in the list are hubs.
     */
    @Test
    void shouldReturnListOfHubsFW() {
        assertNotNull(hubNClustersFW.getHubs());
        assertFalse(hubNClustersFW.getHubs().isEmpty());
        assertTrue(hubNClustersFW.getHubs().stream().allMatch(localidade -> localidade.isHub()));
    }

    /**
     * Tests if the getClusters method returns a non-empty map of clusters.
     * It checks if the map is not null, not empty, and all keys in the map are hubs.
     */
    @Test
    void shouldReturnClustersFW() {
        assertNotNull(hubNClustersFW.getClustersFW());
        assertFalse(hubNClustersFW.getClustersFW().isEmpty());
        assertTrue(hubNClustersFW.getClustersFW().keySet().stream().allMatch(localidade -> localidade.isHub()));
    }

    /**
     * Tests if the getHubs method returns a non-empty list of hubs.
     * It checks if the list is not empty.
     */
    @Test
    void shouldReturnNonEmptyListOfHubsFW() {
        assertFalse(hubNClustersFW.getHubs().isEmpty());
    }

    /**
     * Tests if the getHubs method returns a non-empty list of hubs.
     * It checks if the list is not null, not empty, and all elements in the list are hubs.
     */
    @Test
    void shouldReturnListOfHubsD() {
        assertNotNull(hubNClustersD.getHubs());
        assertFalse(hubNClustersD.getHubs().isEmpty());
        assertTrue(hubNClustersD.getHubs().stream().allMatch(localidade -> localidade.isHub()));
    }

    /**
     * Tests if the getClusters method returns a non-empty map of clusters.
     * It checks if the map is not null, not empty, and all keys in the map are hubs.
     */
    @Test
    void shouldReturnClustersD() {
        assertNotNull(hubNClustersD.getClustersD());
        assertFalse(hubNClustersD.getClustersD().isEmpty());
        assertTrue(hubNClustersD.getClustersD().keySet().stream().allMatch(localidade -> localidade.isHub()));
    }

    /**
     * Tests if the getHubs method returns a non-empty list of hubs.
     * It checks if the list is not empty.
     */
    @Test
    void shouldReturnNonEmptyListOfHubsD() {
        assertFalse(hubNClustersD.getHubs().isEmpty());
    }

    /**
     * Tests if the getHubs method returns a list of hubs only.
     * It checks if all elements in the list are hubs.
     */
    @Test
    void shouldReturnOnlyHubsInListOfHubsFW() {
        assertTrue(hubNClustersFW.getHubs().stream().allMatch(localidade -> localidade.isHub()));
    }

    /**
     * Tests if the getClusters method returns a non-empty map of clusters.
     * It checks if the map is not empty.
     */
    @Test
    void shouldReturnNonEmptyClustersFW() {
        assertFalse(hubNClustersFW.getClustersFW().isEmpty());
    }

    /**
     * Tests if the getClusters method returns only hubs as keys in clusters.
     * It checks if all keys in the map are hubs.
     */
    @Test
    void shouldReturnOnlyHubsAsKeysInClustersFW() {
        assertTrue(hubNClustersFW.getClustersFW().keySet().stream().allMatch(localidade -> localidade.isHub()));
    }

    /**
     * Tests if the getHubs method returns a list of hubs only.
     * It checks if all elements in the list are hubs.
     */
    @Test
    void shouldReturnOnlyHubsInListOfHubsD() {
        assertTrue(hubNClustersD.getHubs().stream().allMatch(localidade -> localidade.isHub()));
    }

    /**
     * Tests if the getClusters method returns a non-empty map of clusters.
     * It checks if the map is not empty.
     */
    @Test
    void shouldReturnNonEmptyClustersD() {
        assertFalse(hubNClustersD.getClustersD().isEmpty());
    }

    /**
     * Tests if the getClusters method returns only hubs as keys in clusters.
     * It checks if all keys in the map are hubs.
     */
    @Test
    void shouldReturnOnlyHubsAsKeysInClustersD() {
        assertTrue(hubNClustersD.getClustersD().keySet().stream().allMatch(localidade -> localidade.isHub()));
    }

    /**
     * Tests if the getClusters method returns non-empty lists as values in clusters.
     * It checks if all values in the map are not empty.
     */
    @Test
    void shouldReturnNonEmptyListsAsValuesInClustersFW() {
        assertTrue(hubNClustersFW.getClustersFW().values().stream().allMatch(list -> !list.isEmpty()));
    }

    /**
     * Tests if the getClusters method returns non-empty lists as values in clusters.
     * It checks if all values in the map are not empty.
     */
    @Test
    void shouldReturnNonEmptyListsAsValuesInClustersD() {
        assertTrue(hubNClustersD.getClustersD().values().stream().allMatch(list -> !list.isEmpty()));
    }
}