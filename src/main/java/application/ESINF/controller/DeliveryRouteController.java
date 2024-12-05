package application.ESINF.controller;

import application.ESINF.domain.Localidades;
import application.ESINF.domain.StructurePath;
import application.ESINF.functions.US_EI07_DeliveryRouteCalculator;

import java.time.LocalTime;

public class DeliveryRouteController {

    public static StructurePath calculateBestDeliveryRoute(Localidades localInicio, LocalTime hora, int autonomia, double averageVelocity, int tempoRecarga, int tempoDescarga) {
        return US_EI07_DeliveryRouteCalculator.calculaMelhorPercurso(localInicio, hora, autonomia, averageVelocity, tempoRecarga, tempoDescarga);
    }

    public static LocalTime getFinishingTimeRoute(StructurePath path, LocalTime horaComeco, int autonomia, double averageVelocity, int tempoRecarga, int tempoDescarga) {
        return US_EI07_DeliveryRouteCalculator.getFinishingTimeRoute(path.getDistanciaTotal(), averageVelocity, horaComeco);
    }

}

