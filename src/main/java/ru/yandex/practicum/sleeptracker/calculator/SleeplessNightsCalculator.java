package ru.yandex.practicum.sleeptracker.calculator;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
import java.util.function.Function;

public class SleeplessNightsCalculator implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private static final double SLEEPLESS_THRESHOLD_HOURS = 1.0;

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions == null || sessions.isEmpty()) {
            return new SleepAnalysisResult("Количество бессонных ночей (сон < 1 часа)", 0);
        }

        long countSleepless = sessions.stream()
                .filter(session -> session.getDurationHours() > 0 && session.getDurationHours() < SLEEPLESS_THRESHOLD_HOURS)
                .count();

        return new SleepAnalysisResult("Количество бессонных ночей (сон < 1 часа)", (int) countSleepless);
    }
}