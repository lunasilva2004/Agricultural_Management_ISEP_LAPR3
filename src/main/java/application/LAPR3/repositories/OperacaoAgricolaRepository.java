package application.LAPR3.repositories;


import application.LAPR3.utils.DbConnectionTimeTask;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

public class OperacaoAgricolaRepository {

    public int podaRegister(int quantidade, Date date,String unidade,String parcelaDesignacao,String plantacao,String nomeComum,String variedade) throws SQLException {

        CallableStatement callStmt = null;
        // Return execution result status
        final int[] result = {0}; // 0 - not registered, 1 - registered, 2 - timeout
        final String[] errorMsg = {null}; // Error message

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ call prcRegistrarOperacaoPoda(?,?,?,?,?,?,?,?,?,?) }");

            // Set IN parameters
            callStmt.setString(1, "Poda");
            callStmt.setInt(2, quantidade);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            callStmt.setDate(3,sqlDate);
            callStmt.setString(4, unidade);
            callStmt.setString(5, parcelaDesignacao);
            callStmt.setString(6,plantacao);
            callStmt.setString(7,nomeComum);
            callStmt.setString(8,variedade);

            // Set Out parameters
            callStmt.registerOutParameter(9, Types.INTEGER);  // for execution result status
            callStmt.registerOutParameter(10, Types.VARCHAR); // for error message

            // Execute the stored procedure
            callStmt.execute();

