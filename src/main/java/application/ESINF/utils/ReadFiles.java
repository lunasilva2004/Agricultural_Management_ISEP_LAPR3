package application.ESINF.utils;

import application.ESINF.domain.Horario;
import application.ESINF.domain.Localidades;
import application.ESINF.functions.US_EI01_GraphBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;

/**
 * The type Read files.
 */
public class ReadFiles {
    /**
     * Import dist.
     *
     * @param Distancias the distancias
     * @throws IOException the io exception
     */
    public static void importDist(String Distancias) throws IOException {
        US_EI01_GraphBuilder rede = US_EI01_GraphBuilder.getInstance();

        BufferedReader reader = new BufferedReader(new FileReader(Distancias));
        String currentLine;
        reader.readLine();
        String[] line;

        while ((currentLine = reader.readLine()) != null) {
            line = currentLine.split(",");
            rede.addRoute(new Localidades(line[0]), new Localidades(line[1]), Integer.parseInt(line[2]));
        }
    }

    /**
     * Import local.
     *
     * @param locais the locais
     * @throws IOException the io exception
     */
    public static void importLocal(String locais) throws IOException {
        US_EI01_GraphBuilder rede = US_EI01_GraphBuilder.getInstance();

        BufferedReader reader = new BufferedReader(new FileReader(locais));
        String currentLine;
        reader.readLine();
        String[] line;

        while ((currentLine = reader.readLine()) != null) {
            line = currentLine.split(",");
            rede.addLocalidade(line[0], Double.parseDouble(line[1]), Double.parseDouble(line[2]));
        }
    }

    public static void importOperatingHours(String horarios) throws IOException {
        US_EI01_GraphBuilder rede = US_EI01_GraphBuilder.getInstance();

        BufferedReader reader = new BufferedReader(new FileReader(horarios));
        String currentLine;
        reader.readLine(); // Ignora a primeira linha (cabeçalho, se houver)
        String[] line;

        while ((currentLine = reader.readLine()) != null) {
            line = currentLine.split(",");
            if (line.length >= 3) {
                String hubId = line[0];
                String openingTime = line[1];
                String closingTime = line[2];

                // Verificar se o hub já existe
                Localidades localidades = rede.getHubById(hubId);

                if (localidades != null) {
                    if (localidades.getHorario() == null){
                        localidades.setHorario(new Horario(LocalTime.parse(openingTime), LocalTime.parse(closingTime)));
                    }else {
                        localidades.getHorario().setOpenTime(LocalTime.parse(openingTime));
                        localidades.getHorario().setCloseTime(LocalTime.parse(closingTime));
                    }

                    System.out.println("Hub ID: " + localidades.getNumId() +
                            ", Opening Time: " + openingTime +
                            ", Closing Time: " + closingTime);
                } else {
                    // Hub não existe, emite mensagem de erro ou cria um novo hub
                    System.out.println("Erro: Hub " + hubId + " não encontrado.");
                }
            }else {
                System.out.println("Erro: Formato inválido na linha - " + currentLine);
            }
        }
    }
}
