package application.LAPR3.utils;

import application.LAPR3.domain.Irrigation;
import application.LAPR3.domain.IrrigationProgram;
import application.LAPR3.repositories.Repositories;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class ReadIrrigationPlan {

    public static void lerPlanoRega(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String linha;

        // Pula o cabeçalho
        reader.readLine();

        while ((linha = reader.readLine()) != null) {
            String[] colunas = linha.split(",");

            if (colunas.length == 7 && colunas[6] != null) {
                // Converte a data e hora do arquivo para objetos LocalDate e LocalTime
                LocalDate data = LocalDate.parse(colunas[0]);
                String setor = colunas[1];
                int duracao = Integer.parseInt(colunas[2]);
                LocalTime horaInicio = LocalTime.parse(colunas[3]);
                LocalTime horaFim = LocalTime.parse(colunas[4]);
                String regularidade = colunas[5];
                String mix = colunas[6];

                Irrigation irrigation = new Irrigation(data, setor, duracao, horaInicio, horaFim, regularidade, mix);
                Repositories.getInstance().getIrrigationProgramRepository().addIrrigation(irrigation);
            }
            if (colunas.length == 6) {
                LocalDate data = LocalDate.parse(colunas[0]);
                String setor = colunas[1];
                int duracao = Integer.parseInt(colunas[2]);
                LocalTime horaInicio = LocalTime.parse(colunas[3]);
                LocalTime horaFim = LocalTime.parse(colunas[4]);
                String regularidade = colunas[5];

                Irrigation irrigation = new Irrigation(data, setor, duracao, horaInicio, horaFim, regularidade, "");
                Repositories.getInstance().getIrrigationProgramRepository().addIrrigation(irrigation);
            }
        }
        reader.close();

    }
}
