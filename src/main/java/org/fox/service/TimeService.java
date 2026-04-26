package org.fox.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeService {

    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");

    public String getTime(String timezone) {

        timezone = normalize(timezone);
        ZoneId zone;

        if (timezone == null || timezone.isEmpty()) {
            zone = ZoneId.of("UTC");
        } else {
            zone = ZoneId.of(timezone);
        }
        ZonedDateTime now = ZonedDateTime.now(zone);
        return now.format(formatter);
    }

    private String normalize(String timezone) {
        if (timezone == null) return null;
        return timezone.replace(" ", "+");
    }
}
