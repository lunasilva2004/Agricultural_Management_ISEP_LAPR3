package application.LAPR3.domain;

public class Parcela {
    private int idParcela;
    private String designacao;
    private int area;
    private String unidadeArea;

    public Parcela(int idParcela, String designacao, int area, String unidadeArea) {
        this.idParcela = idParcela;
        this.designacao = designacao;
        this.area = area;
        this.unidadeArea = unidadeArea;
    }

    public int getIdParcela() {
        return idParcela;
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
}
