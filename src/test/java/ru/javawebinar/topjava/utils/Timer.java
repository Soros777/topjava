package ru.javawebinar.topjava.utils;

import org.slf4j.Logger;

import java.util.Date;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

public class Timer {
    private static final Logger log = getLogger(Timer.class);
    private Map<String, Long> testTimeResults;
    private long testStartTime;

    public Timer (Map<String, Long> testTimeResults) {
        this.testTimeResults = testTimeResults;
    }


    public void start() {
        testStartTime = new Date().getTime();
    }

    public void finish(String testMethodName) {
        long duration = new Date().getTime() - testStartTime;
        testTimeResults.put(testMethodName, duration);
        log.info("Test method {} was working during {} ms", testMethodName, duration);
    }
}
