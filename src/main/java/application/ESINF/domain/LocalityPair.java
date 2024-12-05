package application.ESINF.domain;

import java.util.Objects;

public class LocalityPair {
    private final Localidades localidades1;
    private final Localidades localidades2;

    public LocalityPair(Localidades localidades1, Localidades localidades2) {
        this.localidades1 = localidades1;
        this.localidades2 = localidades2;
    }

    public Localidades getFirstHub() {
        return localidades1;
    }

    public Localidades getSecondHub() {
        return localidades2;
    }

    @Override
    public String toString() {
        return "Hub " + localidades1.getNumId() + " to Hub " + localidades2.getNumId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocalityPair localityPair = (LocalityPair) o;
        return (Objects.equals(localidades1, localityPair.localidades1) && Objects.equals(localidades2, localityPair.localidades2)) ||
                (Objects.equals(localidades1, localityPair.localidades2) && Objects.equals(localidades2, localityPair.localidades1));
    }

    @Override
    public int hashCode() {
        return Objects.hash(localidades1, localidades2);
    }
}
