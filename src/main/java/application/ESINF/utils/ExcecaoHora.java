package application.ESINF.utils;

public class ExcecaoHora extends Exception {

    public ExcecaoHora(String message) {
        super(message);
    }

    public static void verificarHora(String hora) throws ExcecaoHora {
        if (!hora.matches("\\d{2}:\\d{2}")) {
            throw new ExcecaoHora("Formato de hora inválido. Use o formato hh:mm.");
        }
    }
}


