package work.szczepanskimichal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.szczepanskimichal.model.reminder.ReminderCreateDto;
import work.szczepanskimichal.scheduler.ReminderScheduler;

@RestController()
@RequestMapping("/v1/present/reminderdatecache")
@RequiredArgsConstructor
public class ReminderDateCacheController {

    private final ReminderScheduler reminderScheduler;

    @PostMapping("/getReminderDateCachesForNext24h")
    public ResponseEntity<Void> getReminderDateCachesForNext24h(@RequestBody ReminderCreateDto reminderCreateDto) {
        reminderScheduler.getReminderDateCachesForNext24h();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/checkUpcomingReminders")
    public ResponseEntity<Void> checkUpcomingReminders(@RequestBody ReminderCreateDto reminderCreateDto) {
        reminderScheduler.checkUpcomingReminders();
        return ResponseEntity.ok().build();
    }

}
