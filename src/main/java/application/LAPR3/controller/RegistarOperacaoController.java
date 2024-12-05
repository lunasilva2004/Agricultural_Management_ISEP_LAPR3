package application.LAPR3.controller;

import application.LAPR3.domain.Irrigation;
import application.LAPR3.repositories.IrrigationProgramRepository;
import application.LAPR3.repositories.OperacaoAgricolaRepository;
import application.LAPR3.repositories.Repositories;


import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;

public class RegistarOperacaoController {
    private OperacaoAgricolaRepository operacaoAgricolaRepository;
    private IrrigationProgramRepository irrigationProgramRepository;

    public RegistarOperacaoController (){
        getOperacaoAgricolaRepository();
        getIrrigationProgramRepository();
    }

    private IrrigationProgramRepository getIrrigationProgramRepository() {
        if (Objects.isNull(irrigationProgramRepository)) {
            Repositories repositories = Repositories.getInstance();
            irrigationProgramRepository = repositories.getIrrigationProgramRepository();
        }
        return irrigationProgramRepository;
    }

    private OperacaoAgricolaRepository getOperacaoAgricolaRepository() {
        if (Objects.isNull(operacaoAgricolaRepository)) {
            Repositories repositories = Repositories.getInstance();
            operacaoAgricolaRepository = repositories.getOperacaoAgricolaRepository();
        }
        return operacaoAgricolaRepository;
    }

    public void podaRegister(int quantidade, Date date, String unidade, String parcela,String plantacao,String nomeComum,String variedade){
        try {
            operacaoAgricolaRepository.podaRegister(quantidade,date,unidade,parcela,plantacao,nomeComum,variedade);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int semeaduraRegister(String tipoOperacao, int quantidade, int area, Date date, String unidade, int culturaID, int plantaID, int parcelaID) {
        return operacaoAgricolaRepository.semeaduraRegister(tipoOperacao, quantidade,area, date, unidade, culturaID, plantaID, parcelaID);
    }

    public int mondaRegister(int operacaoID, String tipoOperacao, int quantidade, String date, String unidade, int culturaID, int plantaID, int parcelaID) {
        return operacaoAgricolaRepository.mondaRegister(operacaoID, tipoOperacao, quantidade, date, unidade, culturaID, plantaID, parcelaID);
    }

    public int colheitaRegister(String date, String tipoOperacao, int quantidade, String unidade, int culturaID, int plantaID, int parcelaID, String produto) {
        return operacaoAgricolaRepository.colheitaRegister(date, tipoOperacao, quantidade, unidade, culturaID, plantaID, parcelaID, produto);
    }

    public int wateringRegister(Irrigation irrigationDay) throws SQLException {
        return irrigationProgramRepository.wateringRegisterWithFertirrega(irrigationDay);
    }

    public int registarReceira(String designacaoReceita, int[] fatoresProducao) {
        return operacaoAgricolaRepository.fertirregaRegister(designacaoReceita, fatoresProducao);
    }
}
