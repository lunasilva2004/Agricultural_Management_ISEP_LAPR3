package application.ESINF.ui;

import application.ESINF.domain.Localidades;
import application.ESINF.functions.US_EI01_GraphBuilder;
import application.ESINF.functions.US_EI03_VehicleRouteAutonomy;
import application.ESINF.functions.US_EI08_FindDeliveryCircuit;
import application.ESINF.graph.Algorithms;
import application.ESINF.graph.Graph;
import org.apache.commons.math3.util.Pair;

import java.util.*;

public class FindDeliveryCircuitUI {

    public void run() {

        US_EI08_FindDeliveryCircuit us_ei08_findDeliveryCircuit = new US_EI08_FindDeliveryCircuit();
        Scanner scanner = new Scanner(System.in);
        int autonomia;
        int verticeEscolhido;
        int velMedia;
        int tempoDeDescarga;
        int tempoDeCarregamento;
        int nHubs = 5;


        // Loop até que o usuário insira um valor válido
        while (true) {
            System.out.print("Insira a autonomia do veículo (metros): ");

            // Verifica se o valor inserido é um número inteiro
            if (scanner.hasNextInt()) {
                autonomia = scanner.nextInt();
                break; // Sai do loop se um valor válido for inserido
            } else {
                System.out.println("Por favor, insira um valor válido para a autonomia.");
                scanner.next(); // Limpa o buffer do scanner
            }
        }

        // Loop até que o usuário insira um valor válido
        while (true) {
            System.out.print("Insira o tempo de descarga do veículo (segundos): ");

            // Verifica se o valor inserido é um número inteiro
            if (scanner.hasNextInt()) {
                tempoDeDescarga = scanner.nextInt();
                break; // Sai do loop se um valor válido for inserido
            } else {
                System.out.println("Por favor, insira um valor válido para o tempo de descarga.");
                scanner.next(); // Limpa o buffer do scanner
            }
        }


        // Loop até que o usuário insira um valor válido
        while (true) {
            System.out.print("Insira o tempo de carregamento do veículo (segundos): ");

            // Verifica se o valor inserido é um número inteiro
            if (scanner.hasNextInt()) {
                tempoDeCarregamento = scanner.nextInt();
                break; // Sai do loop se um valor válido for inserido
            } else {
                System.out.println("Por favor, insira um valor válido para o tempo de carregamento.");
                scanner.next(); // Limpa o buffer do scanner
            }
        }



        // Loop até que o usuário insira um valor válido
        while (true) {
            System.out.print("Insira a velocidade média do veículo (km/s): ");

            // Verifica se o valor inserido é um número inteiro
            if (scanner.hasNextInt()) {
                velMedia = scanner.nextInt();
                break; // Sai do loop se um valor válido for inserido
            } else {
                System.out.println("Por favor, insira um valor válido para a velocidade média.");
                scanner.next(); // Limpa o buffer do scanner
            }
        }

        Graph<Localidades, Integer> distribuicaoGraph = US_EI01_GraphBuilder.getInstance().getDistribuicao();
        List<Localidades> todosVertices = new ArrayList<>(distribuicaoGraph.vertices());

        // Exibindo um menu para o usuário escolher um vértice
        System.out.println("Escolha um vértice do grafo:");
        for (int i = 0; i < todosVertices.size(); i++) {
            System.out.println((i + 1) + ". " + todosVertices.get(i));
        }

        // Loop até que o usuário escolha um vértice válido
        while (true) {
            System.out.print("Digite o número correspondente ao vértice escolhido: ");
            if (scanner.hasNextInt()) {
                int escolha = scanner.nextInt();
                if (escolha >= 1 && escolha <= todosVertices.size()) {
                    verticeEscolhido = escolha - 1;  // Ajustando para o índice da lista
                    break;
                } else {
                    System.out.println("Por favor, escolha um número válido.");
                }
            } else {
                System.out.println("Por favor, insira um número válido.");
                scanner.next(); // Limpa o buffer do scanner
            }
        }





        Map<String, Object> result = us_ei08_findDeliveryCircuit.findDeliveryCircuit(US_EI01_GraphBuilder.getInstance().getDistribuicao(), nHubs, velMedia, autonomia, todosVertices.get(verticeEscolhido),tempoDeDescarga,tempoDeCarregamento);
        @SuppressWarnings("unchecked")
        LinkedList<Localidades> locaisPassagem = (LinkedList<Localidades>) result.get("locaisPassagem");
        @SuppressWarnings("unchecked")
        List<Integer> distanciaEntreLocais = (List<Integer>) result.get("distanciaEntreLocais");

        if (locaisPassagem.size() - 1 != distanciaEntreLocais.size() || locaisPassagem.size() - 1 == 0) {
            System.out.println();
            System.out.println();
            System.out.println("Erro: O número de locais de passagem não corresponde ao número de distâncias ou nao foi possivel encontrar o caminho com essas restrições.");
            System.out.println();
            System.out.println();
        } else {

            System.out.println();
            System.out.println();
            System.out.println("Shortest Path Information :");
            System.out.println("==========================");
            System.out.println("Autonomia do veiculo: " + autonomia);
            System.out.println("Velocidade média: " + velMedia);
            System.out.println("Numero de carregamentos: " + result.get("numeroCarregamentos"));
            System.out.println("Distancia Total: " + result.get("distanciaTotal"));
            System.out.println("Local de origem: " + result.get("localOrigem").toString());
            System.out.println("Tempo total de Descarga: " + result.get("tempoTotalDescarga").toString());
            System.out.println("Tempo total de Carregamento: " + result.get("tempoTotalCarregamento").toString());
            System.out.println("Tempo total do percurso:" + result.get("tempoPercurso"));
            System.out.println("Tempo total da viagem: " + result.get("tempoTotal"));

            System.out.println();

            // Print loading locations
            System.out.println("Loading Locations:");
            @SuppressWarnings("unchecked")
            List<String> loadingLocations = (List<String>) result.get("loadingLocations");
            for (Object location : loadingLocations) {
                System.out.println("- " + location.toString());
            }

            System.out.println("\nLocais de Passagem e Distâncias:");


            Iterator<Localidades> locaisIterator = locaisPassagem.iterator();
            Iterator<Integer> distanciasIterator = distanciaEntreLocais.iterator();

            Localidades localAtual = null;

            while (locaisIterator.hasNext() && distanciasIterator.hasNext()) {
                if (localAtual == null) {
                    localAtual = locaisIterator.next();
                }

                Localidades proximoLocal = locaisIterator.next();
                Integer distancia = distanciasIterator.next();

                System.out.println(localAtual.toString() + "  --->  " + proximoLocal.toString() + "  | Distancia = " + distancia);

                localAtual = proximoLocal;

            }

            System.out.println();
            System.out.println();

        }
    }

}
