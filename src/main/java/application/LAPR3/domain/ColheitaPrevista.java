package application.LAPR3.domain;

import java.util.Date;

public class ColheitaPrevista {
    private int culturaIdCultura;
    private int culturaPlantaIdPlanta;
    private int culturaParcelaIdParcela;
    private Date culturaDataInicial;
    private String culturaNomeCultura;
    private int idColheitaPrevista;
    private String semana;
    private int quantidade;
    private String unidade;

    public ColheitaPrevista(int culturaIdCultura, int culturaPlantaIdPlanta, int culturaParcelaIdParcela, Date culturaDataInicial, String culturaNomeCultura, int idColheitaPrevista, String semana, int quantidade, String unidade) {
        this.culturaIdCultura = culturaIdCultura;
        this.culturaPlantaIdPlanta = culturaPlantaIdPlanta;
        this.culturaParcelaIdParcela = culturaParcelaIdParcela;
        this.culturaDataInicial = culturaDataInicial;
        this.culturaNomeCultura = culturaNomeCultura;
        this.idColheitaPrevista = idColheitaPrevista;
        this.semana = semana;
        this.quantidade = quantidade;
        this.unidade = unidade;
    }

    public int getCulturaIdCultura() {
        return culturaIdCultura;
    }

    public int getCulturaPlantaIdPlanta() {
        return culturaPlantaIdPlanta;
    }

    public int getCulturaParcelaIdParcela() {
        return culturaParcelaIdParcela;
    }

    public Date getCulturaDataInicial() {
        return culturaDataInicial;
    }

    public String getCulturaNomeCultura() {
        return culturaNomeCultura;
    }

    public int getIdColheitaPrevista() {
        return idColheitaPrevista;
    }

    public String getSemana() {
        return semana;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public String getUnidade() {
        return unidade;
    }
}
