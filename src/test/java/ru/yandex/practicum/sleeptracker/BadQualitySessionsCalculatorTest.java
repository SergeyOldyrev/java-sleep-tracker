package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.calculator.BadQualitySessionsCalculator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BadQualitySessionsCalculatorTest {
    private final BadQualitySessionsCalculator calculator = new BadQualitySessionsCalculator();

    @Test
    void shouldCountExactBadStatus() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDate.now(), LocalTime.of(23, 0), LocalTime.of(7, 0), 8.0, "BAD"),
                new SleepingSession(LocalDate.now(), LocalTime.of(22, 0), LocalTime.of(6, 0), 8.0, "GOOD"),
                new SleepingSession(LocalDate.now(), LocalTime.of(0, 0), LocalTime.of(8, 0), 8.0, "NORMAL")
        );

        SleepAnalysisResult result = calculator.apply(sessions);
        assertEquals("Количество сессий с плохим качеством сна", result.getLabel());
        assertEquals("1", result.getValue());
    }

    @Test
    void shouldIgnoreCaseAndSpacesInStatus() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDate.now(), LocalTime.of(23, 0), LocalTime.of(7, 0), 8.0, " bad "),
                new SleepingSession(LocalDate.now(), LocalTime.of(22, 0), LocalTime.of(6, 0), 8.0, "BaD"),
                new SleepingSession(LocalDate.now(), LocalTime.of(0, 0), LocalTime.of(8, 0), 8.0, "GOOD"),
                new SleepingSession(LocalDate.now(), LocalTime.of(1, 0), LocalTime.of(9, 0), 8.0, " BAD")
        );

        SleepAnalysisResult result = calculator.apply(sessions);
        assertEquals("3", result.getValue());
    }

    @Test
    void shouldHandleNullAndEmptyList() {
        List<SleepingSession> sessions = List.of();
        SleepAnalysisResult result = calculator.apply(sessions);
        assertEquals("0", result.getValue());
    }

    @Test
    void shouldHandleNullStatusInSession() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDate.now(), LocalTime.of(23, 0), LocalTime.of(7, 0), 8.0, null),
                new SleepingSession(LocalDate.now(), LocalTime.of(22, 0), LocalTime.of(6, 0), 8.0, "BAD")
        );

        SleepAnalysisResult result = calculator.apply(sessions);

        assertEquals("1", result.getValue());
    }

    @Test
    void shouldNotCountPartialMatches() {

        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDate.now(), LocalTime.of(23, 0), LocalTime.of(7, 0), 8.0, "BadQuality"),
                new SleepingSession(LocalDate.now(), LocalTime.of(22, 0), LocalTime.of(6, 0), 8.0, "NotBad"),
                new SleepingSession(LocalDate.now(), LocalTime.of(0, 0), LocalTime.of(8, 0), 8.0, "badly"),
                new SleepingSession(LocalDate.now(), LocalTime.of(1, 0), LocalTime.of(9, 0), 8.0, "GOOD")
        );

        SleepAnalysisResult result = calculator.apply(sessions);

        assertEquals("0", result.getValue());
    }


}
