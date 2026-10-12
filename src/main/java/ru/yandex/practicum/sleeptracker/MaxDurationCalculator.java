package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.OptionalInt;
import java.util.function.Function;

public class MaxDurationCalculator implements Function<List<SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions == null || sessions.isEmpty()) {
            return new SleepAnalysisResult("Максимальная длительность сна", -1);
        }

        OptionalInt maxOpt = sessions.stream()
                .mapToInt(s -> (int) Math.round(s.getDurationHours() * 60))
                .max();

        int maxMinutes = maxOpt.getAsInt();

        return new SleepAnalysisResult("Максимальная длительность сна", maxMinutes);
    }
}