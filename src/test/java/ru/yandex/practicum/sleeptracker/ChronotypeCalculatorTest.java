package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChronotypeCalculatorTest {
    private final ChronotypeCalculator calculator = new ChronotypeCalculator();

    @Test
    void shouldIgnoreDaytimeAndShortSessionsAndReturnPigeon() {

        List<SleepingSession> sessions = List.of(new SleepingSession(LocalDate.now(), LocalTime.of(14, 0), LocalTime.of(16, 0), 2.0, "GOOD"),
                new SleepingSession(LocalDate.now(), LocalTime.of(23, 0), LocalTime.of(23, 30), 0.5, "GOOD"),
                new SleepingSession(LocalDate.now(), LocalTime.of(12, 0), LocalTime.of(14, 0), 2.0, "GOOD")
        );
        SleepAnalysisResult result = calculator.apply(sessions);
        assertNotNull(result);
        assertTrue(result.getLabel().contains("Хронотип"));
        assertEquals("Голубь", result.getValue());
    }

    @Test
    void shouldCorrectlyClassifyOwl() {
        List<SleepingSession> sessions = List.of(new SleepingSession(LocalDate.now(),
                LocalTime.of(23, 30), LocalTime.of(10, 0), 10.5, "GOOD"));
        SleepAnalysisResult result = calculator.apply(sessions);
        assertEquals("Сова", result.getValue());
    }

    @Test
    void shouldCorrectlyClassifyLark() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDate.now(), LocalTime.of(21, 0), LocalTime.of(6, 0), 9.0, "GOOD")
        );

        SleepAnalysisResult result = calculator.apply(sessions);

        assertEquals("Жаворонок", result.getValue());
    }


    @Test
    void shouldResolveTieByChoosingPigeon() {
        List<SleepingSession> sessions =
                List.of(new SleepingSession(LocalDate.now(), LocalTime.of(23, 30), LocalTime.of(10, 0), 10.5, "GOOD"),
                        new SleepingSession(LocalDate.now(), LocalTime.of(21, 0), LocalTime.of(6, 0), 9.0, "GOOD"));

        SleepAnalysisResult result = calculator.apply(sessions);
        assertEquals("Голубь", result.getValue());
    }

    @Test
    void shouldClassifyMixedSessionAsPigeon() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDate.now(), LocalTime.of(23, 30), LocalTime.of(6, 0), 6.5, "GOOD")
        );

        SleepAnalysisResult result = calculator.apply(sessions);

        assertEquals("Голубь", result.getValue());
    }
}
