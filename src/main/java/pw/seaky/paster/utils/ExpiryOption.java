package pw.seaky.paster.utils;

public enum ExpiryOption {


    ONE_DAY("24 Hours", 1), SEVEN_DAYS("7 Days", 7), FOURTEEN_DAYS("14 Days", 14), THIRTY_DAYS("30 Days", 30), NEVER("Never", 88888888);

    private final String value;
    private final Integer days;

    ExpiryOption(String value, Integer days) {
        this.value = value;
        this.days = days;

    }

    public Integer getDays() {
        return days;
    }

    public String getValue() {
        return value;
    }

}
