package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalTime;

public class ServletUtil {

    public static LocalDate getOrDefault(String dateString, LocalDate def) {
        return dateString == null || dateString.trim().isEmpty() ? def : LocalDate.parse(dateString);
    }

    public static LocalTime getOrDefault(String timeString, LocalTime def) {
        return timeString == null || timeString.trim().isEmpty() ? def : LocalTime.parse(timeString);
    }
}
