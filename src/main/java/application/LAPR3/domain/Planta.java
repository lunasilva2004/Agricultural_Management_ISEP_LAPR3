package application.LAPR3.domain;

public class Planta {
    private int idPlanta;
    private String especie;
    private String nomeComum;
    private String variedade;
    private String permanencia;
    private int sensorIdSensor;


    public Planta(int idPlanta, String especie, String nomeComum, String variedade, String permanencia, int sensorIdSensor) {
        this.idPlanta = idPlanta;
        this.especie = especie;
        this.nomeComum = nomeComum;
        this.variedade = variedade;
        this.permanencia = permanencia;
        this.sensorIdSensor = sensorIdSensor;
    }

    public int getIdPlanta() {
        return idPlanta;
    }

    public String getEspecie() {
        return especie;
    }

    public String getNomeComum() {
        return nomeComum;
    }

    public String getVariedade() {
        return variedade;
    }

    public String getPermanencia() {
        return permanencia;
    }

    public int getSensorIdSensor() {
        return sensorIdSensor;
    }
}
