package application.ESINF.ui;

import application.ESINF.controller.PossiblePathsBetweenPointsController;
import application.ESINF.domain.LocalityPair;
import application.ESINF.domain.Localidades;
import application.ESINF.domain.PathInfo;

import java.util.*;

/**
 * The type Possible paths between points ui.
 */
public class PossiblePathsBetweenPointsUI implements Runnable {
    private PossiblePathsBetweenPointsController controller;
    @Override
    public void run() {
        if (controller == null) {
            controller = new PossiblePathsBetweenPointsController();
        }
        Scanner scanner = new Scanner(System.in);

        String startingHub = null;
        while (startingHub == null || startingHub.trim().isEmpty()) {
            System.out.println("Enter the starting hub: ");
            startingHub = scanner.nextLine();

            if (startingHub.trim().isEmpty()) {
                System.out.println("\nInvalid starting hub!\n");
            }
        }

        String destinationHub = null;
        while (destinationHub == null || destinationHub.trim().isEmpty()) {
            System.out.println("Enter the destination hub: ");
            destinationHub = scanner.nextLine();

            if (destinationHub.trim().isEmpty()) {
                System.out.println("\nInvalid destination hub!\n");
            }
        }

        Integer vehicleAutonomy = null;
        while (vehicleAutonomy == null || vehicleAutonomy < 0) {
            System.out.println("Enter the vehicle autonomy/maximum distance: ");

            try {
                vehicleAutonomy = scanner.nextInt();
                if (vehicleAutonomy < 0) {
                    System.out.println("\nVehicle Autonomy can not been negative!\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nVehicle Autonomy must be a integer number!\n");
                scanner.next();
            }
        }
        Integer vehicleAverageSpeed = null;
        while (vehicleAverageSpeed == null || vehicleAverageSpeed < 0) {
            System.out.println("Enter the trip average speed: ");

            try {
                vehicleAverageSpeed = scanner.nextInt();
                if (vehicleAverageSpeed < 0) {
                    System.out.println("\nTrip Average Speed can not been negative!\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nTrip Average must be a integer number!\n");
                scanner.next();
            }
        }

        TreeMap<LinkedList<Localidades>, PathInfo> paths = controller.calculatePathsBetweenTwoPoints(startingHub, destinationHub, vehicleAutonomy, vehicleAverageSpeed);
        if (paths.isEmpty()) {
            System.out.println("\nNo possible paths found for desired trip!\n");
        }else {
            displayPaths(paths, vehicleAutonomy);
        }
    }

    /**
     * Display paths.
     *
     * @param paths           the paths
     * @param vehicleAutonomy the vehicle autonomy
     */
    public void displayPaths(TreeMap<LinkedList<Localidades>, PathInfo> paths, Integer vehicleAutonomy) {
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        for (Map.Entry<LinkedList<Localidades>, PathInfo> entry : paths.entrySet()) {
            LinkedList<Localidades> path = entry.getKey();
            PathInfo pathInfo = entry.getValue();

            System.out.println("Path:");
            Iterator<Localidades> hubIterator = path.iterator();
            Localidades localidadeAtual = hubIterator.next();

            while (hubIterator.hasNext()) {
                Localidades localidadeSeguinte = hubIterator.next();
                LocalityPair localityPair = new LocalityPair(localidadeAtual, localidadeSeguinte);
                int distance = pathInfo.getIndividualDistances().get(localityPair);
                if (!localidadeAtual.isHub()){
                System.out.println("Locality " + localidadeAtual.getNumId() + " on " + localidadeAtual.getCoordenadas() + "  ----->  " + "Locality " + localidadeSeguinte.getNumId() + " on " + localidadeSeguinte.getCoordenadas() + "\nDistance between location " + localidadeAtual.getNumId() + " and " + localidadeSeguinte.getNumId() + ": " + distance + "m\n");
                }
                localidadeAtual = localidadeSeguinte;
            }

            System.out.println("Total distance of this path: " + pathInfo.getTotalDistance() + "m");
            System.out.println("Total time of this path: " + pathInfo.getTotalTime() + " hours");
            System.out.println("Autonomy available after this path: " + (vehicleAutonomy - pathInfo.getTotalDistance()) + "m");
            System.out.println("-----------------------------------------------------------------------------------------------------------------");
        }
    }


}
