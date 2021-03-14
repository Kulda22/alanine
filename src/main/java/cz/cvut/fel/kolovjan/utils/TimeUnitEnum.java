package cz.cvut.fel.kolovjan.utils;

import cz.cvut.fel.kolovjan.exception.PluginException;

import java.sql.Time;

public enum TimeUnitEnum {
    MINUTES("m"),
    SECONDS("s");
    private final String TimeUnit;

    TimeUnitEnum(String timeUnit) {
        TimeUnit = timeUnit;
    }

    public String getTimeUnit() {
        return TimeUnit;
    }

    @Override
    public String toString() {
        return "TimeUnitEnum{" +
                "TimeUnit='" + TimeUnit + '\'' +
                '}';
    }

    public static TimeUnitEnum fromString(String s)
    {
        if(s.equals("s")){
            return TimeUnitEnum.SECONDS;
        }else if (s.equals("m")){
            return TimeUnitEnum.MINUTES;
        }else {
            throw new PluginException("Invalid time unit, use only 'm' for minutes or 's' for seconds.");
        }
    }
}
