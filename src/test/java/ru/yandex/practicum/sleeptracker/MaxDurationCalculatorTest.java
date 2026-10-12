package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaxDurationCalculatorTest {
    private final MaxDurationCalculator calculator = new MaxDurationCalculator();

    @Test
    void shouldFindMaxDurationInNormalList() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDate.now(), LocalTime.of(23, 0), LocalTime.of(4, 0), 5.0, "GOOD"),
                new SleepingSession(LocalDate.now(), LocalTime.of(22, 0), LocalTime.of(2, 0), 4.0, "GOOD"),
                new SleepingSession(LocalDate.now(), LocalTime.of(0, 0), LocalTime.of(3, 0), 3.0, "GOOD")
        );

        SleepAnalysisResult result = calculator.apply(sessions);
        assertEquals("Максимальная длительность сна", result.getLabel());
        assertEquals("300", result.getValue());
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
                new SleepingSession(LocalDate.now(), LocalTime.of(23, 0), LocalTime.of(6, 0), 7.0, "GOOD")
        );

        SleepAnalysisResult result = calculator.apply(sessions);
        assertEquals("420", result.getValue());
    }

    @Test
    void shouldHandleAllSessionsWithSameDuration() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDate.now(), LocalTime.of(23, 0), LocalTime.of(3, 0), 4.0, "GOOD"),
                new SleepingSession(LocalDate.now(), LocalTime.of(0, 0), LocalTime.of(4, 0), 4.0, "GOOD"),
                new SleepingSession(LocalDate.now(), LocalTime.of(1, 0), LocalTime.of(5, 0), 4.0, "GOOD")
        );

        SleepAnalysisResult result = calculator.apply(sessions);
        assertEquals("240", result.getValue());
    }

    @Test
    void shouldFindMaxDurationWhenItIsNotFirst() {

        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDate.now(), LocalTime.of(23, 0), LocalTime.of(2, 0), 3.0, "GOOD"),
                new SleepingSession(LocalDate.now(), LocalTime.of(22, 0), LocalTime.of(1, 0), 3.0, "GOOD"),
                new SleepingSession(LocalDate.now(), LocalTime.of(0, 0), LocalTime.of(5, 0), 5.0, "GOOD")
        );

        SleepAnalysisResult result = calculator.apply(sessions);
        assertEquals("300", result.getValue());
    }
}
