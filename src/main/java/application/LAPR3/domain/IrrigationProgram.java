package application.LAPR3.domain;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAmount;
import java.util.List;

@SuppressWarnings("ClassCanBeRecord")
public class IrrigationProgram {
    private final List<String> startTimes;
    private final LocalDate startDate;
    private final String parcel;
    private final int irrigationDuration;
    private final String regularity;
    private boolean hasMix = false;
    private String mix;
    private int mixRegularity;

    public IrrigationProgram(List<String> startTimes, LocalDate startDate, String parcel, int irrigationDuration, String regularity, String mix, int mixRegularity, boolean hasMix) {
        this.startTimes = startTimes;
        this.startDate = startDate;
        this.parcel = parcel;
        this.irrigationDuration = irrigationDuration;
        this.regularity = regularity;
        this.mix = mix;
        this.mixRegularity = mixRegularity;
        this.hasMix = hasMix;
    }
    public IrrigationProgram(List<String> startTimes, LocalDate startDate, String parcel, int irrigationDuration, String regularity) {
        this.startTimes = startTimes;
        this.startDate = startDate;
        this.parcel = parcel;
        this.irrigationDuration = irrigationDuration;
        this.regularity = regularity;
    }

    public String getMix() { return mix;}

    public int getMixRegularity() { return mixRegularity;}

    public List<String> getStartTimes() {
        return startTimes;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public String getParcel() {
        return parcel;
    }

    public int getIrrigationDuration() {
        return irrigationDuration;
    }

    public String getRegularity() {
        return regularity;
    }

    public TemporalAmount getIrrigationTemporalAmount() {
        return Duration.ofMinutes(irrigationDuration);
    }

    public boolean isHasMix() {
        return hasMix;
    }

    public void setHasMix(boolean hasMix) {
        this.hasMix = hasMix;
    }
}
