package ru.yandex.practicum.sleeptracker;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChronotypeCalculator implements Function<List<SleepingSession>, SleepAnalysisResult> {
    private static final LocalTime OWL_SLEEP_LIMIT = LocalTime.of(23, 0);
    private static final LocalTime OWL_WAKE_LIMIT = LocalTime.of(9, 0);
    private static final LocalTime LARK_SLEEP_LIMIT = LocalTime.of(22, 0);
    private static final LocalTime LARK_WAKE_LIMIT = LocalTime.of(7, 0);
    private static final double MIN_SLEEP_DURATION = 1.0;
    private static final LocalTime MIN_NIGHT_START = LocalTime.of(18, 0);

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions == null || sessions.isEmpty()) {
            return new SleepAnalysisResult("Хронотип пользователя", "Недостаточно данных");
        }

        Map<String, Long> counts = sessions.stream()
                .filter(session -> session.getDurationHours() >= MIN_SLEEP_DURATION)
                .filter(session -> !session.getStartTime().isBefore(MIN_NIGHT_START))
                .map(session -> {
                    LocalTime start = session.getStartTime();
                    LocalTime end = session.getEndTime();

                    boolean isOwl = start.isAfter(OWL_SLEEP_LIMIT) && end.isAfter(OWL_WAKE_LIMIT);
                    boolean isLark = start.isBefore(LARK_SLEEP_LIMIT) && end.isBefore(LARK_WAKE_LIMIT);

                    if (isOwl) return "Сова";
                    if (isLark) return "Жаворонок";
                    return "Голубь";
                })
                .collect(Collectors.groupingBy(
                        type -> type,
                        Collectors.counting()
                ));

        long owlCount = counts.getOrDefault("Сова", 0L);
        long larkCount = counts.getOrDefault("Жаворонок", 0L);
        long doveCount = counts.getOrDefault("Голубь", 0L);

        String resultType = determineChronotype(owlCount, larkCount, doveCount);

        return new SleepAnalysisResult("Хронотип пользователя", resultType);
    }

    private String determineChronotype(long owlCount, long larkCount, long doveCount) {
        long maxCount = Math.max(owlCount, Math.max(larkCount, doveCount));

        if (doveCount >= maxCount) {
            return "Голубь";
        }

        if (owlCount == maxCount && owlCount > larkCount) {
            return "Сова";
        }

        if (larkCount == maxCount && larkCount > owlCount) {
            return "Жаворонок";
        }

        return "Голубь";
    }
}