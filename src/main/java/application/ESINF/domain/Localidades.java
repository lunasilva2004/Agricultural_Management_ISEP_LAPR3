package application.ESINF.domain;

import java.util.Objects;

/**
 * The type Hub.
 */
public class Localidades {
    private String numId;
    private Coordenadas coordenadas;
    private boolean isHub = false;
    private Horario horario;

    public Localidades(String numId, Coordenadas coordenadas, boolean isHub, Horario horario) {
        this.numId = numId;
        this.coordenadas = coordenadas;
        this.isHub = isHub;
        this.horario = horario;
    }


    /**
     * Instantiates a new Hub.
     *
     * @param numId the num id
     * @param lat   the lat
     * @param lon   the lon
     */
    public Localidades(String numId, Double lat, Double lon) {
        this.numId = numId;
        this.coordenadas = new Coordenadas(lat, lon);
    }

    public Localidades(String numId, Double lat, Double lon, boolean isHub) {
        this.numId = numId;
        this.coordenadas = new Coordenadas(lat, lon);
        this.isHub =false;
    }

    public String getNumId() {
        return numId;
    }

    public void setNumId(String numId) {
        this.numId = numId;
    }

    public Coordenadas getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(Coordenadas coordenadas) {
        this.coordenadas = coordenadas;
    }

    public boolean isHub() {
        return isHub;
    }

    public void setHub(boolean hub) {
        isHub = hub;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }


    /**
     * Instantiates a new Hub.
     *
     * @param numId the num id
     */
    public Localidades(String numId) {
        this.numId = numId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Localidades localidades = (Localidades) o;
        return Objects.equals(numId, localidades.numId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numId);
    }

    @Override
    public String toString() {
        String info = String.format("%s, coordenadas=%s", numId, coordenadas);

        if (isHub()) {
            // Extraia o número de empregados do numId (considerando que seja um número inteiro no final)
            try {
                int numeroEmpregados = Integer.parseInt(numId.replaceAll("[^0-9]", ""));
                info += String.format(", %d empregados", numeroEmpregados);
            } catch (NumberFormatException e) {
                // Lida com o caso em que não há um número inteiro no final do numId
                info += ", Número de empregados desconhecido";
            }
        }

        return info;
    }

}