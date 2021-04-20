package cz.cvut.fel.kolovjan.utils;

import cz.cvut.fel.kolovjan.exception.AlanineException;

public enum TimeUnitEnum {
    MINUTES("m"),
    SECONDS("s");
    private final String timeUnit;

    TimeUnitEnum(String timeUnit) {
        this.timeUnit = timeUnit;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public String getHumanOutput() {
        if (timeUnit.equals("m")) {
            return "minutes";
        } else {
            return "seconds";
        }
    }

    @Override
    public String toString() {
        return "TimeUnitEnum{" +
                "TimeUnit='" + timeUnit + '\'' +
                '}';
    }

    public static TimeUnitEnum fromString(String s) {
        if(s.equals("s")){
            return TimeUnitEnum.SECONDS;
        }else if (s.equals("m")){
            return TimeUnitEnum.MINUTES;
        }else {
            throw new AlanineException("Invalid time unit, use only 'm' for minutes or 's' for seconds.");
        }
    }
}
