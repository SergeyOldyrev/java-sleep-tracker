package ru.yandex.practicum.sleeptracker;

public class SleepAnalysisResult {
    private final String label;
    private final int value;
    private final String stringValue;

    public SleepAnalysisResult(String label, int value) {
        this.label = label;
        this.value = value;
        this.stringValue = null;
    }
    public SleepAnalysisResult(String label, String stringValue) {
        this.label = label;
        this.value = 0;
        this.stringValue = stringValue;
    }

    @Override
    public String toString() {
        if (stringValue != null) {
            return label + ": " + stringValue;
        }
        return label + ": " + value + " ч";
    }
}