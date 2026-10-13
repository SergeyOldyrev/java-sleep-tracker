package ru.yandex.practicum.sleeptracker.calculator;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
import java.util.OptionalDouble;
import java.util.function.Function;

public class AverageDurationCalculator implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions == null || sessions.isEmpty()) {
            return new SleepAnalysisResult("Средняя длительность сна", -1);
        }

        OptionalDouble averageOpt = sessions.stream()
                // Считаем сразу в минутах, как ты и хотел
                .mapToDouble(s -> s.getDurationHours() * 60)
                .average();

        double avgMinutesDouble = averageOpt.orElse(0.0);

        int avgMinutes = (int) Math.round(avgMinutesDouble);

        return new SleepAnalysisResult("Средняя длительность сна", avgMinutes);
    }
}