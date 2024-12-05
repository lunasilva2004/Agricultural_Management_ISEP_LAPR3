package application.LAPR3.domain;

public class Armazem {
    private int idArmazem;
    private int edificioIdEdificio;
    private int fatorProducaoIdFatorProducao;
    private int quantidade;
    private String unidade;

    public Armazem(int idArmazem, int edificioIdEdificio, int fatorProducaoIdFatorProducao, int quantidade, String unidade) {
        this.idArmazem = idArmazem;
        this.edificioIdEdificio = edificioIdEdificio;
        this.fatorProducaoIdFatorProducao = fatorProducaoIdFatorProducao;
        this.quantidade = quantidade;
        this.unidade = unidade;
    }

    public int getIdArmazem() {
        return idArmazem;
    }

    public int getEdificioIdEdificio() {
        return edificioIdEdificio;
    }

    public int getFatorProducaoIdFatorProducao() {
        return fatorProducaoIdFatorProducao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public String getUnidade() {
        return unidade;
    }
}
