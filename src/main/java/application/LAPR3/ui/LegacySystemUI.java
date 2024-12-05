package application.LAPR3.ui;

import application.LAPR3.utils.ReadXlslFiletoSqlScript;
import application.LAPR3.utils.Utils;

public class LegacySystemUI implements Runnable{
    public void run()
    {
        String xlsxFilePath = Utils.readStringFromConsole();
        ReadXlslFiletoSqlScript.readXlslFile(xlsxFilePath);
    }
}
