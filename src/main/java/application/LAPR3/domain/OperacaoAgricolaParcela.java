package application.LAPR3.domain;

public class OperacaoAgricolaParcela {
    private int operacaoAgricolaIdOperacaoAgricola;
    private int parcelaIdParcela;

    public OperacaoAgricolaParcela(int operacaoAgricolaIdOperacaoAgricola, int parcelaIdParcela) {
        this.operacaoAgricolaIdOperacaoAgricola = operacaoAgricolaIdOperacaoAgricola;
        this.parcelaIdParcela = parcelaIdParcela;
    }

    public int getOperacaoAgricolaIdOperacaoAgricola() {
        return operacaoAgricolaIdOperacaoAgricola;
    }

    public int getParcelaIdParcela() {
        return parcelaIdParcela;
    }
}
