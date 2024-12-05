package application.ESINF.functions;

import application.ESINF.domain.Localidades;
import application.ESINF.domain.StructurePath;
import application.ESINF.graph.Algorithms;
import application.ESINF.graph.map.MapGraph;

import java.time.LocalTime;
import java.util.*;

/**
 * The type Us ei 07 delivery route calculator.
 */
public class US_EI07_DeliveryRouteCalculator {

    // Instância da classe US_EI02_IdealVerticesForNHubs para obter o grafo de hubs
    private static final US_EI02_IdealVerticesForNHubs usei02 = new US_EI02_IdealVerticesForNHubs();
    // Grafo representando as conexões entre locais, com pesos associados às arestas
    private static final MapGraph<Localidades, Integer> graphMod = usei02.getMapGraph();

    /**
     * Gets ponto partida.
     *
     * @param pontoPartida the ponto partida
     * @return the ponto partida
     */
// Método para obter informações sobre o ponto de partida
    public String getPontoPartida(Localidades pontoPartida) {
        StringBuilder info = new StringBuilder();
        // Itera sobre todos os locais no grafo
        for (Localidades localidades : graphMod.vertices()) {
            // Verifica se o número de identificação do local coincide com o ponto de partida fornecido
            if (localidades.getNumId().equals(pontoPartida.getNumId())) {
                info.append("NumId: ").append(localidades.getNumId()).append(" ");
                info.append("Coordenadas: ").append(localidades.getCoordenadas());
                // Define as coordenadas do ponto de partida
                pontoPartida.setCoordenadas(localidades.getCoordenadas());
            }
        }
        // Retorna as informações como uma string
        return info.toString();
    }

    /**
     * Calcula melhor percurso structure path.
     *
     * @param localInicial         the local inicial
     * @param horaInitial          the hora initial
     * @param autonomia            the autonomia
     * @param velocidade           the velocidade
     * @param carregar             the carregar
     * @param decarregarMercadoria the decarregar mercadoria
     * @return the structure path
     */

    // Método principal para calcular o melhor percurso de entrega
    public static StructurePath calculaMelhorPercurso(Localidades localInicial, LocalTime horaInitial, int autonomia, double velocidade, int carregar, int decarregarMercadoria) {
        // Inicializa variáveis
        int nHubsAbertos = 0;
        Localidades local = localInicial;
        LocalTime hora = horaInitial, restanteTempo = LocalTime.of(0, 0), horaInicial = horaInitial;
        List<Localidades> locaisVisitados = new ArrayList<>();
        LinkedList<Localidades> tempCaminho = new LinkedList<>(), melhorCaminho = new LinkedList<>();

        // Loop principal
        while (restanteTempo != null) {
            restanteTempo = null;
            // Loop para encontrar o melhor hub
            for (Localidades hub : getHubs()) {
                if (!locaisVisitados.contains(hub)) {
                    LinkedList<Localidades> caminhoPercorrido = new LinkedList<>();
                    Algorithms.shortestPathWithAutonomy(graphMod, autonomia, local, hub, Comparator.naturalOrder(), Integer::sum, 0, caminhoPercorrido);
                    // Verifica se o tempo final completo está dentro do horário do hub
                    if (getTempoFinalCompleto(caminhoPercorrido, horaInitial, autonomia, velocidade, carregar, decarregarMercadoria, getAllHubsInCourse(caminhoPercorrido, locaisVisitados).size()).isBefore(hub.getHorario().getCloseTime()) && getTempoFinalCompleto(caminhoPercorrido, horaInitial, autonomia, velocidade, carregar, decarregarMercadoria, getAllHubsInCourse(caminhoPercorrido, locaisVisitados).size()).isAfter(hub.getHorario().getOpenTime())) {
                        // Verifica se o hub é uma escolha melhor
                        if (restanteTempo == null ||
                                (getStillOpenHubs(getTempoFinalCompleto(caminhoPercorrido, horaInitial, autonomia, velocidade, carregar, decarregarMercadoria, getAllHubsInCourse(caminhoPercorrido, locaisVisitados).size()))
                                        .size() > nHubsAbertos
                                        &&
                                        minusTime(
                                                hub.getHorario().getCloseTime(), getTempoFinalCompleto(caminhoPercorrido, horaInitial, autonomia, velocidade, carregar, decarregarMercadoria, getAllHubsInCourse(caminhoPercorrido, locaisVisitados).size())
                                        ).isBefore(restanteTempo))) {
                            tempCaminho = caminhoPercorrido;
                            restanteTempo = minusTime(hub.getHorario().getCloseTime(), getTempoFinalCompleto(caminhoPercorrido, horaInitial, autonomia, velocidade, carregar, decarregarMercadoria, getAllHubsInCourse(caminhoPercorrido, locaisVisitados).size()));
                            hora = getTempoFinalCompleto(caminhoPercorrido, horaInitial, autonomia, velocidade, carregar, decarregarMercadoria, getAllHubsInCourse(caminhoPercorrido, locaisVisitados).size());
                            nHubsAbertos = getStillOpenHubs(hora).size();
                        }
                    }
                }
            }
            // Verifica se restanteTempo não é nulo
            if (restanteTempo != null) {
                if (melhorCaminho.isEmpty()) {
                    melhorCaminho.addAll(tempCaminho);
                } else if (!tempCaminho.isEmpty()) {
                    melhorCaminho.removeLast();
                    melhorCaminho.addAll(tempCaminho);
                }
                // Atualiza local e locaisVisitados
                if (!melhorCaminho.isEmpty()) {
                    local = melhorCaminho.getLast();
                    for (int i = 0; i < getAllHubsInCourse(tempCaminho, locaisVisitados).size(); i++) {
                        locaisVisitados.add(getAllHubsInCourse(tempCaminho, locaisVisitados).get(i));
                    }
                } else {
                    break;
                }
                for (int i = 0; i < getAllHubsInCourse(tempCaminho, locaisVisitados).size(); i++) {
                    locaisVisitados.add(getAllHubsInCourse(tempCaminho, locaisVisitados).get(i));
                }
                horaInitial = hora;
                restanteTempo = null;
            }
        }
        // Analisa dados do melhor caminho
        StructurePath structurePath = analyzeData(autonomia, melhorCaminho);
        // Define os tempos de chegada usando o método getTimeTable
        structurePath.setTemposDeChegada(getTimeTable(structurePath, horaInicial, autonomia, velocidade, carregar, decarregarMercadoria));
        // Retorna o melhor caminho calculado
        return structurePath;
    }


