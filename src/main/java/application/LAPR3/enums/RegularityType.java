package application.LAPR3.enums;

public enum RegularityType {
    EVERY("T"),
    ODD("I"),
    EVEN("P"),
    EVERY_THREE("3");

    private final String code;

    RegularityType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
