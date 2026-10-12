package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalTime;

public class SleepingSession {
    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final double durationHours;
    private final String status;


    public SleepingSession(LocalDate date, LocalTime startTime, LocalTime endTime, double durationHours, String status) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.durationHours = durationHours;
        this.status = status;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public double getDurationHours() {
        return durationHours;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return date + " | " + startTime + " - " + endTime + " (" + durationHours + " ч)";
    }
}