    /**
     * Gets tempo final completo.
     *
     * @param caminhoPercorrido the caminho percorrido
     * @param horaComeco        the hora comeco
     * @param autonomia         the autonomia
     * @param averageVelocity   the average velocity
     * @param tempoRecarga      the tempo recarga
     * @param tempoDescarga     the tempo descarga
     * @param numeroDescargas   the numero descargas
     * @return the tempo final completo
     */
    public static LocalTime getTempoFinalCompleto(LinkedList<Localidades> caminhoPercorrido, LocalTime horaComeco, int autonomia, double averageVelocity, int tempoRecarga, int tempoDescarga, int numeroDescargas) {
        // Inicialização da variável para armazenar o tempo final
        LocalTime horaFim = LocalTime.of(0, 0);
        // Análise dos dados do percurso utilizando o método analyzeData
        StructurePath structurePath = analyzeData(autonomia, caminhoPercorrido);
        // Cálculo do tempo final levando em consideração a distância total, a velocidade média e o horário de início
        horaFim = addTime(horaFim, getFinishingTimeRoute(structurePath.getDistanciaTotal(), averageVelocity, horaComeco));
        // Loop para adicionar o tempo de recarga para cada ponto de carregamento no percurso
        for (int i = 0; i < structurePath.getCarregamentos().size(); i++) {
            horaFim = addTime(horaFim, intToLocalTime(tempoRecarga));
        }
        // Loop para adicionar o tempo de descarga para cada descarga no percurso
        for (int i = 0; i < numeroDescargas; i++) {
            horaFim = addTime(horaFim, intToLocalTime(tempoDescarga));
        }
        // Retorna o tempo final calculado
        return horaFim;
    }


    /**
     * Int to local time local time.
     *
     * @param tempo the tempo
     * @return the local time
     */
    public static LocalTime intToLocalTime(int tempo) {
        // Converte o tempo inteiro para um valor em minutos
        double tempoDouble = (double) tempo / 60;
        // Calcula as horas e minutos a partir do valor em minutos
        LocalTime tempoPercurso = LocalTime.of((int) tempoDouble, (int) ((tempoDouble - (int) tempoDouble) * 60));
        // Retorna o tempo convertido para LocalTime
        return tempoPercurso;
    }

    /**
     * Gets finishing time route.
     *
     * @param distanciaTotal  the distancia total
     * @param averageVelocity the average velocity
     * @param horaComeco      the hora comeco
     * @return the finishing time route
     */
    public static LocalTime getFinishingTimeRoute(int distanciaTotal, double averageVelocity, LocalTime horaComeco) {
        // Declaração da variável para armazenar o tempo final
        LocalTime horaFim;
        // Calcula o tempo de percurso em horas a partir da distância total e da velocidade média
        double tempoPercursoDouble = ((double) (distanciaTotal) / 1000) / averageVelocity;
        // Calcula as horas e minutos a partir do valor em horas
        LocalTime tempoPercurso = LocalTime.of((int) tempoPercursoDouble, (int) ((tempoPercursoDouble - (int) tempoPercursoDouble) * 60));
        // Calcula o tempo final somando o tempo de percurso ao horário de início
        horaFim = addTime(horaComeco, tempoPercurso);
        // Retorna o tempo final calculado
        return horaFim;
    }

