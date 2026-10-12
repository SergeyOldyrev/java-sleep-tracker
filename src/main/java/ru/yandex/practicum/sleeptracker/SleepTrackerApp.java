package ru.yandex.practicum.sleeptracker;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


public class SleepTrackerApp {
    private final List<Function<List<SleepingSession>, SleepAnalysisResult>> metrics;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    public static void main(String[] args) {
        System.out.println(" Запуск Sleep Tracker Analyzer...");

        InputStream inputStream = SleepTrackerApp.class.getResourceAsStream("/sleep_log.txt");

        List<SleepingSession> sessions;

        if (inputStream == null) {
            System.out.println(" Ошибка: Файл 'sleep_log.txt' не найден в папке resources!");
            sessions = List.of();
        } else {

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

                sessions = reader.lines().filter(line -> !line.trim().isEmpty()).map(SleepTrackerApp::parseLine).collect(Collectors.toList());

            } catch (Exception e) {
                System.out.println("Произошла ошибка при чтении файла: " + e.getMessage());
                sessions = List.of();
            }
        }

        SleepTrackerApp tracker = new SleepTrackerApp();
        tracker.runAnalysis(sessions);
    }

    private static SleepingSession parseLine(String line) {
        String[] parts = line.split(";");

        if (parts.length < 3) {
            throw new IllegalArgumentException("Неверная строка лога (недостаточно данных): " + line);
        }

        LocalDateTime start = LocalDateTime.parse(parts[0].trim(), FORMATTER);
        LocalDateTime end = LocalDateTime.parse(parts[1].trim(), FORMATTER);
        String status = parts[2].trim();

        Duration duration = Duration.between(start, end);

        if (end.toLocalTime().isBefore(start.toLocalTime())) {
            duration = duration.plus(Duration.ofHours(24));
        }

        double durationHours = duration.toMinutes() / 60.0;

        return new SleepingSession(
                start.toLocalDate(),
                start.toLocalTime(),
                end.toLocalTime(),
                durationHours,
                status
        );
    }

    public SleepTrackerApp() {
        metrics = new ArrayList<>();
        metrics.add(new MinDurationCalculator());
        metrics.add(new MaxDurationCalculator());
        metrics.add(new AverageDurationCalculator());
        metrics.add(new BadQualitySessionsCalculator());
        metrics.add(new SleeplessNightsCalculator());
        metrics.add(new ChronotypeCalculator());
    }

    public void runAnalysis(List<SleepingSession> sessions) {
        System.out.println("--- Запуск анализа всех метрик ---");
        if (sessions == null || sessions.isEmpty()) {
            System.out.println("Нет данных для анализа (файл пуст или не прочитан).");
            return;
        }


        for (Function<List<SleepingSession>, SleepAnalysisResult> metric : metrics) {
            SleepAnalysisResult result = metric.apply(sessions);
            System.out.println(result);
        }
        System.out.println("--- Анализ завершён ---");
    }
}