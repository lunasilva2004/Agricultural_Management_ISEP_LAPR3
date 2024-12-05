package application.ESINF.domain;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class StructurePath {

    // Atributos da classe
    private int distanciaTotal;
    private LinkedList<Localidades> percurso;
    private ArrayList<Integer> carregamentos;
    private Map<Localidades, List<LocalTime>> temposDeChegada;
    private boolean flag;

    // Construtor para casos em que temposDeChegada não é fornecido
    public StructurePath(int distanciaTotal, LinkedList<Localidades> percurso, ArrayList<Integer> carregamentos, boolean flag) {
        this.distanciaTotal = distanciaTotal;
        this.percurso = percurso;
        this.carregamentos = carregamentos;
        this.flag = flag;
    }

    // Construtor para casos em que temposDeChegada é fornecido
    public StructurePath(int distanciaTotal, LinkedList<Localidades> percurso, ArrayList<Integer> carregamentos, Map<Localidades, List<LocalTime>> temposDeChegada, boolean flag) {
        this.distanciaTotal = distanciaTotal;
        this.percurso = percurso;
        this.carregamentos = carregamentos;
        this.temposDeChegada = temposDeChegada;
        this.flag = true;
    }

    // Métodos getters e setters para os atributos
    public int getDistanciaTotal() {
        return distanciaTotal;
    }

    public void setDistanciaTotal(int distanciaTotal) {
        this.distanciaTotal = distanciaTotal;
    }

    public LinkedList<Localidades> getPercurso() {
        return percurso;
    }

    public void setPercurso(LinkedList<Localidades> percurso) {
        this.percurso = percurso;
    }

    public ArrayList<Integer> getCarregamentos() {
        return carregamentos;
    }

    public void setCarregamentos(ArrayList<Integer> carregamentos) {
        this.carregamentos = carregamentos;
    }

    public Map<Localidades, List<LocalTime>> getTemposDeChegada() {
        return temposDeChegada;
    }

    public void setTemposDeChegada(Map<Localidades, List<LocalTime>> temposDeChegada) {
        this.temposDeChegada = temposDeChegada;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    // Método equals sobrescrito para comparar objetos da classe StructurePath
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        StructurePath c = (StructurePath) o;
        // Comparação baseada em flag, carregamentos, distanciaTotal e percurso
        if(c.isFlag() == (this.isFlag() && c.carregamentos.equals(this.carregamentos) && c.distanciaTotal == this.distanciaTotal && c.percurso.equals(this.percurso))){
            return true;
        }
        return false;
    }
}
