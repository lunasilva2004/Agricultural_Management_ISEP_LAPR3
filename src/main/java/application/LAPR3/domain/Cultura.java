package application.LAPR3.domain;

import java.util.Date;

public class Cultura {
    private int idCultura;
    private int plantaIdPlanta;
    private int parcelaIdParcela;
    private String nomeCultura;
    private Date dataInicial;
    private Date dataFinal;
    private double quantidade;
    private String unidade;
    private String tipo;

    public Cultura(int idCultura, int plantaIdPlanta, int parcelaIdParcela, String nomeCultura, Date dataInicial, Date dataFinal, double quantidade, String unidade, String tipo) {
        this.idCultura = idCultura;
        this.plantaIdPlanta = plantaIdPlanta;
        this.parcelaIdParcela = parcelaIdParcela;
        this.nomeCultura = nomeCultura;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.quantidade = quantidade;
        this.unidade = unidade;
        this.tipo = tipo;
    }

    public int getIdCultura() {
        return idCultura;
    }

    public int getPlantaIdPlanta() {
        return plantaIdPlanta;
    }

    public int getParcelaIdParcela() {
        return parcelaIdParcela;
    }

    public String getNomeCultura() {
        return nomeCultura;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public String getUnidade() {
        return unidade;
    }

    public String getTipo() {
        return tipo;
    }
}
