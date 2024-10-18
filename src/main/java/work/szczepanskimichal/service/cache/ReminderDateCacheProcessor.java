package work.szczepanskimichal.service.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.mapper.ReminderDateMapper;
import work.szczepanskimichal.model.reminder.date.ReminderDateCache;
import work.szczepanskimichal.service.PersonService;
import work.szczepanskimichal.service.UserService;
import work.szczepanskimichal.service.notification.NotificationService;
import work.szczepanskimichal.service.reminder.ReminderDateService;
import work.szczepanskimichal.service.reminder.ReminderRecurrenceService;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReminderDateCacheProcessor {

    private final ReminderDateCacheService reminderDateCacheService;
    private final ReminderDateService reminderDateService;
    private final ReminderRecurrenceService reminderRecurrenceService;
    private final PersonService personService;
    private final UserService userService;
    private final NotificationService notificationService;
    private final ReminderDateMapper reminderDateMapper;

    public void cacheReminderDatesToReminderDateCachesForNext24h() {
        reminderDateCacheService.clearCache();

        var reminderDates = reminderDateService.getReminderDatesForNext24h();
        var reminderDateCaches = reminderDates.stream()
                .map(reminderDateMapper::toCache)
                .collect(Collectors.toSet());
        reminderDateCacheService.addReminderDateCaches(reminderDateCaches);
        log.info("Reminder dates cached successfully: {}", reminderDateCaches);
    }

    public void processReminderDateCache(ReminderDateCache reminderDateCache) {
        log.info("processing reminder date cache: {}", reminderDateCache);
        var person = personService.getPersonWithOccasionsAndRemindersByReminderDateId(reminderDateCache.getId());
        var userCommsDto = userService.getUserComms(person.getOwner());
        notificationService.sendReminderMessage(userCommsDto.getEmail(), person);
        reminderDateService.deleteReminderDate(reminderDateCache.getId());
        reminderRecurrenceService.manageReminderRecurrence(reminderDateCache.getReminderId(), reminderDateCache.getDate());
        reminderDateCacheService.removeReminderDateFromCache(reminderDateCache.getId());
        log.info("successfully processed reminder date cache: {} for person: {}", reminderDateCache, person);
    }

}
