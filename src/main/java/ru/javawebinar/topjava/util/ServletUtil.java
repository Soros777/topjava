package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

public class ServletUtil {
    public static LocalTime[] getBoundedTime(Map<String, String[]> parameterMap) {
        String[] fromTimeStr = parameterMap.get("startTime");
        String[] toTimeStr = parameterMap.get("endTime");
        LocalTime fromTime = (fromTimeStr == null || fromTimeStr[0].trim().isEmpty()) ? LocalTime.MIN : LocalTime.parse(fromTimeStr[0]);
        LocalTime toTime = (toTimeStr == null || toTimeStr[0].trim().isEmpty()) ? LocalTime.MAX : LocalTime.parse(toTimeStr[0]);

        return new LocalTime[]{fromTime, toTime};
    }

    public static LocalDate[] getBoundedDate(Map<String, String[]> parameterMap) {
        String[] fromDateStr = parameterMap.get("startDate");
        String[] toDateStr = parameterMap.get("endDate");
        LocalDate fromDate = (fromDateStr == null || fromDateStr[0].trim().isEmpty()) ? LocalDate.MIN : LocalDate.parse(fromDateStr[0]);
        LocalDate toDate = (toDateStr == null || toDateStr[0].trim().isEmpty()) ? LocalDate.MAX : LocalDate.parse(toDateStr[0]);

        return new LocalDate[]{fromDate, toDate};
    }

    public static LocalDate getOrDefault(String dateString, LocalDate def) {
        return dateString == null || dateString.trim().isEmpty() ? def : LocalDate.parse(dateString);
    }

    public static LocalTime getOrDefault(String timeString, LocalTime def) {
        return timeString == null || timeString.trim().isEmpty() ? def : LocalTime.parse(timeString);
    }
}
