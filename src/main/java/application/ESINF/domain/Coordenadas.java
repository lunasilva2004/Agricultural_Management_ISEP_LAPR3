package application.ESINF.domain;

/**
 * The type Coordenadas.
 */
public class Coordenadas {
    private double lat;
    private double lon;

    /**
     * Instantiates a new coordenadas.
     *
     * @param lat the lat
     * @param lon the lon
     */
    public Coordenadas(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    /**
     * Gets lat.
     *
     * @return the lat
     */
    public double getLat() {
        return lat;
    }

    /**
     * Sets lat.
     *
     * @param lat the lat
     */
    public void setLat(double lat) {
        this.lat = lat;
    }

    /**
     * Gets lon.
     *
     * @return the lon
     */
    public double getLon() {
        return lon;
    }

    /**
     * Sets lon.
     *
     * @param lon the lon
     */
    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return String.format("(Latitude: %.2f; Longitude: %.2f)", lat, lon);
    }
}
