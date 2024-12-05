package application.ESINF.ui;

import application.ESINF.controller.DeliveryRouteController;
import application.ESINF.domain.Localidades;
import application.ESINF.domain.StructurePath;
import application.ESINF.functions.US_EI02_IdealVerticesForNHubs;
import application.ESINF.functions.US_EI07_DeliveryRouteCalculator;
import application.ESINF.graph.map.MapGraph;
import application.ESINF.utils.ExcecaoData;
import application.ESINF.utils.ExcecaoHora;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeliveryRouteCalculatorUI implements Runnable {
    private static final US_EI02_IdealVerticesForNHubs usei02 = new US_EI02_IdealVerticesForNHubs();
    private static final MapGraph<Localidades, Integer> graphMod = usei02.getMapGraph();

    private final US_EI07_DeliveryRouteCalculator deliveryRouteCalculator = new US_EI07_DeliveryRouteCalculator();

    public static Scanner scanner = new Scanner(System.in);
    private static DeliveryRouteController controller = new DeliveryRouteController();

    @Override
    public void run() {
        Localidades startingPoint = getLocal();
        System.out.print("Enter the autonomy of the vehicle (in km): ");
        int autonomy = scanner.nextInt();
        System.out.print("Enter the average velocity of the vehicle (in km/h): ");
        double averageVelocity = scanner.nextDouble();
        System.out.print("Enter the vehicle loading time (in minutes): ");
        int rechargeTime = scanner.nextInt();
        System.out.print("Enter the time for unloading orders at each hub (in minutes): ");
        int dischargeTime = scanner.nextInt();
        System.out.print("Do you want to use the current time? (Y/N): ");
        LocalTime startTime;
        if (scanner.next().equalsIgnoreCase("Y")) {
            startTime = LocalTime.now();
        } else {
            System.out.print("Enter the desired start time (HH:mm): ");
            startTime = LocalTime.parse(scanner.next());
        }

        // Call the method for route calculation
        melhorRotaDisplay(startingPoint, startTime, autonomy, averageVelocity, rechargeTime, dischargeTime);
    }


    public static void melhorRotaDisplay(Localidades startingPoint, LocalTime hora, int autonomy,
                                         double averageVelocity, int rechargeTime, int dischargeTime) {
        try {
            System.out.println("|-------------------------------------------------------|");
            System.out.println("| Melhor Rota Para Entrega De Cabazes (Tempo)           |");
            System.out.println("|-------------------------------------------------------|");

            StructurePath path = controller.calculateBestDeliveryRoute(startingPoint, hora, autonomy, averageVelocity, rechargeTime, dischargeTime);
            double tempoTotal = (double) path.getDistanciaTotal() / (averageVelocity * 1000);
            if (path.isFlag()) {
                System.out.printf("| Local Inicial: %-38s |\n", path.getPercurso().get(0).getNumId());
                System.out.printf("| Local Final:   %-38s |\n", path.getPercurso().get(path.getPercurso().size() - 1).getNumId());
                System.out.println("|-------------------------------------------------------|");
                System.out.printf("| Distância Total Percorrida: %-27d (m) |\n", path.getDistanciaTotal());
                System.out.println("|-------------------------------------------------------|");
                System.out.println("| Percurso:                                              |");
                System.out.println("|-------------------------------------------------------|");

                for (Localidades local : path.getPercurso()) {
                    System.out.printf("| %-52s |\n", local.getNumId());
                    if (local.isHub()) {
                        System.out.printf("| HUB %-50s |\n", local.getNumId());
                    }
                }
                System.out.println("|-------------------------------------------------------|");
                System.out.println("| Horário:                                               |");
                System.out.println("|-------------------------------------------------------|");
                System.out.printf("| Local      | Horário Chegada         | Horário Saída          |\n");
                System.out.println("|-------------------------------------------------------|");
                System.out.printf("| %-10s | %-23s | %-23s |\n", path.getPercurso().get(0).getNumId(), "Local Inicial", hora);

                for (Localidades local : path.getTemposDeChegada().keySet()) {
                    if (!local.equals(path.getPercurso().get(path.getPercurso().size() - 1))) {
                        System.out.printf("| %-10s | %-23s | %-23s |\n", local.getNumId(), path.getTemposDeChegada().get(local).get(0), path.getTemposDeChegada().get(local).get(1));
                    } else {
                        System.out.printf("| %-10s | %-23s | %-23s |\n", local.getNumId(), path.getTemposDeChegada().get(local).get(0), "---Final-da-Viagem---");
                    }
                }

                System.out.println("|-------------------------------------------------------|");
                System.out.printf("| Tempo Total Do Percurso: %-30s Horas |\n", controller.getFinishingTimeRoute(path, LocalTime.of(0, 0), autonomy, averageVelocity, rechargeTime, dischargeTime));
                System.out.println("|-------------------------------------------------------|");
                System.out.println("| Carregamentos:                                         |");
                System.out.println("|-------------------------------------------------------|");

                for (int i = 0; i < path.getCarregamentos().size(); i++) {
                    System.out.printf("| %-52s |\n", path.getPercurso().get(path.getCarregamentos().get(i)));
                }

                System.out.println("|-------------------------------------------------------|");
                System.out.printf("| Numero De Carregamentos: %-26d |\n", path.getCarregamentos().size());
                System.out.println("|-------------------------------------------------------|\n\n");

            } else {
                System.out.println("|-------------------------------------------------------|");
                System.out.println("| Não É Possível Realizar O Percurso                    |");
                System.out.println("|-------------------------------------------------------|");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("|-------------------------------------------------------|");
            System.out.println("| Erro A Realizar A Funcionalidade                       |");
            System.out.println("|-------------------------------------------------------|");
        }
    }


    private static String getVariable(String pergunta) {
        String returna;
        int lenght = 53, lenghtQuesion = pergunta.length();
        lenght -= lenghtQuesion;
        lenght = lenght / 2;
        System.out.print("|");
        for (int i = 0; i < lenght + 1; i++) {
            System.out.print(" ");
        }
        System.out.print(String.format("%s", pergunta));
        for (int i = 0; i < lenght + 1; i++) {
            System.out.print(" ");
        }
        System.out.println("|");
        System.out.println("|-------------------------------------------------------|");
        returna = scanner.next();
        System.out.println("|-------------------------------------------------------|");
        return returna;
    }

    private LocalDate getDay() {
        String data = null;
        while (data == null) {
            try {
                System.out.println("Defina O Dia Do Começo Do Percurso (formato dd/mm/yyyy):");
                data = scanner.nextLine();
                ExcecaoData.verificarData(data);
            } catch (ExcecaoData e) {
                System.out.printf("%s\n\n", e.getMessage());
                data = null;
            }
        }
        return LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    private LocalTime getTime() {
        String time = null;
        while (time == null) {
            try {
                System.out.println("Defina A Hora De Saída Do Veículo (formato hh:mm):");
                time = scanner.nextLine();
                ExcecaoHora.verificarHora(time);
            } catch (ExcecaoHora e) {
                System.out.printf("%s\n\n", e.getMessage());
                time = null;
            }
        }
        return LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
    }

    private static Localidades getLocal() {
        List<Localidades> listOfNonHubs = new ArrayList<>();
        System.out.println("|-------------------------------------------------------|");
        System.out.println("| Qual O Local De Partida?                               |");
        System.out.println("|-------------------------------------------------------|");
        System.out.println("| Lista:                                                |");
        System.out.println("|-------------------------------------------------------|");

        for (Localidades local : graphMod.vertices()) {
            if (!local.isHub()) {
                listOfNonHubs.add(local);
            }
        }

        while (!listOfNonHubs.isEmpty()) {
            System.out.printf("| %-51s   |\n", showAndDeleteLocals(listOfNonHubs));
        }

        System.out.println("\nIntroduza O Nome Do Local De Partida Do Veículo De Entrega (CTXX):");
        String stringID = scanner.next();

        while (usei02.getLocalByID(stringID) == null) {
            System.out.println("Id Inválido.");
            System.out.println("\nIntroduza O Nome Do Local De Partida Do Veículo De Entrega (CTXX):");
            stringID = scanner.next();
        }

        return usei02.getLocalByID(stringID);
    }


    public static String showAndDeleteLocals(List<Localidades> listOfNonHubs) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < listOfNonHubs.size(); i++) {
            stringBuilder.append(listOfNonHubs.get(i) + " ");
            if (i == 5) {
                return stringBuilder.toString();
            }
            listOfNonHubs.remove(listOfNonHubs.get(i));
        }
        return stringBuilder.toString();
    }

}