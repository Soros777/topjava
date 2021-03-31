package ru.javawebinar.topjava.util;

import org.springframework.format.Parser;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalTimeFormatter implements Parser<LocalTime> {

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_TIME;

    @Override
    public LocalTime parse(String formatted, Locale locale) throws ParseException {
        if(!StringUtils.hasLength(formatted)) {
            return null;
        }
        return LocalTime.parse(formatted, formatter);
    }
}
