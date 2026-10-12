package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class BadQualitySessionsCalculator implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions == null || sessions.isEmpty()) {
            return new SleepAnalysisResult("Количество сессий с плохим качеством сна", 0);
        }

        long countBad = sessions.stream()
                .filter(session -> {
                    String status = session.getStatus();
                    return status != null && "BAD".equalsIgnoreCase(status.trim());
                })
                .count();

        return new SleepAnalysisResult("Количество сессий с плохим качеством сна", (int) countBad);
    }
}