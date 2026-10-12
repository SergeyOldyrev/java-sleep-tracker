package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SleeplessNightsCalculatorTest {
    private final SleeplessNightsCalculator calculator = new SleeplessNightsCalculator();

    @Test
    void shouldCountCorrectlyWhenMultipleSessionsAreBelowThreshold() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDate.now(), LocalTime.of(23, 0), LocalTime.of(23, 30), 0.5, "GOOD"),
                new SleepingSession(LocalDate.now(), LocalTime.of(0, 0), LocalTime.of(0, 48), 0.8, "GOOD"),
                new SleepingSession(LocalDate.now(), LocalTime.of(22, 0), LocalTime.of(0, 0), 2.0, "GOOD")
        );
        SleepAnalysisResult result = calculator.apply(sessions);
        assertEquals("Количество бессонных ночей (сон < 1 часа)", result.getLabel());
        assertEquals("2", result.getValue());
    }

    @Test
    void shouldReturnZeroWhenNoSessionsAreBelowThreshold() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDate.now(), LocalTime.of(23, 0), LocalTime.of(1, 0), 2.0, "GOOD"),
                new SleepingSession(LocalDate.now(), LocalTime.of(0, 0), LocalTime.of(3, 0), 3.0, "GOOD"),
                new SleepingSession(LocalDate.now(), LocalTime.of(22, 0), LocalTime.of(1, 0), 3.5, "GOOD")
        );

        SleepAnalysisResult result = calculator.apply(sessions);
        assertEquals("0", result.getValue());
    }

    @Test
    void shouldHandleBoundaryConditionExactlyOneHour() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDate.now(), LocalTime.of(23, 0), LocalTime.of(0, 0), 1.0, "GOOD")
        );
        SleepAnalysisResult result = calculator.apply(sessions);
        assertEquals("0", result.getValue());
    }

    @Test
    void shouldHandleBoundaryConditionJustBelowOneHour() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDate.now(), LocalTime.of(23, 0), LocalTime.of(23, 59), 0.99, "GOOD")
        );

        SleepAnalysisResult result = calculator.apply(sessions);
        assertEquals("1", result.getValue());
    }
}