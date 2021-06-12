package ar.edu.itba.pam.travelapp.model.weather.location;

public class TimeZone {
    private final String code;
    private final String name;
    private final Integer gmtOffset;
    private final Boolean isDaylightSaving;
    private final Object nextOffsetChange;

    public TimeZone(String code, String name, Integer gmtOffset, Boolean isDaylightSaving, Object nextOffsetChange) {
        this.code = code;
        this.name = name;
        this.gmtOffset = gmtOffset;
        this.isDaylightSaving = isDaylightSaving;
        this.nextOffsetChange = nextOffsetChange;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Integer getGmtOffset() {
        return gmtOffset;
    }

    public Boolean getDaylightSaving() {
        return isDaylightSaving;
    }

    public Object getNextOffsetChange() {
        return nextOffsetChange;
    }
}
