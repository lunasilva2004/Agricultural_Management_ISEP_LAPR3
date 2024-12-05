package application.LAPR3.domain;

import java.util.Date;

public class EstadoFenologico {
    private int idEstadoFenologico;
    private Date dataEvolucao;
    private Date dataMomento;
    private String designacao;
    private int plantaIdPlanta;

    public EstadoFenologico(int idEstadoFenologico, Date dataEvolucao, Date dataMomento, String designacao, int plantaIdPlanta) {
        this.idEstadoFenologico = idEstadoFenologico;
        this.dataEvolucao = dataEvolucao;
        this.dataMomento = dataMomento;
        this.designacao = designacao;
        this.plantaIdPlanta = plantaIdPlanta;
    }

    public int getIdEstadoFenologico() {
        return idEstadoFenologico;
    }

    public Date getDataEvolucao() {
        return dataEvolucao;
    }

    public Date getDataMomento() {
        return dataMomento;
    }

    public String getDesignacao() {
        return designacao;
    }

    public int getPlantaIdPlanta() {
        return plantaIdPlanta;
    }
}
