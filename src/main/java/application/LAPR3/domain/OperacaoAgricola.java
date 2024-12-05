package application.LAPR3.domain;

public class OperacaoAgricola {
    private int idOperacaoAgricola;
    private String tipoOperacaoDesignacao;
    private int quantidade;
    private int data;
    private String unidade;

    public OperacaoAgricola(int idOperacaoAgricola, String tipoOperacaoDesignacao, int quantidade, int data, String unidade) {
        this.idOperacaoAgricola = idOperacaoAgricola;
        this.tipoOperacaoDesignacao = tipoOperacaoDesignacao;
        this.quantidade = quantidade;
        this.data = data;
        this.unidade = unidade;
    }

    public int getIdOperacaoAgricola() {
        return idOperacaoAgricola;
    }

    public String getTipoOperacaoDesignacao() {
        return tipoOperacaoDesignacao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public int getData() {
        return data;
    }

    public String getUnidade() {
        return unidade;
    }
}
