package ru.yandex.practicum.sleeptracker.calculator;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

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

        Map<Chronotype, Long> counts = sessions.stream()
                .filter(session -> session.getDurationHours() >= MIN_SLEEP_DURATION)
                .filter(session -> !session.getStartTime().isBefore(MIN_NIGHT_START))
                .map(session -> {
                    LocalTime start = session.getStartTime();
                    LocalTime end = session.getEndTime();

                    boolean isOwl = start.isAfter(OWL_SLEEP_LIMIT) && end.isAfter(OWL_WAKE_LIMIT);
                    boolean isLark = start.isBefore(LARK_SLEEP_LIMIT) && end.isBefore(LARK_WAKE_LIMIT);

                    if (isOwl) return Chronotype.OWL;
                    if (isLark) return Chronotype.LARK;
                    return Chronotype.DOVE;
                })
                .collect(Collectors.groupingBy(
                        type -> type,
                        Collectors.counting()
                ));

        long owlCount = counts.getOrDefault(Chronotype.OWL, 0L);
        long larkCount = counts.getOrDefault(Chronotype.LARK, 0L);
        long doveCount = counts.getOrDefault(Chronotype.DOVE, 0L);

        String resultType;
        long maxCount = Math.max(owlCount, Math.max(larkCount, doveCount));

        boolean owlHasMax = (owlCount == maxCount);
        boolean larkHasMax = (larkCount == maxCount);
        boolean doveHasMax = (doveCount == maxCount);

        if (doveHasMax) {
            resultType = Chronotype.DOVE.toString();
        } else if (owlHasMax && !larkHasMax) {
            resultType = Chronotype.OWL.toString();
        } else if (!owlHasMax) {
            resultType = Chronotype.LARK.toString();
        } else {
            resultType = Chronotype.DOVE.toString();
        }

        return new SleepAnalysisResult("Хронотип пользователя", resultType);
    }
}