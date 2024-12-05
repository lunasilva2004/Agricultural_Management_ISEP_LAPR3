package application.LAPR3.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReadXlslFiletoSqlScript {

    public static void readXlslFile(String pathOfXlsxFile) {
        String createTablesPath = "docs/BDDAD/US_BD02/modelo_fisico.txt";
        String sqlScriptFile = "docs/BDDAD/US_BD04/SQL_Script.sql";
        String logFilePath = "docs/BDDAD/US_BD04/sql_scripts_log.txt";

        File createTables = new File(createTablesPath);
        if (!createTables.exists()) {
            System.err.println("The CreateTables File is not found on the path: " + createTablesPath);
            return;
        }
        if (createTables.isDirectory()) {
            System.out.println("The path is a directory. Please insert a valid path to the CreateTables File.");
            return;
        }

        File xlsx = new File(pathOfXlsxFile);
        if (!xlsx.exists()) {
            System.err.println("The Xlsx File is not found on the path: " + pathOfXlsxFile);
            return;
        }
        if (xlsx.isDirectory()) {
            System.out.println("The path is a directory. Please insert a valid path to a Xlsx File.");
            return;
        }

        File sql = new File(sqlScriptFile);
        if (sql.exists()) {
            if (sql.delete()) {
                SqlScriptLogGenerator.addLogEntry("The existing SQL Script File has been deleted.");
            } else {
                System.out.println("Error deleting the existing SQL Script File.");
                return;
            }
        }
        appendTextFileToSQL(createTablesPath, sqlScriptFile);
        generateScriptForExploracaoAgricolaSheet(pathOfXlsxFile, sqlScriptFile);
        generateScriptForCulturasSheet(pathOfXlsxFile, sqlScriptFile);
        generateScriptForFatorProducaoSheet(pathOfXlsxFile, sqlScriptFile);
        generateScriptForFichaTecnicaSheet(pathOfXlsxFile, sqlScriptFile);
        generateScriptForPlantasSheet(pathOfXlsxFile, sqlScriptFile);
        generateScriptForOperacoesSheet(pathOfXlsxFile, sqlScriptFile);
    }

    public static void appendTextFileToSQL(String textFilePath, String sqlFilePath) {
        try (FileWriter fileWriter = new FileWriter(sqlFilePath, true);
             BufferedReader bufferedReader = new BufferedReader(new FileReader(textFilePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileWriter.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateScriptForOperacoesSheet(String pathOfXlsxFile, String pathToSqlFile) {
        try (FileInputStream xlsxFile = new FileInputStream(pathOfXlsxFile);
             Workbook spreadsheet = new XSSFWorkbook(xlsxFile)) {

            for (Sheet sheet : spreadsheet) {
                String sheetName = sheet.getSheetName();
                if (sheetName.equals("Operações")) {

                    try (FileWriter arquivoSQL = new FileWriter(pathToSqlFile, true)) {
                        Row headerRow = sheet.getRow(0);

                        int id = 700;



                        for (Row line : sheet) {
                            if (line.getRowNum() == 0) {
                                // Pule a primeira linha, pois vamos definir o cabeçalho abaixo
                                continue;
                            }

                            String name;
                            name = "Operacao";

                            // Cria um StringBuilder para cada linha de dados, introduzindo o cabeçalho
                            StringBuilder insertSQL = new StringBuilder("INSERT INTO " + name + " (");
                            insertSQL.append("ID_Operacao").append(", ");

                            for (int cellNum = 0; cellNum < headerRow.getLastCellNum(); cellNum++) {
                                Cell cell = headerRow.getCell(cellNum);
                                Cell cellBelow = line.getCell(cellNum);
                                if (cell.getCellType() == CellType.STRING && cellBelow != null && cellBelow.getCellType() != CellType.BLANK) {

                                    if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().equalsIgnoreCase("Parcela")) {
                                        String columnName = cell.getStringCellValue();
                                        if (columnName.equalsIgnoreCase("ID Parcela")) {
                                            String columnname = "ParcelaID_Parcela";
                                            insertSQL.append(columnname).append(", ");
                                        } else if (columnName.equalsIgnoreCase("Operação")) {
                                            String columnname = "Tipo_Operacao";
                                            insertSQL.append(columnname).append(", ");
                                        } else if (columnName.equalsIgnoreCase("Modo")) {
                                            String columnname = "Modo_Tipo_Operacao";
                                            insertSQL.append(columnname).append(", ");
                                        } else if (columnName.equalsIgnoreCase("Data")) {
                                            String columnname = "Data";
                                            insertSQL.append(columnname).append(", ");
                                        } else if (columnName.equalsIgnoreCase("Cultura")) {
                                            String columnname = "CulturaID_Cultura";
                                            insertSQL.append(columnname).append(", ");
                                        } else if (columnName.equalsIgnoreCase("Quantidade")) {
                                            String columnname = "Quantidade";
                                            insertSQL.append(columnname).append(", ");
                                        } else if (columnName.equalsIgnoreCase("Unidade")) {
                                            String columnname = "Unidade";
                                            insertSQL.append(columnname).append(", ");
                                        } else if (columnName.equalsIgnoreCase("Fator de produção")) {
                                            String columnname = "Fator_ProducaoID_FatorProducao";
                                            insertSQL.append(columnname).append(", ");
                                        } else {
                                            insertSQL.append(columnName).append(", ");
                                        }
                                    }
                                }
                            }
                            // Remove a vírgula extra no final
                            insertSQL.delete(insertSQL.length() - 2, insertSQL.length());
                            insertSQL.append(") VALUES (");

                            insertSQL.append(id).append(", ");

                            for (int cellNum = 0; cellNum < headerRow.getLastCellNum(); cellNum++) {
                                Cell celula = line.getCell(cellNum);
                                if (cellNum == 8 && celula != null) {
                                    String value = celula.getStringCellValue();
                                    String subquery = "(SELECT ID_FatorProducao FROM Fator_Producao WHERE Nome_Comercial = '" + value + "')),";
                                    insertSQL.append(subquery);
                                } else if (cellNum == 4 && celula != null){
                                    String value = celula.getStringCellValue();
                                    double valueID = line.getCell(0).getNumericCellValue();
                                    if (DateUtil.isCellDateFormatted(line.getCell(5))) {
                                        Date initialData = line.getCell(5).getDateCellValue();
                                        String nomeCultura = line.getCell(4).getStringCellValue();
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                        String formattedInitialDate = dateFormat.format(initialData);
                                        String subquery = "(SELECT ID_Cultura FROM (SELECT ID_Cultura FROM Cultura WHERE NomeCultura = '" + nomeCultura + "' AND ParcelaID_Parcela = '" + valueID + "' AND ((Tipo = 'Temporária' AND (Data_Final IS NULL OR (Data_Final >= TO_DATE('" + formattedInitialDate + "', 'YYYY-MM-DD') AND Data_Inicial <= TO_DATE('" + formattedInitialDate + "', 'YYYY-MM-DD'))) OR (Tipo = 'Permanente' AND NomeCultura = '" + nomeCultura + "' AND ParcelaID_Parcela = '" + valueID+ "')) ) ORDER BY ID_Cultura ASC) WHERE ROWNUM = 1), ";
                                        insertSQL.append(subquery);
                                    }
                                } else {
                                    // Handle other columns based on their cell type
                                    if (celula != null) {
                                        if (celula.getCellType() == CellType.STRING && !headerRow.getCell(cellNum).getStringCellValue().equalsIgnoreCase("Parcela")) {
                                            insertSQL.append("'").append(celula.getStringCellValue()).append("', ");
                                        } else if (celula.getCellType() == CellType.NUMERIC) {
                                            if (DateUtil.isCellDateFormatted(celula)) {
                                                Date date = celula.getDateCellValue();
                                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                                String formattedDate = dateFormat.format(date);
                                                insertSQL.append("TO_DATE('").append(formattedDate).append("', 'YYYY-MM-DD'), ");
                                            } else {
                                                insertSQL.append(celula.getNumericCellValue()).append(", ");
                                            }
                                        } else if (celula.getCellType() == CellType.BOOLEAN) {
                                            insertSQL.append(celula.getBooleanCellValue()).append(", ");
                                        }
                                    }
                                }
                            }
                            // Remove a vírgula extra no final
                            insertSQL.delete(insertSQL.length() - 2, insertSQL.length());
                            insertSQL.append(");");

                            // Escreve no arquivo .sql
                            arquivoSQL.write(insertSQL + "\n");
                            id++;
                        }
                        for (int i = 0; i < 2; i++) {
                            arquivoSQL.write("\n");
                        }
                        SqlScriptLogGenerator.addLogEntry("Table Operacao Added");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void generateScriptForCulturasSheet(String pathOfXlsxFile, String pathToSqlFile ) {
        try (FileInputStream xlsxFile = new FileInputStream(pathOfXlsxFile);
             Workbook spreadsheet = new XSSFWorkbook(xlsxFile)) {

            for (Sheet sheet : spreadsheet) {
                String sheetName = sheet.getSheetName();
                if (sheetName.equals("Culturas")) {

                    try (FileWriter arquivoSQL = new FileWriter(pathToSqlFile, true)) {
                        Row headerRow = sheet.getRow(0);

                        for (Row line : sheet) {
                            if (line.getRowNum() == 0) {
                                // Pule a primeira linha, pois vamos definir o cabeçalho abaixo
                                continue;
                            }

                            String name = "Cultura";

                            // Cria um StringBuilder para cada linha de dados, introduzindo o cabeçalho
                            StringBuilder insertSQL = new StringBuilder("INSERT INTO " + name + " (");

                            // Variáveis para coletar os valores a serem usados na chamada à procedura
                            int parcelaID = 0; // Substitua pelo valor correto
                            String nomeCultura = ""; // Substitua pelo valor correto
                            double quantidade = 0.0; // Substitua pelo valor correto

                            for (int cellNum = 0; cellNum < headerRow.getLastCellNum(); cellNum++) {
                                Cell cell = headerRow.getCell(cellNum);
                                Cell cellBelow = line.getCell(cellNum);
                                if (cell.getCellType() == CellType.STRING && cellBelow != null && cellBelow.getCellType() != CellType.BLANK) {

                                    if (cell.getCellType() == CellType.STRING) {
                                        String columnName = cell.getStringCellValue();
                                        if (columnName.equalsIgnoreCase("ID")) {
                                            String columnname = "ParcelaID_Parcela";
                                            insertSQL.append(columnname).append(", ");
                                            parcelaID = (int) cellBelow.getNumericCellValue();
                                        } else if (columnName.equalsIgnoreCase("Cultura")) {
                                            String columnname = "NomeCultura";
                                            insertSQL.append(columnname).append(", ");
                                            nomeCultura = cellBelow.getStringCellValue();
                                        } else if (columnName.equalsIgnoreCase("Tipo")) {
                                            String columnname = "Tipo";
                                            insertSQL.append(columnname).append(", ");
                                        } else if (columnName.equalsIgnoreCase("Data Inicial")) {
                                            String columnname = "Data_Inicial";
                                            insertSQL.append(columnname).append(", ");
                                        } else if (columnName.equalsIgnoreCase("Data Final")) {
                                            String columnname = "Data_Final";
                                            insertSQL.append(columnname).append(", ");
                                        } else if (columnName.equalsIgnoreCase("Quantidade")) {
                                            String columnname = "Quantidade";
                                            insertSQL.append(columnname).append(", ");
                                            quantidade = cellBelow.getNumericCellValue();
                                        } else if (columnName.equalsIgnoreCase("Unidades")) {
                                            String columnname = "Unidade";
                                            insertSQL.append(columnname).append(", ");
                                        } else {
                                            insertSQL.append(columnName).append(", ");
                                        }
                                    }
                                }
                            }

                            // Remove a vírgula extra no final
                            insertSQL.delete(insertSQL.length() - 2, insertSQL.length());
                            insertSQL.append(") VALUES (");

                            for (int cellNum = 0; cellNum < headerRow.getLastCellNum(); cellNum++) {
                                Cell celula = line.getCell(cellNum);
                                // Ignora as células da coluna "Tipo"
                                if (celula != null) {
                                    if (celula.getCellType() == CellType.STRING) {
                                        insertSQL.append("'").append(celula.getStringCellValue()).append("', ");
                                    } else if (celula.getCellType() == CellType.NUMERIC) {
                                        if (DateUtil.isCellDateFormatted(celula)) {
                                            Date date = celula.getDateCellValue();
                                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                            String formattedDate = dateFormat.format(date);
                                            insertSQL.append("TO_DATE('").append(formattedDate).append("', 'YYYY-MM-DD'), ");
                                        } else {
                                            insertSQL.append(celula.getNumericCellValue()).append(", ");
                                        }
                                    } else if (celula.getCellType() == CellType.BOOLEAN) {
                                        insertSQL.append(celula.getBooleanCellValue()).append(", ");
                                    } else {
                                        // Se o tipo de célula não for string, numérico ou boolean
                                        insertSQL.append("NULL, ");
                                    }
                                }
                            }

                            // Remove a vírgula extra no final
                            insertSQL.delete(insertSQL.length() - 2, insertSQL.length());
                            insertSQL.append(");");

                            // Escreve no arquivo .sql
                            arquivoSQL.write(insertSQL + "\n");

                        }
                        for (int i = 0; i < 2; i++) {
                            arquivoSQL.write("\n");
                        }
                        SqlScriptLogGenerator.addLogEntry("Table Culturas Added");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public static void generateScriptForFichaTecnicaSheet(String pathOfXlsxFile, String pathToSqlFile) {
        try (FileInputStream xlsxFile = new FileInputStream(pathOfXlsxFile);
             Workbook spreadsheet = new XSSFWorkbook(xlsxFile)) {

            for (Sheet sheet : spreadsheet) {
                String sheetName = sheet.getSheetName();
                if (sheetName.equals("Fator Produção")) {

                    try (FileWriter arquivoSQLFichaTecnica = new FileWriter(pathToSqlFile, true)) {

                        Row headerRow = sheet.getRow(0);

                        // Crie um StringBuilder para o cabeçalho da tabela Ficha_Tecnica
                        int key = 300;
                        int id = 600;
                        for (Row line : sheet) {
                            if (line.getRowNum() == 0) {
                                // Pule a primeira linha, pois vamos definir o cabeçalho abaixo
                                continue;
                            }

                            String tableName;
                            tableName = "Ficha_Tecnica";

                            // Crie um StringBuilder para cada linha de dados, introduzindo o cabeçalho
                            StringBuilder insertSQLFichaTecnica = new StringBuilder("INSERT INTO " + tableName + "(");

                            insertSQLFichaTecnica.append("ID_FichaTecnica").append(", ");
                            insertSQLFichaTecnica.append("Fator_ProducaoID_FatorProducao").append(", ");

                            int aux = 1;
                            for (int cellNum = 0; cellNum < headerRow.getLastCellNum(); cellNum++) {
                                Cell cell = headerRow.getCell(cellNum);
                                Cell cellBellow = line.getCell(cellNum);

                                if (cell.getCellType() == CellType.STRING && cellBellow != null && cellBellow.getCellType() != CellType.BLANK) {
                                    String columnName = cell.getStringCellValue();
                                    if ( columnName.startsWith("C")) {
                                        insertSQLFichaTecnica.append(columnName).append(", ");
                                    }else if (columnName.equalsIgnoreCase("Perc.")){
                                        String columnname = "Perc"+aux;

                                        if (columnname.equals("Perc1")){
                                            insertSQLFichaTecnica.append("Percentagem").append(aux).append(", ");
                                            aux++;
                                        }else if (columnname.equals("Perc2")){
                                            insertSQLFichaTecnica.append("Percentagem").append(aux).append(", ");
                                            aux++;
                                        }else if (columnname.equals("Perc3")){
                                            insertSQLFichaTecnica.append("Percentagem").append(aux).append(", ");
                                            aux++;
                                        }else if (columnname.equals("Perc4")){
                                            insertSQLFichaTecnica.append("Percentagem").append(aux).append(", ");
                                            aux++;
                                        }
                                    }
                                }
                            }
                            // Remova a vírgula extra no final do cabeçalho da tabela Ficha_Tecnica
                            insertSQLFichaTecnica.delete(insertSQLFichaTecnica.length() - 2, insertSQLFichaTecnica.length());
                            insertSQLFichaTecnica.append(") VALUES (");


                            insertSQLFichaTecnica.append(id).append(", ");
                            insertSQLFichaTecnica.append(key).append(", ");


                            for (int cellNum = 5; cellNum < headerRow.getLastCellNum(); cellNum++) {
                                Cell celula = line.getCell(cellNum);

                                // Ignora as células da coluna "Tipo"
                                if (celula != null) {
                                    if (celula.getCellType() == CellType.STRING) {
                                        insertSQLFichaTecnica.append("'").append(celula.getStringCellValue()).append("', ");
                                    } else if (celula.getCellType() == CellType.NUMERIC) {
                                        if (DateUtil.isCellDateFormatted(celula)) {
                                            Date date = celula.getDateCellValue();
                                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                            String formattedDate = dateFormat.format(date);
                                            insertSQLFichaTecnica.append("TO_DATE('").append(formattedDate).append("', 'YYYY-MM-DD'), ");
                                        } else {
                                            insertSQLFichaTecnica.append(celula.getNumericCellValue()).append(", ");
                                        }
                                    } else if (celula.getCellType() == CellType.BOOLEAN) {
                                        insertSQLFichaTecnica.append(celula.getBooleanCellValue()).append(", ");
                                    } else {
                                        // Se o tipo de célula não for string, numérico ou boolean
                                        insertSQLFichaTecnica.append("NULL, ");
                                    }
                                }
                            }

                            // Remova a vírgula extra no final
                            insertSQLFichaTecnica.delete(insertSQLFichaTecnica.length() - 2, insertSQLFichaTecnica.length());

                            insertSQLFichaTecnica.append(");");

                            // Escreva no arquivo .sql
                            arquivoSQLFichaTecnica.write(insertSQLFichaTecnica + "\n");
                            id++;
                            key++;
                        }
                        for (int i = 0; i < 2; i++) {
                            arquivoSQLFichaTecnica.write("\n");
                        }
                        SqlScriptLogGenerator.addLogEntry("Table Ficha_Tecnica Added");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void generateScriptForFatorProducaoSheet(String pathOfXlsxFile, String pathToSqlFile) {
        try (FileInputStream xlsxFile = new FileInputStream(pathOfXlsxFile);
             Workbook spreadsheet = new XSSFWorkbook(xlsxFile)) {

            for (Sheet sheet : spreadsheet) {
                String sheetName = sheet.getSheetName();
                if (sheetName.equals("Fator Produção")) {

                    try (FileWriter arquivoSQLFatorProducao = new FileWriter(pathToSqlFile, true)) {

                        Row headerRow = sheet.getRow(0);

                        int key = 300;
                        for (Row line : sheet) {
                            if (line.getRowNum() == 0) {
                                // Pule a primeira linha, pois vamos definir o cabeçalho abaixo
                                continue;
                            }

                            // Crie um StringBuilder para cada linha de dados, introduzindo o cabeçalho
                            StringBuilder insertSQLFatorProducao = new StringBuilder("INSERT INTO Fator_Producao (ID_FatorProducao, Nome_Comercial, Fabricante, Formato, Tipo_Formulacao, Aplicacao) VALUES (");


                            insertSQLFatorProducao.append(key).append(", ");
                            key++;

                            for (int cellNum = 0; cellNum < headerRow.getLastCellNum(); cellNum++) {
                                Cell cell = headerRow.getCell(cellNum);
                                Cell celula = line.getCell(cellNum);

                                if (cell.getCellType() == CellType.STRING && celula != null) {
                                    String columnName = cell.getStringCellValue();
                                    if (columnName.equalsIgnoreCase("Designação")) {
                                        insertSQLFatorProducao.append("'").append(celula.getStringCellValue()).append("', ");
                                    } else if (columnName.equalsIgnoreCase("Fabricante")) {
                                        insertSQLFatorProducao.append("'").append(celula.getStringCellValue()).append("', ");
                                    } else if (columnName.equalsIgnoreCase("Formato")) {
                                        insertSQLFatorProducao.append("'").append(celula.getStringCellValue()).append("', ");
                                    } else if (columnName.equalsIgnoreCase("Tipo")) {
                                        insertSQLFatorProducao.append("'").append(celula.getStringCellValue()).append("', ");
                                    } else if (columnName.equalsIgnoreCase("Aplicação")) {
                                        insertSQLFatorProducao.append("'").append(celula.getStringCellValue()).append("');");
                                    }
                                }
                            }

                            // Remova a vírgula extra no final
                            insertSQLFatorProducao.delete(insertSQLFatorProducao.length() - 2, insertSQLFatorProducao.length());

                            insertSQLFatorProducao.append(");");

                            // Escreva no arquivo .sql
                            arquivoSQLFatorProducao.write(insertSQLFatorProducao + "\n");
                        }
                        for (int i = 0; i < 2; i++) {
                            arquivoSQLFatorProducao.write("\n");
                        }
                        SqlScriptLogGenerator.addLogEntry("Table Fator_Producao Added");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void generateScriptForPlantasSheet(String pathOfXlsxFile, String pathToSqlFile) {
        try (FileInputStream xlsxFile = new FileInputStream(pathOfXlsxFile);
             Workbook spreadsheet = new XSSFWorkbook(xlsxFile)) {

            for (Sheet sheet : spreadsheet) {
                String sheetName = sheet.getSheetName();
                if (sheetName.equals("Plantas")) {

                    try (FileWriter arquivoSQL = new FileWriter(pathToSqlFile, true)) {
                        Row headerRow = sheet.getRow(0);
                        int id = 500;
                        for (Row line : sheet) {
                            if (line.getRowNum() == 0) {
                                // Pule a primeira linha, pois vamos definir o cabeçalho abaixo
                                continue;
                            }

                            String name;
                            name = "Planta";

                            // Cria um StringBuilder para cada linha de dados, introduzindo o cabeçalho
                            StringBuilder insertSQL = new StringBuilder("INSERT INTO " + name + " (");
                            insertSQL.append("ID_Planta").append(", ");

                            for (int cellNum = 0; cellNum < headerRow.getLastCellNum(); cellNum++) {
                                Cell cell = headerRow.getCell(cellNum);
                                Cell cellBelow = line.getCell(cellNum);
                                if (cell.getCellType() == CellType.STRING && cellBelow != null && cellBelow.getCellType() != CellType.BLANK) {

                                    if (cell.getCellType() == CellType.STRING) {
                                        String columnName = cell.getStringCellValue();
                                        if (columnName.equalsIgnoreCase("Espécie")) {
                                            String columnname = "Especie";
                                            insertSQL.append(columnname).append(", ");
                                        } else if (columnName.equalsIgnoreCase("Nome Comum")) {
                                            String columnname = "Nome_Comum";
                                            insertSQL.append(columnname).append(", ");
                                        } else if (columnName.equalsIgnoreCase("Variedade")) {
                                            String columnname = "Variedade";
                                            insertSQL.append(columnname).append(", ");
                                        } else if (columnName.equalsIgnoreCase("Tipo Plantação")) {
                                            String columnname = "TipoPlantacao";
                                            insertSQL.append(columnname).append(", ");
                                        } else if (columnName.equalsIgnoreCase("Sementeira/Plantação")) {
                                            String columnname = "Sementeira";
                                            insertSQL.append(columnname).append(", ");
                                        } else if (columnName.equalsIgnoreCase("Poda")) {
                                            String columnname = "Poda";
                                            insertSQL.append(columnname).append(", ");
                                        } else if (columnName.equalsIgnoreCase("Floração")) {
                                            String columnname = "Floracao";
                                            insertSQL.append(columnname).append(", ");
                                        } else if (columnName.equalsIgnoreCase("Colheita")) {
                                            String columnname = "Colheita";
                                            insertSQL.append(columnname).append(", ");
                                        } else {
                                            insertSQL.append(columnName).append(", ");
                                        }
                                    }
                                }
                            }
                            // Remove a vírgula extra no final
                            insertSQL.delete(insertSQL.length() - 2, insertSQL.length());
                            insertSQL.append(") VALUES (");

                            insertSQL.append(id).append(", ");


                            for (int cellNum = 0; cellNum < headerRow.getLastCellNum(); cellNum++) {
                                Cell celula = line.getCell(cellNum);
                                // Ignora as células da coluna "Tipo"
                                if (celula != null) {
                                    if (celula.getCellType() == CellType.STRING) {
                                        String cellValue = celula.getStringCellValue();
                                        cellValue = cellValue.replace("'", "");
                                        insertSQL.append("'").append(cellValue).append("', ");
                                    } else if (celula.getCellType() == CellType.NUMERIC) {
                                        if (DateUtil.isCellDateFormatted(celula)) {
                                            Date date = celula.getDateCellValue();
                                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                            String formattedDate = dateFormat.format(date);
                                            insertSQL.append("TO_DATE('").append(formattedDate).append("', 'YYYY-MM-DD'), ");
                                        } else {
                                            insertSQL.append(celula.getNumericCellValue()).append(", ");
                                        }
                                    } else if (celula.getCellType() == CellType.BOOLEAN) {
                                        insertSQL.append(celula.getBooleanCellValue()).append(", ");
                                    } else {
                                        // Se o tipo de célula não for string, numérico ou boolean
                                        insertSQL.append("NULL, ");
                                    }
                                }
                            }
                            // Remove a vírgula extra no final
                            insertSQL.delete(insertSQL.length() - 2, insertSQL.length());
                            insertSQL.append(");");

                            // Escreve no arquivo .sql
                            arquivoSQL.write(insertSQL + "\n");
                            id++;
                        }
                        for (int i = 0; i < 2; i++) {
                            arquivoSQL.write("\n");
                        }
                        SqlScriptLogGenerator.addLogEntry("Table Planta Added");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void generateScriptForExploracaoAgricolaSheet(String pathOfXlsxFile, String pathToSqlFiles) {
        try (FileInputStream xlsxFile = new FileInputStream(pathOfXlsxFile);
             Workbook spreadsheet = new XSSFWorkbook(xlsxFile)) {

            for (Sheet sheet : spreadsheet) {
                String sheetName = sheet.getSheetName();
                if (sheetName.equals("Exploração agrícola")) {

                    try (FileWriter arquivoSQL = new FileWriter(pathToSqlFiles, true)) {
                        Row headerRow = sheet.getRow(0);

                        for (Row line : sheet) {
                            if (line.getRowNum() == 0) {
                                // Pule a primeira linha, pois vamos definir o cabeçalho abaixo
                                continue;
                            }

                            String tipo = line.getCell(1).getStringCellValue().toLowerCase();

                            String tableName;
                            if ("parcela".equals(tipo)) {
                                tableName = "Parcela";
                            } else if ("armazém".equals(tipo) || "garagem".equals(tipo) || "moinho".equals(tipo) || "rega".equals(tipo)) {
                                tableName = "Edificio";
                            } else {
                                // Pule linhas com tipos desconhecidos
                                continue;
                            }

                            // Cria um StringBuilder para cada linha de dados, introduzindo o cabeçalho
                            StringBuilder insertSQL = new StringBuilder("INSERT INTO " + tableName + " (");

                            for (int cellNum = 0; cellNum < headerRow.getLastCellNum(); cellNum++) {
                                Cell cell = headerRow.getCell(cellNum);
                                Cell cellBelow = line.getCell(cellNum);

                                if (cell.getCellType() == CellType.STRING && cellBelow != null && cellBelow.getCellType() != CellType.BLANK) {

                                    if (tableName.equals("Parcela")) {
                                        // Ignora as células da coluna "Tipo"
                                        if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().equalsIgnoreCase("Tipo")) {
                                            String columnName = cell.getStringCellValue();
                                            if (columnName.equalsIgnoreCase("ID")) {
                                                String columnname = "ID_Parcela";
                                                insertSQL.append(columnname).append(", ");
                                            } else if (columnName.equalsIgnoreCase("Tipo")) {
                                                String columnname = "Tipo_Edificio";
                                                insertSQL.append(columnname).append(", ");
                                            } else if (columnName.equalsIgnoreCase("Designação")) {
                                                String columnname = "Designacao";
                                                insertSQL.append(columnname).append(", ");
                                            } else if (columnName.equalsIgnoreCase("Área")||columnName.equalsIgnoreCase("Dimensão")) {
                                                String columnname = "Area";
                                                insertSQL.append(columnname).append(", ");
                                            } else if (columnName.equalsIgnoreCase("Unidade")) {
                                                String columnname = "Unidade_Area";
                                                insertSQL.append(columnname).append(", ");
                                            } else {
                                                insertSQL.append(columnName).append(", ");
                                            }
                                        }
                                    } else {
                                        if (cell.getCellType() == CellType.STRING) {
                                            String columnName = cell.getStringCellValue();
                                            if (columnName.equalsIgnoreCase("ID")) {
                                                String columnname = "ID_Edificio";
                                                insertSQL.append(columnname).append(", ");
                                            } else if (columnName.equalsIgnoreCase("Tipo")) {
                                                String columnname = "Tipo_Edificio";
                                                insertSQL.append(columnname).append(", ");
                                            } else if (columnName.equalsIgnoreCase("Designação")) {
                                                String columnname = "Designacao";
                                                insertSQL.append(columnname).append(", ");
                                            } else if (columnName.equalsIgnoreCase("Dimensão")) {
                                                String columnname = "Dimensao";
                                                insertSQL.append(columnname).append(", ");
                                            } else if (columnName.equalsIgnoreCase("Área")) {
                                                String columnname = "Area";
                                                insertSQL.append(columnname).append(", ");
                                            }else if (columnName.equalsIgnoreCase("Unidade")) {
                                                String columnname = "Unidade_Area";
                                                insertSQL.append(columnname).append(", ");
                                            } else {
                                                insertSQL.append(columnName).append(", ");
                                            }
                                        }
                                    }
                                }
                            }
                            // Remove a vírgula extra no final
                            insertSQL.delete(insertSQL.length() - 2, insertSQL.length());
                            insertSQL.append(") VALUES (");

                            for (int cellNum = 0; cellNum < headerRow.getLastCellNum(); cellNum++) {
                                Cell celula = line.getCell(cellNum);
                                if (tableName.equals("Parcela")) {
                                    // Ignora as células da coluna "Tipo"
                                    if (celula != null && !headerRow.getCell(cellNum).getStringCellValue().equalsIgnoreCase("Tipo")) {
                                        if (celula.getCellType() == CellType.STRING) {
                                            insertSQL.append("'").append(celula.getStringCellValue()).append("', ");
                                        } else if (celula.getCellType() == CellType.NUMERIC) {
                                            if (DateUtil.isCellDateFormatted(celula)) {
                                                Date date = celula.getDateCellValue();
                                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                                String formattedDate = dateFormat.format(date);
                                                insertSQL.append("TO_DATE('").append(formattedDate).append("', 'YYYY-MM-DD'), ");
                                            } else {
                                                insertSQL.append(celula.getNumericCellValue()).append(", ");
                                            }
                                        } else if (celula.getCellType() == CellType.BOOLEAN) {
                                            insertSQL.append(celula.getBooleanCellValue()).append(", ");
                                        } else {
                                            // Se o tipo de célula não for string, numérico ou boolean
                                            insertSQL.append("NULL, ");
                                        }
                                    }
                                } else {
                                    if (celula != null) {
                                        if (celula.getCellType() == CellType.STRING) {
                                            insertSQL.append("'").append(celula.getStringCellValue()).append("', ");
                                        } else if (celula.getCellType() == CellType.NUMERIC) {
                                            if (DateUtil.isCellDateFormatted(celula)) {
                                                Date date = celula.getDateCellValue();
                                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                                String formattedDate = dateFormat.format(date);
                                                insertSQL.append("TO_DATE('" + formattedDate + "', 'YYYY-MM-DD'), ");
                                            } else {
                                                insertSQL.append(celula.getNumericCellValue()).append(", ");
                                            }
                                        } else if (celula.getCellType() == CellType.BOOLEAN) {
                                            insertSQL.append(celula.getBooleanCellValue()).append(", ");
                                        } else {
                                            // Se o tipo de célula não for string, numérico ou boolean
                                            insertSQL.append("NULL, ");
                                        }
                                    }
                                }
                            }
                            // Remove a vírgula extra no final
                            insertSQL.delete(insertSQL.length() - 2, insertSQL.length());
                            insertSQL.append(");");

                            // Escreve no arquivo .sql
                            arquivoSQL.write(insertSQL + "\n");
                        }
                        for (int i = 0; i < 2; i++) {
                            arquivoSQL.write("\n");
                        }
                        SqlScriptLogGenerator.addLogEntry("Tables Parcela and Edificio Added");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}