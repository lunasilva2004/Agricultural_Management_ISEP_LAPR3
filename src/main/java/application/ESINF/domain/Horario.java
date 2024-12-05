package application.ESINF.domain;

import java.time.LocalTime;

public class Horario {
    private LocalTime openTime;
    private LocalTime closeTime;

    public Horario(LocalTime openTime, LocalTime closeTime) {
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public LocalTime getOpenTime() {
        return openTime;
    }

    public void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }

    public LocalTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }

    @Override
    public String toString() {
        return "Horario{" + ", openTime=" + openTime + ", closeTime=" + closeTime + '}';
    }
}
