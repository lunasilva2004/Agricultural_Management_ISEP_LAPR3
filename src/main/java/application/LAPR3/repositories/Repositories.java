package application.LAPR3.repositories;

public class Repositories {
    public Repositories() {
    }
    public static Repositories getInstance() {
        return instance;
    }
    private static final Repositories instance = new Repositories();
    IrrigationProgramRepository irrigationProgramRepository = new IrrigationProgramRepository();

    OperacaoAgricolaRepository operacaoAgricolaRepository = new OperacaoAgricolaRepository();
    public IrrigationProgramRepository getIrrigationProgramRepository() {
        return irrigationProgramRepository;
    }

    public OperacaoAgricolaRepository getOperacaoAgricolaRepository() {
        return operacaoAgricolaRepository;
    }
}
