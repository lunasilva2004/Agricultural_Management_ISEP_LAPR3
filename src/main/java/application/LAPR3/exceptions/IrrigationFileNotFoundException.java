package application.LAPR3.exceptions;

public class IrrigationFileNotFoundException extends RuntimeException{
        public IrrigationFileNotFoundException(String path) {
            super("No irrigation program found for the path " + path + "!");
        }
    }

