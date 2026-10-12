package ru.yandex.practicum.sleeptracker;

import java.util.Objects;

public class SleepAnalysisResult {
    private final String label;
    private final int intValue;
    private final String stringValue;

    public SleepAnalysisResult(String label, int value) {
        this.label = label;
        this.intValue = value;
        this.stringValue = null;
    }

    public SleepAnalysisResult(String label, String value) {
        this.label = label;
        this.intValue = 0;
        this.stringValue = value;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        if (stringValue != null) {
            return stringValue;
        }
        return String.valueOf(intValue);
    }

    @Override
    public String toString() {
        return label + ": " + Objects.requireNonNullElse(stringValue, intValue);
    }
}