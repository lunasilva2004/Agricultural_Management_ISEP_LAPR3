package application.ESINF.ui;

import application.ESINF.functions.US_EI01_GraphBuilder;
import application.ESINF.utils.ReadFiles;

import java.io.IOException;

public class InitializeGraphUI {
    public void run() {
        try {
            //Ficheiro Pequeno
            ReadFiles.importLocal("src/main/resources/Data_ESINF/locais_small.csv");

            //Ficheiro Grande
            //ReadFiles.importLocal("src/main/resources/Data_ESINF/locais_big.csv");

            // Lê as distâncias do arquivo e armazena no repositório


            //Ficheiro Pequeno
            ReadFiles.importDist("src/main/resources/Data_ESINF/distancias_small.csv");

            // Ficheiro Grande
            //ReadFiles.importDist("src/main/resources/Data_ESINF/distancias_big.csv");



            // Cria o Grafo da rede de transportes
            US_EI01_GraphBuilder g = US_EI01_GraphBuilder.getInstance();
            System.out.println(g.toString());

            System.out.println("Grafo de distâncias criado com sucesso e guardado no repositorio!");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
