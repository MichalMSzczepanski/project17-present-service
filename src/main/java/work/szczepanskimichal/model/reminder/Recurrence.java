package work.szczepanskimichal.model.reminder;

import java.time.LocalDateTime;

public enum Recurrence {

    NON(reminder -> null),
    DAILY(dateTime -> dateTime.plusDays(1)),
    WEEKLY(dateTime -> dateTime.plusWeeks(1)),
    MONTHLY(dateTime -> dateTime.plusMonths(1)),
    ANNUALLY(dateTime -> dateTime.plusYears(1));

    private final RecurrenceStrategy strategy;

    Recurrence(RecurrenceStrategy strategy) {
        this.strategy = strategy;
    }

    public LocalDateTime applyStrategy(LocalDateTime dateTime) {
        return strategy.calculateNextReminder(dateTime);
    }
}
