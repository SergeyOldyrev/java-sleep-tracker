package ru.yandex.practicum.sleeptracker.calculator;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
import java.util.OptionalInt;
import java.util.function.Function;

public class MinDurationCalculator implements Function<List<SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {

        if (sessions == null || sessions.isEmpty()) {
            return new SleepAnalysisResult("Минимальная длительность сна", -1);
        }

        OptionalInt minOpt = sessions.stream()
                .mapToInt(s -> (int) Math.round(s.getDurationHours() * 60))
                .min();

        int minMinutes = minOpt.getAsInt();

        return new SleepAnalysisResult("Минимальная длительность сна", minMinutes);
    }
}