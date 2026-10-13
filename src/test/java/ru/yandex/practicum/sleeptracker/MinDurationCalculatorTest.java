package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.calculator.MinDurationCalculator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MinDurationCalculatorTest {
    private final MinDurationCalculator calculator = new MinDurationCalculator();

    @Test
    void shouldFindMinDurationInNormalList() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDate.now(), LocalTime.of(23, 0), LocalTime.of(4, 0), 5.0, "GOOD"),
                new SleepingSession(LocalDate.now(), LocalTime.of(22, 0), LocalTime.of(2, 0), 4.0, "GOOD"),
                new SleepingSession(LocalDate.now(), LocalTime.of(0, 0), LocalTime.of(2, 0), 2.0, "GOOD")
        );

        SleepAnalysisResult result = calculator.apply(sessions);
        assertEquals("Минимальная длительность сна", result.getLabel()); // ✅ Сравниваем со строкой "120", так как getValue() возвращает String
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
                new SleepingSession(LocalDate.now(), LocalTime.of(23, 0), LocalTime.of(6, 0), 7.0, "GOOD")
        );

        SleepAnalysisResult result = calculator.apply(sessions);
        assertEquals("420", result.getValue());
    }

    @Test
    void shouldHandleAllSessionsWithSameDuration() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDate.now(), LocalTime.of(23, 0), LocalTime.of(2, 0), 3.0, "GOOD"),
                new SleepingSession(LocalDate.now(), LocalTime.of(0, 0), LocalTime.of(3, 0), 3.0, "GOOD"),
                new SleepingSession(LocalDate.now(), LocalTime.of(1, 0), LocalTime.of(4, 0), 3.0, "GOOD")
        );

        SleepAnalysisResult result = calculator.apply(sessions);
        assertEquals("180", result.getValue());
    }

    @Test
    void shouldFindMinDurationWhenItIsNotFirst() {

        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDate.now(), LocalTime.of(23, 0), LocalTime.of(4, 0), 5.0, "GOOD"),
                new SleepingSession(LocalDate.now(), LocalTime.of(22, 0), LocalTime.of(2, 0), 4.0, "GOOD"),
                new SleepingSession(LocalDate.now(), LocalTime.of(0, 0), LocalTime.of(1, 30), 1.5, "GOOD")
        );

        SleepAnalysisResult result = calculator.apply(sessions);
        assertEquals("90", result.getValue());
    }
}
