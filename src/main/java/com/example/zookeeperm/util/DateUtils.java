package com.example.zookeeperm.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class DateUtils {
    public static String toAge(long time) {
        var creation = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
        var now = LocalDateTime.now();

        var years = ChronoUnit.YEARS.between(creation, now);
        if (years > 0) {
            return pluralize(years, "year");
        }
        var months = ChronoUnit.MONTHS.between(creation, now);
        if (months > 0) {
            return pluralize(months, "month");
        }
        var days = ChronoUnit.DAYS.between(creation, now);
        if (days > 0) {
            return pluralize(days, "day");
        }
        var hours = ChronoUnit.HOURS.between(creation, now);
        if (hours > 0) {
            return pluralize(hours, "hour");
        }
        var minutes = ChronoUnit.MINUTES.between(creation, now);
        if (minutes > 0) {
            return pluralize(minutes, "minute");
        }
        var seconds = ChronoUnit.SECONDS.between(creation, now);
        if (seconds > 0) {
            return pluralize(seconds, "seconds");
        }

        return "few seconds ago";
    }

    private static String pluralize(long strictlyPositiveCount, String singular) {
        return pluralize(strictlyPositiveCount, singular, singular + "s");
    }

    private static String pluralize(long strictlyPositiveCount, String singular, String plural) {
        if (strictlyPositiveCount == 1) {
            return "1 " + singular + " ago";
        }
        return strictlyPositiveCount + " " + plural + " ago";
    }
}