    /**
     * Gets time table.
     *
     * @param structurePath   the structure path
     * @param horaComeco      the hora comeco
     * @param autonomia       the autonomia
     * @param averageVelocity the average velocity
     * @param tempoRecarga    the tempo recarga
     * @param tempoDescarga   the tempo descarga
     * @return the time table
     */
    public static Map<Localidades, List<LocalTime>> getTimeTable(StructurePath structurePath, LocalTime horaComeco, int autonomia, double averageVelocity, int tempoRecarga, int tempoDescarga) {
        // Mapa que irá armazenar o cronograma de horários para cada ponto no percurso
        Map<Localidades, List<LocalTime>> timeTable = new LinkedHashMap<>();
        // Itera sobre o percurso a partir do segundo ponto (índice 1)
        for (int i = 1; i < structurePath.getPercurso().size(); i++) {
            // Calcula o horário de término para a aresta entre os pontos i-1 e i no percurso
            LocalTime afterEverything = getFinishingTimeRoute(graphMod.edge(structurePath.getPercurso().get(i - 1), structurePath.getPercurso().get(i)).getWeight(), averageVelocity, horaComeco);
            // Lista para armazenar os horários associados ao ponto atual no percurso
            List<LocalTime> listOfTimes = new ArrayList<>();
            // Verifica se o ponto atual no percurso é um ponto de carregamento
            if (structurePath.getCarregamentos().contains(i)) {
                // Verifica se o ponto é um hub
                if (structurePath.getPercurso().get(i).isHub()) {
                    // Adiciona horário de descarga e recarga à lista
                    listOfTimes.add(afterEverything.plusMinutes(tempoDescarga));
                    listOfTimes.add(afterEverything.plusMinutes(tempoRecarga));
                    // Adiciona a lista ao mapa com o ponto atual como chave
                    timeTable.put(structurePath.getPercurso().get(i), listOfTimes);
                    // Atualiza o horário de início para refletir o término do tempo de recarga e descarga
                    horaComeco = afterEverything.plusMinutes(tempoRecarga).plusMinutes(tempoDescarga);
                } else {
                    // Adiciona horário de descarga e recarga à lista
                    listOfTimes.add(afterEverything);
                    listOfTimes.add(afterEverything.plusMinutes(tempoRecarga));
                    // Adiciona a lista ao mapa com o ponto atual como chave
                    timeTable.put(structurePath.getPercurso().get(i), listOfTimes);
                    // Atualiza o horário de início para refletir o término do tempo de recarga
                    horaComeco = afterEverything.plusMinutes(tempoRecarga);
                }
            } else {
                // Verifica se o ponto é um hub
                if (structurePath.getPercurso().get(i).isHub()) {
                    // Adiciona horário de descarga à lista
                    listOfTimes.add(afterEverything);
                    listOfTimes.add(afterEverything.plusMinutes(tempoDescarga));
                    // Adiciona a lista ao mapa com o ponto atual como chave
                    timeTable.put(structurePath.getPercurso().get(i), listOfTimes);
                    // Atualiza o horário de início para refletir o término do tempo de descarga
                    horaComeco = afterEverything.plusMinutes(tempoDescarga);
                } else {
                    // Adiciona horário de início à lista (não há recarga ou descarga)
                    listOfTimes.add(afterEverything);
                    listOfTimes.add(afterEverything);
                    // Adiciona a lista ao mapa com o ponto atual como chave
                    timeTable.put(structurePath.getPercurso().get(i), listOfTimes);
                    // Atualiza o horário de início para refletir o término do tempo de percurso
                    horaComeco = afterEverything;
                }
            }
        }
        // Retorna o mapa completo representando o cronograma de horários para cada ponto no percurso
        return timeTable;
    }

    /**
     * Gets all hubs in course.
     *
     * @param caminho         the caminho
     * @param locaisVisitados the locais visitados
     * @return the all hubs in course
     */
    public static List<Localidades> getAllHubsInCourse(LinkedList<Localidades> caminho, List<Localidades> locaisVisitados) {
        // Lista para armazenar os hubs encontrados no percurso
        List<Localidades> lista = new ArrayList<>();
        // Itera sobre o percurso a partir do segundo ponto (índice 1)
        for (int i = 1; i < caminho.size(); i++) {
            // Verifica se o ponto atual no percurso é um hub
            if (caminho.get(i).isHub()) {
                // Adiciona o hub à lista
                lista.add(caminho.get(i));
            }
        }
        // Retorna a lista de hubs encontrados no percurso
        return lista;
    }

