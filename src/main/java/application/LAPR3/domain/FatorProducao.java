package application.LAPR3.domain;

public class FatorProducao {
    private int idFatorProducao;
    private String nomeComercial;
    private String fabricante;
    private String formato;
    private String tipoFormulacao;
    private int classificacao;
    private int metodoAplicacao;

    public FatorProducao(int idFatorProducao, String nomeComercial, String fabricante, String formato, String tipoFormulacao, int classificacao, int metodoAplicacao) {
        this.idFatorProducao = idFatorProducao;
        this.nomeComercial = nomeComercial;
        this.fabricante = fabricante;
        this.formato = formato;
        this.tipoFormulacao = tipoFormulacao;
        this.classificacao = classificacao;
        this.metodoAplicacao = metodoAplicacao;
    }

    public int getIdFatorProducao() {
        return idFatorProducao;
    }

    public String getNomeComercial() {
        return nomeComercial;
    }

    public String getFabricante() {
        return fabricante;
    }

    public String getFormato() {
        return formato;
    }

    public String getTipoFormulacao() {
        return tipoFormulacao;
    }

    public int getClassificacao() {
        return classificacao;
    }

    public int getMetodoAplicacao() {
        return metodoAplicacao;
    }
}
