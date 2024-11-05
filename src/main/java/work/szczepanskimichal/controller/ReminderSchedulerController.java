package work.szczepanskimichal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.szczepanskimichal.scheduler.ReminderScheduler;

@RestController
@RequestMapping("/v1/present/reminderscheduler")
@RequiredArgsConstructor
public class ReminderSchedulerController {

    private final ReminderScheduler reminderScheduler;

    @GetMapping("/getReminderDateCachesForNext24h")
    public void getReminderDateCachesForNext24h() {
        reminderScheduler.getReminderDateCachesForNext24h();
    }

    @GetMapping("/checkUpcomingReminders")
    public void checkUpcomingReminders() {
        reminderScheduler.checkUpcomingReminders();
    }

}