    /**
     * Add time local time.
     *
     * @param time1 the time 1
     * @param time2 the time 2
     * @return the local time
     */
    public static LocalTime addTime(LocalTime time1, LocalTime time2) {
        // Retorna a soma dos dois tempos
        return time1.plusHours(time2.getHour()).plusMinutes(time2.getMinute()).plusSeconds(time2.getSecond());
    }

    /**
     * Minus time local time.
     *
     * @param time1 the time 1
     * @param time2 the time 2
     * @return the local time
     */
    public static LocalTime minusTime(LocalTime time1, LocalTime time2) {
        // Retorna a diferença entre os dois tempos
        return time1.minusHours(time2.getHour()).minusMinutes(time2.getMinute()).minusSeconds(time2.getSecond());
    }

    /**
     * Gets still open hubs.
     *
     * @param hora the hora
     * @return the still open hubs
     */
    public static List<Localidades> getStillOpenHubs(LocalTime hora) {
        // Lista para armazenar hubs que ainda estão abertos no momento especificado
        List<Localidades> result = new ArrayList<>();
        // Itera sobre todos os hubs obtidos através do método getHubs()
        for (Localidades local : getHubs()) {
            // Verifica se o local é um hub, está aberto no momento e ainda não fechou
            if (local.isHub() && local.getHorario().getOpenTime().isBefore(hora) && local.getHorario().getCloseTime().isAfter(hora)) {
                // Adiciona o hub à lista de hubs ainda abertos
                result.add(local);
            }
        }
        // Retorna a lista de hubs que ainda estão abertos no momento especificado
        return result;
    }

    /**
     * Gets hubs.
     *
     * @return the hubs
     */
    public static List<Localidades> getHubs() {
        // Lista para armazenar todos os hubs no grafo
        List<Localidades> result = new ArrayList<>();
        // Itera sobre todos os vértices no grafo
        for (Localidades local : graphMod.vertices()) {
            // Verifica se o vértice é um hub e, se sim, adiciona à lista
            if (local.isHub()) {
                result.add(local);
            }
        }
        // Retorna a lista de todos os hubs no grafo
        return result;
    }


    /**
     * Analyze data structure path.
     *
     * @param autonomia the autonomia
     * @param caminho   the caminho
     * @return the structure path
     */
    public static StructurePath analyzeData(int autonomia, LinkedList<Localidades> caminho) {
        // Lista para armazenar os índices dos pontos de carregamento no percurso
        ArrayList<Integer> indexDeCarregamentos = new ArrayList<>();
        // Inicialização das variáveis para armazenar informações sobre o percurso
        LinkedList<Localidades> percurso = caminho;
        int distanciaPercorrida = 0, bateria = autonomia;
        boolean flag = true;
        // Verifica se o percurso não é nulo
        if (percurso != null) {
            // Loop sobre o percurso
            for (int i = 0; i < percurso.size() - 1; i++) {
                // Calcula a distância entre os pontos consecutivos no percurso
                int distanciaEntrePontos = graphMod.edge(percurso.get(i), percurso.get(i + 1)).getWeight();
                distanciaPercorrida += distanciaEntrePontos;

                // Verifica se a distância é maior que a autonomia do veículo
                if (distanciaEntrePontos / 1000 > bateria) {
                    // Se sim, verifica se a distância pode ser percorrida com uma recarga
                    if (distanciaEntrePontos / 1000 <= autonomia) {
                        // Adiciona o índice do ponto de carregamento à lista
                        indexDeCarregamentos.add(i);
                        // Atualiza a bateria para a autonomia total
                        bateria = autonomia;
                    } else {
                        // Se não for possível percorrer a distância, define a flag como false
                        flag = false;
                    }
                } else {
                    // Atualiza a bateria subtraindo a distância percorrida
                    bateria -= distanciaEntrePontos / 1000;
                }
            }
        } else {
            // Se o percurso for nulo, define a flag como false
            flag = false;
            // Retorna uma instância de StructurePath com informações vazias
            return new StructurePath(distanciaPercorrida, percurso, indexDeCarregamentos, flag);
        }
        // Retorna uma instância de StructurePath com as informações do percurso analisadas
        return new StructurePath(distanciaPercorrida, percurso, indexDeCarregamentos, flag);
    }

}


// A StructurePath é uma classe que armazena informações relacionadas ao caminho percorrido durante o cálculo de uma rota de entrega. Esta classe contém todos os dados específicos sobre o percurso.
