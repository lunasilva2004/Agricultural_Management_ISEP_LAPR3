package application.LAPR3.domain;

public class FichaTecnica {
    private int idFichaTecnica;
    private int fatorProducaoIdFatorProducao;
    private String substanciaComponenteQuimico;

    public FichaTecnica(int idFichaTecnica, int fatorProducaoIdFatorProducao, String substanciaComponenteQuimico) {
        this.idFichaTecnica = idFichaTecnica;
        this.fatorProducaoIdFatorProducao = fatorProducaoIdFatorProducao;
        this.substanciaComponenteQuimico = substanciaComponenteQuimico;
    }

    public int getIdFichaTecnica() {
        return idFichaTecnica;
    }

    public int getFatorProducaoIdFatorProducao() {
        return fatorProducaoIdFatorProducao;
    }

    public String getSubstanciaComponenteQuimico() {
        return substanciaComponenteQuimico;
    }
}
