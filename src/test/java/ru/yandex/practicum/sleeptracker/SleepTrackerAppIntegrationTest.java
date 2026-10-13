package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class SleepTrackerAppIntegrationTest {

    @Test
    void shouldPrintTotalSessionsCount() {

        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDate.now(), LocalTime.of(23, 0), LocalTime.of(4, 0), 5.0, "GOOD"),
                new SleepingSession(LocalDate.now(), LocalTime.of(22, 0), LocalTime.of(2, 0), 4.0, "GOOD"),
                new SleepingSession(LocalDate.now(), LocalTime.of(0, 0), LocalTime.of(2, 0), 2.0, "GOOD"),
                new SleepingSession(LocalDate.now(), LocalTime.of(23, 0), LocalTime.of(23, 30), 0.5, "BAD")
        );

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {

            SleepTrackerApp tracker = new SleepTrackerApp();
            tracker.runAnalysis(sessions);

            String output = outContent.toString();

            assertTrue(output.contains("Всего сессий сна: 4"),
                    "Вывод должен содержать информацию о количестве сессий. Найдено: " + output);

            assertTrue(output.contains("Минимальная длительность сна"), "Должна быть метрика минимума");
            assertTrue(output.contains("Хронотип пользователя"), "Должна быть метрика хронотипа");

        } finally {

            System.setOut(originalOut);
        }
    }
}