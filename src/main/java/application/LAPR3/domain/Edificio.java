package application.LAPR3.domain;

public class Edificio {
    private int idEdificio;
    private String designacao;
    private int area;
    private String unidadeArea;
    private String tipoEdificio;


    public Edificio(int idEdificio, String designacao, int area, String unidadeArea, String tipoEdificio) {
        this.idEdificio = idEdificio;
        this.designacao = designacao;
        this.area = area;
        this.unidadeArea = unidadeArea;
        this.tipoEdificio = tipoEdificio;
    }

    public int getIdEdificio() {
        return idEdificio;
    }

    public String getDesignacao() {
        return designacao;
    }

    public int getArea() {
        return area;
    }

    public String getUnidadeArea() {
        return unidadeArea;
    }

    public String getTipoEdificio() {
        return tipoEdificio;
    }
}
