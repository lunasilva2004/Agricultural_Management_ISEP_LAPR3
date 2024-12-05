package application.LAPR3.repositories;

import application.LAPR3.domain.Irrigation;
import application.LAPR3.domain.IrrigationProgram;
import application.LAPR3.utils.DbConnectionTimeTask;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IrrigationProgramRepository {
    private final List<IrrigationProgram> irrigationProgramArrayList;
    private final List<Irrigation> irrigationArrayList;

    public IrrigationProgramRepository(List<Irrigation> irrigationArrayList) {
        irrigationProgramArrayList = new ArrayList<>();
        this.irrigationArrayList = irrigationArrayList;
    }

    public IrrigationProgramRepository() {
        irrigationProgramArrayList = new ArrayList<>();
        irrigationArrayList = new ArrayList<>();
    }

    public void addIrrigationProgram(IrrigationProgram irrigationProgram) {
        irrigationProgramArrayList.add(irrigationProgram);
    }

    public List<IrrigationProgram> getIrrigationProgramArrayList() {
        return irrigationProgramArrayList;
    }

    public List<Irrigation> getIrrigationArrayList() {
        return irrigationArrayList;
    }

    public void addIrrigation(Irrigation irrigation) {
        irrigationArrayList.add(irrigation);
    }
    public int wateringRegisterWithFertirrega(Irrigation irrigation) throws SQLException {

        CallableStatement callStmt = null;
        int status;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            status = 0;

            if (irrigation.getData().isBefore(LocalDate.now()) ||
                    (irrigation.getData().isEqual(LocalDate.now()) &&
                            irrigation.getHoraFim().isBefore(LocalTime.now()))) {

                String[] errorMsg = {null};

                callStmt = connection.prepareCall("{ call PRCWATERINGREGISTERWITHFERTIRREGA(?,?,?,?,?,?,?,?) }");

                java.time.LocalDateTime localDateTime = irrigation.getData().atStartOfDay();
                java.util.Date utilDate = java.util.Date.from(localDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                callStmt.setDate(1, sqlDate);
                callStmt.setString(2, String.valueOf(irrigation.getHoraInicio()));
                callStmt.setString(3, String.valueOf(irrigation.getHoraFim()));
                callStmt.setInt(4, Integer.parseInt(String.valueOf(irrigation.getDuracao())));
                callStmt.setString(5, String.valueOf(irrigation.getSetor()));
                callStmt.setString(6, String.valueOf(irrigation.getMix()));


                callStmt.registerOutParameter(7, Types.VARCHAR); // for error message
                callStmt.registerOutParameter(8, Types.INTEGER); // for error code

                DbConnectionTimeTask connectionTimeTask = new DbConnectionTimeTask(callStmt, new int[]{0});
                connectionTimeTask.scheduleTimeout(30000); // tempo max espera 30 segundos

                // Execute the stored procedure
                callStmt.execute();

                // Cancel the timer task (operation completed within the timeout)
                connectionTimeTask.cancelTask();

                // Retrieve the results
                errorMsg[0] = callStmt.getString(7);
                status = callStmt.getInt(8);

                if (errorMsg[0] != null) {
                    System.out.println(errorMsg[0]);
                }
                connection.commit();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (callStmt != null) {
                callStmt.close();
            }
        }
        return status;
    }

}
