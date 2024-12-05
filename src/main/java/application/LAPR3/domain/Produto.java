package application.LAPR3.domain;

public class Produto {
    private String designacao;
    private int colheitaOperacaoAgricolaIdOperacaoAgricola;
    private int armazemIdArmazem;

    public Produto(String designacao, int colheitaOperacaoAgricolaIdOperacaoAgricola, int armazemIdArmazem) {
        this.designacao = designacao;
        this.colheitaOperacaoAgricolaIdOperacaoAgricola = colheitaOperacaoAgricolaIdOperacaoAgricola;
        this.armazemIdArmazem = armazemIdArmazem;
    }

    public String getDesignacao() {
        return designacao;
    }

    public int getColheitaOperacaoAgricolaIdOperacaoAgricola() {
        return colheitaOperacaoAgricolaIdOperacaoAgricola;
    }

    public int getArmazemIdArmazem() {
        return armazemIdArmazem;
    }
}
