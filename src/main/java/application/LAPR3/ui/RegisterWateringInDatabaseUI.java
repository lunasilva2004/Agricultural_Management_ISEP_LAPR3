package application.LAPR3.ui;

import application.LAPR3.controller.RegistarOperacaoController;
import application.LAPR3.domain.Irrigation;
import application.LAPR3.exceptions.IrrigationFileNotFoundException;
import application.LAPR3.repositories.Repositories;
import application.LAPR3.utils.ReadIrrigationPlan;

import java.io.IOException;
import java.sql.SQLException;


public class RegisterWateringInDatabaseUI implements Runnable{
    private RegistarOperacaoController controller;

    public RegisterWateringInDatabaseUI() {
        controller = new RegistarOperacaoController();
    }

    @Override
    public void run() {

        try {
            ReadIrrigationPlan.lerPlanoRega("docs/LAPR3/US_LP02/thirty_days_irrigation_program.csv");
        } catch (IrrigationFileNotFoundException | IOException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        int numOfIrrigationsAdded = 0;
        for (int i = 0; i < Repositories.getInstance().getIrrigationProgramRepository().getIrrigationArrayList().size(); i++) {
            Irrigation irrigation = Repositories.getInstance().getIrrigationProgramRepository().getIrrigationArrayList().get(i);
            int status = 0;
            try {
                status = controller.wateringRegister(irrigation);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if (status == 1) {
                numOfIrrigationsAdded++;
            }
        }
        System.out.println("Number of watering operations added: " + numOfIrrigationsAdded);
    }
    public Runnable scheduledRegistation(){
        try {
            ReadIrrigationPlan.lerPlanoRega("docs/LAPR3/US_LP02/thirty_days_irrigation_program.csv");
        } catch (IrrigationFileNotFoundException | IOException e) {
            return null;
        }
        for (int i = 0; i < Repositories.getInstance().getIrrigationProgramRepository().getIrrigationArrayList().size(); i++) {
            Irrigation irrigation = Repositories.getInstance().getIrrigationProgramRepository().getIrrigationArrayList().get(i);
            try {
                controller.wateringRegister(irrigation);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