            // Retrieve the results
            result[0] = callStmt.getInt(9);
            errorMsg[0] = callStmt.getString(10);

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (callStmt != null) {
                try {
                    callStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // Print or log the error message
        if (errorMsg[0] != null) {
            System.out.println("\nError message: " + errorMsg[0]);
        }
        return result[0];
    }

    public int semeaduraRegister(String tipoOperacao, int quantidade, int area, Date date, String unidade, int culturaID, int plantaID, int parcelaID) {
        CallableStatement callStmt = null;
        // Return execution result status
        final int[] result = {0}; // 0 - not registered, 1 - registered, 2 - timeout
        final String[] errorMsg = {null}; // Error message

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ call PRCREGISTAROPERACAOSEMEADURA(?,?,?,?,?,?,?,?,?) }");

            // Set IN parameters
            callStmt.setString(1, tipoOperacao);
            callStmt.setInt(2, quantidade);
            callStmt.setInt(3, area);
            callStmt.setDate(4, (java.sql.Date) date);
            callStmt.setString(5, unidade);
            callStmt.setInt(6, culturaID);
            callStmt.setInt(7, plantaID);
            callStmt.setInt(8, parcelaID);

            // Set Out parameters
            callStmt.registerOutParameter(9, Types.INTEGER);  // for execution result status
            callStmt.registerOutParameter(10, Types.VARCHAR); // for error message

            DbConnectionTimeTask connectionTimeTask = new DbConnectionTimeTask(callStmt, result);
            connectionTimeTask.scheduleTimeout(30000); // tempo max espera 30 segundos

            // Execute the stored procedure
            callStmt.execute();

            // Cancel the timer task (operation completed within the timeout)
            connectionTimeTask.cancelTask();

            // Retrieve the results
            result[0] = callStmt.getInt(9);
            errorMsg[0] = callStmt.getString(10);

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (callStmt != null) {
                try {
                    callStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // Print or log the error message
        if (errorMsg[0] != null) {
            System.out.println("\nError message: " + errorMsg[0]);
        }
        return result[0];
    }

    public int mondaRegister(int operacaoID, String tipoOperacao, int quantidade, String date, String unidade, int culturaID, int plantaID, int parcelaID) {
        CallableStatement callStmt = null;
        // Return execution result status
        final int[] result = {0}; // 0 - not registered, 1 - registered, 2 - timeout
        final String[] errorMsg = {null}; // Error message

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ call PRCREGISTAROPERACAOMONDA(?,?,?,?,?,?,?,?,?,?) }");

            // Set IN parameters
            callStmt.setInt(1, operacaoID);
            callStmt.setString(2, tipoOperacao);
            callStmt.setInt(3, quantidade);
            callStmt.setString(4, String.valueOf(date));
            callStmt.setString(5, unidade);
            callStmt.setInt(6, culturaID);
            callStmt.setInt(7, plantaID);
            callStmt.setInt(8, parcelaID);

            // Set Out parameters
            callStmt.registerOutParameter(9, Types.INTEGER);  // for execution result status
            callStmt.registerOutParameter(10, Types.VARCHAR); // for error message

            DbConnectionTimeTask connectionTimeTask = new DbConnectionTimeTask(callStmt, result);
            connectionTimeTask.scheduleTimeout(30000); // tempo max espera 30 segundos

            // Execute the stored procedure
            callStmt.execute();

            // Cancel the timer task (operation completed within the timeout)
            connectionTimeTask.cancelTask();

            // Retrieve the results
            result[0] = callStmt.getInt(9);
            errorMsg[0] = callStmt.getString(10);

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (callStmt != null) {
                try {
                    callStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // Print or log the error message
        if (errorMsg[0] != null) {
            System.out.println("\nError message: " + errorMsg[0]);
        }
        return result[0];
    }

    public int colheitaRegister(String date, String tipoOperacao, int quantidade, String unidade, int culturaID, int plantaID, int parcelaID, String produto) {
        CallableStatement callStmt = null;
        // Return execution result status
        final int[] result = {0}; // 0 - not registered, 1 - registered, 2 - timeout
        final String[] errorMsg = {null}; // Error message

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ call PRCREGISTAROPERACAOCOLHEITA(?,?,?,?,?,?,?,?,?,?,?) }");

            // Set IN parameters
            callStmt.setString(1, date);
            callStmt.setInt(2, parcelaID);
            callStmt.setInt(3, culturaID);
            callStmt.setInt(4, plantaID);
            callStmt.setString(5, produto);
            callStmt.setInt(6, quantidade);
            callStmt.setString(7, tipoOperacao);
            callStmt.setString(8, unidade);

            // Set Out parameters
            callStmt.registerOutParameter(9, Types.INTEGER);  // for execution result status
            callStmt.registerOutParameter(10, Types.VARCHAR); // for error message


            DbConnectionTimeTask connectionTimeTask = new DbConnectionTimeTask(callStmt, result);
            connectionTimeTask.scheduleTimeout(30000); // tempo max espera 30 segundos

            // Execute the stored procedure
            callStmt.execute();

            // Cancel the timer task (operation completed within the timeout)
            connectionTimeTask.cancelTask();

            // Retrieve the results
            result[0] = callStmt.getInt(9);
            errorMsg[0] = callStmt.getString(10);

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (callStmt != null) {
                try {
                    callStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // Print or log the error message
        if (errorMsg[0] != null) {
            System.out.println("\nError message: " + errorMsg[0]);
        }
        return result[0];
    }

    public int fertirregaRegister(String designacaoReceita, int[] fatoresProducao) {
        CallableStatement callStmt = null;
        // Return execution result status
        final int[] result = {0}; // 0 - not registered, 1 - registered, 2 - timeout
        final String[] errorMsg = {null}; // Error message

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ call PRCREGISTARFERTIRREGA(?,?,?,?) }");

            ArrayDescriptor arrayDescriptor = ArrayDescriptor.createDescriptor("FATORESPRODUCAOARRAYTYPE", connection);
            ARRAY oracleArray = new ARRAY(arrayDescriptor, connection, fatoresProducao);

            // Definir o ARRAY como parâmetro da stored procedure
            callStmt.setString(1, designacaoReceita);
            callStmt.setArray(2, oracleArray);


            // Set Out parameters
            callStmt.registerOutParameter(3, Types.INTEGER);  // for execution result status
            callStmt.registerOutParameter(4, Types.VARCHAR); // for error message

            DbConnectionTimeTask connectionTimeTask = new DbConnectionTimeTask(callStmt, result);
            connectionTimeTask.scheduleTimeout(30000); // tempo max espera 30 segundos

            // Execute the stored procedure
            callStmt.execute();

            // Cancel the timer task (operation completed within the timeout)
            connectionTimeTask.cancelTask();

            // Retrieve the results
            result[0] = callStmt.getInt(3);
            errorMsg[0] = callStmt.getString(4);

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (callStmt != null) {
                try {
                    callStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // Print or log the error message
        if (errorMsg[0] != null) {
            System.out.println("\nError message: " + errorMsg[0]);
        }
        return result[0];
    }
}

