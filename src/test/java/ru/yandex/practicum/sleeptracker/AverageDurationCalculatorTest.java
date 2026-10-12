package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AverageDurationCalculatorTest {

    private final AverageDurationCalculator calculator = new AverageDurationCalculator();

    @Test
    void shouldCalculateAverageCorrectly() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDate.now(), LocalTime.of(23, 0), LocalTime.of(0, 0), 1.0, "GOOD"),
                new SleepingSession(LocalDate.now(), LocalTime.of(23, 0), LocalTime.of(1, 0), 2.0, "GOOD"),
                new SleepingSession(LocalDate.now(), LocalTime.of(23, 0), LocalTime.of(2, 0), 3.0, "GOOD")
        );

        SleepAnalysisResult result = calculator.apply(sessions);

        assertEquals("120", result.getValue());
    }

    @Test
    void shouldReturnMinusOneForEmptyList() {
        List<SleepingSession> sessions = List.of();
        SleepAnalysisResult result = calculator.apply(sessions);

        assertEquals("-1", result.getValue());
    }
    @Test
    void shouldHandleSingleSession() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDate.now(), LocalTime.of(23, 0), LocalTime.of(0, 30), 1.5, "GOOD")
        );
        SleepAnalysisResult result = calculator.apply(sessions);
        assertEquals("90", result.getValue());
    }

    @Test
    void shouldRoundFractionalAverage() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDate.now(), LocalTime.of(23, 0), LocalTime.of(0, 0), 1.0, "GOOD"),
                new SleepingSession(LocalDate.now(), LocalTime.of(23, 0), LocalTime.of(1, 0), 2.0, "GOOD")
        );

        SleepAnalysisResult result = calculator.apply(sessions);
        assertEquals("90", result.getValue());
    }

    @Test
    void shouldHandleHalfHourPrecision() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDate.now(), LocalTime.of(23, 0), LocalTime.of(0, 12), 1.2, "GOOD"),
                new SleepingSession(LocalDate.now(), LocalTime.of(23, 0), LocalTime.of(0, 18), 1.3, "GOOD")
        );

        SleepAnalysisResult result = calculator.apply(sessions);

        assertEquals("75", result.getValue());
    }
}