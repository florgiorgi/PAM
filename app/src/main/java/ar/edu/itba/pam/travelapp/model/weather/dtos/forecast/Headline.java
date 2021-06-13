package ar.edu.itba.pam.travelapp.model.weather.dtos.forecast;

public class Headline {
    private final String effectiveDate;
    private final Integer effectiveEpochDate;
    private final Integer severity;
    private final String text;
    private final String category;
    private final Object endDate;
    private final Object endEpochDate;
    private final String mobileLink;
    private final String link;

    public Headline(String effectiveDate, Integer effectiveEpochDate, Integer severity, String text,
                    String category, Object endDate, Object endEpochDate, String mobileLink, String link) {
        this.effectiveDate = effectiveDate;
        this.effectiveEpochDate = effectiveEpochDate;
        this.severity = severity;
        this.text = text;
        this.category = category;
        this.endDate = endDate;
        this.endEpochDate = endEpochDate;
        this.mobileLink = mobileLink;
        this.link = link;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public Integer getEffectiveEpochDate() {
        return effectiveEpochDate;
    }

    public Integer getSeverity() {
        return severity;
    }

    public String getText() {
        return text;
    }

    public String getCategory() {
        return category;
    }

    public Object getEndDate() {
        return endDate;
    }

    public Object getEndEpochDate() {
        return endEpochDate;
    }

    public String getMobileLink() {
        return mobileLink;
    }

    public String getLink() {
        return link;
    }
}
