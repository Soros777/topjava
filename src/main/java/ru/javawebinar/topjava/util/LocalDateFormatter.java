package ru.javawebinar.topjava.util;

import org.springframework.format.Parser;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateFormatter implements Parser<LocalDate> {

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;

    @Override
    public LocalDate parse(String formatted, Locale locale) throws ParseException {
        if(!StringUtils.hasLength(formatted)) {
            return null;
        }
        return LocalDate.parse(formatted, formatter);
    }
}
