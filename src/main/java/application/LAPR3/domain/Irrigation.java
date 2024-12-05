package application.LAPR3.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Irrigation {

    private LocalDate data;
    private String setor;
    private int duracao;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private String regularidade;
    private String mix;

    public String getMix() {return mix;}
    public LocalDate getData() {
        return data;
    }

    public String getSetor() {
        return setor;
    }

    public int getDuracao() {
        return duracao;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    public String getRegularidade() {
        return regularidade;
    }

    public Irrigation(LocalDate data, String setor, int duracao, LocalTime horaInicio, LocalTime horaFim, String regularidade, String mix) {
        this.data = data;
        this.setor = setor;
        this.duracao = duracao;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.regularidade = regularidade;
        this.mix = mix;
    }
    public Irrigation(LocalDate data, String setor, int duracao, String horaInicio, String horaFim, String mix) {
        this.data = data;
        this.setor = setor;
        this.duracao = duracao;
        this.horaInicio = LocalTime.parse(horaInicio);
        this.horaFim = LocalTime.parse(horaFim);
        this.mix = mix;
    }

    // Adicione getters e setters conforme necessário
}
